package com.parentapp.parent_assist.test;

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
@RequestMapping(value = "/api/tests", produces = MediaType.APPLICATION_JSON_VALUE)
public class TestController {

    private final TestService testService;

    public TestController(final TestService testService) {
        this.testService = testService;
    }

    @GetMapping
    public ResponseEntity<List<TestDTO>> getAllTests() {
        return ResponseEntity.ok(testService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTest(@PathVariable final String id) {
        return ResponseEntity.ok(testService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createTest(@RequestBody @Valid final TestDTO testDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && testDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && testService.idExists(testDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.test.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        testService.create(testDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTest(@PathVariable final String id,
            @RequestBody @Valid final TestDTO testDTO) {
        testService.update(id, testDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteTest(@PathVariable final String id) {
        testService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
