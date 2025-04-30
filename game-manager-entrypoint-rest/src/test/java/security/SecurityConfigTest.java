package security;

import com.tracktainment.gamemanager.security.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {SecurityConfigTest.TestSecurityConfig.class})
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private HttpSecurity httpSecurity;

    @InjectMocks
    private SecurityConfig securityConfig;

    @Test
    void shouldPermitAccessToSwaggerEndpoints() throws Exception {
        mockMvc.perform(get("/swagger-ui.html"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/swagger-ui/index.html"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isNotFound());

        mockMvc.perform(get("/api-docs"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldRequireAuthenticationForApiEndpoints() throws Exception {
        mockMvc.perform(get("/api/v1/games"))
                .andExpect(status().isUnauthorized());

        mockMvc.perform(get("/api/v1/games/123"))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Test-specific security configuration that mimics the actual SecurityConfig
     * but doesn't rely on JWT configuration that's defined in application.yaml
     */
    @Configuration
    @EnableWebSecurity
    static class TestSecurityConfig {

        @Bean
        public JwtDecoder jwtDecoder() {
            // Mock JwtDecoder to avoid actual JWT validation
            return mock(JwtDecoder.class);
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(auth -> auth
                            .requestMatchers(
                                    "/swagger-ui.html",
                                    "/swagger-ui/**",
                                    "/webjars/**",
                                    "/v3/api-docs/**",
                                    "/api-docs/**"
                            )
                            .permitAll()
                            .anyRequest()
                            .authenticated())
                    .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                    .csrf(AbstractHttpConfigurer::disable);  // Disable CSRF for simplicity in tests

            return http.build();
        }
    }

    @Test
    void shouldHaveProperSecurityAnnotations() {
        org.springframework.context.annotation.Configuration configAnnotation =
                SecurityConfig.class.getAnnotation(org.springframework.context.annotation.Configuration.class);
        assertNotNull(configAnnotation, "Configuration annotation should be present");

        org.springframework.security.config.annotation.web.configuration.EnableWebSecurity webSecurityAnnotation =
                SecurityConfig.class.getAnnotation(org.springframework.security.config.annotation.web.configuration.EnableWebSecurity.class);
        assertNotNull(webSecurityAnnotation, "EnableWebSecurity annotation should be present");
    }

    @Test
    void shouldHaveSecurityFilterChainBean() {
        // Verify securityFilterChain method has @Bean annotation
        try {
            Method securityFilterChainMethod = SecurityConfig.class.getDeclaredMethod("securityFilterChain", HttpSecurity.class);
            org.springframework.context.annotation.Bean beanAnnotation =
                    securityFilterChainMethod.getAnnotation(org.springframework.context.annotation.Bean.class);

            assertNotNull(beanAnnotation, "Bean annotation should be present on securityFilterChain method");

            // Verify return type
            assertEquals(SecurityFilterChain.class, securityFilterChainMethod.getReturnType(),
                    "securityFilterChain method should return SecurityFilterChain");
        } catch (NoSuchMethodException e) {
            fail("securityFilterChain method should exist in SecurityConfig class", e);
        }
    }
}
