package com.expense.tracker.service;

import com.expense.tracker.dto.ExpenseDto;
import com.expense.tracker.entity.Expense;
import com.expense.tracker.entity.User;
import com.expense.tracker.repository.ExpenseRepository;
import com.expense.tracker.repository.UserRepository;
import com.expense.tracker.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public ExpenseDto.ExpenseResponse createExpense(ExpenseDto.ExpenseRequest request) {
        User user = getCurrentUser();

        Expense expense = Expense.builder()
                .user(user)
                .date(request.getDate())
                .category(request.getCategory())
                .amount(request.getAmount())
                .description(request.getDescription())
                .build();

        expense = expenseRepository.save(expense);

        return mapToResponse(expense);
    }

    public List<ExpenseDto.ExpenseResponse> getAllExpenses(LocalDate startDate, LocalDate endDate) {
        User user = getCurrentUser();

        List<Expense> expenses;
        if (startDate != null && endDate != null) {
            expenses = expenseRepository.findByUserIdAndDateBetweenOrderByDateDesc(user.getId(), startDate, endDate);
        } else {
            expenses = expenseRepository.findByUserIdOrderByDateDesc(user.getId());
        }

        return expenses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ExpenseDto.ExpenseResponse getExpenseById(Long id) {
        User user = getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        return mapToResponse(expense);
    }

    public List<ExpenseDto.ExpenseResponse> getExpensesByDate(LocalDate date) {
        User user = getCurrentUser();
        List<Expense> expenses = expenseRepository.findByUserIdAndDate(user.getId(), date);

        return expenses.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ExpenseDto.ExpenseResponse updateExpense(Long id, ExpenseDto.UpdateExpenseRequest request) {
        User user = getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        if (request.getDate() != null) {
            expense.setDate(request.getDate());
        }
        if (request.getCategory() != null) {
            expense.setCategory(request.getCategory());
        }
        if (request.getAmount() != null) {
            expense.setAmount(request.getAmount());
        }
        if (request.getDescription() != null) {
            expense.setDescription(request.getDescription());
        }

        expense = expenseRepository.save(expense);

        return mapToResponse(expense);
    }

    @Transactional
    public void deleteExpense(Long id) {
        User user = getCurrentUser();
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));

        if (!expense.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        expenseRepository.delete(expense);
    }

    private ExpenseDto.ExpenseResponse mapToResponse(Expense expense) {
        return new ExpenseDto.ExpenseResponse(
                expense.getId(),
                expense.getDate(),
                expense.getCategory(),
                expense.getAmount(),
                expense.getDescription()
        );
    }
}
