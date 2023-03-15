package com.parentapp.relationship;

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
@RequestMapping(value = "/api/relationships", produces = MediaType.APPLICATION_JSON_VALUE)
public class RelationshipController {

    private final RelationshipService relationshipService;

    public RelationshipController(final RelationshipService relationshipService) {
        this.relationshipService = relationshipService;
    }

    @GetMapping
    public ResponseEntity<List<RelationshipDTO>> getAllRelationships() {
        return ResponseEntity.ok(relationshipService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RelationshipDTO> getRelationships(@PathVariable final String id) {
        return ResponseEntity.ok(relationshipService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createRelationships(
            @RequestBody @Valid final RelationshipDTO relationshipDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && relationshipDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && relationshipService.idExists(relationshipDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.relationships.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        relationshipService.create(relationshipDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateRelationships(@PathVariable final String id,
            @RequestBody @Valid final RelationshipDTO relationshipDTO) {
        relationshipService.update(id, relationshipDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRelationships(@PathVariable final String id) {
        relationshipService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
