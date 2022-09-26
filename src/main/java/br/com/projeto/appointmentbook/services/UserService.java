package br.com.projeto.appointmentbook.services;

import br.com.projeto.appointmentbook.models.integration.User;
import br.com.projeto.appointmentbook.repositories.UserRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.deleteById(user.getUserId());
    }

    public Optional<User> findById(UUID uuid) {
        return userRepository.findById(uuid);
    }
}
