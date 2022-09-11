package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class LoginCheckFilter implements Filter {  // 로그인 인증 체크 필터 구현

    //인증 체크없이 접근을 허용할 URL
    private static final String[] whitelist = {"/", "/members/add", "/login", "/logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작{}", requestURI);

            if (isLoginCheckPath(requestURI)) {  // whitelist 에 없을 경우
                log.info("인증 체크 로직 실행{}", requestURI);
                HttpSession session = httpRequest.getSession(false);
                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {

                    log.info("미인증 사용자 요청 {}", requestURI);

                    //로그인 화면으로 redirect + 파라미터로 원래 요청했던 값 넘김  ->  V6에서 이 값을 받아서 로그인 후에 원래 요청했던 페이지로 이동
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI);
                    return;  // redirect 가 응답으로 적용되고, 서블릿/컨트롤러가 더는 호출되지 않음. (chain 을 사용해야 다음 필터/서블릿이 호출됨)
                }
            }

            chain.doFilter(request, response);  // 인증 체크가 필요없는 경우(whitelist) 다음으로 이동

        } catch (Exception e) {
            throw e;  // 예외 로깅 가능하지만, 톰캣까지 예외를 보내주어야 함
        }finally {
            log.info("인증 체크 필터 종료{}", requestURI);
        }

    }

    /**
     * whitelist 의 경우 인증 체크하지 않는 로직
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    //여기서 보면 필터는 특별한 기술이 아니라, 컨트롤러 호출 이전에 호출되는 Filter 인터페이스를 상속받아 구현체를 만들고,
    //doFilter 에 세션을 이용한 로직을 만들어 필터에 걸릴 시 컨트롤러 호출(접근) 자체가 안되도록 구현하는 것이다. (내 생각)
    // + 웹 관련 공통 관심사항 처리
}
