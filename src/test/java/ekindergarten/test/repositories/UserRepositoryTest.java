package ekindergarten.test.repositories;

import ekindergarten.domain.User;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.repositories.UserRepository;
import ekindergarten.testingUtils.Constans;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserRepositoryTest extends BaseRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Before
    public void setup() {
        userRepository.save(createUser());
    }

    @Test
    public void shouldFindAllUsers() {
        //when
        List<User> result = userRepository.findAll();
        //then
        Assert.assertEquals(result.size(), 1);
    }

    @Test
    public void shouldFindUserByEmail() {
        //when
        User result = userRepository.findByEmail(Constans.EMAIL);
        //then
        Assert.assertEquals(result.getName(), Constans.NAME);
    }

    @Test
    public void shouldNotFindUserByEmail() {
        //when
        User result = userRepository.findByEmail("fakeEmail");
        //then
        Assert.assertNull(result);
    }


    @Test
    public void shouldFindUserByCivilId() {
        //when
        User result = userRepository.findByCivilId(Constans.CIVIL_ID);
        //then
        Assert.assertEquals(result.getName(), Constans.NAME);
    }


    @Test
    public void shouldFindUserByPhoneNumber() {
        //when
        User result = userRepository.findByPhoneNumber(Constans.PHONE_NUMBER);
        //then
        Assert.assertEquals(result.getName(), Constans.NAME);
    }

    private User createUser() {
        return new User.Builder()
                .withName(Constans.NAME)
                .withSurname(Constans.SURNAME)
                .withCivilId(Constans.CIVIL_ID)
                .withEmail(Constans.EMAIL)
                .withPhoneNumber(Constans.PHONE_NUMBER)
                .withPassword(Constans.PASSWORD)
                .withRole(roleRepository.findByRoleName(Constans.ROLE_USER))
                .build();
    }
}