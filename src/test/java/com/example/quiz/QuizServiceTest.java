package com.example.quiz;

import java.time.LocalDate;
import java.util.ArrayList;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.quiz.entity.Quiz;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.BaseRes;
import com.example.quiz.vo.CreateOrUpdateReq;

@SpringBootTest
public class QuizServiceTest {

	@Autowired
	private QuizService quizService;
	
	@BeforeEach
	private void addDate() {
		CreateOr
		
	}
	@BeforeAll
	//private void addDate() {
		
		
	}



	@Test
	public void createTest() {
		CreateOrUpdateReq req = new CreateOrUpdateReq();
		BaseRes res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test fail!!");
		quizService.create(req);
		// ==============
//		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(0, 1, "test", "test", LocalDate.now().plusDays(2),
//				LocalDate.now().plusDays(9), "q_test", "single", true, "A;B;C;D", false))));
//
//		// req.setQuizList(new ArrayList(null)));
//		res = quizService.create(req);
//		Assert.isTrue(res.getCode() == 400, "create test fail!!");
//		// ===================
//		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, -1, "test", "test", LocalDate.now().plusDays(2),
//				LocalDate.now().plusDays(9), "q_test", "single", true, "A;B;C;D", false))));
//
//		res = quizService.create(req);
//		Assert.isTrue(res.getCode() == 400, "create test fail!!");
//		// ====================
//		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, 1, "test", "test", LocalDate.now().plusDays(2),
//				LocalDate.now().plusDays(9), "q_test", "single", true, "A;B;C;D", false))));
//
//		res = quizService.create(req);
//		Assert.isTrue(res.getCode() == 200, "create test fail!!");

	}

	@Test
	public void updateTest() {
	System.out.println();
}
	private void quizIdTest(CreateOrUpdateReq req, BaseRes res) {
		
		

}
	private void quidTest(CreateOrUpdateReq req, BaseRes res) {
		Quiz = 
		req.setQuizList(new ArrayList<>(Arrays.asList(new Quiz(1, -1, "test", "test", LocalDate.now().plusDays(2),
			LocalDate.now().plusDays(9), "q_test", "single", true, "A;B;C;D", false))));
		res = quizService.create(req);
		Assert.isTrue(res.getCode() == 400, "create test fail!!");
	}
