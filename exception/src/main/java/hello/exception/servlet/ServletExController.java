package hello.exception.servlet;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class ServletExController {  // 예외가 WAS까지 넘어올경우 WAS가 예외를 처리하는 방법
    // 이 컨트롤러에서 예외를 던진다.

    @GetMapping("/error-ex")  // 컨트롤러에서 예외 발생 시 - 500 Internal server error
    public void errorEx() {
        throw new RuntimeException("예외 발생!");
    }

    @GetMapping("/error-404") // response.sendError 사용해 예외가 발생했음을 전달
    public void error404(HttpServletResponse response) throws IOException {
        response.sendError(404, "404 오류!");
    }

    @GetMapping("/error-500") // response.sendError 사용해 예외가 발생했음을 전달
    public void error500(HttpServletResponse response) throws IOException {
        response.sendError(500, "500 오류!");
    }
}
