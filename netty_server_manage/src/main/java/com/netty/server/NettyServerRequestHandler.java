package com.netty.server;

import com.netty.executer.JobWork;
import com.netty.manage.ChannelUserManage;
import com.netty.pkg.Pkg;

import io.netty.channel.Channel;

public class NettyServerRequestHandler {

	public static void handlerMessage(Channel ctx, Pkg pkg) throws Exception {
		JobWork jobWork = new JobWork();
		String userId=pkg.getStr(0);
		Channel channel=ChannelUserManage.getConn(userId);
		String respone = jobWork.jobProcessor(null, pkg);
		
		Pkg responePkg = Pkg.rawPkg();
		responePkg.cmd = pkg.cmd;
		responePkg.put(respone);
		System.out.println("服务器回复" + responePkg.toString());
		channel.writeAndFlush(responePkg);
	}
}
