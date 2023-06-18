package com.melon.echo;

import com.melon.HelloNettyClient;
import com.melon.discard.DiscardClientHandler;

public class EchoClient {
    public static void main(String[] args) throws Exception {
        new HelloNettyClient().setChannelHandler(new EchoClientHandler()).run();
    }
}
