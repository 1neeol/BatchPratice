package org.neeol.bachpratice.repository.custom;

import org.neeol.bachpratice.dto.AnalyticsDTO;
import org.neeol.bachpratice.model.MessageLogs;
import org.springframework.data.jpa.domain.PredicateSpecification;

import java.util.List;

public interface MessageLogsRepositoryCustom {
    List<AnalyticsDTO> countMessageLogsGroupByStatusAndChannel(PredicateSpecification<MessageLogs> spec);

    void saveInBatch(List<MessageLogs> messageLogs);
}
