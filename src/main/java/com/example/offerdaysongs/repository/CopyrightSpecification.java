package com.example.offerdaysongs.repository;

import com.example.offerdaysongs.model.Copyright;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;

public class CopyrightSpecification {

    public static Specification<Copyright> copyrightOwnedBy(final String companyName) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("company").get("name"), companyName);
    }

    public static Specification<Copyright> copyrightOnRecording(final String recordingName) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("recording").get("title"), recordingName);
    }

    public static Specification<Copyright> copyrightStartBeforeDate(final ZonedDateTime date) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), date);
    }

    public static Specification<Copyright> copyrightEndAfterDate(final ZonedDateTime date) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), date);
    }
}