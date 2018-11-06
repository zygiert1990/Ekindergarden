package ekindergarten;

import ekindergarten.domain.Role;
import ekindergarten.repositories.RoleRepository;
import ekindergarten.utils.UserAuthorities;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class Main extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository) {
        if (roleRepository.findByRoleName(UserAuthorities.PARENT) != null)
            return null;
        return args -> {
            List<String> roleNames = Arrays.asList(UserAuthorities.PARENT, UserAuthorities.ADMIN, UserAuthorities.TEACHER);
            roleNames.forEach(name -> roleRepository.save(new Role(name)));
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Main.class);
    }
}