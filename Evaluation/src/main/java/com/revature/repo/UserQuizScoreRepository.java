package com.revature.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.entity.Quiz;
import com.revature.entity.UserQuizScore;

@Repository
public interface UserQuizScoreRepository extends JpaRepository<UserQuizScore, Long>, CrudRepository<UserQuizScore, Long> {
	
	public Optional<List<UserQuizScore>> findQuizByUserEmail(String userEmail);
	public Optional<List<UserQuizScore>> findByUserEmailAndQuiz(String userEmail, Quiz quiz);
}
