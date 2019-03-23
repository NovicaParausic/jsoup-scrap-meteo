package com.scrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scrap.entity.Limit;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {

}
