package pw.paint.DTOs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShortRecipeDto {
    private String id;
    private String name;
    private String author;
    private List<String> tags;
    private byte[] image;
}
