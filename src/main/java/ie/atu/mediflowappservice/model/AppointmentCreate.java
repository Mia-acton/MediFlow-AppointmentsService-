package ie.atu.mediflowappservice.model;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentCreate {

    @NotBlank(message = "Patient name is required.")
    private String patientUserName;

    @NotBlank(message = "Venue is required.")
    private String venue;

    @NotNull(message = "Appointment date is required.")
    @FutureOrPresent(message = "Appointment must be today or in the future.")
    private LocalDate date;

    @NotNull(message = "Appointment time is required.")
    private LocalTime time;

    @NotBlank(message = "Doctor name is required.")
    private String doctorUserName;
}
