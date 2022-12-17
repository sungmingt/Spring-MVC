package hello.itemservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItemServiceApplication {  // MVC 1편의 item-service를 토대로 타임리프/스프링 통합 폼 추가

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}
}
