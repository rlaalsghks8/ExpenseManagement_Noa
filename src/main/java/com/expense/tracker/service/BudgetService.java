package com.expense.tracker.service;

import com.expense.tracker.dto.BudgetDto;
import com.expense.tracker.entity.Budget;
import com.expense.tracker.entity.User;
import com.expense.tracker.repository.BudgetRepository;
import com.expense.tracker.repository.UserRepository;
import com.expense.tracker.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public BudgetDto.BudgetResponse createBudget(BudgetDto.BudgetRequest request) {
        User user = getCurrentUser();

        if (budgetRepository.existsByUserIdAndMonth(user.getId(), request.getMonth())) {
            throw new RuntimeException("Budget for this month already exists");
        }

        Budget budget = Budget.builder()
                .user(user)
                .month(request.getMonth())
                .totalBudget(request.getTotalBudget())
                .build();

        budget = budgetRepository.save(budget);

        return mapToResponse(budget);
    }

    public BudgetDto.BudgetResponse getBudgetByMonth(String month) {
        User user = getCurrentUser();
        Budget budget = budgetRepository.findByUserIdAndMonth(user.getId(), month)
                .orElseThrow(() -> new RuntimeException("Budget not found for this month"));

        return mapToResponse(budget);
    }

    @Transactional
    public BudgetDto.BudgetResponse updateBudget(Long id, BudgetDto.UpdateBudgetRequest request) {
        User user = getCurrentUser();
        Budget budget = budgetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        if (!budget.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized");
        }

        if (request.getMonth() != null) {
            budget.setMonth(request.getMonth());
        }
        if (request.getTotalBudget() != null) {
            budget.setTotalBudget(request.getTotalBudget());
        }

        budget = budgetRepository.save(budget);

        return mapToResponse(budget);
    }

    private BudgetDto.BudgetResponse mapToResponse(Budget budget) {
        return new BudgetDto.BudgetResponse(
                budget.getId(),
                budget.getMonth(),
                budget.getTotalBudget()
        );
    }
}
