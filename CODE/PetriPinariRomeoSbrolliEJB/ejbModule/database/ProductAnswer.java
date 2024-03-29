package database;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * The persistent class for the ProductAnswer database table.
 * @author Cristian Sbrolli
 */
@Entity
public class ProductAnswer implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "questionId")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "submissionId")
	private Submission submission;

	private String word;
	
	public ProductAnswer() {}
	
	public ProductAnswer(Question question,String word) {
		this.question = question;
		this.word = word;
	}
	
	/* ******************
	 * 		SETTERS		*
	 ********************/
	
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}
	
	public void setQuestionId(Question question) {
		this.question = question;
	}
	
	public void setWord(String word) {
		this.word = word;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	/* ******************
	 * 		GETTERS		*
	 ********************/
	
	public Question getQuestionId() {
		return question;
	}


	public String getWord() {
		return word;
	}

	public int getId() {
		return id;
	}
	
	public Submission getSubmission() {
		return submission;
	}


}