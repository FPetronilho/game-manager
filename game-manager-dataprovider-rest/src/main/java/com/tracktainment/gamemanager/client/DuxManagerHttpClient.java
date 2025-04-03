package com.tracktainment.gamemanager.client;

import com.tracktainment.gamemanager.dto.duxmanager.request.AssetRequest;
import com.tracktainment.gamemanager.dto.duxmanager.response.AssetResponse;
import com.tracktainment.gamemanager.util.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(
        name = "dux-manager-http-client",
        url = "${http.url.dux-manager}",
        configuration = FeignClient.class
)
@Validated
public interface DuxManagerHttpClient {

    @PostMapping("/assets/digitalUsers/{digitalUserId}")
    AssetResponse createAsset(
            @PathVariable
            @Pattern(regexp = Constants.ID_REGEX, message = Constants.DIGITAL_USER_ID_INVALID_MSG) String digitalUserId,

            @RequestBody @Valid AssetRequest assetRequest
    );

    @GetMapping("/assets")
    List<AssetResponse> findAssetsByCriteria(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_OFFSET)
            @Min(value = Constants.MIN_OFFSET, message = Constants.OFFSET_INVALID_MSG) Integer offset,

            @RequestParam(required = false, defaultValue = Constants.DEFAULT_LIMIT)
            @Min(value = Constants.MIN_LIMIT, message = Constants.LIMIT_INVALID_MSG)
            @Max(value = Constants.MAX_LIMIT, message = Constants.LIMIT_INVALID_MSG) Integer limit,

            @RequestParam(required = false)
            @Pattern(regexp = Constants.ID_REGEX, message = Constants.DIGITAL_USER_ID_INVALID_MSG) String digitalUserId,

            @RequestParam(required = false)
            @Pattern(regexp = Constants.ID_LIST_REGEX, message = Constants.IDS_INVALID_MSG) String externalIds,

            @RequestParam(required = false)
            @Pattern(regexp = Constants.GROUP_ID_REGEX, message = Constants.GROUP_ID_INVALID_MSG) String groupId,

            @RequestParam(required = false)
            @Pattern(regexp = Constants.ARTIFACT_ID_REGEX, message = Constants.ARTIFACT_ID_INVALID_MSG) String artifactId,

            @RequestParam(required = false)
            @Pattern(regexp = Constants.TYPE_REGEX, message = Constants.TYPE_INVALID_MSG) String type,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdAt,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    );

    @DeleteMapping("/assets")
    void deleteAssetByExternalId(
            @RequestParam
            @Pattern(regexp = Constants.ID_REGEX, message = Constants.DIGITAL_USER_ID_INVALID_MSG) String digitalUserId,

            @RequestParam
            @Pattern(regexp = Constants.ID_REGEX, message = Constants.ID_REGEX) String externalId
    );
}
