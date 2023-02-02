package com.parentapp.parent_assist.children;

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
@RequestMapping(value = "/api/childrens", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChildrenController {

    private final ChildrenService childrenService;

    public ChildrenController(final ChildrenService childrenService) {
        this.childrenService = childrenService;
    }

    @GetMapping
    public ResponseEntity<List<ChildrenDTO>> getAllChildrens() {
        return ResponseEntity.ok(childrenService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChildrenDTO> getChildren(@PathVariable final String id) {
        return ResponseEntity.ok(childrenService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createChildren(@RequestBody @Valid final ChildrenDTO childrenDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && childrenDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && childrenService.idExists(childrenDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.children.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        childrenService.create(childrenDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateChildren(@PathVariable final String id,
            @RequestBody @Valid final ChildrenDTO childrenDTO) {
        childrenService.update(id, childrenDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteChildren(@PathVariable final String id) {
        childrenService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
