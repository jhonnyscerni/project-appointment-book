package br.com.projeto.appointmentbook.repositories.specs;

import br.com.projeto.appointmentbook.filters.AppointmentFilter;
import br.com.projeto.appointmentbook.models.Appointment;
import br.com.projeto.appointmentbook.models.integration.AppointmentUser;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class AppointmentAssocSpecification implements Specification<Appointment> {

    private AppointmentFilter appointmentFilter;

    @Override
    public Predicate toPredicate(Root<Appointment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        query.distinct(true);
        Join<Appointment, AppointmentUser> appointmentUserJoin = root.join("appointmentsUsers");

        Optional.ofNullable(appointmentFilter.getUserId())
            .ifPresent(p -> predicates.add(criteriaBuilder.equal(appointmentUserJoin.get("userId"), appointmentFilter.getUserId())));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
