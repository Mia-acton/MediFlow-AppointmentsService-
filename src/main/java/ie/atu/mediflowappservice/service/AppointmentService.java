package ie.atu.mediflowappservice.service;

import ie.atu.mediflowappservice.model.Appointment;
import ie.atu.mediflowappservice.repository.AppointmentRepository;
import ie.atu.mediflowappservice.model.AppointmentCreate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Appointment createAppointment(AppointmentCreate request) {
        Appointment appointment = new Appointment();

        appointment.setPatientUserName(request.getPatientUserName());
        appointment.setVenue(request.getVenue());
        appointment.setDate(request.getDate());
        appointment.setTime(request.getTime());
        appointment.setDoctorUserName(request.getDoctorUserName());

        return repository.save(appointment);
    }


    public List<Appointment> getAllAppointments() {
        return repository.findAll();
    }

    public Optional<Appointment> getAppointmentById(Long id) {
        return repository.findById(id);
    }

    public List<Appointment> getAppointmentByPatient(String patientUserName) {
        return repository.findByPatientUserName(patientUserName);
    }

    public List<Appointment> getAppointmentByDoctor(String doctorUserName) {
        return repository.findByDoctorUserName(doctorUserName);
    }

    public List<Appointment> search(String patientUserName, String doctorUserName) {
        return repository.findByPatientUserNameAndDoctorUserName(patientUserName, doctorUserName);
    }

    public Appointment updateAppointment(Long id, Appointment updated) {
        return repository.findById(id).map(existing -> {
            existing.setPatientUserName(updated.getPatientUserName());
            existing.setDoctorUserName(updated.getDoctorUserName());
            existing.setVenue(updated.getVenue());
            existing.setDate(updated.getDate());
            existing.setTime(updated.getTime());

            return repository.save(existing);

        }).orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));
    }

    public void deleteAppointment(Long id) {
        repository.deleteById(id);
    }
}