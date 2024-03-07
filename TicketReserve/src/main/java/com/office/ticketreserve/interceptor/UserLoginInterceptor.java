package com.office.ticketreserve.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 컨트롤러 메소드 실행 전에 수행할 작업을 여기에 작성합니다.
    	
    	HttpSession session = request.getSession(false);
    	
    	if (session != null) {
    		Object object = session.getAttribute("loginedUserDto");
    		
    		if (object != null)
    			return true;
    	}
    	
    	response.sendRedirect(request.getContextPath() + "/user/user_login_form");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 컨트롤러 메소드 실행 후, 뷰를 렌더링하기 전에 수행할 작업을 여기에 작성합니다.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 요청 처리가 완전히 끝난 후 (뷰를 렌더링한 후) 수행할 작업을 여기에 작성합니다.
    }
	
}
