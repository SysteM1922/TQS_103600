package tqs.lab3_2cars;

import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.CoreMatchers.is;

import tqs.lab3_2cars.service.CarManagerService;
import tqs.lab3_2cars.controller.CarController;
import tqs.lab3_2cars.data.Car;

@WebMvcTest(CarController.class)
public class CarRestControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CarManagerService carService;

	@BeforeEach
	public void setUp() throws Exception {
	}

	@Test
	public void whenPostCar_thenCreateCar() throws Exception {

		Car car = new Car("Ford", "Transit");

		Mockito.when(carService.save(Mockito.any(Car.class))).thenReturn(car);

		mockMvc.perform(
				post("/api/cars").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.maker", is("Ford")))
				.andExpect(jsonPath("$.model", is("Transit")));

		Mockito.verify(carService, Mockito.times(1)).save(Mockito.any(Car.class));
	}
	
	@Test
	public void givenManyCars_whenGetAllCars_thenReturnJsonArray() throws Exception {

		Car car1 = new Car("Ford", "Transit");
		Car car2 = new Car("VolksWagen", "Passat");

		Mockito.when(carService.getAllCars()).thenReturn(Arrays.asList(car1, car2));

		mockMvc.perform(
				get("/api/cars").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(jsonPath("$[0].maker", is("Ford")))
				.andExpect(jsonPath("$[0].model", is("Transit")))
				.andExpect(jsonPath("$[1].maker", is("VolksWagen")))
				.andExpect(jsonPath("$[1].model", is("Passat")));

		Mockito.verify(carService, Mockito.times(1)).getAllCars();
	}
	
	@Test
	public void givenCar_whenGetCarByCarId_shouldReturnCar() throws Exception {

		Car car1 = new Car("Ford", "Transit");

		Mockito.when(carService.getCarDetails(1L)).thenReturn(Optional.of(car1));

		mockMvc.perform(
				get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.maker", is("Ford")))
				.andExpect(jsonPath("$.model", is("Transit")));

		Mockito.verify(carService, Mockito.times(1)).getCarDetails(Mockito.anyLong());
	}

	@Test
	public void givenNoCar_whenGetCarByCarId_shouldReturnNotFound() throws Exception {

		Mockito.when(carService.getCarDetails(1L)).thenReturn(Optional.empty());

		mockMvc.perform(
				get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());

		Mockito.verify(carService, Mockito.times(1)).getCarDetails(Mockito.anyLong());
	}
}
