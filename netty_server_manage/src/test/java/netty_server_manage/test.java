package netty_server_manage;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.netty.executer.JobWork;
import com.netty.parser.PkgParser;
import com.netty.pkg.Pkg;
import com.netty.service.UserService;
import com.netty.util.PathUtil;
import com.netty.util.XmlUtil;
import com.netty.xmlentity.XmlCmdEntity;

public class test {
	@Resource
//	private  UserService userService = com.netty.utils.ApplicationContextUtil
//			.getBean("userServiceImpl");
//	@Resource
//	private FriendService friendService = ApplicationContextUtil
//			.getBean("friendServiceImpl");


	@Test
	public void testLogin() throws Exception {
//		boolean b = userService.checkUser("1");
//		System.out.println(b);
//		System.out.println(userService.login("1", "1"));
	}

	@Test
	public void testFriend() throws Exception {
//		List<Friend> list=friendService.friendList("1");
//		for (Friend friend : list) {
//			System.out.println(friend.getFriend_number());
//		}
	}
	
	@Test
	public void testReadXml() throws Exception {
//		JobWork jobWork=new JobWork();
//		Pkg pkg=new Pkg();
//		pkg.cmd=0x00A40001;
//		jobWork.jobProcessor(null, pkg);
	}
	
	@Test
	public void  test() throws ClassNotFoundException {
		Class c = Class.forName("com.netty.process.UCProcess.LoginProcess");
		if (c==null) {
			System.out.println("没加载到类");
		}
		else{
			System.out.println(c.getName());
		}
		
	}
	
	// 转化十六进制编码为字符串  
	public static String toStringHex1(String s) {  
	   byte[] baKeyword = new byte[s.length() / 2];  
	   for (int i = 0; i < baKeyword.length; i++) {  
	    try {  
	     baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(  
	       i * 2, i * 2 + 2), 16));  
	    } catch (Exception e) {  
	     e.printStackTrace();  
	    }  
	   }  
	   try {  
	    s = new String(baKeyword, "utf-8");// UTF-16le:Not  
	   } catch (Exception e1) {  
	    e1.printStackTrace();  
	   }  
	   return s;  
	} 
	
	@Test
	public void testPkgToByte() {
		Pkg pkg=Pkg.rawPkg();
    	pkg.cmd=0x00A40001;
    	pkg.put("1");//用户名
    	pkg.put("1");//密码
    	
    	byte[] byt=PkgParser.parsers(pkg);
	}
}
