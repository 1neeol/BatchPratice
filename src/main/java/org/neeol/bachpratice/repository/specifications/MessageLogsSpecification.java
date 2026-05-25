package org.neeol.bachpratice.repository.specifications;


import org.neeol.bachpratice.model.MessageLogs;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class MessageLogsSpecification {

    public static PredicateSpecification<MessageLogs> campaignId(String uuid){

        return (from, builder) -> {

            if(!StringUtils.hasText(uuid)){
                return builder.conjunction();
            }

            return builder.equal(from.get("campaign").get("id"), uuid);
        };
    }

    public static PredicateSpecification<MessageLogs> channel(String channel){

        return (from, builder) -> {
            if(!StringUtils.hasText(channel)){
                return builder.conjunction();
            }

            return builder.like(from.get("channel"), channel);
        };
    }

    public static PredicateSpecification<MessageLogs> startDate(String startDate){
        return (from, builder) -> {
            if(startDate == null){
                return builder.conjunction();
            }

            LocalDate dateFormated = LocalDate.parse(startDate);


            return builder.greaterThan(from.get("updatedAt"), dateFormated);
        };
    };

    public static PredicateSpecification<MessageLogs> endDate(String endDate){
        return (from, builder) -> {
            if(endDate == null){
                return builder.conjunction();
            }

            LocalDate dateFormated = LocalDate.parse(endDate);

            return builder.lessThan(from.get("updatedAt"), dateFormated);
        };
    };
}
