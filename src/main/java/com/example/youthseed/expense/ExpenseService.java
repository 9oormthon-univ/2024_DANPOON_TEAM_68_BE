package com.example.youthseed.expense;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {
    private  final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Create
    public Expense createExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    // Read
    public List<Expense> getExpensesByUserId(Long kakao_Id) {
        return expenseRepository.findByUserId(kakao_Id);
    }

    public Optional<Expense> getExpenseById(Long expenseId) {
        return expenseRepository.findById(expenseId);
    }

    // Update
    public Expense updateExpense(Long expenseId, Expense updatedExpense) {
        return expenseRepository.findById(expenseId)
                .map(expense -> {
                    expense.setCategory(updatedExpense.getCategory());
                    expense.setAmount(updatedExpense.getAmount());
                    expense.setDate(updatedExpense.getDate());
                    expense.setDescription(updatedExpense.getDescription());
                    return expenseRepository.save(expense);
                })
                .orElseThrow(() -> new RuntimeException("Expense not found with ID: " + expenseId));
    }

    // Delete
    public void deleteExpense(Long expenseId) {
        expenseRepository.deleteById(expenseId);
    }
}

