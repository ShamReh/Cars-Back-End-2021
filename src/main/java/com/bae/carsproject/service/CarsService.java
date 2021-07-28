package com.bae.carsproject.service;

import java.util.List;

import com.bae.carsproject.data.Cars;

public interface CarsService {

	public Cars createCar(Cars car1);

	List<Cars> getAllCars();

	public Cars getCar(int id);

	public String deleteCar(int id);

	public Cars replaceCar(Cars newcar, int id);

	List<Cars> getByBrand(String name);

}