package com.bae.carsproject.service;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bae.carsproject.data.Cars;
import com.bae.carsproject.data.CarsRepo;

@Service
@Primary

public class CarsServiceDB implements CarsService {

	private CarsRepo repo;

	public CarsServiceDB(CarsRepo repo) {
		super();
		this.repo = repo;
	}

	@Override
	public Cars createCar(Cars car1) {

		System.out.println("New Car: " + car1);
		return this.repo.save(car1);
	}

	@Override
	public List<Cars> getAllCars() {
		return this.repo.findAll();
	}

	@Override
	public Cars getCar(int id) {
		return this.repo.findById(id).get();
	}

	@Override
	public String deleteCar(int id) {
		this.repo.deleteById(id);

		if (this.repo.existsById(id)) {
			return "Not deleted: " + id;
		} else {
			return "Deleted: " + id;
		}
	}

	@Override
	public Cars replaceCar(Cars newcar, int id) {

		// Pull out existing record
		Cars found = this.repo.findById(id).get();

		System.out.println("FOUND: " + found);

		// Modify Record
		found.setAge(newcar.getAge());
		found.setBrand(newcar.getBrand());
		found.setModel(newcar.getModel());

		System.out.println("FOUND AFTER UPDATE: " + found);

		// Save it back to overwrite it
		Cars updated = this.repo.save(found);

		System.out.println("UPDATED: " + updated);

		return updated;
	}

	@Override
	public List<Cars> getByBrand(String name) {
		return this.repo.findByBrandIgnoreCase(name);
	}

}
