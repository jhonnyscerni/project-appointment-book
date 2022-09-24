package br.com.projeto.appointmentbook.repositories;

import br.com.projeto.appointmentbook.models.integration.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {

}
