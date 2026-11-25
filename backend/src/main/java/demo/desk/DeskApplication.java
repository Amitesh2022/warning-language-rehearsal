package demo.desk;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import java.time.LocalDate;
@SpringBootApplication
public class DeskApplication {
 public static void main(String[] args) { SpringApplication.run(DeskApplication.class,args); }
 @Bean CommandLineRunner seed(DeskRepository repo) {
  return args -> { if (repo.all().isEmpty()) {
  repo.insert("1", new DeskRecord.Input("Water outage wording", "Marie Dubois", "French", "Draft", "SMS exercise",LocalDate.now().plusDays(2),"Use fictional messages only."));
  repo.insert("2", new DeskRecord.Input("Tram closure audio script", "Noor Peeters", "Dutch", "In rehearsal", "Recorded announcement",LocalDate.now().plusDays(5),"Use fictional messages only."));
  repo.insert("3", new DeskRecord.Input("Library cooling centre notice", "Lena Weber", "German", "Signed off", "Municipal site draft",LocalDate.now().plusDays(8),"Use fictional messages only."));
  repo.insert("4", new DeskRecord.Input("Flood drill plain-language script", "Marie Dubois", "French", "Draft", "SMS exercise",LocalDate.now().plusDays(11),"Use fictional messages only."));
  repo.insert("5", new DeskRecord.Input("Heat exercise caption check", "Noor Peeters", "Dutch", "In rehearsal", "Recorded announcement",LocalDate.now().plusDays(14),"Use fictional messages only."));
  repo.insert("6", new DeskRecord.Input("Town hall access update", "Lena Weber", "German", "Signed off", "Municipal site draft",LocalDate.now().plusDays(17),"Use fictional messages only."));
  }};
 }
}
