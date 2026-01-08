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
    public ResponseEntity<String> addFood(@RequestBody Food food) {
        String response = foodService.addFood(food);

        if (response.equals("Item already exists")) {
            // Return 409 Conflict status if it exists
            return ResponseEntity.status(409).body(response);
        }

        // Return 200 OK or 201 Created if successful
        return ResponseEntity.ok(response);
    }

    // PUT /api/food/{itemName}?action=increase
    @PutMapping("/{itemName}")
    public ResponseEntity<Food> updateFoodQuantity(
            @PathVariable String itemName,
            @RequestParam String action) {

        try {
            Food updatedFood = foodService.updateFoodQuantity(itemName, action);
            return ResponseEntity.ok(updatedFood);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{itemName}")
    public ResponseEntity<String> deleteFood(@PathVariable String itemName) {
        try {
            foodService.deleteFood(itemName);
            return ResponseEntity.ok("Item deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}