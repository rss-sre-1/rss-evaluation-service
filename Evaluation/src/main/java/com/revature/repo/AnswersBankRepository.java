package com.revature.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.revature.entity.AnswersBank;
import com.revature.entity.UserQuizScore;

@Repository
public interface AnswersBankRepository extends JpaRepository<AnswersBank,Long>, CrudRepository<AnswersBank,Long>{
	public Optional<List<AnswersBank>> findAnswersByUserScore(UserQuizScore attempt);
}
