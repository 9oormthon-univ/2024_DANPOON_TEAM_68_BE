package com.example.youthseed.expense;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


class ExpenseControllerTest {
    private MockMvc mockMvc;
    private ExpenseService expenseService;
    private ExpenseController expenseController;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Mock Service 계층 생성
        expenseService = Mockito.mock(ExpenseService.class);

        // Controller 생성 및 Mock Service 주입
        expenseController = new ExpenseController(expenseService);

        // MockMvc 초기화
        mockMvc = MockMvcBuilders.standaloneSetup(expenseController).build();

        // ObjectMapper 초기화 및 모듈 등록
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testCreateExpense() throws Exception {
        Expense expense = new Expense();
        expense.setCategory("Food");
        expense.setAmount(10000);
        expense.setDate(LocalDate.of(2024, 11, 23));
        expense.setDescription("Lunch");

        when(expenseService.createExpense(any(Expense.class))).thenReturn(expense);

        mockMvc.perform(post("/api/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(expense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Food"));
    }

    @Test
    void testGetExpensesByUserId() throws Exception {
        Expense expense1 = new Expense();
        expense1.setCategory("Food");
        expense1.setAmount(10000);

        Expense expense2 = new Expense();
        expense2.setCategory("Transport");
        expense2.setAmount(5000);

        when(expenseService.getExpensesByUserId(1L)).thenReturn(Arrays.asList(expense1, expense2));

        mockMvc.perform(get("/api/expenses/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testUpdateExpense() throws Exception {
        Expense updatedExpense = new Expense();
        updatedExpense.setCategory("Dinner");
        updatedExpense.setAmount(15000);

        when(expenseService.updateExpense(eq(1L), any(Expense.class))).thenReturn(updatedExpense);

        mockMvc.perform(put("/api/expenses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedExpense)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category").value("Dinner"))
                .andExpect(jsonPath("$.amount").value(15000));
    }

    @Test
    void testDeleteExpense() throws Exception {
        doNothing().when(expenseService).deleteExpense(1L);

        mockMvc.perform(delete("/api/expenses/1"))
                .andExpect(status().isNoContent());
    }
}