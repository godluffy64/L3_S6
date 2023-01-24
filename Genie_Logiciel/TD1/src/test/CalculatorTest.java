


import org.junit.Test;

import static org.junit.Assert.assertEquals;

class CalculatorTest {

    @Test
    void testAddTwoPositiveNumbers() {
        // Arrange
        int a = 2;
        int b = 3;
        Calculator calculator = new Calculator();

        // Act
        int somme = calculator.add(a, b);

        // Assert
        assertEquals(5, somme);
    }

}
