package br.com.projeto.appointmentbook.models.integration;

import br.com.projeto.appointmentbook.models.Appointment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_USERS_PARTIAL")
public class User implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private UUID userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    private String userStatus;

    private String name;

    private String email;

    private String phoneNumber;

    private String vote;

    private String actionType;

    @ManyToMany(mappedBy = "users",fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Appointment> appointments;
}
