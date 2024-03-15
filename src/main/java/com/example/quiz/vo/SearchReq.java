package com.example.quiz.vo;

import java.time.LocalDate;

public class SearchReq {

	private String quizName;

	private LocalDate startDate;

	private LocalDate endDate;

	private boolean backend;

	public SearchReq() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SearchReq(String quizName, LocalDate startDate, LocalDate endDate, boolean isBackend) {
		super();
		this.quizName = quizName;
		this.startDate = startDate;
		this.endDate = endDate;
		this.backend = isBackend;
	}

	public String getQuizName() {
		return quizName;
	}

	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isBackend() {
		return backend;
	}

	public void setBackend(boolean isBackend) {
		this.backend = isBackend;
	}

}
