package com.parentapp.parent_assist.purchase;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PurchaseDTO {

    @Size(max = 255)
    private String id;

    @NotNull
    private LocalDateTime date;

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private Double amount;

    @NotNull
    @Size(max = 255)
    private String subscriptionId;

}
