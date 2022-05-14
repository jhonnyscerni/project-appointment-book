package br.com.projeto.appointmentbook.integration.response;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SubscriptionDto {

    @NotNull
    private UUID userId;
}