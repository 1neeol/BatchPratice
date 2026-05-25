package org.neeol.bachpratice.repository;

import org.neeol.bachpratice.model.MessageLogs;
import org.neeol.bachpratice.repository.custom.MessageLogsRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface MessageLogsRepository extends JpaRepository<MessageLogs,Long>
        , JpaSpecificationExecutor<MessageLogs>
        , MessageLogsRepositoryCustom {
}
