package com.tracktainment.gamemanager.security.context;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(description = "Digital user security context information")
public class DigitalUser {

    @Schema(description = "Digital user unique identifier", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "Subject identifier from identity provider", example = "auth2|123456789")
    private String subject;
}
