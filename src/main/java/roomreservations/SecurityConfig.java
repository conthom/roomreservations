package roomreservations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Class for setting up password encoder without the spring extra security features
@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // allow all routes in your app
                )
                .formLogin(AbstractHttpConfigurer::disable)  // disable Spring Security login page
                .httpBasic(AbstractHttpConfigurer::disable) // disable basic auth popup
                .logout(AbstractHttpConfigurer::disable); // disable default logout

        return http.build();
    }
}