package com.expense.tracker.repository;

import com.expense.tracker.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    
    Optional<Budget> findByUserIdAndMonth(Long userId, String month);
    
    boolean existsByUserIdAndMonth(Long userId, String month);
}
