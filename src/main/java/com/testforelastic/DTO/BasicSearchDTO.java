package com.testforelastic.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicSearchDTO {
    private String id;
    private String name;
    private String lastName;
    private String highLight;
}
