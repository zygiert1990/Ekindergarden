package ekindergarten.configuration;

import ekindergarten.service.UserDetailsServiceImpl;
import ekindergarten.utils.CustomAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final AuthenticationSuccessHandler customAuthenticationSuccessHandler;

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Autowired
    public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/register/parent", "/login/signin").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/parent/**").hasRole("PARENT")
                .antMatchers("/teacher/**").hasRole("TEACHER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login/signin").successHandler(customAuthenticationSuccessHandler)
                .failureUrl("/fail");
    }

    @Bean
    public PasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder(16);
    }

}
