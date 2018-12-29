package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 由于把shopoperation.html放到了WEB-INF下面，因此直接在页面上是无法访问的，必须要从后台路由进行转发
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="/shopadmin",method= {RequestMethod.GET})
public class ShopAdminController {
	
	/**
	 * 做路径的转发。访问这个路径就可以根据spring-web.xml中配置的视图解析器。取得到【prefix前缀】(WEB-INF/html/)shop/shopoperation(.html)【suffix后缀】这个路径。
	 * 去访问位于WEB-INF下的html文件
	 */
	@RequestMapping(value="/shopoperation")
	public String shopOperation() {
		return "shop/shopoperation";
	}
}
