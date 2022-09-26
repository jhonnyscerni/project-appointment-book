package br.com.projeto.appointmentbook.api.controllers;


import br.com.projeto.appointmentbook.integration.response.SubscriptionDto;
import br.com.projeto.appointmentbook.integration.response.UserStatus;
import br.com.projeto.appointmentbook.models.Appointment;
import br.com.projeto.appointmentbook.models.integration.User;
import br.com.projeto.appointmentbook.services.AppointmentService;
import br.com.projeto.appointmentbook.services.UserService;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
public class AppointmentUserController {

    private final AppointmentService appointmentService;

    private final UserService userService;

    public AppointmentUserController(AppointmentService appointmentService, UserService userService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
    }

    @PostMapping("/appointments/{appointmentId}/users/subscription")
    public ResponseEntity<Object> saveSubscriptionUserInAppointment(@PathVariable(value = "appointmentId") UUID appointmentId,
        @RequestBody @Valid SubscriptionDto subscriptionDto) {
        // ResponseEntity<UserResponse> responseUser;
        Optional<Appointment> appointmentOptional = appointmentService.findById(appointmentId);
        if (!appointmentOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Appointment Not Found.");
        }
        if (appointmentService.existsByCourseAndUser(appointmentId, subscriptionDto.getUserId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: subscription already exists!");
        }
        Optional<User> userModelOptional = userService.findById(subscriptionDto.getUserId());
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        if (userModelOptional.get().getUserStatus().equals(UserStatus.BLOCKED.toString())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User is blocked.");
        }

        appointmentService.saveSubscriptionUserInCourseAndSendNotification(appointmentOptional.get(),
            userModelOptional.get());
        return ResponseEntity.status(HttpStatus.CREATED).body("Subscription created successfully.");
    }
}
