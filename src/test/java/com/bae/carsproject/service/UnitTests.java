package com.bae.carsproject.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import com.bae.carsproject.data.Cars;
import com.bae.carsproject.data.CarsRepo;

@SpringBootTest
@ActiveProfiles("test")
public class UnitTests {

	@Autowired // injects the actual service from the context
	private CarsServiceDB service;

	@MockBean // make a mock CarsRepo
	private CarsRepo repo;

	@Test
	void testUpdate() {

		// GIVEN
		int id = 1;

		Cars testCar = new Cars(id, "Audi", "RS6", 2021); // returned by FindById
		Cars testNewCar = new Cars(id, "Ford", "Mustang", 1991); // new car data

		// WHEN
		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(testCar));
		Mockito.when(this.repo.save(new Cars(id, "Ford", "Mustang", 1991))).thenReturn(testNewCar);

		Cars actual = this.service.replaceCar(testNewCar, id);
		// THEN
		assertThat(actual).isEqualTo(testNewCar);

		Mockito.verify(this.repo, Mockito.times(1)).findById(id);
		Mockito.verify(this.repo, Mockito.times(1)).save(new Cars(id, "Ford", "Mustang", 1991));
	}

	@Test
	void testDelete() {
		int id = 1;

		assertThat(this.service.deleteCar(id)).isEqualTo("Deleted: " + id);
	}

	@Test
	void testCreate() {

		int id = 1;

		Cars testCar = new Cars(id, "Audi", "RS6", 2021);
		Cars car1 = new Cars(id, "Audi", "RS6", 2021);

		Mockito.when(this.repo.save(car1)).thenReturn(testCar);

		Cars actual = this.service.createCar(car1);
		assertThat(actual).isEqualTo(car1);

	}

	@Test
	void testGetById() {

		int id = 1;
		Cars testCar = new Cars(id, "Audi", "RS6", 2021);

		Mockito.when(this.repo.findById(id)).thenReturn(Optional.of(testCar));

		assertThat(this.service.getCar(id)).isEqualTo(testCar);

	}

	@Test
	void testGetAll() {

		List<Cars> getAllCars = new ArrayList<>();
		getAllCars.add(new Cars("BMW", "i8", 2017));

		Mockito.when(this.repo.findAll()).thenReturn(getAllCars);

		assertThat(this.service.getAllCars()).isEqualTo(getAllCars);

	}

}
