package org.neeol.bachpratice.service;

import org.neeol.bachpratice.dto.MessageLogDTO;
import org.neeol.bachpratice.factory.ChannelFactory;
import org.neeol.bachpratice.model.Campaigns;
import org.neeol.bachpratice.model.MessageLogs;
import org.neeol.bachpratice.repository.MessageLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private AnalyticsService analyticsService;
    @Autowired
    private MessageLogsRepository messageLogsRepository;
    @Autowired
    private CampaignsService campaignsService;
    @Autowired
    private ChannelFactory channelFactory;

    private final int BATCH_SIZE = 50;

    public void processBatch(List<MessageLogDTO> request) throws Exception {

        int toProcessCount = 0;

        Map<String, List<MessageLogDTO>> messageLogDTOMap = request.stream()
                .collect(Collectors.groupingBy(MessageLogDTO::getCampaignId));

        List<MessageLogDTO> batch = new ArrayList<>();

        for(String  campaignId : messageLogDTOMap.keySet()){

            Campaigns campaigns = campaignsService.findById(campaignId).orElseThrow(IllegalArgumentException::new);

            for(MessageLogDTO messageLogDTO : messageLogDTOMap.get(campaignId)){

                if(toProcessCount < BATCH_SIZE){
                    batch.add(messageLogDTO);
                    request.remove(messageLogDTO);
                    toProcessCount++;
                } else {
                    channelFactory.processMessages(batch);
                    saveBatch(batch, campaigns);
                    batch.clear();
                    toProcessCount = 0;
                }
            }
        }
    }

    private void saveBatch(List<MessageLogDTO> batch, Campaigns campaigns){

        List<MessageLogs> messages = batch.stream().map(messageLogDTO -> new MessageLogs(messageLogDTO, campaigns)).collect(Collectors.toList());

        messageLogsRepository.saveAll(messages);
    }

}
