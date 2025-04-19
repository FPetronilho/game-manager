package client;

import com.tracktainment.gamemanager.client.DuxManagerHttpClient;
import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DuxManagerHttpClientTest {

    @Mock
    private DuxManagerHttpClient duxManagerHttpClient;

    private AssetRequest assetRequest;
    private AssetResponse assetResponse;
    private String jwt;
    private String digitalUserId;

    @BeforeEach
    void setUp() {
        jwt = "Bearer token";
        digitalUserId = UUID.randomUUID().toString();

        // Create test asset request
        assetRequest = AssetRequest.builder()
                .externalId(UUID.randomUUID().toString())
                .type("game")
                .permissionPolicy(AssetRequest.PermissionPolicy.OWNER)
                .artifactInformation(new AssetRequest.ArtifactInformation(
                        "com.tracktainment",
                        "game-manager",
                        "0.0.1-SNAPSHOT"
                ))
                .build();

        // Create test asset response
        assetResponse = AssetResponse.builder()
                .id(UUID.randomUUID().toString())
                .externalId(assetRequest.getExternalId())
                .type("game")
                .permissionPolicy(AssetResponse.PermissionPolicy.OWNER)
                .build();
    }

    @Test
    void shouldHaveFeignClientAnnotation() {
        // Verify the class has @FeignClient annotation with correct attributes
        FeignClient annotation = DuxManagerHttpClient.class.getAnnotation(FeignClient.class);

        // Assert
        assertNotNull(annotation, "FeignClient annotation should be present");
        assertEquals("dux-manager-http-client", annotation.name(), "FeignClient should have correct name");
        assertEquals("${http.url.dux-manager}", annotation.url(), "FeignClient should have correct URL");
    }

    @Test
    void shouldHaveValidatedAnnotation() {
        // Verify the class has @Validated annotation
        Validated annotation = DuxManagerHttpClient.class.getAnnotation(Validated.class);

        // Assert
        assertNotNull(annotation, "Validated annotation should be present");
    }

    @Test
    void shouldDefineCreateAssetMethod() throws NoSuchMethodException {
        // Get the method and verify its annotations
        Method method = DuxManagerHttpClient.class.getMethod("createAsset",
                String.class, String.class, AssetRequest.class);

        // Verify PostMapping annotation
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        assertNotNull(postMapping, "PostMapping annotation should be present");
        assertArrayEquals(new String[]{"/assets/digitalUsers/{digitalUserId}"}, postMapping.value(),
                "PostMapping should have correct path");

        // Verify return type
        assertEquals(AssetResponse.class, method.getReturnType(), "Method should return AssetResponse");

        // Verify parameters have correct annotations
        assertNotNull(method.getParameters()[0].getAnnotation(RequestHeader.class),
                "First parameter should have RequestHeader annotation");
        assertNotNull(method.getParameters()[1].getAnnotation(PathVariable.class),
                "Second parameter should have PathVariable annotation");
        assertNotNull(method.getParameters()[2].getAnnotation(RequestBody.class),
                "Third parameter should have RequestBody annotation");
    }

    @Test
    void shouldDefineFindAssetsByCriteriaMethod() throws NoSuchMethodException {
        // Get the method and verify its annotations
        Method method = DuxManagerHttpClient.class.getMethod("findAssetsByCriteria",
                String.class, String.class, String.class, String.class, String.class,
                String.class, LocalDate.class, LocalDate.class, LocalDate.class);

        // Verify GetMapping annotation
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        assertNotNull(getMapping, "GetMapping annotation should be present");
        assertArrayEquals(new String[]{"/assets"}, getMapping.value(),
                "GetMapping should have correct path");

        // Verify return type
        assertEquals(List.class, method.getReturnType(), "Method should return List");

        // Verify parameters have correct annotations
        assertNotNull(method.getParameters()[0].getAnnotation(RequestHeader.class),
                "First parameter should have RequestHeader annotation");

        // Verify all RequestParam annotations
        for (int i = 1; i < method.getParameters().length; i++) {
            RequestParam param = method.getParameters()[i].getAnnotation(RequestParam.class);
            assertNotNull(param, "Parameter should have RequestParam annotation");

            // Check if DateTimeFormat annotation is present on date parameters
            if (method.getParameters()[i].getType() == LocalDate.class) {
                DateTimeFormat dateFormat = method.getParameters()[i].getAnnotation(DateTimeFormat.class);
                assertNotNull(dateFormat, "Date parameter should have DateTimeFormat annotation");
                assertEquals(DateTimeFormat.ISO.DATE, dateFormat.iso(),
                        "DateTimeFormat should be set to ISO.DATE");
            }
        }
    }

    @Test
    void shouldDefineDeleteAssetMethod() throws NoSuchMethodException {
        // Get the method and verify its annotations
        Method method = DuxManagerHttpClient.class.getMethod("deleteAssetByExternalId",
                String.class, String.class, String.class);

        // Verify DeleteMapping annotation
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        assertNotNull(deleteMapping, "DeleteMapping annotation should be present");
        assertArrayEquals(new String[]{"/assets"}, deleteMapping.value(),
                "DeleteMapping should have correct path");

        // Verify return type
        assertEquals(void.class, method.getReturnType(), "Method should return void");

        // Verify parameters have correct annotations
        assertNotNull(method.getParameters()[0].getAnnotation(RequestHeader.class),
                "First parameter should have RequestHeader annotation");

        for (int i = 1; i < method.getParameters().length; i++) {
            RequestParam param = method.getParameters()[i].getAnnotation(RequestParam.class);
            assertNotNull(param, "Parameter should have RequestParam annotation");
        }
    }

    @Test
    void shouldCreateAsset() {
        // Arrange
        when(duxManagerHttpClient.createAsset(anyString(), anyString(), any(AssetRequest.class)))
                .thenReturn(assetResponse);

        // Act
        AssetResponse result = duxManagerHttpClient.createAsset(jwt, digitalUserId, assetRequest);

        // Assert
        assertNotNull(result, "Response should not be null");
        assertEquals(assetResponse, result, "Should return the mocked response");
    }

    @Test
    void shouldFindAssetsByCriteria() {
        // Act
        duxManagerHttpClient.findAssetsByCriteria(
                jwt, digitalUserId, null, "com.tracktainment",
                "game-manager", "game", null, null, null);

        // Assert
        verify(duxManagerHttpClient).findAssetsByCriteria(
                jwt, digitalUserId, null, "com.tracktainment",
                "game-manager", "game", null, null, null);
    }

    @Test
    void shouldDeleteAssetByExternalId() {
        // Arrange - no need to set up return value for void method
        String externalId = UUID.randomUUID().toString();

        // Act & Assert - Should not throw exception
        assertDoesNotThrow(() ->
                duxManagerHttpClient.deleteAssetByExternalId(jwt, digitalUserId, externalId));
    }
}
