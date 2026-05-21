package org.neeol.bachpratice.repository;

import org.neeol.bachpratice.model.Campaigns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignsRepository extends JpaRepository<Campaigns,Long> {


    Optional<Campaigns> findById(String id);
}
