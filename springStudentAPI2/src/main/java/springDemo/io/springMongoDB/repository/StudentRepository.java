package springDemo.io.springMongoDB.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import springDemo.io.springMongoDB.model.StudentData;

@Repository
public interface StudentRepository extends MongoRepository<StudentData, String> {

	@Query("'studentName':?0")
	Optional<StudentData> findByStudentName(String name);
}
