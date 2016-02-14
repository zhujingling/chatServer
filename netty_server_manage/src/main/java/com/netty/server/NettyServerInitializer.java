package com.netty.server;

import com.netty.codec.Decoder;
import com.netty.codec.Encoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		// TODO Auto-generated method stub

		ch.pipeline().addLast("codec-http", new HttpServerCodec());
		ch.pipeline().addLast("aggregator", new HttpObjectAggregator(65536));

		ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"));

		ch.pipeline().addLast(new Decoder());

		ch.pipeline().addLast(new Encoder());

		ch.pipeline().addLast(new NettyServerHandler());
	}

}
