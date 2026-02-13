package com.expense.tracker.repository;

import com.expense.tracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    List<Expense> findByUserIdOrderByDateDesc(Long userId);
    
    List<Expense> findByUserIdAndDateBetweenOrderByDateDesc(
        Long userId, 
        LocalDate startDate, 
        LocalDate endDate
    );
    
    List<Expense> findByUserIdAndDate(Long userId, LocalDate date);
    
    @Query("SELECT e FROM Expense e WHERE e.user.id = :userId AND FUNCTION('YEAR', e.date) = :year AND FUNCTION('MONTH', e.date) = :month ORDER BY e.date DESC")
    List<Expense> findByUserIdAndYearAndMonth(
        @Param("userId") Long userId,
        @Param("year") int year,
        @Param("month") int month
    );
}
