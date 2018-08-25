package ekindergarten.repositories;

import ekindergarten.domain.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import utils.Constans;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void shouldFindRoleByRoleName() {
        //when
        Role result = roleRepository.findByRoleName(Constans.ROLE_USER);
        //then
        Assert.assertEquals(result.getRoleName(), Constans.ROLE_USER);
    }
}