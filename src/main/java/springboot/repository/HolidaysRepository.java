package springboot.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import springboot.model.Holiday;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface HolidaysRepository extends CrudRepository<Holiday, String>  {


}
