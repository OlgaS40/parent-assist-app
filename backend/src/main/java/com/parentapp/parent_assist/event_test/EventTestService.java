package com.parentapp.parent_assist.event_test;

import com.parentapp.parent_assist.children.Children;
import com.parentapp.parent_assist.children.ChildrenRepository;
import com.parentapp.parent_assist.test.Test;
import com.parentapp.parent_assist.test.TestRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventTestService {

    private final EventTestRepository eventTestRepository;
    private final TestRepository testRepository;
    private final ChildrenRepository childrenRepository;

    public EventTestService(final EventTestRepository eventTestRepository,
            final TestRepository testRepository, final ChildrenRepository childrenRepository) {
        this.eventTestRepository = eventTestRepository;
        this.testRepository = testRepository;
        this.childrenRepository = childrenRepository;
    }

    public List<EventTestDTO> findAll() {
        final List<EventTest> eventTests = eventTestRepository.findAll(Sort.by("id"));
        return eventTests.stream()
                .map((eventTest) -> mapToDTO(eventTest, new EventTestDTO()))
                .collect(Collectors.toList());
    }

    public EventTestDTO get(final Long id) {
        return eventTestRepository.findById(id)
                .map(eventTest -> mapToDTO(eventTest, new EventTestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventTestDTO eventTestDTO) {
        final EventTest eventTest = new EventTest();
        mapToEntity(eventTestDTO, eventTest);
        return eventTestRepository.save(eventTest).getId();
    }

    public void update(final Long id, final EventTestDTO eventTestDTO) {
        final EventTest eventTest = eventTestRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventTestDTO, eventTest);
        eventTestRepository.save(eventTest);
    }

    public void delete(final Long id) {
        eventTestRepository.deleteById(id);
    }

    private EventTestDTO mapToDTO(final EventTest eventTest, final EventTestDTO eventTestDTO) {
        eventTestDTO.setId(eventTest.getId());
        eventTestDTO.setDate(eventTest.getDate());
        eventTestDTO.setOption(eventTest.getOption());
        eventTestDTO.setTestId(eventTest.getTestId() == null ? null : eventTest.getTestId().getId());
        eventTestDTO.setChildId(eventTest.getChildId() == null ? null : eventTest.getChildId().getId());
        return eventTestDTO;
    }

    private EventTest mapToEntity(final EventTestDTO eventTestDTO, final EventTest eventTest) {
        eventTest.setDate(eventTestDTO.getDate());
        eventTest.setOption(eventTestDTO.getOption());
        final Test testId = eventTestDTO.getTestId() == null ? null : testRepository.findById(eventTestDTO.getTestId())
                .orElseThrow(() -> new NotFoundException("testId not found"));
        eventTest.setTestId(testId);
        final Children childId = eventTestDTO.getChildId() == null ? null : childrenRepository.findById(eventTestDTO.getChildId())
                .orElseThrow(() -> new NotFoundException("childId not found"));
        eventTest.setChildId(childId);
        return eventTest;
    }

}
