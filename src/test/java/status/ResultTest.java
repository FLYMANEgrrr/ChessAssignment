package status;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ResultTest {

    @Test
    public void testNormalResult(){
        int result1= Result.calculate(15,20000);
        int result2=Result.calculate(20,30000);
        assertTrue(result1>result2);
    }

    @Test
    public void testMoveIsMoreImportant(){
        int result3=Result.calculate(5,100000);
        int result4=Result.calculate(50,10000);
        assertTrue(result3>result4);
    }

    @Test
    public void testAddMove() {
        Result result = new Result();
        result.moveProperty().set(0);
        result.addMove();
        assertEquals(1, result.moveProperty().get());
    }

}
