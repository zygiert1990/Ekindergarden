package ekindergarten.test.repositories;

import ekindergarten.MainTest;
import ekindergarten.domain.Role;
import ekindergarten.repositories.RoleRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ekindergarten.testingUtils.Constans;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest(classes = MainTest.class)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void shouldFindRoleByRoleName() {
        //given
        roleRepository.save(new Role(Constans.ROLE_USER));
        //when
        Role result = roleRepository.findByRoleName(Constans.ROLE_USER);
        //then
        Assert.assertEquals(result.getRoleName(), Constans.ROLE_USER);
    }
}