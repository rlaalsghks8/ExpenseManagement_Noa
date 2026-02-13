package com.expense.tracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class ExpenseDto {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpenseRequest {
        @NotNull(message = "Date is required")
        private LocalDate date;

        @NotBlank(message = "Category is required")
        private String category;

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        private Long amount;

        private String description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExpenseResponse {
        private Long id;
        private LocalDate date;
        private String category;
        private Long amount;
        private String description;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateExpenseRequest {
        private LocalDate date;
        private String category;
        private Long amount;
        private String description;
    }
}
