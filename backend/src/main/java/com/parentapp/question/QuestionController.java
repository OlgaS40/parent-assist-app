package com.parentapp.question;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(final QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        return ResponseEntity.ok(questionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestion(@PathVariable final String id) {
        return ResponseEntity.ok(questionService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createQuestion(@RequestBody @Valid final QuestionDTO questionDTO,
                                               final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && questionDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && questionService.idExists(questionDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.questions.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        questionService.create(questionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateQuestion(@PathVariable final String id,
                                               @RequestBody @Valid final QuestionDTO questionDTO) {
        questionService.update(id, questionDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuestion(@PathVariable final String id) {
        questionService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
