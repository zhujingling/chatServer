package com.netty.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

//心跳包
public class HeartBeat extends IdleStateHandler {

	private int i = 0;

	public HeartBeat(int readerIdleTimeSeconds, int writerIdleTimeSeconds,
			int allIdleTimeSeconds) {
		super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt)
			throws Exception {
		// TODO Auto-generated method stub
		super.channelIdle(ctx, evt);
		if (evt.state() == IdleState.WRITER_IDLE)
			i++;

		if (i == 3) {
			ctx.channel().close();
			System.out.println("掉了。");
		}
	}
}
