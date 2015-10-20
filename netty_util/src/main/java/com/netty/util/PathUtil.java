package com.netty.util;

import java.io.File;
import java.net.URL;

import org.apache.commons.lang.SystemUtils;

public class PathUtil {
	 public static String XCFGPath() {
	        URL url = PathUtil.class.getClassLoader().getResource("");
	        String path = url.getPath();
	        if (SystemUtils.IS_OS_WINDOWS) {
	            if (path.startsWith("/")) {
	                path = path.substring(1);
	            }
	        }
	        return path;
	    }

	    public static String CMDPath() {
	        return XCFGPath() + "cmds" + File.separator + "cmdConfigs.xml";
	    }
}
