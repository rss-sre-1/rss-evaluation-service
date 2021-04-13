package com.revature.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.entity.Quiz;
import com.revature.entity.Subject;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long>, CrudRepository<Quiz, Long> {
	
	public Optional<List<Quiz>> findQuizBySubject(Subject subject);
	

	
	
	
		
}


