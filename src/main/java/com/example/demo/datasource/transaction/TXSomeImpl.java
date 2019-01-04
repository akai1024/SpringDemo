package com.example.demo.datasource.transaction;

import java.util.Optional;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.example.demo.datasource.model.CounterModel;
import com.example.demo.datasource.repo.CounterRepo;

public class TXSomeImpl implements TransactionCallback<Integer> {

	private CounterRepo counterRepo;

	public TXSomeImpl(CounterRepo counterRepo) {
		this.counterRepo = counterRepo;
	}

	@Override
	public Integer doInTransaction(TransactionStatus status) {
		int count = 0;
		try {
			
			Optional<CounterModel> opModel = counterRepo.findByIdForUpdate(1);
			CounterModel model = opModel.isPresent() ? opModel.get() : new CounterModel();
			count = model.getCounter() + 1;
			model.setCounter(count);
			counterRepo.save(model);

		} catch (Exception e) {
			status.setRollbackOnly();
			System.err.println(e.toString());
//			e.printStackTrace();
			count = -1;
		}
		return count;
	}

}
