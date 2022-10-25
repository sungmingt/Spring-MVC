package hello.itemservice.validation;

import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.*;

class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required","item");
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {  // rejectValue 가 내부적으로 이 codesResolver 를 사용하는 것이다.
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);

        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                        "required.itemName",
                        "required.java.lang.String",
                        "required"
        );
    }


}
