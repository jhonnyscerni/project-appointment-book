package br.com.projeto.appointmentbook.services;

import br.com.projeto.appointmentbook.models.Appointment;
import br.com.projeto.appointmentbook.models.integration.AppointmentUser;
import br.com.projeto.appointmentbook.repositories.AppointmentUserRepository;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentUserService {

    private final AppointmentUserRepository appointmentUserRepository;

    public AppointmentUser save(AppointmentUser appointmentUser) {
        return appointmentUserRepository.save(appointmentUser);
    }

    public boolean existsByAppointmentAndId(Appointment appointment, UUID userId) {
        return appointmentUserRepository.existsByAppointmentAndId(appointment, userId);
    }

    @Transactional
    public AppointmentUser saveAndSendSubscriptionUserInAppointment(AppointmentUser appointmentUser) {
        appointmentUser = appointmentUserRepository.save(appointmentUser);
        //Caso queira fazer a sincronização dos dados no microservico de authUser ele pode chamar o medoto para salvar em uma tabela do outro lado
        //authUserClient.postSubscriptionUserInAppointment(appointmentUser.getAppointment().getAppointmentId(), appointmentUser.getUserId());
        return appointmentUser;
    }
}
