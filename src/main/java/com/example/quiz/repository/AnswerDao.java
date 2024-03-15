package com.example.quiz.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.quiz.entity.Answer;

@Repository
@Transactional
public interface AnswerDao extends JpaRepository<Answer, Integer> {

	public List<Answer> findByQuizIdOrderByQuId(int quizId);

	public boolean existsByQuizIdAndEmail(int quizId, String email);

}
