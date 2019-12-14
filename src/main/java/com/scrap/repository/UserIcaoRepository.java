package com.scrap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scrap.entity.UserIcao;

@Repository
public interface UserIcaoRepository extends JpaRepository<UserIcao, Long>{
	
	List<UserIcao> findByIcao(String icao);
	
	void deleteByUser(String sessionId);
	
	void deleteByUserAndIcao(String sessionId, String icao);
}
