package com.parentapp.parent_assist.questions;

import com.parentapp.parent_assist.test.Test;
import com.parentapp.parent_assist.test.TestRepository;
import com.parentapp.parent_assist.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class QuestionsService {

    private final QuestionsRepository questionsRepository;
    private final TestRepository testRepository;

    public QuestionsService(final QuestionsRepository questionsRepository,
            final TestRepository testRepository) {
        this.questionsRepository = questionsRepository;
        this.testRepository = testRepository;
    }

    public List<QuestionsDTO> findAll() {
        final List<Questions> questionss = questionsRepository.findAll(Sort.by("id"));
        return questionss.stream()
                .map((questions) -> mapToDTO(questions, new QuestionsDTO()))
                .collect(Collectors.toList());
    }

    public QuestionsDTO get(final String id) {
        return questionsRepository.findById(id)
                .map(questions -> mapToDTO(questions, new QuestionsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final QuestionsDTO questionsDTO) {
        final Questions questions = new Questions();
        mapToEntity(questionsDTO, questions);
        questions.setId(questionsDTO.getId());
        return questionsRepository.save(questions).getId();
    }

    public void update(final String id, final QuestionsDTO questionsDTO) {
        final Questions questions = questionsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(questionsDTO, questions);
        questionsRepository.save(questions);
    }

    public void delete(final String id) {
        questionsRepository.deleteById(id);
    }

    private QuestionsDTO mapToDTO(final Questions questions, final QuestionsDTO questionsDTO) {
        questionsDTO.setId(questions.getId());
        questionsDTO.setName(questions.getName());
        questionsDTO.setDescription(questions.getDescription());
        questionsDTO.setAgeUnit(questions.getAgeUnit());
        questionsDTO.setAgeFrom(questions.getAgeFrom());
        questionsDTO.setAgeTo(questions.getAgeTo());
        questionsDTO.setAnswer(questions.getAnswer());
        questionsDTO.setOption1(questions.getOption1());
        questionsDTO.setOption2(questions.getOption2());
        questionsDTO.setOption3(questions.getOption3());
        questionsDTO.setOption4(questions.getOption4());
        questionsDTO.setOption5(questions.getOption5());
        questionsDTO.setTestQuestions(questions.getTestQuestionTests() == null ? null : questions.getTestQuestionTests().stream()
                .map(test -> test.getId())
                .collect(Collectors.toList()));
        return questionsDTO;
    }

    private Questions mapToEntity(final QuestionsDTO questionsDTO, final Questions questions) {
        questions.setName(questionsDTO.getName());
        questions.setDescription(questionsDTO.getDescription());
        questions.setAgeUnit(questionsDTO.getAgeUnit());
        questions.setAgeFrom(questionsDTO.getAgeFrom());
        questions.setAgeTo(questionsDTO.getAgeTo());
        questions.setAnswer(questionsDTO.getAnswer());
        questions.setOption1(questionsDTO.getOption1());
        questions.setOption2(questionsDTO.getOption2());
        questions.setOption3(questionsDTO.getOption3());
        questions.setOption4(questionsDTO.getOption4());
        questions.setOption5(questionsDTO.getOption5());
        final List<Test> testQuestions = testRepository.findAllById(
                questionsDTO.getTestQuestions() == null ? Collections.emptyList() : questionsDTO.getTestQuestions());
        if (testQuestions.size() != (questionsDTO.getTestQuestions() == null ? 0 : questionsDTO.getTestQuestions().size())) {
            throw new NotFoundException("one of testQuestions not found");
        }
        questions.setTestQuestionTests(testQuestions.stream().collect(Collectors.toSet()));
        return questions;
    }

    public boolean idExists(final String id) {
        return questionsRepository.existsByIdIgnoreCase(id);
    }

}
