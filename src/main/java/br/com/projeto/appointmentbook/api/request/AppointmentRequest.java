package br.com.projeto.appointmentbook.api.request;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class AppointmentRequest {

    private UUID id;

    private LocalDateTime dateAppointment;

    private String locationService;

    private String comments;

    private UUID userId;
}
