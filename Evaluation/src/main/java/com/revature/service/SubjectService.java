package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.entity.Subject;
import com.revature.exceptions.EvaluationException;
import com.revature.exceptions.NoContentException;
import com.revature.repo.SubjectRepository;

@Service
public class SubjectService {
	
	//Auto-wiring subject repository.
	@Autowired
	SubjectRepository sr;
	
	//Method to find all subjects
	public List<Subject> subjectList() {
		return sr.findAll();
	}
	
	//MEthod to fins subject by subject ID.
	public Subject findById(Long subjectId) {	
		return sr.findById(subjectId).orElseThrow(() -> new NoContentException("Unable to find Subject by id."));
		
	}
	
	//Method to insert Subject into database.
	public String insertSubject(Subject s) {
		try {
			sr.save(s);
			return "{'message':'Subject added successfully.'}";
		} catch (Exception e) {
			throw new EvaluationException("Unable to add Subject.");
		}
	}
	
	//Method to delete subject by subject ID.
	public String deleteSubjectById(Long subjectId) {
		try {
			sr.deleteById(subjectId);
			return "{'message':'Subject deleted successfully.'}";
		} catch (Exception e) {
			throw new EvaluationException("Unable to delete Subject by id.");
		}
	}


}
