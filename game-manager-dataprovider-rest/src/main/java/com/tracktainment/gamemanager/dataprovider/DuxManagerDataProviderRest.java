package com.tracktainment.gamemanager.dataprovider;

import com.tracktainment.gamemanager.client.DuxManagerHttpClient;
import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DuxManagerDataProviderRest implements DuxManagerDataProvider {

    private final DuxManagerHttpClient duxManagerHttpClient;

    @Override
    public AssetResponse createAsset(
            String jwt,
            String digitalUserId,
            AssetRequest assetRequest
    ) {
        return duxManagerHttpClient.createAsset(
                jwt,
                digitalUserId,
                assetRequest
        );
    }

    @Override
    public List<AssetResponse> findAssetsByCriteria(
            String jwt,
            String digitalUserId,
            String externalIds,
            String groupId,
            String artifactId,
            String type,
            LocalDate createdAt,
            LocalDate from,
            LocalDate to
    ) {
        return duxManagerHttpClient.findAssetsByCriteria(
                jwt,
                digitalUserId,
                externalIds,
                groupId,
                artifactId,
                type,
                createdAt,
                from,
                to
        );
    }

    @Override
    public void deleteAsset(
            String jwt,
            String digitalUserId,
            String externalId
    ) {
        duxManagerHttpClient.deleteAssetByExternalId(
                jwt,
                digitalUserId,
                externalId
        );
    }
}
