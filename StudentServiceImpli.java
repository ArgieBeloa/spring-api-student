package springDemo.io.springMongoDB.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.validation.ConstraintViolationException;
import springDemo.io.springMongoDB.exception.TodoCollectionException;
import springDemo.io.springMongoDB.model.StudentData;

import springDemo.io.springMongoDB.repository.StudentRepository;

@Service
public class StudentServiceImpli implements StudentService{

	private StudentRepository studentRepository;
	
	@Override
	public void createStudent(StudentData studentData) throws ConstraintViolationException, TodoCollectionException {
		Optional<StudentData> student = studentRepository.findByStudentName(studentData.getStudentName());
		
		if(student.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
		}
		else {
			studentData.setCreateAt(new Date(System.currentTimeMillis()));
			studentRepository.save(studentData);
		}
		
	}

}
