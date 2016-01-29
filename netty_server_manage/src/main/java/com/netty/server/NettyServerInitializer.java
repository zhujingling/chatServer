package com.netty.server;

import java.util.Date;

import javax.net.ssl.SSLEngine;

import com.netty.codec.Decoder;
import com.netty.security.SecureChatSslContextFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocket13FrameEncoder;
import io.netty.handler.codec.http.websocketx.WebSocketFrameDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub

		ch.pipeline().addLast("codec-http", new HttpServerCodec());
		ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));
		
		ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));
		// // 消息解码器
		 ch.pipeline().addLast(
		 new ObjectDecoder(1024 * 1024, ClassResolvers
		 .weakCachingConcurrentResolver(this.getClass()
		 .getClassLoader())));
		 // 消息编码器
		 ch.pipeline().addLast(new ObjectEncoder());

		ch.pipeline().addLast(new NettyServerHandler());
	}

}
