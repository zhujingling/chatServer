package com.netty.server;

import com.netty.executer.JobWork;
import com.netty.pkg.Pkg;
import com.netty.processor.IProcessor;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class NettyServerRequestHandler {  
	
	public static void handlerMessage(Channel ctx, Pkg pkg) throws Exception {
		JobWork jobWork=new JobWork();
		String respone=jobWork.jobProcessor(null, pkg);
		System.out.println(respone);
		ctx.writeAndFlush(pkg);
	}
}
