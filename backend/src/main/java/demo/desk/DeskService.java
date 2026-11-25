package demo.desk;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import java.util.*;
@Service
public class DeskService {
 private final DeskRepository repo;
 public DeskService(DeskRepository repo) { this.repo=repo; }
 public List<DeskRecord> list(String q) {
  String query=q.strip().toLowerCase(Locale.ROOT);
  return repo.all().stream().filter(d->String.join(" ",d.title(),d.owner(),d.category(),d.stage(),d.context(),d.notes()).toLowerCase(Locale.ROOT).contains(query)).toList();
 }
 public DeskRecord save(String id,DeskRecord.Input v) {
  if (!Set.of("French","Dutch","German").contains(v.category()) || !Set.of("Draft","In rehearsal","Signed off").contains(v.stage()))
   throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Pick an available language and stage.");
  var input=new DeskRecord.Input(v.title().strip(),v.owner().strip(),v.category(),v.stage(),v.context().strip(),v.due(),v.notes().strip());
  if(id==null){id=UUID.randomUUID().toString();repo.insert(id,input);}
  else if(!repo.update(id,input)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"This record was not found.");
  return new DeskRecord(id,input.title(),input.owner(),input.category(),input.stage(),input.context(),input.due(),input.notes());
 }
}
