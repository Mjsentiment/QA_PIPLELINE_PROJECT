package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    @Test
    public void testAddNumbers() {
        int result = Main.addNumbers(5, 7);
        assertEquals(12, result, "5 + 7 should equal 12");
    }

    @Test
    public void testAddNumbersNegative() {
        int result = Main.addNumbers(-3, 3);
        assertEquals(0, result, "-3 + 3 should equal 0");
    }
}
