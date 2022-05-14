package br.com.projeto.appointmentbook.models;

import br.com.projeto.appointmentbook.models.integration.AppointmentUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_APPOINTMENT")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime dateAppointment;

    private String locationService;

    private String comments;

    // Criando Integração com FullCalendar

    @Column(name = "START")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime start;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CLASS_NAME")
    private String className;

    //Criando Mapeamento com para integração

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Set<AppointmentUser> appointmentsUsers;

    public AppointmentUser convertToAppintmentUserModel(UUID userID) {
        return new AppointmentUser(null, this, userID);
    }

}
