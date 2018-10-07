package ekindergarten.test.repositories;

import ekindergarten.domain.Role;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.utils.UserRoles;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RoleRepositoryTest extends BaseJpaTestConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void shouldFindRoleByRoleName() {
        //given
        roleRepository.save(new Role(UserRoles.PARENT));
        //when
        Role result = roleRepository.findByRoleName(UserRoles.PARENT);
        //then
        Assert.assertEquals(result.getRoleName(), UserRoles.PARENT);
    }
}