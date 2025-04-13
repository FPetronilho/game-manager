package com.tracktainment.gamemanager.dataprovider;

import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;

import java.time.LocalDate;
import java.util.List;

public interface DuxManagerDataProvider {

    AssetResponse createAsset(String digitalUserId, AssetRequest assetRequest);

    List<AssetResponse> findAssetsByCriteria(
            String digitalUserId,
            String externalIds,
            String groupId,
            String artifactId,
            String type,
            LocalDate createdAt,
            LocalDate from,
            LocalDate to
    );

    void deleteAsset(String digitalUserId, String externalId);
}
