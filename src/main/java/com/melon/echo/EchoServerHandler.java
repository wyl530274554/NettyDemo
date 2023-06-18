package com.melon.echo;

import com.melon.HelloNettyHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

public class EchoServerHandler extends HelloNettyHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead0(ctx, msg);

        ByteBuf bufData = Unpooled.buffer();
        bufData.writeBytes("server to client".getBytes());
        ctx.channel().writeAndFlush(bufData);
    }
}
