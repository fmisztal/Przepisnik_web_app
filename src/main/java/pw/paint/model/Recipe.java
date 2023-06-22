package pw.paint.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "recipes")
public class Recipe {
    @Id
    private ObjectId id;
    private String name;
    @DBRef
    private User author;
    private Boolean status;
    @DBRef
    private List<Tag> tags;
    private List<String> ingredients;
    private List<String> steps;
    private Integer timeMinutes;
    private byte[] image;

    public Recipe(String name, User author, Boolean status, Integer timeMinutes) {
        this.name = name;
        this.author = author;
        this.status = status;
        this.timeMinutes = timeMinutes;
    }
}
