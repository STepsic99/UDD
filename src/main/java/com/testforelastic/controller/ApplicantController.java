package com.testforelastic.controller;

import com.testforelastic.DTO.AdvancedSearchRequestDTO;
import com.testforelastic.DTO.BasicSearchDTO;
import com.testforelastic.DTO.GeolocationSearchDTO;
import com.testforelastic.model.Applicant;
import com.testforelastic.service.ApplicantService;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/applicants")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApplicantController {

    private final ApplicantService applicantService;


    public ApplicantController(ApplicantService applicantService){
        this.applicantService = applicantService;
    }

    @GetMapping(value = "/basic-search")
    public List<BasicSearchDTO> basicSearch(@RequestParam String searchedText){
        return applicantService.basicSearch(searchedText);
    }

    @PostMapping(value = "/advanced-search")
    public List<BasicSearchDTO> advancedSearch(@RequestBody List<AdvancedSearchRequestDTO> searchedText){
        return applicantService.advancedSearch(searchedText);
    }

    @GetMapping(value = "/geolocation-search")
    public List<GeolocationSearchDTO> geolocationSearch(@RequestParam String city, @RequestParam double radius) throws IOException, InterruptedException {
        return applicantService.geolocationSearch(city,radius);
    }

}
