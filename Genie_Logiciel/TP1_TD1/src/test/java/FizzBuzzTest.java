import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FizzBuzzTest {
    FizzBuzz FizzBuzzUnderTest;
   @BeforeEach
    public void initFizzBuzzObject(){
       FizzBuzzUnderTest = new FizzBuzz();
   }
    @ParameterizedTest(name = "{0} Donne Fizz")
   @ValueSource(ints = {3,9,13,53})
    public void testFizz(int arg){
       //ACT
       String  result = FizzBuzzUnderTest.getResult(arg);
       //ASSERT
       assertEquals(result,"Fizz");
   }
    @ParameterizedTest(name = "{0} Donne Buzz")
    @ValueSource(ints = {5,10})
    public void testBuzz(int arg){
        //ACT
        String  result = FizzBuzzUnderTest.getResult(arg);
        //ASSERT
        assertEquals(result,"Buzz");
    }
    @ParameterizedTest(name = "{0} Donne Buzz")
    @ValueSource(ints = {15})
    public void testFizzBuzz(int arg){
        //ACT
        String  result = FizzBuzzUnderTest.getResult(arg);
        //ASSERT
        assertEquals(result,"FizzBuzz");
    }
}