package com.foodmanagement.backend.service;

import com.foodmanagement.backend.model.Food;
import com.foodmanagement.backend.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    // Get all items
    public List<Food> getAllFood() {
        return foodRepository.findAll();
    }

    // Add new item
    public Food addFood(Food food) {
        return foodRepository.save(food);
    }

    // Update item
    public Food updateFood(Long id, Food foodDetails) {
        Food food = foodRepository.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        food.setItemName(foodDetails.getItemName());
        food.setQuantity(foodDetails.getQuantity());
        return foodRepository.save(food);
    }

    // Delete item
    public void deleteFood(Long id) {
        foodRepository.deleteById(id);
    }
}