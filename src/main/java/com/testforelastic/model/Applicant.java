package com.testforelastic.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.elasticsearch.common.geo.GeoPoint;

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
    private String education;
    private String cvContent;
    private String coverLetterContent;
    private String address;
    @GeoPointField
    private GeoPoint location;
}
