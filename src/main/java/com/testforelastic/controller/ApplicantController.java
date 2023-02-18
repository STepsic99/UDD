package com.testforelastic.controller;

import com.testforelastic.DTO.BasicSearchDTO;
import com.testforelastic.model.Applicant;
import com.testforelastic.repository.ApplicantRepository;
import org.springframework.data.elasticsearch.client.elc.QueryBuilders;
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

    private final ApplicantRepository applicantRepository;

    private final ElasticsearchOperations elasticsearchOperations;

    public ApplicantController(ApplicantRepository applicantRepository, ElasticsearchOperations elasticsearchOperations){
        this.applicantRepository = applicantRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @PostMapping(value = "/", consumes = "application/json")
    public Applicant create(@RequestBody Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    @GetMapping
    public Applicant findByName(@RequestParam String name) {
        var query = new CriteriaQuery(new Criteria("name").contains("Ime1"));
        SearchHit<Applicant> foundApplicant = elasticsearchOperations.searchOne(query, Applicant.class);
        System.out.println(foundApplicant);
        return foundApplicant.getContent();
    }

    @GetMapping(value = "/basic-search")
    public List<Applicant> basicSearch(@RequestParam String searchedText){
        List<Applicant> applicants = applicantRepository.findByName(searchedText);
        return applicants;
    }

}
