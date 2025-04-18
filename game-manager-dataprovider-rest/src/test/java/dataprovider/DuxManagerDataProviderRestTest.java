package dataprovider;

import com.tracktainment.gamemanager.client.DuxManagerHttpClient;
import com.tracktainment.gamemanager.dataprovider.DuxManagerDataProviderRest;
import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import testutil.TestGameDataUtil;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DuxManagerDataProviderRestTest {

    @Mock
    private DuxManagerHttpClient duxManagerHttpClient;

    @InjectMocks
    private DuxManagerDataProviderRest duxManagerDataProviderRest;

    private String jwt;
    private String digitalUserId;
    private AssetRequest assetRequest;
    private AssetResponse assetResponse;

    @BeforeEach
    void setUp() {
        jwt = "Bearer token";
        digitalUserId = UUID.randomUUID().toString();
        assetRequest = TestGameDataUtil.createTestAssetRequest(UUID.randomUUID().toString());
        assetResponse = TestGameDataUtil.createTestAssetResponse();
    }

    @Test
    void shouldCreateAssetSuccessfully() {
        // Arrange
        when(duxManagerHttpClient.createAsset(jwt, digitalUserId, assetRequest))
                .thenReturn(assetResponse);

        // Act
        AssetResponse result = duxManagerDataProviderRest.createAsset(jwt, digitalUserId, assetRequest);

        // Assert
        assertNotNull(result);
        assertEquals(assetResponse, result);

        verify(duxManagerHttpClient).createAsset(jwt, digitalUserId, assetRequest);
    }

    @Test
    void shouldFindAssetsByCriteriaSuccessfully() {
        // Arrange
        List<AssetResponse> assetResponses = Arrays.asList(assetResponse, TestGameDataUtil.createTestAssetResponse());
        String externalIds = "id1,id2";
        String groupId = "com.tracktainment";
        String artifactId = "game-manager";
        String type = "game";
        LocalDate createdAt = LocalDate.now();
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();

        when(duxManagerHttpClient.findAssetsByCriteria(
                jwt, digitalUserId, externalIds, groupId, artifactId, type, createdAt, from, to))
                .thenReturn(assetResponses);

        // Act
        List<AssetResponse> results = duxManagerDataProviderRest.findAssetsByCriteria(
                jwt, digitalUserId, externalIds, groupId, artifactId, type, createdAt, from, to);

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(assetResponses, results);

        verify(duxManagerHttpClient).findAssetsByCriteria(
                jwt, digitalUserId, externalIds, groupId, artifactId, type, createdAt, from, to);
    }

    @Test
    void shouldHandleEmptyResultsWhenFindingAssetsByCriteria() {
        // Arrange
        when(duxManagerHttpClient.findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull()))
                .thenReturn(Collections.emptyList());

        // Act
        List<AssetResponse> results = duxManagerDataProviderRest.findAssetsByCriteria(
                jwt, digitalUserId, null, null, null, null, null, null, null);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());

        verify(duxManagerHttpClient).findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void shouldDeleteAssetSuccessfully() {
        // Arrange
        String externalId = UUID.randomUUID().toString();
        doNothing().when(duxManagerHttpClient).deleteAssetByExternalId(jwt, digitalUserId, externalId);

        // Act
        duxManagerDataProviderRest.deleteAsset(jwt, digitalUserId, externalId);

        // Assert
        verify(duxManagerHttpClient).deleteAssetByExternalId(jwt, digitalUserId, externalId);
    }

    @Test
    void shouldPropagateExceptionWhenCreateAssetFails() {
        // Arrange
        RuntimeException exception = new RuntimeException("Failed to create asset");
        when(duxManagerHttpClient.createAsset(jwt, digitalUserId, assetRequest))
                .thenThrow(exception);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                duxManagerDataProviderRest.createAsset(jwt, digitalUserId, assetRequest));

        assertEquals("Failed to create asset", thrown.getMessage());
        verify(duxManagerHttpClient).createAsset(jwt, digitalUserId, assetRequest);
    }

    @Test
    void shouldPropagateExceptionWhenFindAssetsByCriteriaFails() {
        // Arrange
        RuntimeException exception = new RuntimeException("Failed to find assets");
        when(duxManagerHttpClient.findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull()))
                .thenThrow(exception);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                duxManagerDataProviderRest.findAssetsByCriteria(
                        jwt, digitalUserId, null, null, null, null, null, null, null));

        assertEquals("Failed to find assets", thrown.getMessage());
        verify(duxManagerHttpClient).findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void shouldPropagateExceptionWhenDeleteAssetFails() {
        // Arrange
        String externalId = UUID.randomUUID().toString();
        RuntimeException exception = new RuntimeException("Failed to delete asset");
        doThrow(exception).when(duxManagerHttpClient).deleteAssetByExternalId(jwt, digitalUserId, externalId);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                duxManagerDataProviderRest.deleteAsset(jwt, digitalUserId, externalId));

        assertEquals("Failed to delete asset", thrown.getMessage());
        verify(duxManagerHttpClient).deleteAssetByExternalId(jwt, digitalUserId, externalId);
    }
}
