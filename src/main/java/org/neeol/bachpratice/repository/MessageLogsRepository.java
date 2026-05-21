package org.neeol.bachpratice.repository;

import org.neeol.bachpratice.model.MessageLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageLogsRepository extends JpaRepository<MessageLogs,Long> {
}
