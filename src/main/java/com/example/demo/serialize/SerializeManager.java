package com.example.demo.serialize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.ChatController;
import com.example.demo.controller.PlayerController;

/**
 * 序列化管理
 * 
 * @author kai
 *
 */
@RestController()
@RequestMapping("/save")
public class SerializeManager {

	private static Logger logger = LoggerFactory.getLogger(SerializeManager.class);

	private static final String SAVE_ROOT = "save";
	private static final String SAVE_FILE = ".save";

	@Autowired
	private ChatController chatController;
	
	@Autowired
	private PlayerController playerController;
	
	public SerializeManager() {

		if (logger.isInfoEnabled()) {
			logger.info("SerializeManager init");
		}

		// 建立存檔的目錄
		File rootDir = new File(SAVE_ROOT);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
	}

	private static String getSavePath(String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return null;
		}
		return SAVE_ROOT + File.separator + fileName + SAVE_FILE;
	}
	
	@RequestMapping("/all")
	public String saveAllData() {
		
		chatController.save();
		playerController.save();
		
		return "finished";
	}

	/**
	 * 存檔
	 * @param fileName
	 * @param serializable
	 */
	public static void save(String fileName, Serializable serializable) {
		String filePath = getSavePath(fileName);
		try {
			FileOutputStream fo = new FileOutputStream(filePath);
			ObjectOutputStream oo = new ObjectOutputStream(fo);
			oo.writeObject(serializable);
			oo.close();
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("save serialize fail, " + filePath);
			}
		}
	}

	/**
	 * 讀檔
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T read(String fileName) {
		T object = null;
		String filePath = getSavePath(fileName);
		try {
			FileInputStream fi = new FileInputStream(filePath);
			ObjectInputStream oi = new ObjectInputStream(fi);
			object = (T) oi.readObject();
			oi.close();
		} catch (Exception e) {
			if (logger.isErrorEnabled()) {
				logger.error("read serialize fail, " + filePath);
			}
		}
		return object;
	}

}
