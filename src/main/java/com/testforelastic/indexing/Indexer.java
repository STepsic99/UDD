package com.testforelastic.indexing;

import com.testforelastic.indexing.handlers.DocumentHandler;
import com.testforelastic.indexing.handlers.PDFHandler;
import com.testforelastic.model.Applicant;
import com.testforelastic.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Indexer {
    @Autowired
    private ApplicantRepository repository;

    public Indexer() {
    }

    public DocumentHandler getHandler(String fileName){
       if(fileName.endsWith(".pdf")){
            return new PDFHandler();
        }else{
            return null;
        }
    }

    public boolean add(Applicant unit){
        unit = repository.save(unit);
        if(unit!=null)
            return true;
        else
            return false;
    }

}
