package org.neeol.bachpratice.service;

import org.neeol.bachpratice.dto.AnalyticsDTO;
import org.neeol.bachpratice.dto.MessageLogDTO;
import org.neeol.bachpratice.factory.ChannelFactory;
import org.neeol.bachpratice.model.Campaigns;
import org.neeol.bachpratice.model.MessageLogs;
import org.neeol.bachpratice.repository.MessageLogsRepository;
import org.neeol.bachpratice.repository.specifications.MessageLogsSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.PredicateSpecification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @Autowired
    private MessageLogsRepository messageLogsRepository;
    @Autowired
    private CampaignsService campaignsService;
    @Autowired
    private ChannelFactory channelFactory;
    @Autowired
    private TransactionTemplate transactionTemplate;

    private final int BATCH_SIZE = 100;

    public void processBatch(List<MessageLogDTO> request) throws Exception {

        Map<String, List<MessageLogDTO>> messageLogDTOMap = request.stream()
                .collect(Collectors.groupingBy(MessageLogDTO::getCampaignId));

        List<MessageLogDTO> batchToProcess = new ArrayList<>();

        for(String  campaignId : messageLogDTOMap.keySet()){

            Campaigns campaigns = campaignsService.findById(campaignId)
                    .orElseThrow(IllegalArgumentException::new);

            List<MessageLogDTO> itensToProcess = messageLogDTOMap.get(campaignId);

            for(MessageLogDTO messageLogDTO : itensToProcess){

                batchToProcess.add(messageLogDTO);

                if(batchToProcess.size() >= BATCH_SIZE ||
                        (itensToProcess.size() < BATCH_SIZE && batchToProcess.size() == itensToProcess.size())) {
                    channelFactory.processMessages(batchToProcess);
                    saveBatch(batchToProcess, campaigns);
                    batchToProcess.clear();
                }
            }
        }
    }

    public List<AnalyticsDTO> findAllByParamsJpql(String campaignId, String channel, String startDate, String endDate) throws Exception {

        LocalDate start = LocalDate.parse(startDate);

        LocalDate end = LocalDate.parse(endDate);

        return messageLogsRepository.findUsingJpql(campaignId, channel, start, end);
    };

    public List<AnalyticsDTO> findAllByParamsCriteria(String campaignId, String channel, String startDate, String endDate) throws Exception {

        PredicateSpecification<MessageLogs> spec = MessageLogsSpecification.campaignId(campaignId)
                .and(MessageLogsSpecification.channel(channel))
                .and(MessageLogsSpecification.startDate(startDate))
                .and(MessageLogsSpecification.endDate(endDate));

        return messageLogsRepository.countMessageLogsGroupByStatusAndChannel(spec);
    };


    private void saveBatch(List<MessageLogDTO> batch, Campaigns campaigns){

        System.out.println("Processado: " + batch.size());

        List<MessageLogs> messages = batch.stream()
                .map(messageLogDTO -> new MessageLogs(messageLogDTO, campaigns))
                .collect(Collectors.toList());

        transactionTemplate.execute(status -> {
            messageLogsRepository.saveInBatch(messages);
            return null;
        });
    }

}
