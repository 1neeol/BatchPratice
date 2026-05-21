package org.neeol.bachpratice.Channels;

import org.neeol.bachpratice.interfaces.MessageSenderStrategy;
import org.springframework.stereotype.Component;

@Component
public class SmsChannel implements MessageSenderStrategy {
    @Override
    public void processMessage(String message) {
        System.out.println("Sending message to SMS: " + message);
    }
}
