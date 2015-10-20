package com.netty.processor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.netty.util.ClzUtil;
import com.netty.util.PathUtil;
import com.netty.util.XmlUtil;
import com.netty.xmlentity.XmlCmdEntity;

public class Processors {
	private static Map<Integer, IProcessor> processors = new HashMap<Integer, IProcessor>();
	static {
		  try {
			if (!getConverters()) {
			        getDefaultConverter();
			    }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private static boolean getConverters() throws Exception {

		processors.clear();
		String path = PathUtil.CMDPath();
//		path = "F:/src/chatServer/netty_server_manage/src/main/resources/cmds/cmdConfigs.xml";
		List<XmlCmdEntity> list = XmlUtil.getCmdByXml(path);
		for (XmlCmdEntity xmlCmdEntity : list) {
			IProcessor processor = (IProcessor) ClzUtil
					.getFNObject(xmlCmdEntity.getPackagepath());
			if (null != processor) {
				processors.put(xmlCmdEntity.getCode(), processor);
			}
		}
		return true;
	}

	private static void getDefaultConverter() {
	        if (!processors.isEmpty()) {
	            processors.clear();
	        }
	}
	public static IProcessor get(int cmd) {
		if (0 >= cmd) {
			return null;
		}

		IProcessor proc = processors.get(cmd);
		if (proc != null) {
			try {
				return proc.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {

			}
		}

		return null;
	}
}
