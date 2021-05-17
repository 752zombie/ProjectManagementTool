package com.example.projecttool.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DueDateCalculatorTest {

    DueDateCalculator dueDateCalculator;

    @BeforeEach
    void setUp() {
        dueDateCalculator = new DueDateCalculator();
    }

    @Test
    void testCorrectDateWorkWeekend() {
        String endDate1 = dueDateCalculator.calculateDueDate(1, 5, "2021-05-20", "weekend-true", 1);
        String endDate2 = dueDateCalculator.calculateDueDate(24, 5, "2021-05-20", "weekend-true", 1);
        String endDate3 = dueDateCalculator.calculateDueDate(3, 5, "2021-05-20", "weekend-true", 1);
        String endDate4 = dueDateCalculator.calculateDueDate(5, 5, "2021-05-20", "weekend-true", 1);
        String endDate5 = dueDateCalculator.calculateDueDate(1, 5, "2021-05-20", "weekend-true", 3);

        assertEquals("2021-05-25", endDate1);
        assertEquals("2021-05-20", endDate2);
        assertEquals("2021-05-21", endDate3);
        assertEquals("2021-05-21", endDate4);
        assertEquals("2021-05-21", endDate5);
    }

    @Test
    void testCorrectDateNotCountingWeekends() {
        String endDate1 = dueDateCalculator.calculateDueDate(1, 5, "2021-05-20", "weekend-false", 1);
        String endDate2 = dueDateCalculator.calculateDueDate(24, 5, "2021-05-20", "weekend-false", 1);
        String endDate3 = dueDateCalculator.calculateDueDate(3, 5, "2021-05-20", "weekend-false", 1);
        String endDate4 = dueDateCalculator.calculateDueDate(5, 5, "2021-05-20", "weekend-false", 1);
        String endDate5 = dueDateCalculator.calculateDueDate(1, 5, "2021-05-20", "weekend-false", 3);

        assertEquals("2021-05-27", endDate1);
        assertEquals("2021-05-20", endDate2);
        assertEquals("2021-05-21", endDate3);
        assertEquals("2021-05-21", endDate4);
        assertEquals("2021-05-21", endDate5);
    }

    @Test
    void testNoEmployees() {
        assertThrows(ArithmeticException.class, () -> dueDateCalculator.calculateDueDate(1, 5, "2021-05-20", "weekend-false", 0));
    }

    @Test
    void testNoHoursADay() {
        assertThrows(ArithmeticException.class, () -> dueDateCalculator.calculateDueDate(0, 5, "2021-05-20", "weekend-false", 1));
    }
}