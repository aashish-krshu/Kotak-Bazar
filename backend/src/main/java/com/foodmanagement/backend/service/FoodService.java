package com.foodmanagement.backend.service;

import com.foodmanagement.backend.model.Food;
import com.foodmanagement.backend.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public String addFood(Food food) {
        // 1. Check if the item exists
        if (foodRepository.existsByItemName(food.getItemName())) {
            return "Item already exists";
        }

        // 2. Item does not exist: Set quantity to 0 as requested
        food.setQuantity(0);

        // 3. Save to database
        foodRepository.save(food);

        return "Item added successfully";
    }

    public Food updateFoodQuantity(String itemName, String action) {
        // 1. Find the item
        Food food = foodRepository.findByItemName(itemName)
                .orElseThrow(() -> new RuntimeException("Item not found: " + itemName));

        // 2. specific logic for buttons
        if ("increase".equalsIgnoreCase(action)) {
            food.setQuantity(food.getQuantity() + 1);
        }
        else if ("decrease".equalsIgnoreCase(action)) {
            int newQuantity = food.getQuantity() - 1;
            // Logic: If not positive (less than 0), set to zero
            if (newQuantity < 0) {
                newQuantity = 0;
            }
            food.setQuantity(newQuantity);
        }

        // 3. Save updated entity
        return foodRepository.save(food);
    }

    // Delete item
    @Transactional // Required for custom delete operations
    public void deleteFood(String itemName) {
        // Ideally, check if it exists first to avoid silent failures
        if (!foodRepository.existsByItemName(itemName)) {
            throw new RuntimeException("Item not found");
        }

        foodRepository.deleteByItemName(itemName);
    }
}