package com.melon.discard;

import com.melon.HelloNettyHandler;
import io.netty.channel.ChannelHandlerContext;

public class DiscardServerHandler extends HelloNettyHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //丢弃
        System.out.println("丢弃");
    }
}
