package pw.paint.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import pw.paint.model.Tag;

import java.util.Optional;

public interface TagRepository extends MongoRepository<Tag, ObjectId> {
    Optional<Tag> findByName(String names);
}
