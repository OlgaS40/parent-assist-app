package com.parentapp.cashin;

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
@RequestMapping(value = "/api/cashIn", produces = MediaType.APPLICATION_JSON_VALUE)
public class CashInController {

    private final CashInService cashInService;

    public CashInController(final CashInService cashInService) {
        this.cashInService = cashInService;
    }

    @GetMapping
    public ResponseEntity<List<CashInDTO>> getAllCashIn() {
        return ResponseEntity.ok(cashInService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CashInDTO> getCashIn(@PathVariable final String id) {
        return ResponseEntity.ok(cashInService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Void> createCashIn(@RequestBody @Valid final CashInDTO cashInDTO,
            final BindingResult bindingResult) throws MethodArgumentNotValidException {
        if (!bindingResult.hasFieldErrors("id") && cashInDTO.getId() == null) {
            bindingResult.rejectValue("id", "NotNull");
        }
        if (!bindingResult.hasFieldErrors("id") && cashInService.idExists(cashInDTO.getId())) {
            bindingResult.rejectValue("id", "Exists.purchase.id");
        }
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(new MethodParameter(
                    this.getClass().getDeclaredMethods()[0], -1), bindingResult);
        }
        cashInService.create(cashInDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCashIn(@PathVariable final String id,
            @RequestBody @Valid final CashInDTO cashInDTO) {
        cashInService.update(id, cashInDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCashIn(@PathVariable final String id) {
        cashInService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
