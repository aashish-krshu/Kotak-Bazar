package com.foodmanagement.backend.controller;

import com.foodmanagement.backend.model.Food;
import com.foodmanagement.backend.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@CrossOrigin(origins = "*") // IMPORTANT: Allows Next.js to connect
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping
    public List<Food> getAllFood() {
        return foodService.getAllFood();
    }

    @PostMapping
    public Food addFood(@RequestBody Food food) {
        return foodService.addFood(food);
    }

    @PutMapping("/{id}")
    public Food updateFood(@PathVariable Long id, @RequestBody Food food) {
        return foodService.updateFood(id, food);
    }

    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable Long id) {
        foodService.deleteFood(id);
    }
}