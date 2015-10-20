package com.netty.executer;

import com.netty.pkg.Pkg;
import com.netty.processor.IProcessor;
import com.netty.processor.Processors;
import com.netty.session.ISession;

public class JobWork {
	public String jobProcessor(ISession session,Pkg pkg) {
		IProcessor processor;
		processor = Processors.get(pkg.getCmd());
		return processor.process(session, pkg);
	}

}
