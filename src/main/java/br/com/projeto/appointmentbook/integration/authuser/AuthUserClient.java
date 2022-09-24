package br.com.projeto.appointmentbook.integration.authuser;

import br.com.projeto.appointmentbook.integration.response.ResponsePageDto;
import br.com.projeto.appointmentbook.integration.response.UserResponse;
import java.util.List;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Log4j2
@Component
public class AuthUserClient {

    @Value("${api.url.authuser}")
    String REQUEST_URL_AUTHUSER;
    private final RestTemplate restTemplate;

    public AuthUserClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<UserResponse> getOneUserById(UUID userId) {
        String url = REQUEST_URL_AUTHUSER + "/users/" + userId;
        return restTemplate.exchange(url, HttpMethod.GET, null, UserResponse.class);
    }
}