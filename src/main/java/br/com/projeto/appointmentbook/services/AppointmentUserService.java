package br.com.projeto.appointmentbook.services;

import br.com.projeto.appointmentbook.models.Appointment;
import br.com.projeto.appointmentbook.models.integration.AppointmentUser;
import br.com.projeto.appointmentbook.repositories.AppointmentUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentUserService {

    private final AppointmentService appointmentService;

    private final AppointmentUserRepository appointmentUserRepository;

    public AppointmentUser save(AppointmentUser appointmentUser) {
        return appointmentUserRepository.save(appointmentUser);
    }
}
