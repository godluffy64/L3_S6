package com.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class FizzBuzzTest {
    @Test
    public void testFizzBuzz() {
        // ARRANGE
        FizzBuzz fizzBuzz = new FizzBuzz();

        // ACT
        String result = fizzBuzz.getResult(15);
        String result2 = fizzBuzz.getResult(5);
        String result3 = fizzBuzz.getResult(3);

        // ASSERT
        assertEquals("FizzBuzz", result);
        assertEquals("Buzz", result2);
        assertEquals("Fizz", result3);
    }

     
    }
