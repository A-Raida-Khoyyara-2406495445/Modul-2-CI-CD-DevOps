package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
  CarRepository carRepository;

  @BeforeEach
  void setUp() {
    carRepository = new CarRepository();
  }

  @Test
  void testCreateAndFind() {
    Car car = new Car();
    car.setCarId("1");
    car.setCarName("Honda Civic");
    carRepository.create(car);

    Car savedCar = carRepository.findById("1");
    assertNotNull(savedCar);
    assertEquals(car.getCarId(), savedCar.getCarId());
  }

  @Test
  void testUpdate() {
    Car car = new Car();
    car.setCarId("1");
    car.setCarName("Lama");
    carRepository.create(car);

    Car newCarData = new Car();
    newCarData.setCarId("1");
    newCarData.setCarName("Baru");

    carRepository.update("1", newCarData);
    Car updatedCar = carRepository.findById("1");
    assertEquals("Baru", updatedCar.getCarName());
  }

  @Test
  void testDelete() {
    Car car = new Car();
    car.setCarId("1");
    carRepository.create(car);

    carRepository.delete("1");
    Car deletedCar = carRepository.findById("1");
    assertNull(deletedCar);
  }
}