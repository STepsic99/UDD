package com.testforelastic.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeolocationSearchDTO {
    private String name;
    private String lastName;
    private String education;
    private String address;
}
