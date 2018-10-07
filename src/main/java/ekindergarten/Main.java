package ekindergarten;

import ekindergarten.domain.Role;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.utils.UserRoles;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {
        if (roleRepository.findByRoleName(UserRoles.PARENT) != null)
            return null;
        return args -> {
            List<String> roleNames = Arrays.asList(UserRoles.PARENT, UserRoles.ADMIN, UserRoles.TEACHER);
            roleNames.forEach(name -> roleRepository.save(new Role(name)));
        };
    }
}