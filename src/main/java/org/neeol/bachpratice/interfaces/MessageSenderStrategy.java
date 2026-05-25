package org.neeol.bachpratice.interfaces;

public interface MessageSenderStrategy {

    void processMessage(String message);

    boolean supporter(String channelName);

}
