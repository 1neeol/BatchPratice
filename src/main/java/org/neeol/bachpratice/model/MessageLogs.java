package org.neeol.bachpratice.model;

import jakarta.persistence.*;
import lombok.Data;
import org.neeol.bachpratice.dto.MessageLogDTO;

import java.time.LocalDateTime;

@Entity
@Data
public class MessageLogs {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String UUID;
    private String channel;
    private String recipient;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @ManyToOne()
    @JoinColumn(name = "campaign_id")
    private Campaigns campaign;
    private String status;
    private String payload;

    public MessageLogs(){};

    public MessageLogs(MessageLogDTO messageLogDTO,  Campaigns campaign){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.channel = messageLogDTO.getChannel();
        this.recipient = messageLogDTO.getRecipient();
        this.payload = messageLogDTO.getPayload();
        this.status = messageLogDTO.getStatus();
        this.campaign = campaign;
    }

    @PrePersist
    protected void onCreate() {
        if(this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
