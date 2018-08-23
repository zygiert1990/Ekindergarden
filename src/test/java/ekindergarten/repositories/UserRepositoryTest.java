package ekindergarten.repositories;

import ekindergarten.domain.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import utils.Constans;

import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class UserRepositoryTest {

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