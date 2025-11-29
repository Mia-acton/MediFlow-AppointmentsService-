package ie.atu.mediflowappservice;

import ie.atu.mediflowappservice.model.Appointment;
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

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository repo;

    @InjectMocks
    private AppointmentService service;

    private Appointment appointment;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        appointment = new Appointment(
                1L,
                "john_doe",
                "Clinic A",
                LocalDate.now().plusDays(1),
                LocalTime.of(10, 30),
                "dr_smith"
        );
    }

    @Test
    void createThenFindById() {
        when(repo.save(appointment)).thenReturn(appointment);
        when(repo.findById(1L)).thenReturn(Optional.of(appointment));

        service.createAppointment(appointment);
        Optional<Appointment> found = service.getAppointmentById(1L);

        assertTrue(found.isPresent());
        assertEquals("john_doe", found.get().getPatientUserName());
    }

    @Test
    void findAllAppointments() {
        when(repo.findAll()).thenReturn(List.of(appointment));

        List<Appointment> all = service.getAllAppointments();

        assertEquals(1, all.size());
        assertEquals("Clinic A", all.get(0).getVenue());
    }

    @Test
    void updateAppointmentSuccess() {
        Appointment updated = new Appointment(
                1L,
                "john_doe",
                "Clinic B",
                LocalDate.now().plusDays(2),
                LocalTime.of(11, 0),
                "dr_smith"
        );

        when(repo.findById(1L)).thenReturn(Optional.of(appointment));
        when(repo.save(updated)).thenReturn(updated);

        Appointment result = service.updateAppointment(1L, updated);

        assertEquals("Clinic B", result.getVenue());
        assertEquals(LocalTime.of(11, 0), result.getTime());
    }

    @Test
    void deleteAppointmentSuccess() {
        when(repo.findById(1L)).thenReturn(Optional.of(appointment));
        doNothing().when(repo).deleteById(1L);

        service.deleteAppointment(1L);

        verify(repo, times(1)).deleteById(1L);
    }

    @Test
    void findByIdNotFound() {
        when(repo.findById(2L)).thenReturn(Optional.empty());

        Optional<Appointment> found = service.getAppointmentById(2L);
        assertTrue(found.isEmpty());
    }
}
