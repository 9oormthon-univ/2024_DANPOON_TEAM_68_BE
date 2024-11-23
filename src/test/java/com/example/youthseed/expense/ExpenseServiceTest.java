package com.example.youthseed.expense;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.List;

class ExpenseServiceTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseService expenseService;

    public ExpenseServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExpense() {
        Expense expense = new Expense();
        expense.setCategory("Food");
        expense.setAmount(10000);
        expense.setDate(LocalDate.of(2024, 11, 23));
        expense.setDescription("Lunch");

        when(expenseRepository.save(expense)).thenReturn(expense);

        Expense createdExpense = expenseService.createExpense(expense);
        assertNotNull(createdExpense);
        assertEquals("Food", createdExpense.getCategory());
    }

    @Test
    void testGetExpensesByUserId() {
        Expense expense1 = new Expense();
        expense1.setCategory("Food");
        expense1.setAmount(10000);

        Expense expense2 = new Expense();
        expense2.setCategory("Transport");
        expense2.setAmount(5000);

        when(expenseRepository.findByUserId(1L)).thenReturn(Arrays.asList(expense1, expense2));

        List<Expense> expenses = expenseService.getExpensesByUserId(1L);
        assertEquals(2, expenses.size());
    }

    @Test
    void testUpdateExpense() {
        Expense existingExpense = new Expense();
        existingExpense.setCategory("Food");
        existingExpense.setAmount(10000);
        existingExpense.setDate(LocalDate.of(2024, 11, 23));
        existingExpense.setDescription("Lunch");

        Expense updatedExpense = new Expense();
        updatedExpense.setCategory("Dinner");
        updatedExpense.setAmount(15000);

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(existingExpense));
        when(expenseRepository.save(any(Expense.class))).thenReturn(updatedExpense);

        Expense result = expenseService.updateExpense(1L, updatedExpense);
        assertEquals("Dinner", result.getCategory());
        assertEquals(15000, result.getAmount());
    }

    @Test
    void testDeleteExpense() {
        doNothing().when(expenseRepository).deleteById(1L);

        assertDoesNotThrow(() -> expenseService.deleteExpense(1L));
        verify(expenseRepository, times(1)).deleteById(1L);
    }
}