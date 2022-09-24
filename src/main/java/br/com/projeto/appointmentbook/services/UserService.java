package br.com.projeto.appointmentbook.services;

import br.com.projeto.appointmentbook.models.integration.User;
import br.com.projeto.appointmentbook.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
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
}
