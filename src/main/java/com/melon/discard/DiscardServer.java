package com.melon.discard;

import com.melon.HelloNettyServer;

public class DiscardServer {
    public static void main(String[] args) throws Exception {
        new HelloNettyServer().setChannelHandler(new DiscardServerHandler()).run();
    }
}
