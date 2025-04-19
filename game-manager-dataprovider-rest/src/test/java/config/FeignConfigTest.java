package config;

import com.tracktainment.gamemanager.config.FeignConfig;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class FeignConfigTest {

    @InjectMocks
    private FeignConfig feignConfig;

    @Test
    void shouldCreateOkHttpClient() {
        // Act
        OkHttpClient client = feignConfig.client();

        // Assert
        assertNotNull(client, "OkHttpClient should not be null");
    }

    @Test
    void shouldHaveBeanAnnotation() {
        // This test validates the bean annotation is present
        try {
            java.lang.reflect.Method method = FeignConfig.class.getDeclaredMethod("client");
            org.springframework.context.annotation.Bean annotation =
                    method.getAnnotation(org.springframework.context.annotation.Bean.class);

            // Assert
            assertNotNull(annotation, "Bean annotation should be present");

            // If the bean has a name, we can validate it, but it's not required
            if (annotation.name().length > 0) {
                assertEquals("http2PatchEnabler", annotation.name()[0], "Bean should have the correct name if specified");
            }
        } catch (NoSuchMethodException e) {
            fail("Method 'client' should exist", e);
        }
    }

    @Test
    void shouldHaveConfigurationAnnotation() {
        // Verify the class has @Configuration annotation
        org.springframework.context.annotation.Configuration annotation =
                FeignConfig.class.getAnnotation(org.springframework.context.annotation.Configuration.class);

        // Assert
        assertNotNull(annotation, "Configuration annotation should be present");
    }
}
