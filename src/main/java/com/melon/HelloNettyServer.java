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
        EventLoopGroup bossGroup = new NioEventLoopGroup(1); //处理Server Channel所有事件和IO
        EventLoopGroup workerGroup = new NioEventLoopGroup(); //处理Client Channel所有事件和IO
        ServerBootstrap bootstrap = new ServerBootstrap(); //启动器
        bootstrap.group(bossGroup, workerGroup) //指定工作组
                .channel(NioServerSocketChannel.class) //注册server channel
                .childHandler(new ChannelInitializer<SocketChannel>() { //注册客户端ChannelHandler
                    /**
                     * 通道注册后调用，每个客户端的连接都会调用一次
                     * @param socketChannel 此次连接的channel
                     */
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        System.out.println("init client channel");
                        socketChannel.pipeline().addLast(new HelloNettyServerHandler());
                    }
                })
                .option(ChannelOption.SO_BACKLOG, 128) //设置server 全连接队列的大小
                .childOption(ChannelOption.SO_KEEPALIVE, true); //设置client channel TCP参数
        /*端口绑定、启动服务*/
        ChannelFuture channelFuture = bootstrap.bind(port).sync();
        /*为异步IO操作通知接口注册监听器*/
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
        bossGroup.shutdownGracefully();

        System.out.println("服务器已关闭");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("服务器启动中...");
        int port = 8888;
        new HelloNettyServer(port).run();
    }
}
