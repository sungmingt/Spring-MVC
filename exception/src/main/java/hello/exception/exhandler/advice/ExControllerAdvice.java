package hello.exception.exhandler.advice;

import hello.exception.exception.UserException;
import hello.exception.exhandler.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
// 대상으로 지정한 컨트롤러들에 @ExceptionHandler, @InitBinder 기능을 부여해주는 역할을 함. (AOP와 비슷)
// 대상 미지정 시 모든 컨트롤러에 적용.
public class ExControllerAdvice {


    @ResponseStatus(HttpStatus.BAD_REQUEST) // 정상처리(200)를 400으로 바꿈
    @ExceptionHandler(IllegalArgumentException.class) //생략가능
    public ErrorResult illegalExHandler(IllegalArgumentException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("BAD", e.getMessage());
    }
    // 이 컨트롤러 내에 IllegalArgumentException 예외가 발생한 경우 이놈을 실행. 다른 컨트롤러 영향x
    // @RestController이기 때문에 @ResonseBody가 붙어 반환값을 Json으로 알아서 바꿔줌.(HttpMessageConverter)
    // 물론 @Controller + 반환타입 String으로 view를 반환할 수도 있음 (컨트롤러와 거의 유사하게 동작함)
    // 이러면 정상처리됨. 다시요청x.

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandler(UserException e) {
        log.error("[exceptionHandler] ex", e);
        ErrorResult errorResult = new ErrorResult("USER-EX", e.getMessage());
        return new ResponseEntity<>(errorResult, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandler(Exception e) { // 기본적으로 Exception과 그 자식까지 처리해준다
        log.error("[exceptionHandler] ex", e);
        return new ErrorResult("EX", "내부 오류");
    }


}

