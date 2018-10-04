package ekindergarten.test.repositories;

import ekindergarten.domain.Address;
import ekindergarten.repositories.AddressRepository;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;
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
        addressRepository.save(TestUtil.createAddress());
        //when
        Address result = addressRepository
                .findAllByCityAndZipCodeAndHomeNumberAndFlatNumber(
                        Constans.CITY,
                        Constans.ZIP_CODE,
                        Constans.HOME_NUMBER,
                        Constans.FLAT_NUMBER
                );
        //then
        Assert.assertNotNull(result);
    }
}