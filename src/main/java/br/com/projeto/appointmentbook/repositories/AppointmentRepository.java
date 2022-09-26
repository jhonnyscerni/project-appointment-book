package br.com.projeto.appointmentbook.repositories;

import br.com.projeto.appointmentbook.models.Appointment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID>, JpaSpecificationExecutor<Appointment> {

    @Query(value="select case when count(tcu) > 0 THEN true ELSE false END FROM tb_appointment_users tcu WHERE tcu.appointmentId= :appointmentId and tcu.user_id= :userId",nativeQuery = true)
    boolean existsByAppointmentAndUser(@Param("courseId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    @Query(value="insert into tb_appointment_users values (:appointmentId,:userId);",nativeQuery = true)
    void saveAppointmentUser(@Param("appointmentId") UUID courseId, @Param("userId") UUID userId);

    @Modifying
    @Query(value="delete from tb_appointment_users where appointmentId= :appointmentId",nativeQuery = true)
    void deleteAppointmentUserByCourse(@Param("appointmentId") UUID courseId);

    @Modifying
    @Query(value="delete from tb_appointment_users where user_id= :userId",nativeQuery = true)
    void deleteAppointmentUserByUser(@Param("userId") UUID userId);
}
