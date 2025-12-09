package ie.atu.mediflowappservice.service;

import ie.atu.mediflowappservice.model.Appointment;
import ie.atu.mediflowappservice.repository.AppointmentRepository;
import ie.atu.mediflowappservice.model.AppointmentCreate;
import ie.atu.mediflowappservice.exception.AppointmentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public Appointment getAppointmentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AppointmentNotFoundException(id));
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

    public Appointment updateAppointment(Long id, AppointmentCreate request) {
        return repository.findById(id).map(existing -> {
            existing.setPatientUserName(request.getPatientUserName());
            existing.setDoctorUserName(request.getDoctorUserName());
            existing.setVenue(request.getVenue());
            existing.setDate(request.getDate());
            existing.setTime(request.getTime());

            return repository.save(existing);

        }).orElseThrow(() -> new AppointmentNotFoundException(id));
    }

    public void deleteAppointment(Long id) {

        if (!repository.existsById(id)) {
            throw new AppointmentNotFoundException(id);
        }

        repository.deleteById(id);
    }
}