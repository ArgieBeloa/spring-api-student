package springDemo.io.springMongoDB.service;

import jakarta.validation.ConstraintViolationException;
import springDemo.io.springMongoDB.exception.TodoCollectionException;
import springDemo.io.springMongoDB.model.StudentData;


public interface StudentService {
	public void createStudent(StudentData studentData) throws ConstraintViolationException, TodoCollectionException;
}
