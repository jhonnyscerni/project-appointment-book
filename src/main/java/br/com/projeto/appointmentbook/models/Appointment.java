package br.com.projeto.appointmentbook.models;

import br.com.projeto.appointmentbook.models.integration.AppointmentUser;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TB_APPOINTMENT")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private UUID id;

    private LocalDateTime dateAppointment;

    private String locationService;

    private String comments;

    // Criando Integração com FullCalendar

    @Column(name = "START")
    private OffsetDateTime start;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "CLASS_NAME")
    private String className;

    //Criando Mapeamento com para integração

    @OneToMany(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Set<AppointmentUser> appointmentsUsers;

    @Column(nullable = false)
    private UUID userId;
}
