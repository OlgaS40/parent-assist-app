package com.parentapp.backend.test;

import com.parentapp.backend.util.NotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class TestService {

    private final TestRepository testRepository;

    public TestService(final TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<TestDTO> findAll() {
        final List<Test> tests = testRepository.findAll(Sort.by("id"));
        return tests.stream()
                .map((test) -> mapToDTO(test, new TestDTO()))
                .collect(Collectors.toList());
    }

    public TestDTO get(final String id) {
        return testRepository.findById(id)
                .map(test -> mapToDTO(test, new TestDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final TestDTO testDTO) {
        final Test test = new Test();
        mapToEntity(testDTO, test);
        test.setId(testDTO.getId());
        return testRepository.save(test).getId();
    }

    public void update(final String id, final TestDTO testDTO) {
        final Test test = testRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(testDTO, test);
        testRepository.save(test);
    }

    public void delete(final String id) {
        testRepository.deleteById(id);
    }

    private TestDTO mapToDTO(final Test test, final TestDTO testDTO) {
        testDTO.setId(test.getId());
        testDTO.setName(test.getName());
        return testDTO;
    }

    private Test mapToEntity(final TestDTO testDTO, final Test test) {
        test.setName(testDTO.getName());
        return test;
    }

    public boolean idExists(final String id) {
        return testRepository.existsByIdIgnoreCase(id);
    }

}
