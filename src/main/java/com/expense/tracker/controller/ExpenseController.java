package com.expense.tracker.controller;

import com.expense.tracker.dto.ExpenseDto;
import com.expense.tracker.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@RequiredArgsConstructor
public class ExpenseController {

    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseDto.ExpenseResponse> createExpense(@Valid @RequestBody ExpenseDto.ExpenseRequest request) {
        ExpenseDto.ExpenseResponse response = expenseService.createExpense(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDto.ExpenseResponse>> getAllExpenses(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<ExpenseDto.ExpenseResponse> expenses = expenseService.getAllExpenses(startDate, endDate);
        return ResponseEntity.ok(expenses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto.ExpenseResponse> getExpenseById(@PathVariable Long id) {
        ExpenseDto.ExpenseResponse response = expenseService.getExpenseById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<ExpenseDto.ExpenseResponse>> getExpensesByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<ExpenseDto.ExpenseResponse> expenses = expenseService.getExpensesByDate(date);
        return ResponseEntity.ok(expenses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto.ExpenseResponse> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseDto.UpdateExpenseRequest request
    ) {
        ExpenseDto.ExpenseResponse response = expenseService.updateExpense(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.ok().build();
    }
}
