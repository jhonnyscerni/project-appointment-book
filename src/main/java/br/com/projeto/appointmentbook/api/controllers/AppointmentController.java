package br.com.projeto.appointmentbook.api.controllers;


import br.com.projeto.appointmentbook.api.request.AppointmentRequest;
import br.com.projeto.appointmentbook.filters.AppointmentFilter;
import br.com.projeto.appointmentbook.models.Appointment;
import br.com.projeto.appointmentbook.services.AppointmentService;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public ResponseEntity<Page<Appointment>> search(AppointmentFilter filter,
        @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable pageable,
        Authentication authentication) {
        return ResponseEntity.ok().body(appointmentService.search(filter, pageable));
    }

    @GetMapping("list")
    public ResponseEntity<List<Appointment>> list(Authentication authentication) {
        return ResponseEntity.ok().body(appointmentService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable UUID id) {
        return ResponseEntity.ok().body(appointmentService.buscarOuFalhar(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> update(@PathVariable UUID id,
        @RequestBody @Valid Appointment appointment) {
        return ResponseEntity.status(HttpStatus.OK).body(appointmentService.update(id, appointment));
    }


    @PostMapping
    public ResponseEntity<Object> saveAppointment(@RequestBody @Valid AppointmentRequest appointmentRequest){
        log.debug("POST saveAppointment AppointmentRequest received {} ", appointmentRequest.toString());

        var appointment = new Appointment();
        appointment.setDateAppointment(appointmentRequest.getDateAppointment());
        appointment.setComments(appointmentRequest.getComments());
        appointment.setStart(appointmentRequest.getDateAppointment());
        appointment.setTitle(appointmentRequest.getTitle());
        appointment.setLocationService(appointmentRequest.getLocationService());

        //Atribuindo o compromisso ao usuario
        Appointment appointmentSalved = appointmentService.save(appointment);
        appointmentService.saveSubscriptionUserInAppointment(appointmentSalved.getAppointmentId(), appointmentRequest.getUserId());


        log.debug("POST saveAppointment appointmentId saved {} ", appointmentRequest.getId());
        log.info("Appointment saved successfully appointmentId {} ", appointmentRequest.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentRequest);
    }
}
