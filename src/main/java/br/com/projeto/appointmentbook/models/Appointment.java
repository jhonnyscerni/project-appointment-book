package br.com.projeto.appointmentbook.models;

import br.com.projeto.appointmentbook.models.integration.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "TB_APPOINTMENT")
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private OffsetDateTime dateAppointment;

    private String locationService;

    private String comments;

    // Criando Integração com FullCalendar

    @Column(name = "START")
    private OffsetDateTime start = dateAppointment;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CLASS_NAME")
    private String className;

    private String groupId;

    //Criando Mapeamento com para integração
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(    name = "TB_APPOINTMENT_USERS",
        joinColumns = @JoinColumn(name = "appointment_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users;

}
