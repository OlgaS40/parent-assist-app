package com.parentapp.question;

import com.parentapp.test.Test;
import com.parentapp.test.TestRepository;
import com.parentapp.util.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Transactional
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;

    public QuestionService(final QuestionRepository questionRepository,
                           final TestRepository testRepository) {
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
    }

    public List<QuestionDTO> findAll() {
        final List<Question> questions = questionRepository.findAll(Sort.by("id"));
        return questions.stream()
                .map((question) -> mapToDTO(question, new QuestionDTO()))
                .collect(Collectors.toList());
    }

    public QuestionDTO get(final String id) {
        return questionRepository.findById(id)
                .map(question -> mapToDTO(question, new QuestionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final QuestionDTO questionDTO) {
        final Question question = new Question();
        mapToEntity(questionDTO, question);
        question.setId(questionDTO.getId());
        return questionRepository.save(question).getId();
    }

    public void update(final String id, final QuestionDTO questionDTO) {
        final Question question = questionRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(questionDTO, question);
        questionRepository.save(question);
    }

    public void delete(final String id) {
        questionRepository.deleteById(id);
    }

    private QuestionDTO mapToDTO(final Question question, final QuestionDTO questionDTO) {
        questionDTO.setId(question.getId());
        questionDTO.setName(question.getName());
        questionDTO.setDescription(question.getDescription());
        questionDTO.setAgeUnit(question.getAgeUnit());
        questionDTO.setAgeFrom(question.getAgeFrom());
        questionDTO.setAgeTo(question.getAgeTo());
        questionDTO.setAnswer(question.getAnswer());
        questionDTO.setOption1(question.getOption1());
        questionDTO.setOption2(question.getOption2());
        questionDTO.setOption3(question.getOption3());
        questionDTO.setOption4(question.getOption4());
        questionDTO.setOption5(question.getOption5());
        questionDTO.setTestQuestions(question.getQuestionTests() == null ? null : question.getQuestionTests().stream()
                .map(Test::getId)
                .collect(Collectors.toList()));
        return questionDTO;
    }

    private Question mapToEntity(final QuestionDTO questionDTO, final Question question) {
        question.setName(questionDTO.getName());
        question.setDescription(questionDTO.getDescription());
        question.setAgeUnit(questionDTO.getAgeUnit());
        question.setAgeFrom(questionDTO.getAgeFrom());
        question.setAgeTo(questionDTO.getAgeTo());
        question.setAnswer(questionDTO.getAnswer());
        question.setOption1(questionDTO.getOption1());
        question.setOption2(questionDTO.getOption2());
        question.setOption3(questionDTO.getOption3());
        question.setOption4(questionDTO.getOption4());
        question.setOption5(questionDTO.getOption5());
        final List<Test> testQuestions = testRepository.findAllById(
                questionDTO.getTestQuestions() == null ? Collections.emptyList() : questionDTO.getTestQuestions());
        if (testQuestions.size() != (questionDTO.getTestQuestions() == null ? 0 : questionDTO.getTestQuestions().size())) {
            throw new NotFoundException("one of testQuestions not found");
        }
        question.setQuestionTests(new HashSet<>(testQuestions));
        return question;
    }

    public boolean idExists(final String id) {
        return questionRepository.existsByIdIgnoreCase(id);
    }

}
