package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**POJO-класс для данных товара, которые мы отправляем API.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemToSend {

    private int sellerID;
    private String name;
    private int price;
    private Statistics statistics;

}
