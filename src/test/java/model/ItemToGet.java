package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**POJO-класс для данных товара, которые мы получаем от API.*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemToGet {
    private String      createdAt;
    private String      id;
    private String      name;
    private int         price;
    private long        sellerId;
    private Statistics  statistics;
}
