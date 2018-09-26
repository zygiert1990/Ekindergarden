package ekindergarten.test.repositories;

import ekindergarten.domain.Role;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.testingUtils.Constans;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleRepositoryTest extends BaseJpaTestConfig {

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