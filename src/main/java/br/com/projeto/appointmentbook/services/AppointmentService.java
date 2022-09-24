package br.com.projeto.appointmentbook.services;


import br.com.projeto.appointmentbook.filters.AppointmentFilter;
import br.com.projeto.appointmentbook.models.Appointment;
import br.com.projeto.appointmentbook.models.exceptions.EntityInUseException;
import br.com.projeto.appointmentbook.models.exceptions.EntityNotFoundException;
import br.com.projeto.appointmentbook.repositories.AppointmentRepository;
import br.com.projeto.appointmentbook.repositories.specs.AppointmentAssocSpecification;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppointmentService {

    private static final String MSG_OBJECT_IN_USE
        = "Appointment %d cannot be removed as it is in use";
    private final AppointmentRepository appointmentRepository;

    public Appointment buscarOuFalhar(UUID id) {
        return appointmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("There is no appointment registration ", id));
    }

    public void delete(UUID id) {
        try {
            appointmentRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.warn("appointment {} not found", id);
            throw new EntityNotFoundException("appointment noit found");

        } catch (DataIntegrityViolationException e) {
            log.warn("Permission {} cannot be removed as it is in use", id);
            throw new EntityInUseException(
                String.format(MSG_OBJECT_IN_USE, id));
        }
    }


    public Page<Appointment> search(AppointmentFilter filter, Pageable pageable) {
        log.debug("GET AppointmentFilter filter received {} ", filter.toString());

        return appointmentRepository.findAll(new AppointmentAssocSpecification(filter), pageable);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Optional<Appointment> findById(UUID uuid) {
        return appointmentRepository.findById(uuid);
    }

    public List<Appointment> list() {
        return appointmentRepository.findAll();
    }

    public Appointment update(UUID id, Appointment appointment) {
        log.debug("PUT UUID roleId received {} ", id.toString());
        log.debug("PUT RoleRequest roleRequest received {} ", appointment.toString());
        Appointment app = buscarOuFalhar(id);
        app.setDateAppointment(appointment.getDateAppointment());
        app.setComments(appointment.getComments());
        app.setStart(appointment.getDateAppointment());
        app.setTitle(appointment.getTitle());
        app.setLocationService(appointment.getLocationService());
        return appointmentRepository.save(app);
    }
}
