package com.example.demo.datasource.transaction;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.demo.datasource.model.CounterModel;
import com.example.demo.datasource.model.KLogModel;
import com.example.demo.datasource.repo.CounterRepo;
import com.example.demo.datasource.repo.KLogRepo;

/**
 * 範例交易(Transaction)<br>
 * 1. query的重點是必須在select後面加上for update避免幻讀產生<br>
 * 2. TransactionCallback與TransactionCallbackWithoutResult差別在於是否需要回傳值<br>
 * 3. 在catch exception的地方呼叫status.setRollbackOnly()以達成事務回滾的效果
 */
@Service
public class TXCounter {

	@Autowired
	private DataSource datasource;

	@Autowired
	private PlatformTransactionManager txManager;

	/** 完成交易必要的執行器 */
	private TransactionTemplate transactionTemplate;
	
	
	
	// 方案一：運用JdbcTemplate執行query
	private JdbcTemplate jdbcTemplate;

	
	
	// 方案二：運用repository執行query
	@Autowired
	private CounterRepo counterRepo;
	
	@Autowired
	private KLogRepo kLogRepo;
	

	/**
	 * 利用postContruct避免datasource尚未實例化完成
	 */
	@PostConstruct
	public void initial() {
		jdbcTemplate = new JdbcTemplate(datasource);
		transactionTemplate = new TransactionTemplate(txManager);
		transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
	}

	/**
	 * 將資料庫數值+1並寫一行紀錄
	 */
	public int addAndGet() {
		return transactionTemplate.execute(new TransactionCallback<Integer>() {
			@Override
			public Integer doInTransaction(TransactionStatus status) {
				int count = 0;
				try {
					String selectQuery = "select c.counter from counter c where c.id = 1 for update;";
					count = jdbcTemplate.queryForObject(selectQuery, Integer.class) + 1;

					String updateQuery = "update counter c set c.counter = " + count + " where c.id = 1;";
					jdbcTemplate.update(updateQuery);

					// 嘗試insert別的table
					String insertQuery = "insert into kai.k_log (`kTopic`,`kMessage`) values('" + count
							+ "','success');";
					jdbcTemplate.execute(insertQuery);

					randomException();

				} catch (Exception e) {
					status.setRollbackOnly();
					System.err.println(e.toString());
//					e.printStackTrace();
					count = -1;
				}
				return count;
			}
		});
	}

	public void add() {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				try {
					String query = "update counter c set c.counter = c.counter + 1 where c.id = 1;";
					jdbcTemplate.update(query);

					randomException();

				} catch (Exception e) {
					status.setRollbackOnly();
					System.err.println(e.toString());
				}

			}
		});
	}

	/**
	 * 直接運用repository將資料庫數值+1並寫一行紀錄
	 */
	public int addAndGetByRepo() {
		return transactionTemplate.execute(new TransactionCallback<Integer>() {
			@Override
			public Integer doInTransaction(TransactionStatus status) {
				int count = 0;
				try {
					
//					Optional<CounterModel> opModel = counterRepo.findById(1);
					Optional<CounterModel> opModel = counterRepo.findByIdForUpdate(1);
					CounterModel model = opModel.isPresent() ? opModel.get() : new CounterModel();
					count = model.getCounter() + 1;
					model.setCounter(count);
					counterRepo.save(model);
					
					// 運用repo寫一行紀錄
					KLogModel logModel = new KLogModel();
					logModel.setCounter(count);
					logModel.setkMessage("success");
					logModel.setEventDate(new Date());
					kLogRepo.save(logModel);
					
					randomException();

				} catch (Exception e) {
					status.setRollbackOnly();
					System.err.println(e.toString());
//					e.printStackTrace();
					count = -1;
				}
				return count;
			}
		});
	}
	
	
	/**
	 * 隨機製造exception
	 */
	private static void randomException() throws Exception {
		if (new Random().nextInt(100) % 2 == 0) {
			throw new Exception("randomException");
		}
	}

}
