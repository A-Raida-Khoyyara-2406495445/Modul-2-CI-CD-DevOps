package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.CarService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarController.class)
class CarControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private CarService carService;

  @Test
  void testCreateCarPage() throws Exception {
    mockMvc.perform(get("/car/createCar"))
        .andExpect(status().isOk())
        .andExpect(view().name("createCar"))
        .andExpect(model().attributeExists("car"));
  }

  @Test
  void testCreateCarPost() throws Exception {
    mockMvc.perform(post("/car/createCar")
            .param("carName", "Toyota")
            .param("carColor", "Blue")
            .param("carQuantity", "10"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("listCar"));

    verify(carService, times(1)).create(any(Car.class));
  }

  @Test
  void testCarListPage() throws Exception {
    List<Car> allCars = Arrays.asList(new Car());
    when(carService.findAll()).thenReturn(allCars);

    mockMvc.perform(get("/car/listCar"))
        .andExpect(status().isOk())
        .andExpect(view().name("carList"))
        .andExpect(model().attribute("cars", allCars));
  }

  @Test
  void testEditCarPage() throws Exception {
    Car car = new Car();
    car.setCarId("123");
    when(carService.findById("123")).thenReturn(car);

    mockMvc.perform(get("/car/editCar/123"))
        .andExpect(status().isOk())
        .andExpect(view().name("editCar"))
        .andExpect(model().attribute("car", car));
  }

  @Test
  void testEditCarPost() throws Exception {
    mockMvc.perform(post("/car/editCar")
            .param("carId", "123")
            .param("carName", "Updated Name"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/car/listCar")); // Pastikan ini sesuai dengan return di Controller-mu

    verify(carService, times(1)).update(eq("123"), any(Car.class));
  }

  @Test
  void testDeleteCar() throws Exception {
    mockMvc.perform(post("/car/deleteCar")
            .param("carId", "123"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("listCar"));

    verify(carService, times(1)).deleteCarById("123");
  }
}