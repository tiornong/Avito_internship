package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static config.ApiConstants.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Statistics {

    private Integer contacts;
    private Integer likes;
    private Integer viewCount;

    public static Statistics get() {
        return new Statistics(TEST_CONTACTS, TEST_LIKES, TEST_VIEWCOUNT);
    }

}
