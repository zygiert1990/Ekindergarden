package ekindergarten.security.configuration;

import ekindergarten.security.jwt.JwtAuthenticationEntryPoint;
import ekindergarten.security.jwt.JwtAuthenticationProvider;
import ekindergarten.security.jwt.JwtAuthenticationTokenFilter;
import ekindergarten.security.jwt.JwtSuccessHandler;
import ekindergarten.utils.UserAuthorities;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationProvider authenticationProvider;
    private final JwtAuthenticationEntryPoint entryPoint;

    public WebSecurityConfig(JwtAuthenticationProvider authenticationProvider, JwtAuthenticationEntryPoint entryPoint) {
        this.authenticationProvider = authenticationProvider;
        this.entryPoint = entryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authenticationProvider));
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() {
        JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/", "/register*", "/login*",
                        "/index*", "/layout/**", "/images/**", "/register/parent", "/admin*",
                        "/parent*", "/teacher*", "/add-child*", "/child*", "/absence*",
                        "/fees*", "/remarks*", "/observations*").permitAll()
                .antMatchers("/rest/admin/**").hasAuthority(UserAuthorities.ADMIN)
                .antMatchers("/rest/parent/**").hasAuthority(UserAuthorities.PARENT)
                .antMatchers("/rest/news/**").hasAuthority(UserAuthorities.PARENT)
                .antMatchers("/rest/childinfo/**").hasAuthority(UserAuthorities.PARENT)
                .antMatchers("/rest/teacher/**").hasAuthority(UserAuthorities.TEACHER)
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Bean
    public PasswordEncoder passEncoder() {
        return new BCryptPasswordEncoder(5);
    }
}