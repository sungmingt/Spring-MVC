package hello.login.web.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyFilter implements javax.servlet.Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        javax.servlet.Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
    }

    @Override
    public void destroy() {
        javax.servlet.Filter.super.destroy();
    }
}
