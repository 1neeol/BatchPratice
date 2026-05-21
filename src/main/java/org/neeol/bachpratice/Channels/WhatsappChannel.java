package org.neeol.bachpratice.Channels;

import org.neeol.bachpratice.interfaces.MessageSenderStrategy;
import org.springframework.stereotype.Component;

@Component
public class WhatsappChannel implements MessageSenderStrategy {


    @Override
    public void processMessage(String message) {
        System.out.println("Sending message to Whatsapp: " + message);
    }
}
