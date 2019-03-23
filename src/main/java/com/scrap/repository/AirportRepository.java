package com.scrap.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.scrap.entity.Airport;

@Repository
public interface AirportRepository extends PagingAndSortingRepository<Airport, String> {

}
