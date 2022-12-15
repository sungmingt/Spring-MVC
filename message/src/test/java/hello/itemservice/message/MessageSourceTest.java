package hello.itemservice.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {  // 스프링 메시지 소스 테스트

    @Autowired
    MessageSource ms;


    @Test  // local 값이 null 일 경우 basename 에서 설정한 기본 이름 메시지 파일(messages.properties)을 조회한다
    void helloMessage() {
        String result = ms.getMessage("hello",null, null);
        assertThat(result).isEqualTo("안녕");
    }

    @Test  // messages.properties 에 없는 메시지일 경우 예외 발생
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }

    @Test  // defaultMessage 값을 주면 예외가 뜨지않음
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
        assertThat(result).isEqualTo("기본 메시지");
    }

    @Test  // 파라미터 전달
    void argumentMessage() {
        String message = ms.getMessage("hello.name", new Object[]{"Spring"}, null);
        assertThat(message).isEqualTo("안녕 Spring");
    }



    @Test  // locale default
    void defaultLang() {
        assertThat(ms.getMessage("hello", null, null)).isEqualTo("안녕");
        assertThat(ms.getMessage("hello", null, Locale.KOREA)).isEqualTo("안녕");
    }

    @Test  // messages_en.properties 에서 조회
    void enLang() {
        assertThat(ms.getMessage("hello", null, Locale.ENGLISH)).isEqualTo("hello");
    }



}
