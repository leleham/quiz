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

	// �]�� id �����b DB �O AI(Auto IncrementaL) , �ҥH�n�[�W������
	// GenerationType.IDENTITY �O���D�����Ʀr�W����Ѹ�Ʈw
	// ���ݩʪ���ƫ��A�O Integer�ɡA�n�[
	// ���ݩʪ���ƫ��A�O Int�ɡA�D����:���Y�n�b�s�W(JPA��SAVE)��ƫ�A�Y�ɨ��o�ӵ���ƪ��y�����A�N�n�[
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
