package ie.atu.mediflowappservice.service;

import ie.atu.mediflowappservice.model.Appointment;
import ie.atu.mediflowappservice.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Appointment createAppointment(Appointment appointment) {
        return repository.save(appointment);
    }

    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return repository.findById(id);
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        return repository.findById(id).map(appointment -> {
            appointment.setAppointmentId(updatedAppointment.getAppointmentId());
            appointment.setVenue(updatedAppointment.getVenue());
            appointment.setDate(updatedAppointment.getDate());
            appointment.setTime(updatedAppointment.getTime());
            appointment.setDoctorUserName(updatedAppointment.getDoctorUserName());
            return repository.save(appointment);
        }).orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));
    }

    public void deleteAppointment(Long id) {
        repository.deleteById(id);
    }
}