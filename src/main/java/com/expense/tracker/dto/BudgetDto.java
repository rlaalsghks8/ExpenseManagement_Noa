package com.expense.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class BudgetDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BudgetRequest {
        @NotBlank(message = "Month is required")
        private String month; // YYYY-MM

        @NotNull(message = "Total budget is required")
        @Positive(message = "Total budget must be positive")
        private Long totalBudget;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BudgetResponse {
        private Long id;
        private String month;
        private Long totalBudget;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateBudgetRequest {
        private String month;
        private Long totalBudget;
    }
}
