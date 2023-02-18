package com.testforelastic.indexing.handlers;

import com.testforelastic.model.Applicant;

import java.io.File;

public abstract class DocumentHandler {
    /**
     * Od prosledjene datoteke se konstruise Lucene Document
     *
     * @param file
     *            datoteka u kojoj se nalaze informacije
     * @return Lucene Document
     */
    public abstract Applicant getIndexUnit(File file);
    public abstract String getText(File file);

    public abstract Applicant getUpgradedIndexUnit(File file,  Applicant retVal);

}
