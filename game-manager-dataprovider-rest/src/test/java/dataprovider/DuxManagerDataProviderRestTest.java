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
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DuxManagerDataProviderRestAdditionalTest {

    @Mock
    private DuxManagerHttpClient duxManagerHttpClient;

    @InjectMocks
    private DuxManagerDataProviderRest duxManagerDataProviderRest;

    private String jwt;
    private String digitalUserId;
    private AssetRequest assetRequest;
    private AssetResponse assetResponse1;
    private AssetResponse assetResponse2;

    @BeforeEach
    void setUp() {
        jwt = "Bearer token";
        digitalUserId = UUID.randomUUID().toString();
        assetRequest = TestGameDataUtil.createTestAssetRequest(UUID.randomUUID().toString());
        assetResponse1 = TestGameDataUtil.createTestAssetResponse();
        assetResponse2 = TestGameDataUtil.createTestAssetResponse();
    }

    @Test
    void shouldFindAssetsByCriteriaWithAllParameters() {
        // Arrange
        List<AssetResponse> expectedResponses = Arrays.asList(assetResponse1, assetResponse2);
        String externalIds = "id1,id2";
        String groupId = "com.tracktainment";
        String artifactId = "game-manager";
        String type = "game";
        LocalDate createdAt = LocalDate.now();
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();

        when(duxManagerHttpClient.findAssetsByCriteria(
                jwt, digitalUserId, externalIds, groupId, artifactId, type, createdAt, from, to))
                .thenReturn(expectedResponses);

        // Act
        List<AssetResponse> results = duxManagerDataProviderRest.findAssetsByCriteria(
                jwt, digitalUserId, externalIds, groupId, artifactId, type, createdAt, from, to);

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(expectedResponses, results);

        verify(duxManagerHttpClient).findAssetsByCriteria(
                jwt, digitalUserId, externalIds, groupId, artifactId, type, createdAt, from, to);
    }

    @Test
    void shouldFindAssetsByCriteriaWithOnlyRequiredParameters() {
        // Arrange
        List<AssetResponse> expectedResponses = Arrays.asList(assetResponse1, assetResponse2);

        when(duxManagerHttpClient.findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull()))
                .thenReturn(expectedResponses);

        // Act
        List<AssetResponse> results = duxManagerDataProviderRest.findAssetsByCriteria(
                jwt, digitalUserId, null, null, null, null, null, null, null);

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(expectedResponses, results);

        verify(duxManagerHttpClient).findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), isNull(), isNull(), isNull(), isNull(), isNull(), isNull());
    }

    @Test
    void shouldFindAssetsByExternalId() {
        // Arrange
        String externalId = UUID.randomUUID().toString();
        List<AssetResponse> expectedResponses = Arrays.asList(assetResponse1);

        when(duxManagerHttpClient.findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), eq(externalId), anyString(), anyString(), anyString(),
                isNull(), isNull(), isNull()))
                .thenReturn(expectedResponses);

        // Act
        List<AssetResponse> results = duxManagerDataProviderRest.findAssetsByCriteria(
                jwt, digitalUserId, externalId, "com.tracktainment", "game-manager", "game",
                null, null, null);

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(expectedResponses, results);

        verify(duxManagerHttpClient).findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), eq(externalId), anyString(), anyString(), anyString(),
                isNull(), isNull(), isNull());
    }

    @Test
    void shouldFindAssetsByDateRange() {
        // Arrange
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();
        List<AssetResponse> expectedResponses = Arrays.asList(assetResponse1, assetResponse2);

        when(duxManagerHttpClient.findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), anyString(), anyString(), anyString(),
                isNull(), eq(from), eq(to)))
                .thenReturn(expectedResponses);

        // Act
        List<AssetResponse> results = duxManagerDataProviderRest.findAssetsByCriteria(
                jwt, digitalUserId, null, "com.tracktainment", "game-manager", "game",
                null, from, to);

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        assertEquals(expectedResponses, results);

        verify(duxManagerHttpClient).findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), anyString(), anyString(), anyString(),
                isNull(), eq(from), eq(to));
    }

    @Test
    void shouldFindAssetsByCreatedAtDate() {
        // Arrange
        LocalDate createdAt = LocalDate.now();
        List<AssetResponse> expectedResponses = Arrays.asList(assetResponse1);

        when(duxManagerHttpClient.findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), anyString(), anyString(), anyString(),
                eq(createdAt), isNull(), isNull()))
                .thenReturn(expectedResponses);

        // Act
        List<AssetResponse> results = duxManagerDataProviderRest.findAssetsByCriteria(
                jwt, digitalUserId, null, "com.tracktainment", "game-manager", "game",
                createdAt, null, null);

        // Assert
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(expectedResponses, results);

        verify(duxManagerHttpClient).findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), isNull(), anyString(), anyString(), anyString(),
                eq(createdAt), isNull(), isNull());
    }

    @Test
    void shouldCreateAssetWithAllParameters() {
        // Arrange
        when(duxManagerHttpClient.createAsset(jwt, digitalUserId, assetRequest))
                .thenReturn(assetResponse1);

        // Act
        AssetResponse result = duxManagerDataProviderRest.createAsset(jwt, digitalUserId, assetRequest);

        // Assert
        assertNotNull(result);
        assertEquals(assetResponse1, result);

        verify(duxManagerHttpClient).createAsset(jwt, digitalUserId, assetRequest);
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
    void shouldHandleEmptyAssetResponses() {
        // Arrange
        List<AssetResponse> emptyResponses = Arrays.asList();

        when(duxManagerHttpClient.findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), anyString(), anyString(), anyString(), anyString(),
                isNull(), isNull(), isNull()))
                .thenReturn(emptyResponses);

        // Act
        List<AssetResponse> results = duxManagerDataProviderRest.findAssetsByCriteria(
                jwt, digitalUserId, "externalId", "com.tracktainment", "game-manager", "game",
                null, null, null);

        // Assert
        assertNotNull(results);
        assertTrue(results.isEmpty());

        verify(duxManagerHttpClient).findAssetsByCriteria(
                eq(jwt), eq(digitalUserId), anyString(), anyString(), anyString(), anyString(),
                isNull(), isNull(), isNull());
    }

    @Test
    void shouldPropagateClientExceptions() {
        // Arrange
        RuntimeException exception = new RuntimeException("API connection error");
        when(duxManagerHttpClient.createAsset(jwt, digitalUserId, assetRequest))
                .thenThrow(exception);

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () ->
                duxManagerDataProviderRest.createAsset(jwt, digitalUserId, assetRequest));

        assertEquals("API connection error", thrown.getMessage());
        verify(duxManagerHttpClient).createAsset(jwt, digitalUserId, assetRequest);
    }
}
