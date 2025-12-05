package ie.atu.mediflowappservice.controller;

import ie.atu.mediflowappservice.model.Appointment;
import ie.atu.mediflowappservice.model.AppointmentCreate;
import ie.atu.mediflowappservice.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Appointment> createAppointment(
            @RequestBody @Valid AppointmentCreate request) {

        Appointment saved = service.createAppointment(request);

        return ResponseEntity
                .created(URI.create("/appointments/" + saved.getAppointmentId()))
                .body(saved);
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> getAllAppointments() {
        return ResponseEntity.ok(service.getAllAppointments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return service.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/patient/{patientUserName}")
    public ResponseEntity<List<Appointment>> getByPatient(@PathVariable String patientUserName) {
        return ResponseEntity.ok(service.getAppointmentByPatient(patientUserName));
    }

    @GetMapping("/doctor/{doctorUserName}")
    public ResponseEntity<List<Appointment>> getByDoctor(@PathVariable String doctorUserName) {
        return ResponseEntity.ok(service.getAppointmentByDoctor(doctorUserName));
    }

    @GetMapping("/search")
    public ResponseEntity<List<Appointment>> search(
            @RequestParam String patient,
            @RequestParam String doctor
    ) {
        return ResponseEntity.ok(service.search(patient, doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(
            @PathVariable Long id,
            @RequestBody Appointment appointment
    ) {
        return ResponseEntity.ok(service.updateAppointment(id, appointment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        service.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
