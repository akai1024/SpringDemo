package com.example.demo.datasource.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.datasource.model.CounterModel;

@Repository
public interface CounterRepo extends JpaRepository<CounterModel, Integer> {

//	@Query("update CounterModel set counter = counter + 1 where id = :id")
//	public int addAndGet(int id);

	@Query(value = "select c.* from counter c where c.id = :id for update;", nativeQuery = true)
	public Optional<CounterModel> findByIdForUpdate(int id);

}
