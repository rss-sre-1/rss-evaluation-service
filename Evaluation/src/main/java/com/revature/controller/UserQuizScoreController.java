package com.revature.controller;

import java.util.ArrayList;
import java.util.List;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import com.revature.entity.UserQuizScore;
import com.revature.service.UserQuizScoreService;

@RestController
@RequestMapping(value="/userscore")
public class UserQuizScoreController {
	private static final Logger log= LoggerFactory.getLogger(QuestionsBankController.class);
	
	@Autowired
	UserQuizScoreService uqss;
	
	//Change endpoint from /takenquiz to /obtain/taken
	@RequestMapping(value = "/obtain/taken", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody()
    public List<Long> getTakenQuiz (@RequestBody UserQuizScore uqs) {
		List<Long> takenQuiz = new ArrayList<>();
		takenQuiz = uqss.getTakenQuiz(uqs.getUserEmail());
		MDC.put("Receiving takenQuiz", takenQuiz);
		log.info("Getting taken quiz");
		return takenQuiz;
	}
	
	@GetMapping(value = "attempts", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserQuizScore> findAttempts(@RequestParam("userEmail") String userEmail, @RequestParam("quizId") long quizId) {
		log.info("Finding attempts");
		return uqss.findByUserAndQuiz(userEmail, quizId);
	}
}
