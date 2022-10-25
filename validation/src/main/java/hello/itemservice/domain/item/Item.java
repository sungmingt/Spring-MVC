package hello.itemservice.domain.item;

import lombok.Data;

// ObjectError 처리방법 (하지만 제약이 많고 복잡하기때문에 ObjectError 의 경우 직접 자바 코드로 작성하는 것이 권장된다.)
//@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000", message = "총합이 10000원이 넘도록 입력해주세요.")
@Data
public class Item {

//    @NotNull(groups = UpdateCheck.class)  // Form 전송객체 분리하므로 모두 주석처리
    private Long id;

    //@NotBlank(groups = {UpdateCheck.class, SaveCheck.class})
    private String itemName;

//    @NotNull(groups = {UpdateCheck.class, SaveCheck.class})
//    @Range(min = 1000, max = 1000000)
    private Integer price;

//    @NotNull(groups = {UpdateCheck.class, SaveCheck.class})
//    @Max(value = 9999, groups = SaveCheck.class)
    private Integer quantity;

    public Item() {}

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
