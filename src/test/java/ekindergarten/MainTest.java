package ekindergarten;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class MainTest {

    @Bean
    public PasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder(16);
    }


}
