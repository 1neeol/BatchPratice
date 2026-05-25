package org.neeol.bachpratice.Channels;

import org.neeol.bachpratice.interfaces.MessageSenderStrategy;
import org.springframework.stereotype.Component;

@Component
public class MailChannel implements MessageSenderStrategy {

    @Override
    public void processMessage(String message) {
        System.out.println("Sending message to Mail: " + message);
    }

    @Override
    public boolean supporter(String channelName){
        return channelName.equals("EMAIL");
    }
}
