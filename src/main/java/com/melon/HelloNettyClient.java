package com.melon;

import com.melon.HelloNettyHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class HelloNettyClient {
    private static final String HOST = "127.0.0.1"; //服务器地址
    private static final int PORT = 8080; //服务器端口

    private ChannelHandler channelHandler = new HelloNettyHandler();

    public HelloNettyClient setChannelHandler(ChannelHandler channelHandler) {
        this.channelHandler = channelHandler;

        return this;
    }

    public void run() throws Exception {
        EventLoopGroup loopGroup = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(channelHandler);
                    }
                });
        ChannelFuture channelFuture = bootstrap.connect(HOST, PORT).sync();
        channelFuture.channel().closeFuture().sync();

        loopGroup.shutdownGracefully();
        System.out.println("HelloNettyClient已退出");
    }
}
