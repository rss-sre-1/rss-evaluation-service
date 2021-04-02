package com.revature.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.revature.entity.Quiz;
import com.revature.service.QuizService;

@RestController
@RequestMapping(value="/quiz")
public class QuizController {
	
	private static final Logger log= LoggerFactory.getLogger(QuestionsBankController.class);
	//
	@Autowired
	QuizService qs;
	
	//Change endpoint from /addquiz to /admin/add
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody()
	public Quiz insertQuiz (@RequestBody Quiz q) {
		//Log4j
		MDC.put("Quiz added", q.toString());
		log.info("Quiz added"+" "+q.getQuizTopic());
		System.out.println(q);
		return this.qs.insertQuiz(q);
	}
	
	//Change endpoint from /findbyid to /obtain/id
	@RequestMapping(value = "/obtain/id", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody()
	public Quiz findQuizById (@RequestBody Quiz q) {
		
		Quiz q1 = qs.findById(q.getQuizId());
		q1.setSubjectId(q1.getSubject().getSubjectId());
		MDC.put("Pulling by id", q.toString());
		log.info("Quiz" + q + "pulled by id");
		return q1;
	}
	
	//Change endpoint from /findbysubject to /obtain/subject
	@RequestMapping(value = "/obtain/subject", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody()
	public List<Quiz> findQuizBySubjectId (@RequestBody Quiz q) {
		System.out.println(q);
		//return this.qs.findQuizBySubject(q.getSubjectId());
		List<Quiz> q1 = qs.findQuizBySubject(q.getSubjectId());
		for (int i = 0; i < q1.size(); i++) {
		    q1.get(i).setSubjectId(q.getSubjectId());
		}
		MDC.put("Pulling by subject", q.toString());
		log.info("Quiz" + q + "pulled by id");
		return q1;
	}
	
	//Change endpoint from /getallquizzes to /obtain/all
	@RequestMapping(value = "/obtain/all", method = RequestMethod.GET)
	@ResponseBody()
	public List<Quiz> getAllQuizzes(){
		return this.qs.getAllQuizzes();
	}	
}
