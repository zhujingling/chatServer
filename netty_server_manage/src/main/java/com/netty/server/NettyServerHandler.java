package com.netty.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.netty.manage.ChannelGroupManage;
import com.netty.manage.ChannelUserManage;
import com.netty.parser.PkgParser;
import com.netty.pkg.Pkg;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

public class NettyServerHandler extends ChannelHandlerAdapter {

	public AttributeKey<String> USER_ID = AttributeKey.valueOf("userId");

	// map用于channel和具体的用户名(用户id)绑定起来，可以根据具体业务实现认证信息和channel绑定

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {

		//第一个if已经交给了这个ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws"))去实现
//		 if (msg instanceof FullHttpRequest) {
//		 NettyServerProxy.httpHandleRequest(ctx, (FullHttpRequest) msg);
//		 } else	
			 if (msg instanceof WebSocketFrame) {

			Pkg pkg = handleRequestMsg(ctx, msg);
			Channel channel = ChannelUserManage.getConn(pkg.getStr(0));
			// 这里可以做到一个
			NettyServerProxy.handleWebSocketFrame(channel, pkg);
		}

	}

	private Pkg handleRequestMsg(ChannelHandlerContext ctx, Object msg) {
		Attribute<String> attr = ctx.attr(USER_ID);
		String user = ctx.attr(USER_ID).get();
		 String request = ((TextWebSocketFrame) msg).text();
		 Pkg pkg=new Gson().fromJson(request,Pkg.class);
		if (user == null) {
			attr.set(pkg.getStr(0));
			Channel channel = ctx.channel();
			ChannelUserManage.addConn(pkg.getStr(0), channel);
		}
		System.out.println(request);
		return pkg;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : ChannelGroupManage.channels) {
			channel.writeAndFlush(incoming.remoteAddress() + " 加入;" + "时间:"
					+ new Date().toLocaleString());
		}
		ChannelGroupManage.channels.add(ctx.channel());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		for (Channel channel : ChannelGroupManage.channels) {
			channel.writeAndFlush(incoming.remoteAddress() + " 离开;" + "时间:"
					+ new Date().toLocaleString());
		}

		ChannelGroupManage.channels.remove(ctx.channel());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println(incoming.remoteAddress() + "上线;" + "时间:"
				+ new Date().toLocaleString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();

		System.out.println(incoming.remoteAddress() + ":" + "掉线了;" + "时间:"
				+ new Date().toLocaleString());
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		Channel incoming = ctx.channel();

		// 当出现异常就关闭连接
		ctx.close();
	}

}
