package br.com.projeto.appointmentbook.api.controllers;


import br.com.projeto.appointmentbook.api.request.AppointmentRequest;
import br.com.projeto.appointmentbook.filters.AppointmentFilter;
import br.com.projeto.appointmentbook.models.Appointment;
import br.com.projeto.appointmentbook.models.integration.AppointmentUser;
import br.com.projeto.appointmentbook.services.AppointmentService;
import br.com.projeto.appointmentbook.services.AppointmentUserService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@AllArgsConstructor
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    private final AppointmentUserService appointmentUserService;

    @GetMapping
    public ResponseEntity<Page<Appointment>> search(AppointmentFilter filter,
        @PageableDefault(sort = "id", direction = Direction.ASC, page = 0, size = 10) Pageable pageable) {
        return ResponseEntity.ok().body(appointmentService.search(filter, pageable));
    }

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid AppointmentRequest appointmentRequest){
        log.debug("POST saveCourse courseDto received {} ", appointmentRequest.toString());

        var appointment = new Appointment();
        BeanUtils.copyProperties(appointmentRequest, appointment);
        Appointment appointmentSalved = appointmentService.save(appointment);

        AppointmentUser appointmentUser = new AppointmentUser();
        appointmentUser.setAppointment(appointmentSalved);
        appointmentUser.setUserId(appointmentRequest.getUserId());

        appointmentUserService.save(appointmentUser);

        log.debug("POST saveCourse courseId saved {} ", appointmentRequest.getId());
        log.info("Course saved successfully courseId {} ", appointmentRequest.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentRequest);
    }
}
