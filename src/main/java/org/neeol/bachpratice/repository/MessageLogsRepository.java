package org.neeol.bachpratice.repository;

import org.neeol.bachpratice.dto.AnalyticsDTO;
import org.neeol.bachpratice.model.MessageLogs;
import org.neeol.bachpratice.repository.custom.MessageLogsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface MessageLogsRepository extends JpaRepository<MessageLogs,Long>
        , JpaSpecificationExecutor<MessageLogs>
        , MessageLogsRepositoryCustom {

    @Query(value = "select new org.neeol.bachpratice.dto.AnalyticsDTO(ml.channel, ml.status, COUNT(*)) from MessageLogs ml where ml.campaign.id = :campaignId" +
            " and ml.channel = :channel and ml.updatedAt between :startDate and :endDate group by ml.channel, ml.status")
    List<AnalyticsDTO> findUsingJpql(String campaignId, String channel, LocalDate startDate, LocalDate endDate);
}
