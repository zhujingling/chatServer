package com.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
	public void start() throws Exception {
		Thread th=new Thread(new Runnable() {
			
			public void run() {
				// TODO Auto-generated method stub
				EventLoopGroup bossGroup = new NioEventLoopGroup();
				EventLoopGroup workerGroup = new NioEventLoopGroup();
				try {
					ServerBootstrap b = new ServerBootstrap();
					b.group(bossGroup, workerGroup)
							.channel(NioServerSocketChannel.class)
							.childHandler(new NettyServerInitializer())
							.option(ChannelOption.SO_BACKLOG, 128)
							.childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(64, 1024, 65536))
							.childOption(ChannelOption.SO_KEEPALIVE, true);

					ChannelFuture f = b.bind(8089).sync();

					f.channel().closeFuture().sync();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					workerGroup.shutdownGracefully();
					bossGroup.shutdownGracefully();
				}
			}
		});
		th.start();
//		EventLoopGroup bossGroup = new NioEventLoopGroup();
//		EventLoopGroup workerGroup = new NioEventLoopGroup();
//		try {
//			ServerBootstrap b = new ServerBootstrap();
//			b.group(bossGroup, workerGroup)
//					.channel(NioServerSocketChannel.class)
//					.childHandler(new NettyServerInitializer())
//					.option(ChannelOption.SO_BACKLOG, 128)
//					.childOption(ChannelOption.SO_KEEPALIVE, true);
//
//			ChannelFuture f = b.bind(8089).sync();
//
//			f.channel().closeFuture().sync();
//		} finally {
//			workerGroup.shutdownGracefully();
//			bossGroup.shutdownGracefully();
//		}
	}
}
