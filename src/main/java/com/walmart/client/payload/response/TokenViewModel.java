package com.walmart.client.payload.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TokenViewModel {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
}
