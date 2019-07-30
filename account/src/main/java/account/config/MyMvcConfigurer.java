package account.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import account.user.pojo.User;

@Configuration
public class MyMvcConfigurer implements WebMvcConfigurer {
	// 拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor())
			.addPathPatterns("/getOrder")
			.addPathPatterns("/addOrder")
			.addPathPatterns("/getSum")
			
			.addPathPatterns("/getTypes")
			.addPathPatterns("/addTypes");
	}

	// 类型转换器
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addFormatter(new DateFormatter("yyyy-MM-dd"));
	}
}

class MyInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			response.setCharacterEncoding("utf-8");
			response.getWriter().append("请先登录");
			return false;
		}
		return true;
	}
}