package com.ooc.hexcyper.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UserService {

    public String getCurrentUserId() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            Cookie[] cookies = request.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("userIdCookie".equals(cookie.getName())) {
                        return cookie.getValue();
                    }
                }
            }
        }

        return "defaultUserId";
    }
}
