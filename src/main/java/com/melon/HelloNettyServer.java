package com.melon;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HelloNettyServer {
    private int port;

    private HelloNettyServer(int port) {
        this.port = port;
    }

    private void run() throws Exception {
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class) //注册server channel类型
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    /**
                     * 通道注册后调用，每个客户端的连接都会调用一次
                     * @param socketChannel 此次连接的channel
                     */
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("init client channel");
                        socketChannel.pipeline().addLast(new HelloNettyServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) //设置全连接队列的大小
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        /*端口绑定*/
        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        /*监听server channel IO操作的结果*/
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                boolean success = future.isSuccess();
                if (success) {
                    System.out.println("服务器启动成功");
                } else {
                    System.out.println("服务器启动失败");
                }
            }
        });

        //同步server关闭
        channelFuture.channel().closeFuture().sync();

        //退出
        workerGroup.shutdownGracefully();
        boosGroup.shutdownGracefully();

        System.out.println("服务器已关闭");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("服务器启动中...");
        int port = 8888;
        new HelloNettyServer(port).run();
    }
}
