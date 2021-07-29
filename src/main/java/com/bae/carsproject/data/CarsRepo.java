package com.bae.carsproject.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarsRepo extends JpaRepository<Cars, Integer> {

	List<Cars> findByBrandIgnoreCase(String name);

}