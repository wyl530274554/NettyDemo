package com.melon.discard;

import com.melon.HelloNettyClient;

public class DiscardClient {
    public static void main(String[] args) throws Exception {
        new HelloNettyClient().setChannelHandler(new DiscardClientHandler()).run();
    }
}
