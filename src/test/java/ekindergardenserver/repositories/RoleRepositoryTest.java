package ekindergardenserver.repositories;

import utils.Constans;
import ekindergardenserver.domain.Role;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@SpringBootTest
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void shouldFindRoleByRoleName(){
        //given
        Role role = new Role(Constans.ROLE_PARENT);
        roleRepository.save(role);
        //when
        Role result = roleRepository.findByRoleName(Constans.ROLE_PARENT);
        //then
        Assert.assertEquals(role, result);
    }

}