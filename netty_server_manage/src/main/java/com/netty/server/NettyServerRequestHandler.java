package com.netty.server;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.netty.executer.JobWork;
import com.netty.pkg.Pkg;
import com.netty.process.LoginProcess;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class NettyServerRequestHandler {  

	private static LoginProcess loginProcess=new LoginProcess();
	
	public static void handlerMessage(ChannelHandlerContext ctx, Pkg pkg) throws Exception {
	
		Pkg response=new Pkg();
		response.cmd=pkg.getCmd();
		JobWork jobWork=new JobWork();
		response.data=jobWork.jobProcessor(null, pkg);
//		response.data=loginProcess.login(user_number, user_password);
		String ret=new Gson().toJson(response);
		System.out.println(ret);
		ctx.channel().writeAndFlush(new TextWebSocketFrame(ret));
	}
}
