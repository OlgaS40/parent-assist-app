package com.parentapp.skill;

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
@RequestMapping(value = "/api/skills", produces = MediaType.APPLICATION_JSON_VALUE)
public class SkillController {

    private final SkillService skillService;

    public SkillController(final SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAllSkills() {
        return ResponseEntity.ok(skillService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getSkill(@PathVariable final String id) {
        return ResponseEntity.ok(skillService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createSkill(@RequestBody @Valid final SkillDTO skillDTO,
                                            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && skillDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && skillService.idExists(skillDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.skills.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        skillService.create(skillDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateSkill(@PathVariable final String id,
                                            @RequestBody @Valid final SkillDTO skillDTO) {
        skillService.update(id, skillDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteSkill(@PathVariable final String id) {
        skillService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
