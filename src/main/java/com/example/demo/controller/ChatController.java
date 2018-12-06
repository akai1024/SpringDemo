package com.example.demo.controller;

import java.util.Date;
import java.util.LinkedList;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.chat.ChatMessage;
import com.example.demo.serialize.SerializeManager;

@RestController
@RequestMapping("/chat")
public class ChatController {

	private static final String CHAT_SAVE = "chat";

	private static final int CHAT_RECORD_SIZE = 20;
	private LinkedList<ChatMessage> chatRecord;

	@PostConstruct
	public void init() {
		LinkedList<ChatMessage> chatRecordSave = SerializeManager.read(CHAT_SAVE);
		chatRecord = (chatRecordSave == null) ? new LinkedList<>() : chatRecordSave;

		fixChatRecordSize();
	}

	/**
	 * 每十秒存檔一次
	 */
	@Scheduled(fixedDelay = 10000)
	public void save() {
		SerializeManager.save(CHAT_SAVE, chatRecord);
	}

	@RequestMapping()
	public LinkedList<ChatMessage> getChatRecord() {
		return chatRecord;
	}

	@RequestMapping("/speak")
	public LinkedList<ChatMessage> playerSpeak(@RequestParam("msg") String msg, HttpServletRequest request) {
		if (msg == null || msg.isEmpty() || !PlayerController.isLogin(request)) {
			return chatRecord;
		}
		String name = PlayerController.getPlayer(request).getName();
		addChatRecord(name, msg);
		return chatRecord;
	}
	
	private void addChatRecord(String name, String msg) {
		chatRecord.addLast(new ChatMessage(new Date(), name, msg));
		fixChatRecordSize();
	}

	private void fixChatRecordSize() {
		int rmSize = chatRecord.size() - CHAT_RECORD_SIZE;
		if (rmSize > 0) {
			for (int i = 0; i < rmSize; i++) {
				chatRecord.removeFirst();
			}
		}
	}

}
