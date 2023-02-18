package com.testforelastic.repository;

import com.testforelastic.model.Applicant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicantRepository extends ElasticsearchRepository<Applicant, String> {
    List<Applicant> findByName(String name);
}
