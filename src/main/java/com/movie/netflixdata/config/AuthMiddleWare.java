package com.movie.netflixdata.config;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/tvshows/*")
public class AuthMiddleWare implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String token=req.getHeader("X-Auth-Token");
        if(token==null){
        res.setStatus(401);
        }
        chain.doFilter(request,response);
    }

}
