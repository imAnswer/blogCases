package org.example.inventory.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CancelParam {

    @NotNull(message = "orderId is null")
    private String orderId;
}
