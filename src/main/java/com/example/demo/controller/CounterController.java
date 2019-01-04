package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.datasource.model.CounterModel;
import com.example.demo.datasource.repo.CounterRepo;
import com.example.demo.datasource.transaction.TXCounter;

@RestController
@RequestMapping("/counter")
public class CounterController {

	@Autowired
	private CounterRepo counterRepo;

	@Autowired
	private TXCounter txCounter;

	@RequestMapping("/addAndGet")
	public synchronized int addAndGet() {
		Optional<CounterModel> opModel = counterRepo.findById(1);
		CounterModel model = opModel.isPresent() ? opModel.get() : new CounterModel();
		int counter = model.getCounter() + 1;
		model.setCounter(counter);
		counterRepo.save(model);
		return counter;
	}

	@RequestMapping("/addAndGet2")
	public int addAndGet2() throws Exception {
		return txCounter.addAndGet();
	}
	
	@RequestMapping("/addAndGet3")
	public String addAndGet3() throws Exception {
		txCounter.add();
		return "ok";
	}
	
	@RequestMapping("/addAndGet4")
	public int addAndGet4() throws Exception {
		return txCounter.addAndGetByRepo();
	}
	
	@RequestMapping("/addAndGet5")
	public int addAndGet5(@RequestParam("id") int id) throws Exception {
		return txCounter.someImpl(id);
	}
	
}
