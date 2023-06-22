package pw.paint.DTOs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private ObjectId id;
    private String userName;
    private String password;
    private String email;
    private List<FolderDto> folders;
}
