package com.example.quiz.vo;

import java.util.List;

import com.example.quiz.entity.Quiz;

public class SearchRes extends BaseRes {
	
	
	private List<Quiz> quizlist;

	public SearchRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}

	public SearchRes(int code, String message, List<Quiz> quizlist) {
		super(code, message);
		this.quizlist = quizlist;
	}

	public List<Quiz> getQuizlist() {
		return quizlist;
	}

	public void setQuizlist(List<Quiz> quizlist) {
		this.quizlist = quizlist;
	}

}
