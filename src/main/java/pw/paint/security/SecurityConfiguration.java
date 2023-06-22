package pw.paint.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/homepage.html",
                        "/dodaj-przepis.html",
                        "/przepisnik.html"
                )
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(new RegexRequestMatcher("/recipe/*", "PUT"),
                        new RegexRequestMatcher("/recipe/new/*", "POST"),
                        new RegexRequestMatcher("/recipe/search/private/*", "POST"),
                        new RegexRequestMatcher("/recipe/*", "DELETE"),
                        new RegexRequestMatcher("/folder/*", "PUT"),
                        new RegexRequestMatcher("/folder/*", "POST"),
                        new RegexRequestMatcher("/folder/*", "DELETE")
                )
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .anyRequest()
                .permitAll();

        return http.build();
    }
}
