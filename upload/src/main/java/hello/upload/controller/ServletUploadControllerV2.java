package hello.upload.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletUploadControllerV2 {

    @Value("${file.dir}")   //파일이 업로드되는 경로
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "upload-form";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest request) throws ServletException, IOException {
        log.info("request ={}", request);
        String itemName = request.getParameter("itemName");
        log.info("itemName ={}", itemName);
        Collection<Part> parts = request.getParts();
        log.info("parts ={}", parts);


        //멀티파트 형식은 전송 데이터를 하나하나 각각 부분('Part')로 나누어 전송하며, 'parts' 에는 이렇게 나누어진 데이터가 각각 담긴다
        //서블릿이 제공하는 'Part' 는 멀티파트 형식을 편리하게 읽을 수 있는 다양한 메서드를 제공한다.
        for (Part part : parts) {
            log.info("==== PART ====");
            log.info("name ={}", part.getName());

            Collection<String> headerNames = part.getHeaderNames();  //parts도 헤더와 바디로 구분된다.
            for (String headerName : headerNames) {
                log.info("header {}: {}", headerName, part.getHeader(headerName));
            }


            //편의 메서드 (파일 명을 가져올 수 있는 메서드)
            log.info("submittedFilename={}", part.getSubmittedFileName());  //Content_Disposition; filename
            log.info("size={}", part.getSize()); // part 바디의 size

            //Part 의 전송 데이터를 읽기
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            log.info("body={}", body);

            //전송된 데이터(파일)를 파일에 저장
            if (StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("파일 저장 fullPath={}", fullPath);
                part.write(fullPath);   //파일 저장
            }
        }
        return "upload-form";
    }

    //서블릿이 제공하는 'Part' 는 편하긴 하지만, 'HttpServletRequest' 를 사용해야 하고,
    // 추가로 파일 부분만 구분하려면 여러가지 코드가 필요함.
    // -> 스프링이 편리하게 해결
}
