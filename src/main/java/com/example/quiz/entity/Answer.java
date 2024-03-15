package com.example.quiz.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity

@Table(name = "answer")
public class Answer {

	// 因為 id 此欄位在 DB 是 AI(Auto IncrementaL) , 所以要加上此註釋
	// GenerationType.IDENTITY 是指主見的數字增長交由資料庫
	// 當屬性的資料型態是 Integer時，要加
	// 當屬性的資料型態是 Int時，非必須:但若要在新增(JPA的SAVE)資料後，即時取得該筆資料的流水號，就要加
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "phone")
	private String phone;

	@Column(name = "email")
	private String email;

	@Column(name = "age")
	private int age;

	@Column(name = "quiz_id")
	private int quizId;

	@Column(name = "qu_id")
	private int quId;

	@Column(name = "answer")
	private String answer;

	@Column(name = "is_text")
	private boolean text;

	public Answer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Answer(String name, String phone, String email, int age, int quizId, int quId, String answer, boolean text) {
		super();

		this.name = name;
		this.phone = phone;
		this.email = email;
		this.age = age;
		this.quizId = quizId;
		this.quId = quId;
		this.answer = answer;
		this.text = text;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int qiizId) {
		this.quizId = qiizId;
	}

	public int getQuId() {
		return quId;
	}

	public void setQuId(int quId) {
		this.quId = quId;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public boolean isText() {
		return text;
	}

	public void setText(boolean text) {
		this.text = text;
	}

}
