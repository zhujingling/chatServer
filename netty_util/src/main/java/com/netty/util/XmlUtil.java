package com.netty.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.netty.xmlentity.XmlCmdEntity;

public class XmlUtil {
	@SuppressWarnings("rawtypes")
	public static List<XmlCmdEntity> getCmdByXml(String path) throws Exception {
		List<XmlCmdEntity> listCmdEntity = new ArrayList<XmlCmdEntity>();
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(path));

		Element root = document.getRootElement();
		Iterator it = root.elementIterator();
		while (it.hasNext()) {
			XmlCmdEntity item = new XmlCmdEntity();
			Element childEle = (Element) it.next();
			item.setId(childEle.attribute("id").getText());

			Iterator itChild = childEle.elementIterator();
			while (itChild.hasNext()) {
				Element e = (Element) itChild.next();
				if ("code".equals(e.getName())) {
					String cmd=e.getText();
					int npos = cmd.indexOf("0x");					
					if (npos != -1) {
						cmd = cmd.substring(npos + "0x".length());
						int ncmd = str2int(cmd);
						item.setCode(ncmd);
					}

				} else if ("package".equals(e.getName())) {
					item.setPackagepath(e.getText());
				} else {
					item.setDesc(e.getText());
				}
			}
			listCmdEntity.add(item);
		}
		return listCmdEntity;
	}

	private static int str2int(String s) {
		if (StringUtils.isBlank(s)) {
			return 0;
		}

		char[] carray = s.toCharArray();
		int length = carray.length;
		if (length % 2 != 0) {
			return 0;
		}

		byte[] ret = new byte[4];
		int j = 0;
		for (int i = 0; i < length; i += 2) {
			int lval = digit(carray[i]);
			int rval = digit(carray[i + 1]);
			if (lval == -1 || rval == -1) {
				return 0;
			}

			ret[j++] = (byte) (((lval << 4) | rval) & 0xFF);
		}

		return BytesToInt(ret);
	}

	private static int digit(char ch) {
		return Character.digit(ch, 16);
	}

	private static int BytesToInt(byte[] b) {
		int s = 0;
		for (int i = 0; i < 3; i++) {
			if (b[i] >= 0) {
				s = s + b[i];
			} else {
				s = s + 256 + b[i];
			}
			s = s * 256;
		}
		if (b[3] >= 0) {
			s = s + b[3];
		} else {
			s = s + 256 + b[3];
		}
		return s;
	}

}
