package com.netty.process.UCProcess;

import com.netty.pkg.Pkg;
import com.netty.processor.IProcessor;
import com.netty.session.ISession;

public class SendMessageProcess implements IProcessor {

	@Override
	public String process(ISession session, Pkg pkg) {
		// TODO Auto-generated method stub
		
		//pkg 定义，第一个值是用户id，第二个值是消息内容
		String msg=pkg.getStr(1);
		return msg;
	}

}
