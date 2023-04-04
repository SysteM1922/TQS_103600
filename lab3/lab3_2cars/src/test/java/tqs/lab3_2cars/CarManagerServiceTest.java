package tqs.lab3_2cars;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.lab3_2cars.data.Car;
import tqs.lab3_2cars.data.CarRepository;
import tqs.lab3_2cars.service.CarManagerService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CarManagerServiceTest {
	
	@Mock(lenient = true)
	private CarRepository carRepository;

	@InjectMocks
	private CarManagerService carManagerService;

	@BeforeEach
	public void setUp() {
		Car car1 = new Car("Ford", "Transit");
		car1.setCarId(1L);
		Car car2 = new Car("Volswagen", "Passat");
		Car car3 = new Car("Renault", "Clio");

		List<Car> allCars = Arrays.asList(car1, car2, car3);

		Mockito.when(carRepository.findAll()).thenReturn(allCars);
		Mockito.when(carRepository.findByCarId(1L)).thenReturn(car1);
		Mockito.when(carRepository.findByCarId(-99L)).thenReturn(null);
	}

	@Test
	public void whenValid_carShouldBeFound() {
		Car car = carManagerService.getCarDetails(1L).get();
		assertThat(car.getMaker()).isEqualTo("Ford");
		Mockito.verify(carRepository, Mockito.times(1)).findByCarId(1L);
	}

	@Test
	public void whenInvalid_carShouldNotBeFound() {
		assertThrows(NullPointerException.class, () -> {
			carManagerService.getCarDetails(-99L).get();
		});
		Mockito.verify(carRepository, Mockito.times(1)).findByCarId(-99L);
	}

	@Test
	public void exist3Cars_whenGetAll_returnAllOf3() {
		List<Car> allCars = carManagerService.getAllCars();
		assertThat(allCars).hasSize(3).extracting(Car::getMaker).containsOnly("Ford", "Volswagen", "Renault");
		Mockito.verify(carRepository, Mockito.times(1)).findAll();
	}
}
