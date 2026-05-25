package org.neeol.bachpratice.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.neeol.bachpratice.dto.AnalyticsDTO;
import org.neeol.bachpratice.model.MessageLogs;
import org.neeol.bachpratice.repository.custom.MessageLogsRepositoryCustom;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MessageLogsRepositoryImpl implements MessageLogsRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<AnalyticsDTO> countMessageLogsGroupByStatusAndChannel(PredicateSpecification<MessageLogs> spec) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<AnalyticsDTO> query = cb.createQuery(AnalyticsDTO.class);
        Root<MessageLogs> root = query.from(MessageLogs.class);

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, cb);
            if (predicate != null) {
                query.where(predicate);
            }
        }

        query.groupBy(root.get("status"), root.get("channel"));

        query.select(cb.construct(
                AnalyticsDTO.class,
                root.get("channel"),
                root.get("status"),
                cb.count(root)
        ));

        return em.createQuery(query).getResultList();
    }

    @Override
    public void saveInBatch(List<MessageLogs> messageLogs) {
        int batchSize = 50;

        for (int i = 0; i < messageLogs.size(); i++) {
            em.persist(messageLogs.get(i));
            if (i % batchSize == 0 && i > 0) {
                em.flush();
                em.clear();
            }
        }
    }

}
