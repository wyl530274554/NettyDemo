package com.melon;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class HelloNettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 不能调用super.channelRead(ctx, msg);
     * 否则会报io.netty.util.IllegalReferenceCountException: refCnt: 0
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf msgBuf = (ByteBuf) msg;
        while (msgBuf.isReadable()){
            System.out.print((char)msgBuf.readByte());
            System.out.flush();
        }
        System.out.println();
        ReferenceCountUtil.release(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
