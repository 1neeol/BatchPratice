package org.neeol.bachpratice.enums;

public enum Channel {
    WHATSAPP("WHATSAPP"),
    EMAIL("EMAIL"),
    SMS("SMS");

    public final String value;

    Channel(String s){
        value = s;
    }
}
