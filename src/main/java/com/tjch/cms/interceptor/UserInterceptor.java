package com.tjch.cms.interceptor;




import com.tjch.cms.base.ResponseBase;
import com.tjch.cms.dto.TokenInfo;
import com.tjch.cms.utils.JsonUtils;
import com.tjch.cms.utils.ProductToken;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

public class UserInterceptor implements HandlerInterceptor {

	@Autowired
	private StringRedisTemplate redisTemplate;
    @Autowired
    private ProductToken productToken;

	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception{

		// 检查用户传递的 token是否合法
		TokenInfo tokenInfo = this.getUserToKen(request);
//
		if ( StringUtils.isBlank(tokenInfo.getToken())||StringUtils.isBlank(tokenInfo.getUsername())) {
			// 返回登录
			System.out.println("没有传入对应的身份信息，返回登录");
			JSONObject obj=new JSONObject();
			obj.put("code",500);
			obj.put("msg","服务器错误");
			response.setContentType("text/plain; charset=utf-8");
			response.getWriter().write(obj.toString());
			return false;
		}
		try {
			String token = redisTemplate.opsForValue().get(tokenInfo.getUsername());
//
			if (token != null && token.equals(tokenInfo.getToken())) {
                productToken.updateTokenTime(tokenInfo.getUsername());
				System.out.println("校验成功");
				return true;
			} else {
				System.out.println("校验失败，返回登录");
				JSONObject obj=new JSONObject();
				obj.put("code",100);
				obj.put("msg","token错误");
				response.setContentType("text/plain; charset=utf-8");
				response.getWriter().write(obj.toString());
				return false;
			}
		} catch (Exception e) {
			System.out.println("校验失败,对用户的信息匹配错误，返回登录");
			JSONObject obj=new JSONObject();
			obj.put("code",500);
			obj.put("msg","服务器错误");
			response.setContentType("text/plain; charset=utf-8");
			response.getWriter().write(obj.toString());
			return false;
		}

	}

	/**
	 * 在cookie中获取用户传递的token
	 */
	private TokenInfo getUserToKen(HttpServletRequest request) {
		TokenInfo info = new TokenInfo();
		String username = request.getParameter("username");
		String token = request.getParameter("token");
//
		if ( StringUtils.isNotBlank(token)&&StringUtils.isNotBlank(username)) {
			info.setUsername(username);
			info.setToken(token);
		}
		return info;
	}

	/**
	 * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,  ModelAndView modelAndView) throws Exception{

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

	public void returnErrorResponse(HttpServletResponse response, ResponseBase result)
			throws IOException, UnsupportedEncodingException {
		OutputStream out = null;
		try {
			response.setCharacterEncoding("utf-8");
			response.setContentType("text/json");
			out = response.getOutputStream();
			out.write(JsonUtils.objectToJson(result).getBytes("utf-8"));
			out.flush();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
