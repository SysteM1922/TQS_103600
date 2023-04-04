package tqs.lab3_2cars.service;

import org.springframework.stereotype.Service;

import tqs.lab3_2cars.data.Car;
import tqs.lab3_2cars.data.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarManagerService {

    final
    CarRepository carRepository;

    public CarManagerService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car save(Car oneCar) {
        return carRepository.save(oneCar);
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId) {
        return Optional.of(carRepository.findByCarId(carId) );
    }
}
