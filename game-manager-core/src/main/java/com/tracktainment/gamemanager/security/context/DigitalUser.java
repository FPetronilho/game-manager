package com.tracktainment.gamemanager.security.context;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DigitalUser {

    private String id;
    private String subject;
    private IdentityProvider identityProvider;
    private String tenantId;

    @ToString
    @Getter
    @RequiredArgsConstructor
    public enum IdentityProvider {

        GOOGLE_IDENTITY_PLATFORM("googleIdentityPlatform"),
        APPLE_ID("appleId"),
        MICROSOFT_ENTRA_ID("microsoftEntraId"),
        AMAZON_COGNITO("amazonCognito");

        private final String value;
    }
}
