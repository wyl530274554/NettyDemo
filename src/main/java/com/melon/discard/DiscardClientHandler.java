package com.melon.discard;

import com.melon.HelloNettyHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class DiscardClientHandler extends HelloNettyHandler {
    private static final int SIZE = 256;
    private ByteBuf content;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("DiscardClientHandler channelActive");
        content = ctx.alloc().directBuffer(SIZE).writeZero(SIZE);
        repeatSendContent(ctx);
    }

    private void repeatSendContent(ChannelHandlerContext ctx) throws InterruptedException {
        ctx.writeAndFlush(content.retainedDuplicate()).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (channelFuture.isSuccess()) {
                    repeatSendContent(ctx);
                }
            }
        });

        Thread.sleep(1000);
    }
}
