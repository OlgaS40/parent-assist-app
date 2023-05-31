package com.parentapp.schedule_activity;

import com.parentapp.activity.Activity;
import com.parentapp.activity.ActivityRepository;
import com.parentapp.auth.service.UserService;
import com.parentapp.email_notify.EmailService;
import com.parentapp.users.UserDTO;
import com.parentapp.util.NotFoundException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ScheduleActivityService {
    @Value("${parentAssist.app.emailAddress}")
    private String appEmail;
    @Value("${schedule.mail.senderName}")
    private String senderName;
    @Value("${schedule.mail.subject}")
    private String mailSubject;
    @Value("${schedule.mail.content}")
    private String mailContent;
    @Value("${parentAssist.app.domain}")
    private String appUrl;
    private final ScheduleActivityRepository scheduleActivityRepository;
    private final ActivityRepository activityRepository;
    private final EmailService emailService;
    private final UserService userService;

    public ScheduleActivityService(
            final ScheduleActivityRepository scheduleActivityRepository,
            final ActivityRepository activityRepository, EmailService emailService, UserService userService) {
        this.scheduleActivityRepository = scheduleActivityRepository;
        this.activityRepository = activityRepository;
        this.emailService = emailService;
        this.userService = userService;
    }

    public List<ScheduleActivityDTO> findAll() {
        final List<ScheduleActivity> scheduleActivities = scheduleActivityRepository.findAll(Sort.by("id"));
        return scheduleActivities.stream()
                .map((scheduleActivity) -> mapToDTO(scheduleActivity, new ScheduleActivityDTO()))
                .collect(Collectors.toList());
    }

    public ScheduleActivityDTO get(final String id) {
        return scheduleActivityRepository.findById(id)
                .map(scheduleActivity -> mapToDTO(scheduleActivity, new ScheduleActivityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ScheduleActivityDTO scheduleActivityDTO) {
        final ScheduleActivity scheduleActivity = new ScheduleActivity();
        mapToEntity(scheduleActivityDTO, scheduleActivity);
        scheduleActivity.setId(scheduleActivityDTO.getId());
        return scheduleActivityRepository.save(scheduleActivity).getId();
    }
    public void notifyCreatedActivities() {
        List<UserDTO> users = userService.findAllAsDTO()
                .stream()
                .filter(UserDTO::isEnabled)
                .toList();

        users.forEach(user -> {
            try {
                sendNotificationEmail(user, appUrl);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void update(final String id, final ScheduleActivityDTO scheduleActivityDTO) {
        final ScheduleActivity scheduleActivity = scheduleActivityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(scheduleActivityDTO, scheduleActivity);
        scheduleActivityRepository.save(scheduleActivity);
    }

    public void delete(final String id) {
        scheduleActivityRepository.deleteById(id);
    }

    private ScheduleActivityDTO mapToDTO(final ScheduleActivity scheduleActivity,
                                         final ScheduleActivityDTO scheduleActivityDTO) {
        scheduleActivityDTO.setId(scheduleActivity.getId());
        scheduleActivityDTO.setPeriodFrom(scheduleActivity.getPeriodFrom());
        scheduleActivityDTO.setPeriodTo(scheduleActivity.getPeriodTo());
        scheduleActivityDTO.setActivityId(scheduleActivity.getActivity() == null ? null : scheduleActivity.getActivity().getId());
        return scheduleActivityDTO;
    }

    private ScheduleActivity mapToEntity(final ScheduleActivityDTO scheduleActivityDTO,
                                         final ScheduleActivity scheduleActivity) {
        scheduleActivity.setPeriodFrom(scheduleActivityDTO.getPeriodFrom());
        scheduleActivity.setPeriodTo(scheduleActivityDTO.getPeriodTo());
        final Activity activityId = scheduleActivityDTO.getActivityId() == null ? null : activityRepository.findById(scheduleActivityDTO.getActivityId())
                .orElseThrow(() -> new NotFoundException("activityId not found"));
        scheduleActivity.setActivity(activityId);
        return scheduleActivity;
    }

    public boolean idExists(final String id) {
        return scheduleActivityRepository.existsByIdIgnoreCase(id);
    }

    private void sendNotificationEmail(UserDTO user, String siteURL) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailService.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(appEmail, senderName);
        helper.setTo(user.getEmail());
        helper.setSubject(mailSubject);

        mailContent = mailContent.replace("[[name]]", user.getUsername());
        String appURL = siteURL + "/auth";

        mailContent = mailContent.replace("[[URL]]", appURL);

        helper.setText(mailContent, true);

        emailService.sendEmail(message);
    }
}
