package org.neeol.bachpratice.service;

import org.neeol.bachpratice.model.Campaigns;
import org.neeol.bachpratice.repository.CampaignsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CampaignsService {

    @Autowired
    private CampaignsRepository campaignsRepository;

    public Optional<Campaigns> findById(String id){
      return campaignsRepository.findById(id);
    };
}
