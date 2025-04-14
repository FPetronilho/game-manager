package com.tracktainment.gamemanager.security.context;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class DigitalUser {

    private String id;
    private String subject;
}
