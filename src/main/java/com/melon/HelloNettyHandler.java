package com.melon;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * SimpleChannelInboundHandler不需要释放msg，它内部自动处理
 * ChannelInboundHandlerAdapter不能调用super.channelRead()，而且需要手动调用方法释放msg
 * ReferenceCountUtil.release(msg);
 */
@ChannelHandler.Sharable
public class HelloNettyHandler extends SimpleChannelInboundHandler {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HelloNettyHandler channelActive");
    }

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
        System.out.println("exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive");
        ctx.close();
    }

    /**
     * 客户端连接断开后，会先后调用handler类中的channelInactive和channelUnregistered
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("channelUnregistered");
    }

}
