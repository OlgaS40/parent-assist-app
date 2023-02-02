package com.parentapp.parent_assist.parents;

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
@RequestMapping(value = "/api/parentss", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParentsController {

    private final ParentsService parentsService;

    public ParentsController(final ParentsService parentsService) {
        this.parentsService = parentsService;
    }

    @GetMapping
    public ResponseEntity<List<ParentsDTO>> getAllParentss() {
        return ResponseEntity.ok(parentsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParentsDTO> getParents(@PathVariable final String id) {
        return ResponseEntity.ok(parentsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createParents(@RequestBody @Valid final ParentsDTO parentsDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && parentsDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && parentsService.idExists(parentsDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.parents.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        parentsService.create(parentsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateParents(@PathVariable final String id,
            @RequestBody @Valid final ParentsDTO parentsDTO) {
        parentsService.update(id, parentsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteParents(@PathVariable final String id) {
        parentsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
