package ie.atu.mediflowappservice;

import ie.atu.mediflowappservice.exception.AppointmentNotFoundException;
import ie.atu.mediflowappservice.model.Appointment;
import ie.atu.mediflowappservice.model.AppointmentCreate;
import ie.atu.mediflowappservice.service.AppointmentService;
import ie.atu.mediflowappservice.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Test class introducing Mockito
class AppointmentServiceTest {

    @Mock
    private AppointmentRepository repo;

    @InjectMocks
    private AppointmentService service;

    private Appointment appointment;
    private AppointmentCreate appointmentCreate;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        appointmentCreate = new AppointmentCreate(
                "john_doe",
                "Clinic A",
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 30),
                "dr_smith"
        );

        appointment = new Appointment(
                1L,
                "john_doe",
                "Clinic A",
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 30),
                "dr_smith"
        );
    }

    // Create and Get
    @Test
    void createThenFindById() {
        when(repo.save(any(Appointment.class))).thenReturn(appointment);
        when(repo.findById(1L)).thenReturn(Optional.of(appointment));

        Appointment created = service.createAppointment(appointmentCreate);
        Appointment found = service.getAppointmentById(1L);

        assertNotNull(created);
        assertNotNull(found);
        assertEquals("john_doe", found.getPatientUserName());
    }

    // Get All
    @Test
    void findAllAppointments() {
        when(repo.findAll()).thenReturn(List.of(appointment));

        List<Appointment> all = service.getAllAppointments();

        assertEquals(1, all.size());
        assertEquals("Clinic A", all.get(0).getVenue());
    }

    // Update Success
    @Test
    void updateAppointmentSuccess() {
        AppointmentCreate updatedCreate = new AppointmentCreate(
                "john_doe",
                "Clinic B",
                LocalDate.now().plusDays(2),
                LocalTime.of(11, 0),
                "dr_smith"
        );

        Appointment updated = new Appointment(
                1L,
                "john_doe",
                "Clinic B",
                LocalDate.now().plusDays(2),
                LocalTime.of(11, 0),
                "dr_smith"
        );

        when(repo.findById(1L)).thenReturn(Optional.of(appointment));
        when(repo.save(any(Appointment.class))).thenReturn(updated);

        Appointment result = service.updateAppointment(1L, updatedCreate);

        assertEquals("Clinic B", result.getVenue());
        assertEquals(LocalTime.of(11, 0), result.getTime());
    }

    // Delete Success
    @Test
    void deleteAppointmentSuccess() {
        when(repo.existsById(1L)).thenReturn(true);
        doNothing().when(repo).deleteById(1L);

        service.deleteAppointment(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    // Get Not Found - throws exception
    @Test
    void findByIdNotFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(AppointmentNotFoundException.class, () -> {
            service.getAppointmentById(2L);
        });
    }

    // Delete Not Found - throws exception
    @Test
    void deleteAppointmentNotFound() {
        when(repo.existsById(1L)).thenReturn(false);

        assertThrows(AppointmentNotFoundException.class, () -> {
            service.deleteAppointment(1L);
        });
    }
}
