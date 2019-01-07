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

		/**
		 * 事務隔離級別
		 * TransactionDefinition.ISOLATION_DEFAULT：這是默認值，表示使用底層數據庫的默認隔離級別。對大部分數據庫而言，通常這值就是TransactionDefinition.ISOLATION_READ_COMMITTED。
		 * TransactionDefinition.ISOLATION_READ_UNCOMMITTED：該隔離級別表示一個事務可以讀取另一個事務修改但還沒有提交的數據。該級別不能防止臟讀和不可重複讀，因此很少使用該隔離級別。
		 * TransactionDefinition.ISOLATION_READ_COMMITTED：該隔離級別表示一個事務只能讀取另一個事務已經提交的數據。該級別可以防止臟讀，這也是大多數情況下的推薦值。
		 * TransactionDefinition.ISOLATION_REPEATABLE_READ：該隔離級別表示一個事務在整個過程中可以多次重複執行某個查詢，並且每次返回的記錄都相同。即使在多次查詢之間有新增的數據滿足該查詢，這些新增的記錄也會被忽略。該級別可以防止臟讀和不可重複讀。
		 * TransactionDefinition.ISOLATION_SERIALIZABLE：所有的事務依次逐個執行，這樣事務之間就完全不可能產生干擾，也就是說，該級別可以防止臟讀、不可重複讀以及幻讀。但是這將嚴重影響程序的性能。通常情況下也不會用到該級別。
		 */
		transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);

		/**
		 * 事務傳播行為
		 * TransactionDefinition.PROPAGATION_REQUIRED：如果當前存在事務，則加入該事務；如果當前沒有事務，則創建一個新的事務。
		 * TransactionDefinition.PROPAGATION_REQUIRES_NEW：創建一個新的事務，如果當前存在事務，則把當前事務掛起。
		 * TransactionDefinition.PROPAGATION_SUPPORTS：如果當前存在事務，則加入該事務；如果當前沒有事務，則以非事務的方式繼續運行。
		 * TransactionDefinition.PROPAGATION_NOT_SUPPORTED：以非事務方式運行，如果當前存在事務，則把當前事務掛起。
		 * TransactionDefinition.PROPAGATION_NEVER：以非事務方式運行，如果當前存在事務，則拋出異常。
		 * TransactionDefinition.PROPAGATION_MANDATORY：如果當前存在事務，則加入該事務；如果當前沒有事務，則拋出異常。
		 * TransactionDefinition.PROPAGATION_NESTED：如果當前存在事務，則創建一個事務作為當前事務的嵌套事務來運行；如果當前沒有事務，則該取值等價於TransactionDefinition.PROPAGATION_REQUIRED。
		 */
		transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

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
	 * 以某個class實作TransactionCallback
	 */
	public int someImpl(int id) {
		return transactionTemplate.execute(new TXSomeImpl(counterRepo, id));
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
