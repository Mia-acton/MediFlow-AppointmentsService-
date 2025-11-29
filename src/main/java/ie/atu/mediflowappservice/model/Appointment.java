package ie.atu.mediflowappservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @NotBlank(message = "Patient name is required.")
    private String patientUserName;

    @NotBlank(message = "Venue is required.")
    private String venue;

    @NotNull(message = "Appointment date is required.")
    @FutureOrPresent(message = "Appointment must be today or in the future.")
    private LocalDate date; // Date without time

    @NotNull(message = "Appointment time is required.")
    private LocalTime time; // Time of day without a date or timezone

    @NotBlank(message = "Doctor name is required.")
    private String doctorUserName;
}