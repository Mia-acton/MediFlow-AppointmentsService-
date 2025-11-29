package ie.atu.mediflowappservice.repository;

import ie.atu.mediflowappservice.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientUserName(String patientUserName);
    List<Appointment> findByDoctorUserName(String doctorUserName);
    List<Appointment> findByPatientUserNameAndDoctorUserName(String patientUserName, String doctorUserName);
}
