package com.testforelastic.repository;

import com.testforelastic.model.Applicant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicantRepository extends ElasticsearchRepository<Applicant, String> {
}
