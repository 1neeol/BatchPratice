package org.neeol.bachpratice.dto;

import lombok.Data;

@Data
public class MessageLogDTO {
    private String message;
    private String status;
    private String channel;
    private String recipient;
    private String payload;
    private String campaignId;
}
