package org.neeol.bachpratice.controller;

import jakarta.annotation.Nullable;
import org.neeol.bachpratice.dto.AnalyticsDTO;
import org.neeol.bachpratice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/analytics")
public class AnalyticsController {

    @Autowired
    private MessageService messageService;


    @GetMapping("/criteria/{campaignId}")
    public List<AnalyticsDTO> findByCampaignIdCriteria(@PathVariable String campaignId
            ,@Nullable @RequestParam("channel") String channel
            ,@Nullable @RequestParam("endDate") String endDate
            ,@Nullable @RequestParam("endDate") String startDate) throws Exception {
        return messageService.findAllByParamsCriteria(campaignId, channel, startDate, endDate);
    }

    @GetMapping("/jpql/{campaignId}")
    public List<AnalyticsDTO> findByCampaignIdJpql(@PathVariable String campaignId
            ,@Nullable @RequestParam("channel") String channel
            ,@Nullable @RequestParam("endDate") String endDate
            ,@Nullable @RequestParam("endDate") String startDate) throws Exception {
        return messageService.findAllByParamsJpql(campaignId, channel, startDate, endDate);
    }
}
