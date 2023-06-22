package pw.paint.DTOs.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequest {
    int pageNumber ;
    int pageSize ;
    String author;
    String keyword;
    List<String> tags;
}
