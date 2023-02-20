package com.testforelastic.service;

import co.elastic.clients.elasticsearch._types.query_dsl.GeoDistanceQuery;
import com.testforelastic.DTO.AdvancedSearchRequestDTO;
import com.testforelastic.DTO.BasicSearchDTO;
import com.testforelastic.DTO.GeolocationSearchDTO;
import com.testforelastic.model.Applicant;

import com.testforelastic.model.Coordinates;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;


@Service
public class ApplicantService {

    private ElasticsearchRestTemplate template;

    private final GeolocationService geolocationService;

    public ApplicantService(ElasticsearchRestTemplate template, GeolocationService geolocationService) {
        this.template = template;
        this.geolocationService = geolocationService;
    }

    public List<BasicSearchDTO> basicSearch(String searchedText){
        List<BasicSearchDTO> retVal = new ArrayList<BasicSearchDTO>();

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(searchedText)
                        .field("name")
                        .field("lastName")
                        .field("education")
                        .field("cvContent")
                        .field("coverLetterContent"))
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(150).numOfFragments(1).preTags("<b>").postTags("</b>"),
                        new HighlightBuilder.Field("coverLetterContent").fragmentSize(150).numOfFragments(1).preTags("<b>").postTags("</b>")
                )
                .build();

        SearchHits<Applicant> hits = template.search(searchQuery, Applicant.class);
        for(SearchHit<Applicant> hit: hits){
            retVal.add(BasicSearchDTO.builder().name(hit.getContent().getName())
                            .lastName(hit.getContent().getLastName())
                            .education(hit.getContent().getEducation())
                            .address(hit.getContent().getAddress())
                            .cvHighLight(hit.getHighlightField("cvContent").isEmpty()?"":hit.getHighlightField("cvContent").get(0))
                            .clHighLight(hit.getHighlightField("coverLetterContent").isEmpty()?"":hit.getHighlightField("coverLetterContent").get(0))
                            .build());
        }
        return retVal;
    }

    public List<BasicSearchDTO> advancedSearch(List<AdvancedSearchRequestDTO> searchedText){
        List<BasicSearchDTO> retVal = new ArrayList<BasicSearchDTO>();

        BoolQueryBuilder builder = QueryBuilders.boolQuery();

        for(AdvancedSearchRequestDTO searchRequest: searchedText){
            if(searchRequest.getOperator().equalsIgnoreCase("AND")){
                if(!searchRequest.isPhrased()) builder.must(QueryBuilders.matchQuery(searchRequest.getFieldName(),searchRequest.getValue()));
                else builder.must(QueryBuilders.matchPhraseQuery(searchRequest.getFieldName(),searchRequest.getValue()));
            } else if(searchRequest.getOperator().equalsIgnoreCase("OR")){
                if(!searchRequest.isPhrased()) builder.should(QueryBuilders.matchQuery(searchRequest.getFieldName(),searchRequest.getValue()));
                else builder.should(QueryBuilders.matchPhraseQuery(searchRequest.getFieldName(),searchRequest.getValue()));
            }
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withHighlightFields(new HighlightBuilder.Field("cvContent").fragmentSize(150).numOfFragments(1).preTags("<b>").postTags("</b>"),
                        new HighlightBuilder.Field("coverLetterContent").fragmentSize(150).numOfFragments(1).preTags("<b>").postTags("</b>")
                )
                .build();

        SearchHits<Applicant> hits = template.search(searchQuery, Applicant.class);
        for(SearchHit<Applicant> hit: hits){
            retVal.add(BasicSearchDTO.builder().name(hit.getContent().getName())
                    .lastName(hit.getContent().getLastName())
                    .education(hit.getContent().getEducation())
                    .address(hit.getContent().getAddress())
                    .cvHighLight(hit.getHighlightField("cvContent").isEmpty()?"":hit.getHighlightField("cvContent").get(0))
                    .clHighLight(hit.getHighlightField("coverLetterContent").isEmpty()?"":hit.getHighlightField("coverLetterContent").get(0))
                    .build());
        }
        return retVal;
    }


    public List<GeolocationSearchDTO> geolocationSearch(String city, double radius) throws IOException, InterruptedException {
        List<GeolocationSearchDTO> retVal = new ArrayList<GeolocationSearchDTO>();

        GeoPoint geoPoint = geolocationService.getCoordinatesBasedOnAddress(city);

        GeoDistanceQueryBuilder geoDistanceQueryBuilder = new GeoDistanceQueryBuilder("location")
                .point(geoPoint.getLat(),geoPoint.getLon())
                .distance(radius, DistanceUnit.KILOMETERS);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(geoDistanceQueryBuilder)
                .build();

        SearchHits<Applicant> hits = template.search(searchQuery, Applicant.class);
        for(SearchHit<Applicant> hit: hits){
            retVal.add(GeolocationSearchDTO.builder().name(hit.getContent().getName())
                    .lastName(hit.getContent().getLastName())
                    .education(hit.getContent().getEducation())
                    .address(hit.getContent().getAddress())
                    .build());
        }
        return retVal;
    }
}
