package ekindergardenserver.repositories;

import ekindergardenserver.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByCity(String city);

}
