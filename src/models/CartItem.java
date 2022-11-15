package models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CartItem {
    String login;
    Integer itemId;
    String itemType;
    String itemTitle;
    Integer itemCost;
}
