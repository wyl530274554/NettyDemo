package com.melon.echo;

import com.melon.HelloNettyServer;
import com.melon.discard.DiscardServerHandler;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        new HelloNettyServer().setChannelHandler(new EchoServerHandler()).run();
    }
}
