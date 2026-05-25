package org.neeol.bachpratice.factory;

import org.neeol.bachpratice.Channels.MailChannel;
import org.neeol.bachpratice.Channels.SmsChannel;
import org.neeol.bachpratice.Channels.WhatsappChannel;
import org.neeol.bachpratice.dto.MessageLogDTO;
import org.neeol.bachpratice.enums.Channel;
import org.neeol.bachpratice.interfaces.MessageSenderStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static org.neeol.bachpratice.enums.Channel.*;

@Component
public class ChannelFactory {

    @Autowired
    private List<MessageSenderStrategy> messageSenderStrategies;

    public void processMessages(List<MessageLogDTO> messages) throws Exception {
        for (MessageLogDTO message : messages) {
            for (MessageSenderStrategy senderStrategy : messageSenderStrategies) {
                if (senderStrategy.supporter(message.getChannel())) {
                    senderStrategy.processMessage(message.getMessage());
                    break;
                }
            }
        }
    }

}
