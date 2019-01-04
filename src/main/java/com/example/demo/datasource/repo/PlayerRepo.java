package com.example.demo.datasource.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.datasource.model.PlayerModel;

@Repository
public interface PlayerRepo extends JpaRepository<PlayerModel, String> {

	public Optional<PlayerModel> findByName(String name);
	
}
