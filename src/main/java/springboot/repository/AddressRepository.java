package springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springboot.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
