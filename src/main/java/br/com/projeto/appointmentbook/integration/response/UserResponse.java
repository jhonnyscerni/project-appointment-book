package br.com.projeto.appointmentbook.integration.response;

import java.util.UUID;
import lombok.Data;

@Data
public class UserResponse {

    private UUID id;

    private String username;

    private String password;

    private UserStatus userStatus;
}
