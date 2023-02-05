import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatricePolonaiseTest {
    CalculatricePolonaise CalculatricePolonaiseUnderTest;
    @BeforeEach
    public void initCalculatricePolonaiseObject(){
        CalculatricePolonaiseUnderTest = new CalculatricePolonaise();
    }
    @ParameterizedTest(name = "{0} Donne :  {1} ")
    @CsvSource({" 3 4 + ,2" , "3 4 + 5 *,35"})
    public void testCalcule(String operation ,float exceptedResult){
        //ASSERT
        assertEquals(exceptedResult,CalculatricePolonaiseUnderTest.calcule(operation));
    }
}
