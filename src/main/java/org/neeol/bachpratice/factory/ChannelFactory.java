package org.neeol.bachpratice.factory;

import org.neeol.bachpratice.Channels.MailChannel;
import org.neeol.bachpratice.Channels.SmsChannel;
import org.neeol.bachpratice.Channels.WhatsappChannel;
import org.neeol.bachpratice.dto.MessageLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.neeol.bachpratice.enums.Channel.*;

@Component
public class ChannelFactory {

    @Autowired
    private MailChannel mailChannel;
    @Autowired
    private WhatsappChannel whatsappChannel;
    @Autowired
    private SmsChannel smsChannel;


    public void processMessages(List<MessageLogDTO> messages) throws Exception {

        for(MessageLogDTO messageLogDTO : messages){

            switch(messageLogDTO.getChannel()){

                case WHATSAPP:
                    whatsappChannel.processMessage(messageLogDTO.getMessage());
                    break;
                case EMAIL:
                    mailChannel.processMessage(messageLogDTO.getMessage());
                    break;
                case SMS:
                    smsChannel.processMessage(messageLogDTO.getMessage());
                    break;
                default:
                    throw new Exception();


            }
        }


    }

}
