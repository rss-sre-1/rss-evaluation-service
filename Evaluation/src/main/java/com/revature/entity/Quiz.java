//This is a quiz entity which going to make QUIZZES table in database and create Many-to-One relationship with SUBJECTS table.
package com.revature.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="QUIZZES")

@EqualsAndHashCode(exclude= {"questions"})
@ToString(exclude= {"questions"})
public class Quiz {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="QUIZ_SEQ")
	@Column(name="QUIZ_ID")
	private long quizId;
	
	@Column(name="QUIZ_TOPIC", nullable=false)
	private String quizTopic;
	
	@Column(name="QUIZ_DESCRIPTION", nullable=true)
	private String quizDescription;
	
	@Column(name="QUIZ_TOTAL_POINTS", nullable=true)
	private int quizTotalPoints;
	
	@Column(name="QUIZ_DIFFICULTY", nullable=true)
	private String quizDifficulty;
	
	
	@Column(name="CREATOR_EMAIL", nullable=false)
	private String creatorEmail;
	
	//We create one transient field for subjectId.
	//It will take input from front-end and do the rest of the process which help to maintain relationship with SUBJECTS table. 
	private transient Long subjectId;
	
	//We create unidirectional Many-To-One relationship from QUIZZES table to SUBJECTS table where subject_id is a foreign key in QUIZZES table
	@ManyToOne
    @JoinColumn(name = "SUBJECT_ID")
    private Subject subject;
	
	@OneToMany(
			mappedBy="quiz",
			cascade = CascadeType.ALL,
	        orphanRemoval = true
			)
	@JsonIgnoreProperties({"quiz"})
	@JsonBackReference
	private List<QuestionsBank> questions;
	

	public Quiz() {
		super();
	}
	


	public Quiz(long quizId, String quizTopic, String quizDescription, int quizTotalPoints, String quizDifficulty,
			String creatorEmail, Long subjectId, Subject subject) {
		super();
		this.quizId = quizId;
		this.quizTopic = quizTopic;
		this.quizDescription = quizDescription;
		this.quizTotalPoints = quizTotalPoints;
		this.quizDifficulty = quizDifficulty;
		this.creatorEmail = creatorEmail;
		this.subjectId = subjectId;
		this.subject = subject;
	}


	public long getQuizId() {
		return quizId;
	}

	public void setQuizId(long quizId) {
		this.quizId = quizId;
	}

	public String getQuizTopic() {
		return quizTopic;
	}

	public void setQuizTopic(String quizTopic) {
		this.quizTopic = quizTopic;
	}

	public String getQuizDescription() {
		return quizDescription;
	}

	public void setQuizDescription(String quizDescription) {
		this.quizDescription = quizDescription;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}
	
	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	
	public int getQuizTotalPoints() {
		return quizTotalPoints;
	}

	
	public void setQuizTotalPoints(int quizTotalPoints) {
		this.quizTotalPoints = quizTotalPoints;
	}

	
	public String getQuizDifficulty() {
		return quizDifficulty;
	}


	public void setQuizDifficulty(String quizDifficulty) {
		this.quizDifficulty = quizDifficulty;
	}


	public List<QuestionsBank> getQuestions() {
		return questions;
	}



	public void setQuestions(List<QuestionsBank> questions) {
		this.questions = questions;
	}



	@Override
	public String toString() {
		return "Quiz [quizId=" + quizId + ", quizTopic=" + quizTopic + ", quizDescription=" + quizDescription
				+ ", quizTotalPoints=" + quizTotalPoints + ", quizDifficulty=" + quizDifficulty + ", creatorEmail="
				+ creatorEmail + ", subject=" + subject + ", questions=" + questions + "]";
	}


		
	
}
