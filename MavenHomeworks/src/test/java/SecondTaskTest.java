import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class SecondTaskTest {

    private SecondTasksInterface task;

    @Before
    public void setUp() {
        task = new SecondTask();
    }

    @After
    public void tearDown() {
        task = null;
    }

    @Test
    public void testTaskFirst() {
        int[] actualResult = {3, 2, 1};
        int[] testInit = {4, 3, 2, 1};
        int[] testResult = task.initTask(testInit);
        Assert.assertArrayEquals(testResult, actualResult);
        System.out.println();
    }

    @Test
    public void testTaskSecond() {
        int[] actualResult = {0};
        int[] testInit = {17, 4, 4, 0};
        int[] testResult = task.initTask(testInit);
        Assert.assertArrayEquals(testResult, actualResult);
        System.out.println();
    }

    @Test
    public void testTaskThird() {
        int[] actualResult = {2, 2, 5, 5, 7, 7};
        int[] testInit = {12, 4, 4, 2, 2, 5, 5, 7, 7};
        int[] testResult = task.initTask(testInit);
        Assert.assertArrayEquals(testResult, actualResult);
        System.out.println();
    }

    @Test(expected = RuntimeException.class)
    public void exceptionTestFirst() {
        int[] testInit = {4,3,2,1};
        int[] testResult = task.initTask(testInit);
        Assert.assertThat(testResult, null);
        System.out.println();
    }

    @Test(expected = RuntimeException.class)
    public void exceptionTestSecond() {
        int[] testInit = {0};
        int[] testResult = task.initTask(testInit);
        Assert.assertThat(testResult, null);
        System.out.println();
    }

    @Test(expected = RuntimeException.class)
    public void exceptionTestThird() {
        int[] testInit = {12, 5, 12, 5, 12, 5};
        int[] testResult = task.initTask(testInit);
        Assert.assertThat(testResult, null);
        System.out.println();
    }
}
