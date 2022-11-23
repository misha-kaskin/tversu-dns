package models;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    private Integer id;
    private Integer parent;
    private Integer level;
    private String text;
    private String author;
    private Integer itemId;
    private String itemType;
}
