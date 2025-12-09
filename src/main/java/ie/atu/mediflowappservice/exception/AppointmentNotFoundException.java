package ie.atu.mediflowappservice.exception;

// Error Handling
public class AppointmentNotFoundException extends RuntimeException {
    public AppointmentNotFoundException(Long id) {
        super("Appointment not found with id: " + id);
    }
}
