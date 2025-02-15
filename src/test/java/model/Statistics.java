package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    // Object используется т.к. в ином случае было бы необходимо создавать отдельный класс 1
    // (например, CorruptedStatistics) для негативных тестов, в которых мы пытаемся создать заказ с значениями неправильных типов
    private Object contacts;
    private Object likes;
    private Object viewCount;

}
