package ekindergarten.test.service;

import ekindergarten.domain.Address;
import ekindergarten.domain.Role;
import ekindergarten.model.UserDto;
import ekindergarten.repositories.AddressRepository;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.service.AddressService;
import ekindergarten.service.UserService;
import ekindergarten.test.repositories.BaseJpaTestConfig;
import ekindergarten.testingUtils.Constans;
import ekindergarten.testingUtils.TestUtil;
import ekindergarten.utils.UserValidationService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AddressServiceTest extends BaseJpaTestConfig {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private AddressService addressService;
    private UserService userService;

    @Before
    public void setup() {
        UserValidationService userValidationService = new UserValidationService(userRepository);
        userService = new UserService(userRepository, userValidationService, roleRepository, passwordEncoder);

        roleRepository.save(new Role(Constans.ROLE_USER));
        userService.registerNewParent(TestUtil.createUserDto());

        addressService = new AddressService(addressRepository, userRepository);
    }

    @Test
    public void shouldAddAddress() {
        // given
        addressService.addAddress(TestUtil.createAddress(), Constans.EMAIL);
        // when
        Address result = addressRepository.findAllByCityAndZipCodeAndHomeNumberAndFlatNumber(
                Constans.CITY,
                Constans.ZIP_CODE,
                Constans.HOME_NUMBER,
                Constans.FLAT_NUMBER
        );
        //then
        assertNotNull(result);
        assertEquals(result.getUsers().size(), 1);
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotAddSecondAddressToUser() {
        // given
        addressService.addAddress(TestUtil.createAddress(), Constans.EMAIL);
        addressService.addAddress(TestUtil.createAddress(), Constans.EMAIL);
    }

    @Test
    public void shouldAssignSameAddressToAnotherUser() {
        // given
        userService.registerNewParent(UserDto.builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withCivilId("ASD789654")
                .withEmail("ala@wp.pl")
                .withPhoneNumber("777888999")
                .withPassword("Asdfg183!")
                .withMatchingPassword("Asdfg183!")
                .build());
        addressService.addAddress(TestUtil.createAddress(), Constans.EMAIL);
        addressService.addAddress(TestUtil.createAddress(), "ala@wp.pl");
        // when
        Address result = addressRepository.findAllByCityAndZipCodeAndHomeNumberAndFlatNumber(
                Constans.CITY,
                Constans.ZIP_CODE,
                Constans.HOME_NUMBER,
                Constans.FLAT_NUMBER
        );
        // then
        assertEquals(result.getUsers().size(), 2);
    }
}