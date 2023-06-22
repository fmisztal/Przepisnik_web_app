package pw.paint.service;

import org.bson.types.ObjectId;
import pw.paint.DTOs.model.UserDto;

public interface UserService {
    UserDto getUserById(ObjectId id);
}
