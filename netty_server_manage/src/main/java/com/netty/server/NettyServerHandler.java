package com.netty.server;

import java.util.Date;

import com.netty.manage.ChannelGroupManage;
import com.netty.manage.ChannelUserManage;
import com.netty.pkg.Pkg;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

public class NettyServerHandler extends ChannelHandlerAdapter {

	public AttributeKey<String> USER_ID = AttributeKey.valueOf("userId");

	// map用于channel和具体的用户名(用户id)绑定起来，可以根据具体业务实现认证信息和channel绑定

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Attribute<String> attr = ctx.attr(USER_ID);
		String user = ctx.attr(USER_ID).get();
		Pkg pkg = (Pkg) msg;
		if (user == null) {
			attr.set(pkg.getStr(0));
			Channel channel = ctx.channel();
			ChannelUserManage.addConn(pkg.getStr(0), channel);
		}
		System.out.println(pkg.toString());
		Channel channel = ChannelUserManage.getConn(pkg.getStr(0));
		// 这里可以做到一个
		NettyServerProxy.handleWebSocketFrame(channel, pkg);

	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();		
		for (Channel channel : ChannelGroupManage.channels) {
			Pkg pkg=Pkg.rawPkg();
			String strAdded="[Server-]您的好友:" + "["
					+ incoming.remoteAddress() + "]" + "上线了" + "*****" + "时间:"
					+ new Date().toLocaleString();
			pkg.put(strAdded);
			channel.writeAndFlush(pkg);
		}
		ChannelGroupManage.channels.add(incoming);
		
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		Channel outing = ctx.channel();
		ChannelGroupManage.channels.remove(outing);
		for (Channel channel : ChannelGroupManage.channels) {
			Pkg pkg=Pkg.rawPkg();
			String strRemove="[Server-]您的好友:" + "["
					+ outing.remoteAddress() + "]" + "离开了" + "*****" + "时间:"
					+ new Date().toLocaleString();
			pkg.put(strRemove);
			channel.writeAndFlush(pkg);
		}
		
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[Client-]" + "[" + incoming.remoteAddress() + "]"
				+ "上线" + "*****" + "时间:" + new Date().toLocaleString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		Channel incoming = ctx.channel();
		System.out.println("[Client-]" + "[" + incoming.remoteAddress() + "]"
				+ "掉线了" + "*****" + "时间:" + new Date().toLocaleString());
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
