package org.neeol.bachpratice.repository;

import org.neeol.bachpratice.model.Campaigns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignsRepository extends JpaRepository<Campaigns,Long>, JpaSpecificationExecutor<Campaigns> {

    Optional<Campaigns> findById(String id);


}
