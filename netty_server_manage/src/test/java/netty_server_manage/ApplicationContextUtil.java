package netty_server_manage;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.ContextLoader;

/**
 * 
 * 
 * ApplicationContextUtil:spring工具类
 * 
 * weiRongLang 2015年3月30日 上午9:54:27
 * 
 * @version 1.0.0
 *
 */
public class ApplicationContextUtil {

	private static ApplicationContext ctx;

	@SuppressWarnings("unchecked")
	public synchronized static <T> T getBean(String beanId) {

		if (ctx == null) {
			initContext();
		}

		return (T) ctx.getBean(beanId);
	}

	private static void initContext() {

		ctx = null;
		ctx = ContextLoader.getCurrentWebApplicationContext();
		if (ctx == null) {

			ctx = new ClassPathXmlApplicationContext("spring-mybatis.xml");
			// ctx = new ClassPathXmlApplicationContext(new
			// String[]{"applicationContext.xml","applicationContext-beans.xml"});
		}
	}
}
