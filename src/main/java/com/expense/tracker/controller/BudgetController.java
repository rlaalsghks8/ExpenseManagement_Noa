package com.expense.tracker.controller;

import com.expense.tracker.dto.BudgetDto;
import com.expense.tracker.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/budgets")
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping
    public ResponseEntity<BudgetDto.BudgetResponse> createBudget(@Valid @RequestBody BudgetDto.BudgetRequest request) {
        BudgetDto.BudgetResponse response = budgetService.createBudget(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{month}")
    public ResponseEntity<BudgetDto.BudgetResponse> getBudgetByMonth(@PathVariable String month) {
        BudgetDto.BudgetResponse response = budgetService.getBudgetByMonth(month);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BudgetDto.BudgetResponse> updateBudget(
            @PathVariable Long id,
            @Valid @RequestBody BudgetDto.UpdateBudgetRequest request
    ) {
        BudgetDto.BudgetResponse response = budgetService.updateBudget(id, request);
        return ResponseEntity.ok(response);
    }
}
