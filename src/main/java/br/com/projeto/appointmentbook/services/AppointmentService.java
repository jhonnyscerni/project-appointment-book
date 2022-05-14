package br.com.projeto.appointmentbook.services;


import br.com.projeto.appointmentbook.filters.AppointmentFilter;
import br.com.projeto.appointmentbook.models.Appointment;
import br.com.projeto.appointmentbook.models.exceptions.EntityInUseException;
import br.com.projeto.appointmentbook.models.exceptions.EntityNotFoundException;
import br.com.projeto.appointmentbook.repositories.AppointmentRepository;
import br.com.projeto.appointmentbook.repositories.specs.AppointmentSpecification;
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

//    public Consulta salvar(Consulta consulta) {
//
//        Usuario usuario = usuarioService.buscarOuFalhar(projetoSecurity.getUsuarioId());
//        Profissional profissional = new Profissional();
//        Paciente paciente = new Paciente();
//        Clinica clinica = new Clinica();
//
//        if (usuario.getDiscriminatorValue().equals("User")) {
//            profissional = profissionalService.buscarOuFalhar(usuario.getId());
//            if (profissional.getClinicaId() != null) {
//                clinica = clinicaService.buscarOuFalhar(profissional.getClinicaId());
//            }
//            paciente = pacienteService.buscarOuFalhar(consulta.getPaciente().getId());
//            consulta.setTitle(" - Paciente: " + paciente.getNome());
//        } else if (usuario.getDiscriminatorValue().equals("Patient")) {
//            profissional = profissionalService.buscarOuFalhar(consulta.getProfissional().getId());
//            paciente = pacienteService.buscarOuFalhar(usuario.getId());
//            if (paciente.getClinicaId() != null) {
//                clinica = clinicaService.buscarOuFalhar(paciente.getClinicaId());
//            }
//            consulta.setTitle("Profissional: " + profissional.getNome() + " - Paciente: " + paciente.getNome());
//        } else if (usuario.getDiscriminatorValue().equals("Clinic")) {
//            clinica = clinicaService.buscarOuFalhar(usuario.getId());
//            profissional = profissionalService.buscarOuFalhar(consulta.getProfissional().getId());
//            paciente = pacienteService.buscarOuFalhar(consulta.getPaciente().getId());
//        } else {
//            profissional = profissionalService.buscarOuFalhar(consulta.getProfissional().getId());
//            paciente = pacienteService.buscarOuFalhar(consulta.getPaciente().getId());
//            clinica = clinicaService.buscarOuFalhar(consulta.getClinica().getId());
//            consulta.setTitle("Profissional: " + profissional.getNome() + " - Paciente: " + paciente.getNome());
//        }
//
//        consulta.setStart(consulta.getDataHora());
//
//        consulta.setProfissional(profissional);
//        consulta.setPaciente(paciente);
//        consulta.setClinica(clinica);
//        return consultaRepository.save(consulta);
//    }

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

        return appointmentRepository.findAll(new AppointmentSpecification(filter), pageable);
    }

    public Appointment save(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }
}
