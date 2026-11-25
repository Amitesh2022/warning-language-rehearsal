package demo.desk;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
@RestController
@RequestMapping("/api")
public class DeskController {
 private final DeskService service;
 public DeskController(DeskService service){this.service=service;}
 @GetMapping("/health") Map<String,String> health(){return Map.of("status","UP");}
 @GetMapping("/records") List<DeskRecord> list(@RequestParam(defaultValue="") String q){return service.list(q);}
 @PostMapping("/records") @ResponseStatus(HttpStatus.CREATED) DeskRecord create(@Valid @RequestBody DeskRecord.Input body){return service.save(null,body);}
 @PutMapping("/records/{id}") DeskRecord update(@PathVariable String id,@Valid @RequestBody DeskRecord.Input body){return service.save(id,body);}
 @ExceptionHandler(MethodArgumentNotValidException.class)
 @ResponseStatus(HttpStatus.BAD_REQUEST)
 Map<String,Object> invalid(MethodArgumentNotValidException ex){
  Map<String,String> fields=new LinkedHashMap<>();
  ex.getBindingResult().getFieldErrors().forEach(v->fields.putIfAbsent(v.getField(),v.getDefaultMessage()));
  return Map.of("detail","Check the highlighted fields.","fields",fields);
 }
}
