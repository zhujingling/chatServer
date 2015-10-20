package netty_util.testUtil;

import java.util.List;

import org.junit.Test;

import com.netty.util.PathUtil;
import com.netty.util.XmlUtil;
import com.netty.xmlentity.XmlCmdEntity;


public class test {

	@Test
	public void testReadXml() throws Exception {
		String path=PathUtil.CMDPath();
		List<XmlCmdEntity> list=XmlUtil.getCmdByXml(path);
		for (XmlCmdEntity xmlCmdEntity : list) {
			System.out.println(xmlCmdEntity.getId());
		}
	}
}
