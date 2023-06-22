package pw.paint.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pw.paint.model.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(ObjectId id);
}
