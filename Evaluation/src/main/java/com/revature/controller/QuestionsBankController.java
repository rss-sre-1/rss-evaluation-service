package com.revature.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.revature.beans.Question;
import com.revature.beans.Result;
import com.revature.entity.AnswersBank;
import com.revature.entity.Option;
import com.revature.entity.QuestionsBank;
import com.revature.entity.UserQuizScore;
import com.revature.service.AnswersBankService;
import com.revature.service.QuestionsBankService;
import com.revature.service.UserQuizScoreService;

@RestController
@RequestMapping(value="/question")
public class QuestionsBankController {
	
	private static final Logger log= LoggerFactory.getLogger(QuestionsBankController.class);
	
	@Autowired QuestionsBankService qbs;
	UserQuizScoreService uqss;
	AnswersBankService abs;
	
	//We use constructor auto-wiring to auto-wired multiple services.
	@Autowired
	public QuestionsBankController( UserQuizScoreService uqsService, AnswersBankService abService) {
		this.uqss=uqsService;
		this.abs=abService;
	}
	
	@RequestMapping(value = "/admin/questions", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody()
	public List<QuestionsBank> getQuestions (@RequestBody long id) {
		MDC.put("Quiz questions retreived", id);
		log.info("Questions returned");
		return this.qks.findQuestionsByQuiz(id);
	}

	//Change endpoint from /add to /admin/add
	 @RequestMapping(value = "/admin/add", method = RequestMethod.POST,
	            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

	    @ResponseBody()
	    public QuestionsBank insertQuestion (@RequestBody QuestionsBank qb) {
	    	MDC.put("Question added", qb.toString());
	        log.info("Inserted question"); 
			return this.qbs.InsertQuestion(qb);
	 	}
	 
	 //Change endpoint from /delete to /admin/delete
	 @RequestMapping(value = "/admin/delete", method = RequestMethod.POST,
	            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

	    @ResponseBody()
	    public List<String> deleteQuestion (@RequestBody QuestionsBank qb) {
	    	//Log4j
	    	MDC.put("Question deleted", qb.toString());
	        log.info("Deleted question"); 
			return this.qbs.deleteQuestion(qb.getQuestionId());
	 	}
	 
	 	//Change endpoint from /addall to /admin/addall
		@RequestMapping(value = "/admin/addall", method = RequestMethod.POST,
	            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	    @ResponseBody()
	    public List<QuestionsBank> insertAllQuestions (@RequestBody List<QuestionsBank> qbList) {
	    	List<QuestionsBank> qbList1 = new ArrayList<QuestionsBank>();
	    	System.out.println("start of insert");
	    	for (int i = 0; i < qbList.size(); i++) {
	    		QuestionsBank current = qbList.get(i);
	    		
	    		
	    		System.out.println(current);
	    		List<Option> options = current.getOptions();
	    		
	    		for(Option op : options) {
	    			System.out.println(op.getCorrect());
	    			op.setQb(current);
	    		}

	    		
	    		QuestionsBank res = qbs.InsertQuestion(current);
	    		qbList1.add(res);
	    	}
	    	
	    	
	    	for(QuestionsBank new_qb : qbList1) {
	    		new_qb.setQuiz(null);
	    		List<Option> m_options = new_qb.getOptions();
	    		for(Option op : m_options) {
	    			op.setQb(null);
	    		}
    		}
	    	
	    	//Log4j
	    	MDC.put("Multiple questions added", qbList.toString());
	        log.info("Inserted multiple questions"); 
	    	return qbList1;
			
		}
		
}
