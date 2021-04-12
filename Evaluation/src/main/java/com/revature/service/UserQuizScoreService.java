package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.entity.Quiz;
import com.revature.entity.UserQuizScore;
import com.revature.exceptions.EvaluationException;
import com.revature.repo.QuizRepository;
import com.revature.repo.UserQuizScoreRepository;

@Service
public class UserQuizScoreService {

	UserQuizScoreRepository uqsr;
	QuizRepository qr;
	
	//We use constructor auto-wiring to auto-wired multiple repositories.
	@Autowired
	public UserQuizScoreService(UserQuizScoreRepository uqsRepository, QuizRepository qRepository) {
		this.uqsr=uqsRepository;
		this.qr = qRepository;
	}
	
	//Methods to insert user taken quiz score.
	//we get only quizId from front-end and then we find quiz using that quizId. 
	//Then we set that quiz in the user quiz score object to insert that record into database.
	public UserQuizScore InsertUserQuizScore(UserQuizScore uqs) {
		uqs.setQuiz(qr.findById(uqs.getQuizId()).orElseThrow(() -> new NoContentException("Unable to find Quiz by id"))); 
		try {
			return uqsr.save(uqs);
		} catch (Exception e) {
			throw new EvaluationException("Unable to add User Quiz Score.");
		}
	}
	
	//Method to find taken quiz by userEmail which returns only list of quiz ID of taken quizzes.
	public List<Long> getTakenQuiz(String userEmail){
		List<Long> quizList = new ArrayList<>();
		List<UserQuizScore> uqsList = new ArrayList<UserQuizScore>();
		uqsList=uqsr.findQuizByUserEmail(userEmail).orElseThrow(() -> new NoContentException("Unable to find User Quiz Scores by User Email"));
		for (int i = 0; i < uqsList.size(); i++) {
		    quizList.add(uqsList.get(i).getQuiz().getQuizId());
		}
		return quizList;
	}
	
	
	public List<UserQuizScore> findByUserAndQuiz(String userEmail, long quizId){
		Quiz quiz = qr.findById(quizId).orElseThrow(() -> new NoContentException("Unable to find Quiz by id"));
		return uqsr.findByUserEmailAndQuiz(userEmail, quiz).orElseThrow(() -> new NoContentException("Unable to find User Quiz Scores by User Email"));
	}
	
	public UserQuizScore findById(long id) {
		return uqsr.findById(id).orElseThrow(() -> new NoContentException("Unable to find User Quiz Score by id"));
	}
}
