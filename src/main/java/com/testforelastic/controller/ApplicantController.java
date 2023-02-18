package com.testforelastic.controller;

import com.testforelastic.DTO.BasicSearchDTO;
import com.testforelastic.model.Applicant;
import com.testforelastic.service.ApplicantService;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.web.bind.annotation.*;

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

}
