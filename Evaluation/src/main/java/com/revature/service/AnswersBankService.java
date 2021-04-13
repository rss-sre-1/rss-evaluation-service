package com.revature.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.entity.AnswersBank;
import com.revature.entity.UserQuizScore;
import com.revature.exceptions.EvaluationException;
import com.revature.exceptions.NoContentException;
import com.revature.repo.AnswersBankRepository;
import com.revature.repo.QuestionsBankRepository;
import com.revature.repo.QuizRepository;
import com.revature.repo.SubjectRepository;
import com.revature.repo.UserQuizScoreRepository;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@Service
public class AnswersBankService {

	@Autowired
	QuestionsBankRepository qbr;
	@Autowired
	QuizRepository qr;
	@Autowired
	SubjectRepository sr;
	@Autowired
	AnswersBankRepository abr;
	@Autowired
	UserQuizScoreRepository uqsr;
	@Autowired
	QuizService qs;
	
	private MeterRegistry meterRegistry;
	private Counter quizGradeCounter;
	private static final String QUIZGRADED = "quiz_graded";
	private static final String GRADED = "graded";
	private static final String TYPE = "type";
	
	@Autowired
	public AnswersBankService (MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        quizGradeCounter = meterRegistry.counter(QUIZGRADED, TYPE, GRADED);
	}
	
	//We use constructor auto-wiring to auto-wired multiple repositories.
	//@Autowired
	public AnswersBankService(QuestionsBankRepository qbRepository, QuizRepository qRepository, SubjectRepository sRepository, AnswersBankRepository aRepository, UserQuizScoreRepository uRepository, QuizService qService) {
		this.qbr = qbRepository;
		this.qr = qRepository;
		this.sr = sRepository;
		this.abr = aRepository;
		this.uqsr = uRepository;
		this.qs = qService;
	}
	
	
		//Returns List of answers based on a specific UserQuizScore
		public List<AnswersBank> findAnswersByAttempt(UserQuizScore attempt){
			
			return abr.findAnswersByUserScore(attempt).orElseThrow(() -> new NoContentException("Unable to find answer by attempt."));
		}
		
		
		//Returns a List of only correct answers based on a specific Quiz
//		public List<String> findCorrectAnswersByQuiz(Quiz q){
//			
//			List<QuestionsBank> questions = qbr.findQuestionsByQuiz(q);
//	
//			List<String> correctAnswers = new ArrayList<>();
//			
//			for(QuestionsBank qb : questions) {
//				
//				correctAnswers.add(qb);
//			}
//			
//			return correctAnswers;		
//		}
		
		
		//Method to add an AnswersBank
		public AnswersBank addAnswersBank(AnswersBank ab) {
			ab.setQuestion(qbr.findById(ab.getQuestion().getQuestionId()).get());
			ab.setUserScore(uqsr.findById(ab.getUserScore().getUserScoreId()).get());
			quizGradeCounter.increment(1);
			try {
			return abr.save(ab);
			} catch (Exception e) {
				throw new EvaluationException("Unable to save answers.");
			}
		}
		
		//Method list of AnswersBank, for debugging
		public List<AnswersBank> findAll(){
			return abr.findAll();
		}
}
