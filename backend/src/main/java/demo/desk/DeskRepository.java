package demo.desk;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
@Repository
public class DeskRepository {
 private final JdbcTemplate db;
 public DeskRepository(JdbcTemplate db) { this.db=db; }
 public List<DeskRecord> all() {
  return db.query("select * from desk_record order by id", (r,n)->new DeskRecord(r.getString("id"),r.getString("title"),r.getString("owner"),r.getString("category"),r.getString("stage"),r.getString("context"),r.getObject("due",LocalDate.class),r.getString("notes")));
 }
 public void insert(String id,DeskRecord.Input v) {
  db.update("insert into desk_record(id,title,owner,category,stage,context,due,notes) values(?,?,?,?,?,?,?,?)",id,v.title(),v.owner(),v.category(),v.stage(),v.context(),v.due(),v.notes());
 }
 public boolean update(String id,DeskRecord.Input v) {
  return db.update("update desk_record set title=?,owner=?,category=?,stage=?,context=?,due=?,notes=? where id=?",v.title(),v.owner(),v.category(),v.stage(),v.context(),v.due(),v.notes(),id)>0;
 }
}
