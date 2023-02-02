package com.parentapp.parent_assist.skills;

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
@RequestMapping(value = "/api/skillss", produces = MediaType.APPLICATION_JSON_VALUE)
public class SkillsController {

    private final SkillsService skillsService;

    public SkillsController(final SkillsService skillsService) {
        this.skillsService = skillsService;
    }

    @GetMapping
    public ResponseEntity<List<SkillsDTO>> getAllSkillss() {
        return ResponseEntity.ok(skillsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillsDTO> getSkills(@PathVariable final String id) {
        return ResponseEntity.ok(skillsService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createSkills(@RequestBody @Valid final SkillsDTO skillsDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && skillsDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && skillsService.idExists(skillsDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.skills.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        skillsService.create(skillsDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSkills(@PathVariable final String id,
            @RequestBody @Valid final SkillsDTO skillsDTO) {
        skillsService.update(id, skillsDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSkills(@PathVariable final String id) {
        skillsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
