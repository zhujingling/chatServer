package com.netty.processor;

import com.netty.pkg.Pkg;
import com.netty.session.ISession;

public interface IProcessor {
	 /*
     * 处理输入的统一通讯协议格式的数据
     * 
     * @param 1. session -ISession : 客户端连接标示 2. pkg -Pkg : 统一通讯协议格式的数据
     * 
     * @return String
     */
    public String process(ISession session, Pkg pkg);
}
