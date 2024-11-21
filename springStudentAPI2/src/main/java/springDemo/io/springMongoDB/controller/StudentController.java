package springDemo.io.springMongoDB.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;

import jakarta.validation.ConstraintViolationException;

import springDemo.io.springMongoDB.model.StudentData;

import springDemo.io.springMongoDB.repository.StudentRepository;

import springDemo.io.springMongoDB.service.StudentService;

@RestController
public class StudentController {

	
	@Autowired
	private StudentRepository studentRepository ;
	
	@Autowired
	private StudentService studentService;
	
//	get All Data
	@GetMapping("/student")
	public  ResponseEntity<?> getAllStudents() {
		List<StudentData> students  = studentRepository.findAll(); 
		
		if(students.size() > 0)
		{	
			return new ResponseEntity<List<StudentData>>(students, HttpStatus.OK);
				
		}else {
			return new ResponseEntity<>("no tools available", HttpStatus.NOT_FOUND);
		}
		
	}
	
//	add data
	@PostMapping("/student/addData")
	public ResponseEntity<?> createStudent(@RequestBody  StudentData studentData){
	    
	     
		try {
		
			studentData.setCreateAt(new Date(System.currentTimeMillis()));
			studentRepository.save(studentData);
			
			return new ResponseEntity<StudentData>(studentData,HttpStatus.OK);
			
		} catch (ConstraintViolationException e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}
		
	}
	            
	
//	get one document
	@GetMapping("/student/{id}")
	public ResponseEntity<?> getSingleStudent(@PathVariable("id") String id)
	{
		Optional<StudentData> studentOptional = studentRepository.findById(id);
		
		if(studentOptional.isPresent())
		{
			return new ResponseEntity<>(studentOptional.get(), HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Student not found by id "+id, HttpStatus.NOT_FOUND );
		}
		
	}
	
	
//update data
	@PutMapping("/student/{id}")
	public ResponseEntity<?> updataById(@PathVariable("id") String id, @RequestBody  StudentData studentData)
	{
		Optional<StudentData> studentOptional = studentRepository.findById(id);
		
		if( studentOptional.isPresent())
		{
			StudentData studentSave = studentOptional.get();
			
			 studentSave.setStudentName(studentData.getStudentName() != null ? studentData.getStudentName(): studentSave.getStudentName());
			 studentSave.setStudentAddress(studentData.getStudentAddress() != null ? studentData.getStudentAddress(): studentSave.getStudentAddress());
			 studentSave.setMobileNumber(studentData.getMobileNumber() != null ? studentData.getMobileNumber():  studentSave.getMobileNumber());
				
			 studentSave.setUpdatedAt(new Date(System.currentTimeMillis()));
			
			studentRepository.save(studentData);
			
			return new ResponseEntity<> (studentSave, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("Student not found by id "+id, HttpStatus.NOT_FOUND );
		}
		
	}
	
	
//	delete data
	@DeleteMapping("/student/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
		
		try {
			
			studentRepository.deleteById(id);
			
			return new ResponseEntity<>("Successfully delete document by id "+id,HttpStatus.OK);	
		} catch (Exception e) {
			
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
}
	
//	para sa html display
	// Get all students and display them in HTML
    @GetMapping("/api/students")
    public String getAllStudents(Model model) {
        List<StudentData> students = studentRepository.findAll();

        if (!students.isEmpty()) {
            model.addAttribute("students", students);
        } else {
            model.addAttribute("error", "No students available");
        }

        return "student"; // This refers to students.html
    }
	
	
}
