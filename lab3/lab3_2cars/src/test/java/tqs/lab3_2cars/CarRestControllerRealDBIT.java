package tqs.lab3_2cars;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import tqs.lab3_2cars.data.Car;
import tqs.lab3_2cars.data.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CarRestControllerRealDBIT {

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

		Car[] cars = restTemplate.getForObject("/api/cars", Car[].class);
		assertThat(cars).extracting(Car::getMaker).containsExactly("Fiat", "Ford");
	}

	@Test
	public void givenCars_whenGetCarByCarId_thenReturnCar() {
		Car car1 = new Car("Fiat", "Punto");
		Car car2 = new Car("Ford", "Transit");
		carRepository.saveAndFlush(car1);
		carRepository.saveAndFlush(car2);

		Car car = restTemplate.getForObject("/api/cars/" + car1.getCarId(), Car.class);
		assertThat(car).extracting(Car::getMaker).isEqualTo("Fiat");
	}

	@Test
	public void givenCars_whenGetCarByCarId_thenStatus404() {
		Car car1 = new Car("Fiat", "Punto");
		Car car2 = new Car("Ford", "Transit");
		carRepository.saveAndFlush(car1);
		carRepository.saveAndFlush(car2);

		Car car = restTemplate.getForObject("/api/cars/" + 100, Car.class);
		assertThat(car).isNull();
	}
}
