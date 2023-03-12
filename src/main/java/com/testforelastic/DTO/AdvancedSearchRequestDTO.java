package com.testforelastic.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdvancedSearchRequestDTO {
    private String fieldName;
    private String value;
    private boolean phrased;
    private String operator;
}
