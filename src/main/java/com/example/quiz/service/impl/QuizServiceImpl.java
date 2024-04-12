package com.example.quiz.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.example.quiz.constants.RtnCode;
import com.example.quiz.entity.Answer;
import com.example.quiz.entity.Quiz;
import com.example.quiz.repository.AnswerDao;
import com.example.quiz.repository.QuizDao;
import com.example.quiz.service.ifs.QuizService;
import com.example.quiz.vo.CreateOrUpdateReq;
import com.example.quiz.vo.AnswerReq;
import com.example.quiz.vo.BaseRes;
import com.example.quiz.vo.SearchRes;
import com.example.quiz.vo.StatisticsRes;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuizServiceImpl implements QuizService {

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private AnswerDao answerDao;

	@Override
	public BaseRes create(CreateOrUpdateReq req) {
		return checkParams(req, true);
	}

//		BaseRes res = checkParams(req);
//		if (res != null) {
//			return res;
//		}
//		// 檢查問券是否已存在
//		if (quizDao.existsByQuizId(req.getQuizlist().get(0).getQuizId())) {
//			return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
//
//		}
//		quizDao.saveAll(req.getQuizlist());
//		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
//
//	}

	@Override
	public SearchRes search(String quizName, LocalDate startDate, LocalDate endDate, boolean isBackend) {
		if (!StringUtils.hasText(quizName)) {
			quizName = ""; // containing 代的參數值為空字串，表示會撈取全部

		}
		if (startDate == null) {
			startDate = LocalDate.of(1970, 1, 1); // 將開始時間設定為很早之前的時間

		}
		if (endDate == null) {
			endDate = LocalDate.of(2099, 12, 31);// 將結束時間設定在很久之後的時間
		}
		if (isBackend) { // isBackend == true
			return new SearchRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(),
					quizDao.findByQuizNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqual(quizName,
							startDate, endDate));

		} else {
			return new SearchRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(),
					quizDao.findByQuizNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualAndPublishedTrue(
							quizName, startDate, endDate));
		}

	}

	@Override
	public BaseRes daleteQuiz(List<Integer> quizIds) {
		if (CollectionUtils.isEmpty(quizIds)) {// 同時判斷 quizIds 是否為null 以及空集合
			return new BaseRes(RtnCode.PARAM_ERROE.getCode(), RtnCode.PARAM_ERROE.getMessage());
		}
		quizDao.deleteAllByQuizIdInAndPublishedFalseOrQuizIdInAndStartDateAfter(quizIds, quizIds, LocalDate.now());

		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public BaseRes deleteQuestions(int quizId, List<Integer> quIds) {
		if (quizId <= 0 || CollectionUtils.isEmpty(quIds)) {
			return new BaseRes(RtnCode.PARAM_ERROE.getCode(), RtnCode.PARAM_ERROE.getMessage());
		}
		// 根據 (quizId and 未發布) or (quizId and 尚未開始)找問券
		List<Quiz> res = quizDao.findByQuizIdAndPublishedFalseOrQuizIdAndStartDateAfterOrderByQuId(// )
				quizId, quizId, LocalDate.now());
		if (res.isEmpty()) {
			return new BaseRes(RtnCode.QUIZ_NOT_FOUND.getCode(), RtnCode.QUIZ_NOT_FOUND.getMessage());

		}
//		int j = 0;
//		for(int item : quIds) {// quIds = 1, 4
//			//1 : j = 0, item = 1, item -1 -j = 1-1-0 = 0:
//			//2 : j = 1, item = 4, item -1 -j = 4 -1 -1 =2
//			res.remove(item - 1 -j);
//			j++;
//		}
		List<Quiz> retainList = new ArrayList<>();
		for (Quiz item : res) {
			if (!quIds.contains(item.getQuId())) {// 保留不在刪除清單中的
				retainList.add(item);
			}
		}
		for (int i = 0; i < retainList.size(); i++) {
			retainList.get(i).setQuId(i + 1);
		}
		// 刪除整張問券
		quizDao.deleteByQuizId(quizId);
		// 將保留的問題存回DB
		if (!retainList.isEmpty()) {
			quizDao.saveAll(retainList);
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());

	}

	@Override
	public BaseRes update(CreateOrUpdateReq req) {
		return checkParams(req, false);
	}
//		if (res != null) {
//			return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
//		}
//
//		if (!quizDao.existsByQuizIdAndPublishedFalseOrQuizIdAndStartDateAfter(req.getQuizlist().get(0).getQuizId(),
//				req.getQuizlist().get(0).getQuizId(), LocalDate.now())) {
//			return new BaseRes(RtnCode.QUIZ_NOT_FOUND.getCode(), RtnCode.QUIZ_NOT_FOUND.getMessage());
//		}
//		quizDao.deleteByQuizId(req.getQuizlist().get(0).getQuizId());
//		// 根據是否要發布，再把 published的值 set到傳送過來的quizlist中
//
//		quizDao.saveAll(req.getQuizlist());
//		return create(req);
//
//	}

	private BaseRes checkParams(CreateOrUpdateReq req, boolean isCreate) {
		if (CollectionUtils.isEmpty(req.getQuizList())) {
			return new BaseRes(RtnCode.PARAM_ERROE.getCode(), RtnCode.PARAM_ERROE.getMessage());
		}

		for (Quiz item : req.getQuizList()) {
			if (item.getQuizId() <= 0 || item.getQuId() == 0 || !StringUtils.hasText(item.getQuizName())
					|| item.getStartDate() == null || item.getEndDate() == null
					|| !StringUtils.hasText(item.getQuestion()) || !StringUtils.hasText(item.getType())) {
				return new BaseRes(RtnCode.PARAM_ERROE.getCode(), RtnCode.PARAM_ERROE.getMessage());

			}
		}
		// 蒐集 req 中所有的 quizId
		// 原則上是一個req中所有的quizId會相同(一張問卷多個問題)。但也是有可能奇功一筆資料的quizId是錯的
		// 為保證所有資料的正確性，就先去蒐集req中所有的quizId
//		
		Set<Integer> quizIds = new HashSet<>(); // set 不會存在相同的值， 就是set中已存在相同的值，就不會新增
		Set<Integer> quIds = new HashSet<>(); // 檢查問題編號是否有重複，正常應該是都不重複
		for (Quiz item : req.getQuizList()) {
			quizIds.add(item.getQuizId());
			quIds.add(item.getQuId());
		}
		if (quizIds.size() != 1) {
			return new BaseRes(RtnCode.QUIZ_ID_DOES_NOT_MATCH.getCode(), RtnCode.QUIZ_ID_DOES_NOT_MATCH.getMessage());
		}
		if (quizIds.size() != req.getQuizList().size()) {
			return new BaseRes(RtnCode.DUPLICATED_QUESTION_ID.getCode(), RtnCode.DUPLICATED_QUESTION_ID.getMessage());
		}

		// 檢查開始時間不能大於結束時間
		for (Quiz item : req.getQuizList()) {
			if (item.getStartDate().isAfter(item.getEndDate())) {
				return new BaseRes(RtnCode.TIME_FORMAT_ERROR.getCode(), RtnCode.TIME_FORMAT_ERROR.getMessage());

			}
		}
		if (isCreate) {
			// 檢查問券是否已存在
			if (quizDao.existsByQuizId(req.getQuizList().get(0).getQuizId())) {
				return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
			}
		} else { // isCreate == false，執行原本 update 中的方法
			// 確認傳過來的 quizId 是否真的可以刪除(可以刪除的條件是: 尚未發布或是尚未開始)
			if (!quizDao.existsByQuizIdAndPublishedFalseOrQuizIdAndStartDateAfter(req.getQuizList().get(0).getQuizId(),
					req.getQuizList().get(0).getQuizId(), LocalDate.now())) {
				return new BaseRes(RtnCode.QUIZ_NOT_FOUND.getCode(), RtnCode.QUIZ_NOT_FOUND.getMessage());
			}
			// 刪除整張問卷
			try {
				quizDao.deleteByQuizId(req.getQuizList().get(0).getQuizId());

			} catch (Exception e) {
				return new BaseRes(RtnCode.DELETE_QUIZ_ERROR.getCode(), RtnCode.DELETE_QUIZ_ERROR.getMessage());
			}
		}
		// 根據是否要發布，再把 published 的值 set 到傳送過來的 quizList 中
		for (Quiz item : req.getQuizList()) {
			item.setPublished(req.isPublished());
		}
		// 存回DB
		quizDao.saveAll(req.getQuizList());
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public BaseRes answer(AnswerReq req) {
		if (CollectionUtils.isEmpty(req.getAnswerList())) {
			return new BaseRes(RtnCode.PARAM_ERROE.getCode(), RtnCode.PARAM_ERROE.getMessage());
		}
		for (Answer item : req.getAnswerList()) {
			if (!StringUtils.hasText(item.getName()) || !StringUtils.hasText(item.getPhone())
					|| !StringUtils.hasText(item.getEmail()) || item.getQuizId() <= 0 || item.getQuId() <= 0
					|| item.getAge() < 0) {
				return new BaseRes(RtnCode.PARAM_ERROE.getCode(), RtnCode.PARAM_ERROE.getMessage());
			}

		}

		Set<Integer> quizIds = new HashSet<>(); // set 不會存在相同的值， 就是set中已存在相同的值，就不會新增
		Set<Integer> quIds = new HashSet<>(); // 檢查問題編號是否有重複，正常應該是都不重複
		for (Answer item : req.getAnswerList()) {
			quizIds.add(item.getQuizId());
			quIds.add(item.getQuId());
		}
		if (quizIds.size() != 1) {
			return new BaseRes(RtnCode.QUIZ_EXISTS.getCode(), RtnCode.QUIZ_EXISTS.getMessage());
		}
		if (quizIds.size() != req.getAnswerList().size()) {
			return new BaseRes(RtnCode.DUPLICATED_QUESTION_ID.getCode(), RtnCode.DUPLICATED_QUESTION_ID.getMessage());
		}
		// 檢查必填問題是否有回答
		List<Integer> res = quizDao.findQuIdsByQuizIdAndNecessaryTrue(req.getAnswerList().get(0).getQuizId());
		for (Answer item : req.getAnswerList()) {
			if (res.contains(item.getQuId()) && StringUtils.hasText(item.getAnswer())) {
				return new BaseRes(RtnCode.QUESTION_NO_ANSWER.getCode(), RtnCode.QUESTION_NO_ANSWER.getMessage());
			}
		}
		for (int item : res) {
			Answer ans = req.getAnswerList().get(item);
			if (!StringUtils.hasText(ans.getAnswer())) {
				return new BaseRes(RtnCode.QUESTION_NO_ANSWER.getCode(), RtnCode.QUESTION_NO_ANSWER.getMessage());

			}
		}
		if (answerDao.existsByQuizIdAndEmail(req.getAnswerList().get(0).getQuizId(),
				req.getAnswerList().get(0).getEmail())) {
			answerDao.saveAll(req.getAnswerList());
			return new BaseRes(RtnCode.DUPLICATED_QUIZ_ANSWER.getCode(), RtnCode.DUPLICATED_QUIZ_ANSWER.getMessage());
		}
		return new BaseRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}

	@Override
	public StatisticsRes statistics(int quizId) {
		if (quizId <= 0) {
			return new StatisticsRes(RtnCode.PARAM_ERROE.getCode(), RtnCode.PARAM_ERROE.getMessage());
		}

		List<Quiz> quizs = quizDao.findByQuizId(quizId);
		// Map<String,Integer> quIdAnswerMap = new HashMap<>();
		// qus 是非簡答題的題目編號之集合
		List<Integer> qus = new ArrayList<>();
		// 若是簡答題，options是空的
		for (Quiz item : quizs) {
			if (StringUtils.hasText(item.getOptions())) {
				qus.add(item.getQuId());
			}
		}
		List<Answer> answers = answerDao.findByQuizIdOrderByQuId(quizId);
		// quIdAnswerMap: 問題編號與答案的 mapping
		Map<Integer, String> quIdAnswerMap = new HashMap<>();
		// 把非簡答題的每題答案各自串成字串，及一個選項(答案)會有一個字串
		// StringBuffer strBuf = new StringBuffer();
		for (Answer item : answers) {
			// 若是包含在qus此List中的,就表示是選擇題(單，多選)
			if (qus.contains(item.getQuId())) {
				// 若key 已存在
				if (quIdAnswerMap.containsKey(item.getQuId())) {
					// 1.透過key取得對應的value
					String str = quIdAnswerMap.get(item.getQuId());
					// 2.把原有的值和這次取得的值串接變成新的值
					str += item.getAnswer();
					// 3.將新的數值放回到原本的key之下
					quIdAnswerMap.put(item.getQuId(), str);
				} else {// key 不存在，直接新增key 和value
					quIdAnswerMap.put(item.getQuId(), item.getAnswer());

				}

			}
		}
		// 計算每題每個選項的次數

		// answerCountMap:選項(答案)與次數的mapping

		Map<Integer, Map<String, Integer>> quizIdAndAnsCountMap = new HashMap<>();
		// 使用foreach 遍歷 map中的每個項目
		// 遍歷的對象要從map轉成entrySet，好處是可以直接取得map中的key和value
		for (Entry<Integer, String> item : quIdAnswerMap.entrySet()) {
			Map<String, Integer> answerCountMap = new HashMap<>();
			String[] optionList = quizs.get(item.getKey() - 1).getOptions().split(";");
			// 把問題的選項與次數做mapping
			for (String option : optionList) {
				String newStr = item.getValue();
				int length1 = newStr.length();
				newStr = newStr.replace(option, "");
				int length2 = newStr.length();
				int count = (length1 - length2) / option.length();
				answerCountMap.put(option, count);
			}
			quizIdAndAnsCountMap.put(item.getKey(), answerCountMap);
		}

		return new StatisticsRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage(), quizIdAndAnsCountMap);
	}

	@Override
	public BaseRes objMapper(String str) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			Quiz quiz = mapper.readValue(str, Quiz.class);

		} catch (Exception e) {
			// 1.回傳固定錯誤訊息
//			return new BaseRes(RtnCode.PARAM_ERROR.getCode(),RtnCode.ERROR_CODE.getMessage());
			// 2.回傳 catch 中 exception 的錯誤訊息
			return new BaseRes(RtnCode.ERROR_CODE, e.getMessage());

		}
		return new StatisticsRes(RtnCode.SUCCESS.getCode(), RtnCode.SUCCESS.getMessage());
	}
}
