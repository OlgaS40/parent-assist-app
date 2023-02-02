package com.parentapp.parent_assist.questions;

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
@RequestMapping(value = "/api/questionss", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionsController {

    private final QuestionsService questionsService;

    public QuestionsController(final QuestionsService questionsService) {
        this.questionsService = questionsService;
    }

    @GetMapping
    public ResponseEntity<List<QuestionsDTO>> getAllQuestionss() {
        return ResponseEntity.ok(questionsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionsDTO> getQuestions(@PathVariable final String id) {
        return ResponseEntity.ok(questionsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createQuestions(@RequestBody @Valid final QuestionsDTO questionsDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && questionsDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && questionsService.idExists(questionsDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.questions.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        questionsService.create(questionsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateQuestions(@PathVariable final String id,
            @RequestBody @Valid final QuestionsDTO questionsDTO) {
        questionsService.update(id, questionsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteQuestions(@PathVariable final String id) {
        questionsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
