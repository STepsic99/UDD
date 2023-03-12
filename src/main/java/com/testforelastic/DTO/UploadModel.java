package com.testforelastic.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadModel {
    private String name;
    private String lastName;
    private String education;
    private String address;
    private MultipartFile[] files;
}
