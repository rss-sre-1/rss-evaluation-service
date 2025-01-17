package com.revature.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.entity.QuestionsBank;
import com.revature.entity.Quiz;

@Repository
public interface QuestionsBankRepository extends JpaRepository<QuestionsBank, Long>, CrudRepository<QuestionsBank, Long> {

	public Optional<List<QuestionsBank>> findQuestionsByQuiz(Quiz q);

}
