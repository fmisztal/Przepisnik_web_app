package pw.paint.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Folder {
    private String name;
    @DBRef
    private List<Recipe> recipes;

    public Folder(String name){
        this.name = name;
    }
}
