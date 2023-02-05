import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BerlinUhrTest {
    BerlinUhr BerlinUhrUnderTest;
    @BeforeEach
    public void initBerlinUhrObject(){
        BerlinUhrUnderTest = new BerlinUhr();
    }
    @ParameterizedTest(name = "{0} Donne {1} ")
    @CsvSource ({"1,0000" , "5,R000","10,RR00","15,RRR0","20,RRRR"})
    public void testTopHours(int arg,String exceptedResult){
        //ASSERT
        assertEquals(exceptedResult,BerlinUhrUnderTest.getTopHours(arg));
    }
}
