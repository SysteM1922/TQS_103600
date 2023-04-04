package tqs.lab3_2cars.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import tqs.lab3_2cars.data.Car;
import tqs.lab3_2cars.service.CarManagerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarManagerService carManagerService;

    public CarController(CarManagerService injectedCarManagerService) {
        this.carManagerService = injectedCarManagerService;
    }


    @PostMapping("/cars")
    public ResponseEntity<Car> createCar(@RequestBody Car newCar) {
        Car car = null;
        try {
            car = carManagerService.save(newCar);
            return new ResponseEntity<Car>(car, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Car>(car, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/cars",  produces = "application/json")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId) {
        Car car = null;
        try{
            car = carManagerService.getCarDetails(carId).get();
            return new ResponseEntity<Car>(car, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Car>(car, HttpStatus.NOT_FOUND);
        }

    }

}
