package demo.desk;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
public record DeskRecord(String id,String title,String owner,String category,String stage,String context,LocalDate due,String notes) {
 public record Input(@NotBlank @Size(max=100) String title,@NotBlank @Size(max=80) String owner,
 @NotBlank @Size(max=40) String category,@NotBlank @Size(max=40) String stage,
 @NotBlank @Size(max=100) String context,@NotNull LocalDate due,@NotBlank @Size(max=500) String notes) {}
}
