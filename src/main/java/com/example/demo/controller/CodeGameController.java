package com.example.demo.controller;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.codegame.CodeGameRecord;
import com.example.demo.codegame.GuessRecord;

/**
 * 猜密碼遊戲(Bulls and Cows)
 * 
 * @author kai
 *
 */
@RestController
@RequestMapping("/codeGame")
public class CodeGameController {

	private static final Logger logger = LoggerFactory.getLogger(CodeGameController.class);

	private static final int CODE_SIZE = 4;
	private ArrayList<Integer> currentCode = new ArrayList<>();
	private int guessTimes;
	private ArrayList<GuessRecord> guessRecords = new ArrayList<>();

	private ArrayList<CodeGameRecord> records = new ArrayList<>();

	@PostConstruct
	public void init() {
		generateCode();
	}

	@RequestMapping()
	public ArrayList<String> home() {
		return getCommandList();
	}

	private void generateCode() {

		currentCode.clear();
		guessTimes = 0;
		guessRecords.clear();

		ArrayList<Integer> codeList = new ArrayList<>();
		// 填入0~9共10個數字
		for (int i = 0; i <= 9; i++) {
			codeList.add(i);
		}

		Collections.shuffle(codeList);
		Collections.shuffle(codeList);

		for (int i = 0; i < CODE_SIZE; i++) {
			currentCode.add(codeList.get(i));
		}

		if (logger.isInfoEnabled()) {
			logger.info("current code is " + currentCode.toString());
		}
	}

	@RequestMapping("/commandList")
	public ArrayList<String> getCommandList() {
		ArrayList<String> list = new ArrayList<>();

		// 取得controller底下所有的RequestMapping
		Method[] methods = CodeGameController.class.getDeclaredMethods();
		for (Method method : methods) {
			for (Annotation annotation : method.getAnnotations()) {
				if (annotation.annotationType().equals(RequestMapping.class)) {
					RequestMapping requestMapping = (RequestMapping) annotation;
					String[] cmd = requestMapping.value();
					if (cmd.length > 0) {
						list.add(cmd[0].replaceAll("/", ""));
					}
				}
			}
		}

		return list;
	}

	@RequestMapping("/newRound")
	public String requestNewRound() {
		newRound(false);
		return "new round begin!";
	}

	private void newRound(boolean isSuccess) {
		addRecord(isSuccess);
		generateCode();
	}

	private void addRecord(boolean isSuccess) {
		CodeGameRecord record = new CodeGameRecord();
		record.setCode(currentCode.toString());
		record.setGuessTimes(guessTimes);
		record.setSuccess(isSuccess);
		records.add(record);
	}

	@RequestMapping("/record")
	public ArrayList<GuessRecord> getGuessRecords() {
		return guessRecords;
	}

	@RequestMapping("/history")
	public ArrayList<CodeGameRecord> getRecords() {
		return records;
	}

	@RequestMapping("/guess")
	private String guessCode(@RequestParam("code") String code) {
		int aCount = 0;
		int bCount = 0;

		if (code != null && code.length() == currentCode.size()) {
			HashSet<Integer> numSet = new HashSet<>();
			for (int i = 0; i < currentCode.size(); i++) {

				try {

					int guessNum = Integer.valueOf(code.substring(i, i + 1));
					if (numSet.contains(guessNum)) {
						return "illegal guessing pattern";
					} else {
						numSet.add(guessNum);
					}

					if (guessNum == currentCode.get(i)) {
						aCount++;
						continue;
					}

					if (currentCode.contains(guessNum)) {
						bCount++;
						continue;
					}

				} catch (Exception e) {
					return "illegal guessing pattern";
				}
			}
		}

		guessTimes++;
		if (aCount == currentCode.size()) {
			newRound(true);
			return "Bingo! current code is " + code;
		}

		String result = aCount + "A" + bCount + "B";
		GuessRecord record = new GuessRecord();
		record.setGuess(code);
		record.setResult(result);
		guessRecords.add(record);

		return result;
	}

}
