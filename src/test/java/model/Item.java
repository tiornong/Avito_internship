package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {

    private int sellerID;
    private String name;
    private int price;
    private Statistics statistics;

}
