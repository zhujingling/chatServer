package com.netty.processor;

import com.netty.pkg.Pkg;
import com.netty.session.ISession;

public abstract class AbstractProcessor implements IProcessor {

	public String process(ISession session,Pkg pkg) {
        return process(session, pkg);
    }
}
