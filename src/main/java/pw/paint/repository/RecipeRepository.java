package pw.paint.repository;

import com.mongodb.DBRef;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import pw.paint.model.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeRepository extends MongoRepository<Recipe, ObjectId> {
    Optional<Recipe> findById(ObjectId id);
    Optional<Recipe> findByName(String name);
    @Query("{'status':{$eq: ?0}}")
    Page<Recipe> findAll(Boolean status,Pageable pageable);
    @Query("{'name': { $regex: ?0, $options: 'i' },'status':{$eq: ?1}}")
    Page<Recipe> findByNameContaining(String keyword, Boolean status, Pageable pageable);
    @Query("{'tags': { $all: ?0 },'status':{$eq: ?1}}")
    Page<Recipe> findByTagsAll(List<DBRef> tags, Boolean status, Pageable pageable);
    @Query("{'author': ?0,'status':{$eq: ?1}}")
    Page<Recipe> findByAuthor(DBRef author, Boolean status,Pageable pageable);
    @Query("{'name': { $regex: ?0, $options: 'i' }, 'tags': { $all: ?1 },'status':{$eq: ?2}}")
    Page<Recipe> findByTagsAllAndNameContaining(String keyword,List<DBRef> tags, Boolean status, Pageable pageable );
    @Query("{ 'tags': { $all: ?0 },'author': ?1,'status':{$eq: ?2}}")
    Page<Recipe> findByTagsAndAuthor(List<DBRef> tags, DBRef author, Boolean status,Pageable pageable);
    @Query("{'name': { $regex: ?0, $options: 'i' },'author': ?1,'status':{$eq: ?2}}")
    Page<Recipe> findByNameContainingAndAuthor(String keyword, DBRef author , Boolean status,Pageable pageable);
    @Query("{'name': { $regex: ?0, $options: 'i' }, 'tags': { $all: ?1 },'author': ?2,'status':{$eq: ?3}}")
    Page<Recipe> findByTagsAllAndNameContainingAndAuthor(String keyword,List<DBRef> tags, DBRef author, Boolean status, Pageable pageable);
}
