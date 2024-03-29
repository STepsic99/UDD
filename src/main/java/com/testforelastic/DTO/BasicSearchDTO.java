package com.testforelastic.DTO;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BasicSearchDTO {
    private String name;
    private String lastName;
    private String education;
    private String address;
    private String cvHighLight;
    private String clHighLight;

}
