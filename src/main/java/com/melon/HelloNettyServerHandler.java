package com.melon;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * SimpleChannelInboundHandler不需要释放msg，它内部自动处理
 * ChannelInboundHandlerAdapter不能调用super.channelRead()，而且需要手动调用方法释放msg
 * ReferenceCountUtil.release(msg);
 */
public class HelloNettyServerHandler extends SimpleChannelInboundHandler {
    /**
     * 有数据可操作
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf msgBuf = (ByteBuf) msg;
        while (msgBuf.isReadable()) {
            System.out.print((char) msgBuf.readByte());
            System.out.flush();
        }
        System.out.println();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
