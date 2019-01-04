package com.example.demo.datasource.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.datasource.model.KLogModel;

@Repository
public interface KLogRepo extends JpaRepository<KLogModel, Integer> {

}
