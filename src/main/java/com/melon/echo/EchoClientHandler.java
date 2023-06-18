package com.melon.echo;

import com.melon.HelloNettyHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

public class EchoClientHandler extends HelloNettyHandler {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("client to server", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead0(ctx, msg);

        ctx.channel().close();
    }
}
