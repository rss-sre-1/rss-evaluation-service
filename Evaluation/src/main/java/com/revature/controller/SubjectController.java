package com.revature.controller;

import java.util.List;

import org.jboss.logging.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.revature.entity.Subject;
import com.revature.service.SubjectService;

import jdk.internal.org.jline.utils.Log;

@RestController
@RequestMapping(value="/subject")
public class SubjectController {
	
	@Autowired
    SubjectService ss;
	
	//Change endpoint from /all to /obtain/all
	@GetMapping("/obtain/all")
    public List<Subject> getAllSubjects() {
        return ss.subjectList();
    }
	
	//Change endpoint from /add to /admin/add
	@RequestMapping(value = "/admin/add", method = RequestMethod.POST,
	            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody()
	public String insertSubject (@RequestBody Subject s) {
		//Log4j
		MDC.put("Subject addition", s.getSubjectName());
		Log.info("Subject added"+""+s.getSubjectName());
		return this.ss.insertSubject(s);
			
	}
}
