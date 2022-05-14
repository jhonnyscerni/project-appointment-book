package br.com.projeto.appointmentbook.repositories;

import br.com.projeto.appointmentbook.models.integration.AppointmentUser;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentUserRepository extends JpaRepository<AppointmentUser, UUID> {

}
