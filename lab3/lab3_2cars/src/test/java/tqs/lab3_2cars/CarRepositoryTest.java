package tqs.lab3_2cars;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.lab3_2cars.data.Car;
import tqs.lab3_2cars.data.CarRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private CarRepository carRepository;

	@Test
	public void whenFindById_thenReturnCar() {
		Car car = new Car("Ford", "Transit");
		entityManager.persistAndFlush(car);

		Car found = carRepository.findByCarId(car.getCarId());
		assertThat(found).isNotNull();
		assertThat(found.getMaker()).isEqualTo(car.getMaker());
	}

	@Test
	public void whenInvalidId_thenReturnNull() {
		Car fromDb = carRepository.findByCarId(-11L);
		assertThat(fromDb).isNull();
	}

	@Test
	public void givenSetOfCars_whenFindAll_thenReturnAllCars() {
		Car car1 = new Car("Ford", "Transit");
		Car car2 = new Car("Volkswagen", "Passat");
		Car car3 = new Car("Renault", "Clio");

		entityManager.persist(car1);
		entityManager.persist(car2);
		entityManager.persist(car3);
		entityManager.flush();

		Iterable<Car> allCars = carRepository.findAll();

		assertThat(allCars).hasSize(3).contains(car1, car2, car3).extracting(Car::getMaker).containsOnly("Ford", "Volkswagen", "Renault");
	}
}
