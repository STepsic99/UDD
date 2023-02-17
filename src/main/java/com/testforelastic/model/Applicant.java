package com.testforelastic.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "applicant")
public class Applicant {
    @Id
    private String id;
    private String name;
    private String lastName;
}
