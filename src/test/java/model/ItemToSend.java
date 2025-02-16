package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static config.ApiConstants.*;

/**POJO-класс для данных товара, которые мы отправляем API.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemToSend {

    private Integer     sellerID;
    private String      name;
    private Integer     price;
    private Statistics  statistics;

    public static ItemToSend get(){
        Statistics statistics = new Statistics(TEST_CONTACTS, TEST_LIKES, TEST_VIEWCOUNT);
        return new ItemToSend(TEST_SELLER_ID, TEST_NAME, TEST_PRICE, statistics);
    }

    public static ItemToSend get(Statistics statistics){
        return new ItemToSend(TEST_SELLER_ID, TEST_NAME, TEST_PRICE, statistics);
    }
}
