package com.parentapp.event_test;

import com.parentapp.child.Child;
import com.parentapp.child.ChildRepository;
import com.parentapp.test.Test;
import com.parentapp.test.TestRepository;
import com.parentapp.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class EventTestService {

    private final EventTestRepository eventTestRepository;
    private final TestRepository testRepository;
    private final ChildRepository childRepository;

    public EventTestService(final EventTestRepository eventTestRepository,
            final TestRepository testRepository, final ChildRepository childRepository) {
        this.eventTestRepository = eventTestRepository;
        this.testRepository = testRepository;
        this.childRepository = childRepository;
    }

    public List<EventTestDTO> findAll() {
        final List<EventTest> eventTests = eventTestRepository.findAll(Sort.by("id"));
        return eventTests.stream()
                .map((eventTest) -> mapToDTO(eventTest, new EventTestDTO()))
                .collect(Collectors.toList());
    }

    public EventTestDTO get(final String id) {
        return eventTestRepository.findById(id)
                .map(eventTest -> mapToDTO(eventTest, new EventTestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final EventTestDTO eventTestDTO) {
        final EventTest eventTest = new EventTest();
        mapToEntity(eventTestDTO, eventTest);
        return eventTestRepository.save(eventTest).getId();
    }

    public void update(final String id, final EventTestDTO eventTestDTO) {
        final EventTest eventTest = eventTestRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventTestDTO, eventTest);
        eventTestRepository.save(eventTest);
    }

    public void delete(final String id) {
        eventTestRepository.deleteById(id);
    }

    private EventTestDTO mapToDTO(final EventTest eventTest, final EventTestDTO eventTestDTO) {
        eventTestDTO.setId(eventTest.getId());
        eventTestDTO.setDate(eventTest.getDate());
        eventTestDTO.setOption(eventTest.getOption());
        eventTestDTO.setTestId(eventTest.getTest() == null ? null : eventTest.getTest().getId());
        eventTestDTO.setChildId(eventTest.getChild() == null ? null : eventTest.getChild().getId());
        return eventTestDTO;
    }

    private EventTest mapToEntity(final EventTestDTO eventTestDTO, final EventTest eventTest) {
        eventTest.setDate(eventTestDTO.getDate());
        eventTest.setOption(eventTestDTO.getOption());
        final Test testId = eventTestDTO.getTestId() == null ? null : testRepository.findById(eventTestDTO.getTestId())
                .orElseThrow(() -> new NotFoundException("testId not found"));
        eventTest.setTest(testId);
        final Child childId = eventTestDTO.getChildId() == null ? null : childRepository.findById(eventTestDTO.getChildId())
                .orElseThrow(() -> new NotFoundException("childId not found"));
        eventTest.setChild(childId);
        return eventTest;
    }

}
