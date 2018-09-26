package ekindergarten.test.repositories;

import ekindergarten.domain.Address;
import ekindergarten.repositories.AddressRepository;
import ekindergarten.testingUtils.Constans;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AddressRepositoryTest extends BaseJpaTestConfig {

    @Autowired
    AddressRepository addressRepository;

    @Test
    public void shouldFindAllAddressesByCity() {
        //given
        addressRepository.save(createAddress());
        //when
        List<Address> result = addressRepository.findAllByCity(Constans.CITY);
        //then
        Assert.assertEquals(result.size(), 1);
    }

    private Address createAddress() {
        return Address.builder()
                .withCity(Constans.CITY)
                .withZipCode(Constans.ZIP_CODE)
                .withStreet(Constans.STREET)
                .withHomeNumber(Constans.HOME_NUMBER)
                .withFlatNumber(Constans.FLAT_NUMBER)
                .build();
    }

}