package tqs.lab3_2cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import tqs.lab3_2cars.data.Car;
import tqs.lab3_2cars.data.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class CarRestControllerTemplateIT {
	
	@LocalServerPort
	int randomServerPort;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CarRepository carRepository;

	@AfterEach
	public void resetDb() {
		carRepository.deleteAll();
	}

	@Test
	public void whenValidInput_thenCreateCar() {
		Car car = new Car("Fiat", "Punto");
		restTemplate.postForEntity("/api/cars", car, Car.class);
		assertThat(carRepository.findAll()).extracting(Car::getMaker).containsOnly("Fiat");
	}

	@Test
	public void givenCars_whenGetCars_thenStatus200() {
		Car car1 = new Car("Fiat", "Punto");
		Car car2 = new Car("Ford", "Transit");
		carRepository.saveAndFlush(car1);
		carRepository.saveAndFlush(car2);

		ResponseEntity<List<Car>> response = restTemplate.exchange("/api/cars", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Car>>() {
				});
		assertThat(response.getStatusCode().value()).isEqualTo(200);
		assertThat(response.getBody()).extracting(Car::getMaker).containsExactly("Fiat", "Ford");
	}

	@Test
	public void givenCars_whenGetCarByCarId_thenReturnCar() {
		Car car1 = new Car("Fiat", "Punto");
		Car car2 = new Car("Ford", "Transit");
		carRepository.saveAndFlush(car1);
		carRepository.saveAndFlush(car2);

		ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/1", Car.class);
		assertThat(response.getBody().getMaker()).isEqualTo("Fiat");
	}

	@Test
	public void givenCars_whenGetCarByCarId_thenStatus404() {
		Car car1 = new Car("Fiat", "Punto");
		Car car2 = new Car("Ford", "Transit");
		carRepository.saveAndFlush(car1);
		carRepository.saveAndFlush(car2);

		ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/3", Car.class);
		assertThat(response.getStatusCode().value()).isEqualTo(404);
	}

}
