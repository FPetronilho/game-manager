package swagger;

import com.tracktainment.gamemanager.swagger.OpenApiConfig;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OpenApiConfigTest {

    @InjectMocks
    private OpenApiConfig openApiConfig;

    @Test
    void shouldCreateOpenAPIWithCorrectConfig() {
        // Arrange
        String applicationName = "game-manager";
        String applicationVersion = "0.0.1-SNAPSHOT";

        ReflectionTestUtils.setField(openApiConfig, "applicationName", applicationName);
        ReflectionTestUtils.setField(openApiConfig, "applicationVersion", applicationVersion);

        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertNotNull(openAPI);
        assertNotNull(openAPI.getInfo());
        assertEquals(applicationName + " API Documentation", openAPI.getInfo().getTitle());
        assertEquals(applicationVersion, openAPI.getInfo().getVersion());
        assertTrue(openAPI.getInfo().getDescription().contains(applicationName));

        // Check security configuration
        assertNotNull(openAPI.getSecurity());
        assertFalse(openAPI.getSecurity().isEmpty());

        SecurityRequirement securityRequirement = openAPI.getSecurity().get(0);
        assertTrue(securityRequirement.containsKey("bearerAuth"));

        // Check security schemes
        assertNotNull(openAPI.getComponents());
        assertNotNull(openAPI.getComponents().getSecuritySchemes());
        assertTrue(openAPI.getComponents().getSecuritySchemes().containsKey("bearerAuth"));

        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");
        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType());
        assertEquals("bearer", securityScheme.getScheme());
        assertEquals("JWT", securityScheme.getBearerFormat());
    }

    @Test
    void shouldCreateOpenAPIWithCorrectSecurityConfig() {
        // Arrange
        String applicationName = "game-manager";
        String applicationVersion = "0.0.1-SNAPSHOT";

        ReflectionTestUtils.setField(openApiConfig, "applicationName", applicationName);
        ReflectionTestUtils.setField(openApiConfig, "applicationVersion", applicationVersion);

        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertNotNull(openAPI);

        // Verify security configuration
        List<SecurityRequirement> securityRequirements = openAPI.getSecurity();
        assertNotNull(securityRequirements);
        assertFalse(securityRequirements.isEmpty());

        SecurityRequirement securityRequirement = securityRequirements.get(0);
        assertTrue(securityRequirement.containsKey("bearerAuth"));

        // Verify security scheme
        Components components = openAPI.getComponents();
        assertNotNull(components);

        Map<String, SecurityScheme> securitySchemes = components.getSecuritySchemes();
        assertNotNull(securitySchemes);
        assertTrue(securitySchemes.containsKey("bearerAuth"));

        SecurityScheme securityScheme = securitySchemes.get("bearerAuth");
        assertNotNull(securityScheme);
        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType());
        assertEquals("bearer", securityScheme.getScheme());
        assertEquals("JWT", securityScheme.getBearerFormat());
        assertNotNull(securityScheme.getDescription());
        assertTrue(securityScheme.getDescription().contains("JWT"));
    }

    @Test
    void shouldCreateOpenAPIWithCorrectInfo() {
        // Arrange
        String applicationName = "game-manager";
        String applicationVersion = "0.0.1-SNAPSHOT";

        ReflectionTestUtils.setField(openApiConfig, "applicationName", applicationName);
        ReflectionTestUtils.setField(openApiConfig, "applicationVersion", applicationVersion);

        // Act
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        // Assert
        assertNotNull(openAPI);

        // Verify info object
        Info info = openAPI.getInfo();
        assertNotNull(info);
        assertEquals(applicationName + " API Documentation", info.getTitle());
        assertEquals(applicationVersion, info.getVersion());
        assertTrue(info.getDescription().contains(applicationName));

        // Verify contact details
        Contact contact = info.getContact();
        assertNotNull(contact);
        assertNotNull(contact.getName());
        assertNotNull(contact.getEmail());
        assertNotNull(contact.getUrl());

        // Verify license details
        License license = info.getLicense();
        assertNotNull(license);
        assertNotNull(license.getName());
        assertNotNull(license.getUrl());
    }

    @Test
    void shouldHandleDifferentApplicationValues() {
        String[] applicationNames = {"test-app", "demo-service", "api-gateway"};
        String[] applicationVersions = {"1.0.0", "2.3.4-SNAPSHOT", "0.1.0-beta"};

        for (String appName : applicationNames) {
            for (String appVersion : applicationVersions) {
                // Arrange
                ReflectionTestUtils.setField(openApiConfig, "applicationName", appName);
                ReflectionTestUtils.setField(openApiConfig, "applicationVersion", appVersion);

                // Act
                OpenAPI openAPI = openApiConfig.customOpenAPI();

                // Assert
                assertNotNull(openAPI);
                assertEquals(appName + " API Documentation", openAPI.getInfo().getTitle());
                assertEquals(appVersion, openAPI.getInfo().getVersion());
                assertTrue(openAPI.getInfo().getDescription().contains(appName));
            }
        }
    }
}
