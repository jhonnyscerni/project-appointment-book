package br.com.projeto.appointmentbook.api.controllers;


import br.com.projeto.appointmentbook.integration.authuser.AuthUserClient;
import br.com.projeto.appointmentbook.integration.response.SubscriptionDto;
import br.com.projeto.appointmentbook.integration.response.UserResponse;
import br.com.projeto.appointmentbook.integration.response.UserStatus;
import br.com.projeto.appointmentbook.models.Appointment;
import br.com.projeto.appointmentbook.models.integration.AppointmentUser;
import br.com.projeto.appointmentbook.services.AppointmentService;
import br.com.projeto.appointmentbook.services.AppointmentUserService;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

@Log4j2
@RestController
public class AppointmentUserController {

    private final AppointmentUserService appointmentUserService;

    private final AppointmentService appointmentService;

    private final AuthUserClient authUserClient;

    public AppointmentUserController(AppointmentUserService appointmentUserService, AppointmentService appointmentService,
        AuthUserClient authUserClient) {
        this.appointmentUserService = appointmentUserService;
        this.appointmentService = appointmentService;
        this.authUserClient = authUserClient;
    }

    @PostMapping("/appointments/{appointmentId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInAppointment(@PathVariable(value = "appointmentId") UUID appointmentId,
        @RequestBody @Valid SubscriptionDto subscriptionDto) {
        ResponseEntity<UserResponse> responseUser;
        Optional<Appointment> appointmentOptional = appointmentService.findById(appointmentId);
        if (!appointmentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment Not Found.");
        }
        if (appointmentUserService.existsByAppointmentAndId(appointmentOptional.get(), subscriptionDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }
        try {
            responseUser = authUserClient.getOneUserById(subscriptionDto.getUserId());
            if (responseUser.getBody().getUserStatus().equals(UserStatus.BLOCKED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.");
            }
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode().equals(HttpStatus.NOT_FOUND)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }
        }
        AppointmentUser appointmentUser = appointmentUserService.saveAndSendSubscriptionUserInAppointment(
            appointmentOptional.get().convertToAppintmentUserModel(subscriptionDto.getUserId()));
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentUser);
    }
}
