package com.netty.session;

public interface ISession {
	 /*
     * token
     */
    public String token();

    public void token(String token);

    /*
     * 临时token
     */
    public String tokenTmp();

    public void tokenTmp(String token_tmp);

}
