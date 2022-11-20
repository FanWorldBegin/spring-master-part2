package springboot.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import springboot.model.Contact;

import java.util.List;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface ContactRepository extends CrudRepository<Contact, Integer> {

    List<Contact> findByStatus(String status);

    // 参数可以 ?1 也可以直接使用变量名status, 此时不会按照方法名生成sql 而是按照@query的名字
    // JPQL 查询 使用 entity 名字 pageable 仍然可以使用（混合使用）
    @Query("SELECT c FROM Contact c WHERE c.status = :status")
    // native query使用 表中的列名
    //@Query(value = "SELECT * FROM contact_msg c WHERE c.status = :status",nativeQuery = true)
    // @Param("status") 如果变量名字和query中使用的名字不一样，可以取别名
    Page<Contact> findByStatus(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 WHERE c.contactId = ?2")
    int updateStatusById(String status, int id);

    Page<Contact> findOpenMsgs(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    int updateMsgStatus(String status, int id);

    @Query(nativeQuery = true)
    Page<Contact> findOpenMsgsNative(@Param("status") String status, Pageable pageable);

    @Transactional
    @Modifying
    @Query(nativeQuery = true)
    int updateMsgStatusNative(String status, int id);

}
