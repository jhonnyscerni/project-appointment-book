package br.com.projeto.appointmentbook.integration.response;

import br.com.projeto.appointmentbook.models.integration.User;
import java.util.UUID;
import lombok.Data;

@Data
public class UserEventResponse {

    private UUID userId;

    private String username;

    private String name;

    private String email;

    private String phoneNumber;

    private String vote;

    private String userStatus;

    private String actionType;


    public User convertToUser(){
        User user = new User();
        user.setUserId(this.userId);
        user.setUsername(this.getUsername());
        user.setName(this.getName());
        user.setEmail(this.getEmail());
        user.setPhoneNumber(this.getPhoneNumber());
        user.setVote(this.getVote());
        user.setUserStatus(this.getUserStatus());
        return user;
    }

}
