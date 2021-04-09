package com.revature.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.entity.QuestionsBank;
import com.revature.entity.Quiz;
import com.revature.exceptions.EvaluationException;
import com.revature.repo.QuestionsBankRepository;
import com.revature.repo.QuizRepository;
import com.revature.repo.SubjectRepository;

@Service
public class QuestionsBankService {
	
	QuestionsBankRepository qbr;
	QuizRepository qr;
	SubjectRepository sr;
	
	//We use constructor auto-wiring to auto-wired multiple repositories.
	@Autowired
	public QuestionsBankService(QuestionsBankRepository qbRepository, QuizRepository qRepository, SubjectRepository sRepository) {
		this.qbr=qbRepository;
		this.qr = qRepository;
		this.sr = sRepository;
	}
	
	//Method to find all questions.
	public List<QuestionsBank> findAllQuestions(){
		return qbr.findAll();
	}
	
	//Methods to find questions by quiz.
	//we get only quizId from front-end and then we find object of quiz using that quizId. So that we use it to find the questions by quiz.
	//EDIT: changing parameter to int, for direct quiz id comparison
	public List<QuestionsBank> findQuestionsByQuiz(long id){
		//qb.setQuiz(qr.findById(qb.getQuiz().getQuizId()).get());
		return qbr.findQuestionsByQuiz(qr.findById(id).orElse(null)).orElseThrow(() -> new EvaluationException("Unable to find Questions by Quiz id."));	
	}
	
	//Method to get question by question ID.
	public QuestionsBank getQuestion(long questionId){
		return this.qbr.findById(questionId).orElseThrow(() -> new EvaluationException("Unable to find Question by Question id."));
	}
	
	//Methods to insert question.
	//we get only quizId from front-end and then we find quiz using that quizId. 
	//Then we set that quiz in the questionsbank object to insert that record into database.
	public QuestionsBank InsertQuestion(QuestionsBank qb) {
		
		long quiz_id = qb.getQuizId();
		Quiz quiz = qr.findById(quiz_id).orElseThrow(() -> new EvaluationException("Unable to find Quiz by id."));
		qb.setQuiz(quiz); 
		try {
		return qbr.save(qb);
		} catch (Exception e) {
			throw new EvaluationException("Unable to Quiz Question.");
		}
		
	}
	
	//Method to delete question by question ID.
	public List<String> deleteQuestion(long questionId) {
		List<String> tmp = new ArrayList<String>();
		try {
			this.qbr.deleteById(questionId);
		} catch (Exception e) {
			throw new EvaluationException("Unable to delete Question.");
		}
		tmp.add("deleted question with id " + questionId);
		return tmp;
	}
	

}
