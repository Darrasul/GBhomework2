import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ThirdTaskTest {

    private ThirdTaskInterface task;

        @Before
    public void setUp() {
        task = new ThirdTask();
    }

        @After
    public void tearDown() {
        task = null;
    }

        @Test
    public void rightArrayTest() {
        boolean actualResult = true;
        int[] testInit = {1, 4, 1, 4};
        boolean testResult = task.initTask(testInit);
        Assert.assertEquals(actualResult, testResult);
    }

    @Test
    public void firstSymbolWrongTest() {
        boolean actualResult = false;
        int[] testInit = {0, 4, 0, 4};
        boolean testResult = task.initTask(testInit);
        Assert.assertEquals(actualResult, testResult);
    }

    @Test
    public void secondSymbolWrongTest() {
        boolean actualResult = false;
        int[] testInit = {1, 0, 1, 0};
        boolean testResult = task.initTask(testInit);
        Assert.assertEquals(actualResult, testResult);
    }

    @Test
    public void wrongArrayTest() {
        boolean actualResult = false;
        int[] testInit = {2, 3, 2, 3};
        boolean testResult = task.initTask(testInit);
        Assert.assertEquals(actualResult, testResult);
    }
}
