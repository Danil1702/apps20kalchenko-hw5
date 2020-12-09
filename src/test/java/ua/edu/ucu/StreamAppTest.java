package ua.edu.ucu;

import org.junit.Ignore;
import ua.edu.ucu.stream.*;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author andrii
 */
public class StreamAppTest {
    
    private IntStream intStream;
    private IntStream voidIntStream;

    @Before
    public void init() {
        int[] intArr = {-1, 0, 1, 2, 3};
        intStream = AsIntStream.of(intArr);
    }
    
    @Test
    public void testStreamOperations() {
        System.out.println("streamOperations");
        int expResult = 42;
        int result = StreamApp.streamOperations(intStream);
        assertEquals(expResult, result);        
    }

    @Test
    public void testStreamToArray() {
        System.out.println("streamToArray");
        int[] expResult = {-1, 0, 1, 2, 3};
        int[] result = StreamApp.streamToArray(intStream);
        assertArrayEquals(expResult, result);        
    }

    @Test
    public void testStreamForEach() {
        System.out.println("streamForEach");
        String expResult = "-10123";
        String result = StreamApp.streamForEach(intStream);
        assertEquals(expResult, result);        
    }

    @Test
    public void testAverage() {
        System.out.println("average");
        Double expResult = 1.0;
        Double result = intStream.average();
        assertEquals(expResult, result);
    }

    @Test
    public void testMinimum() {
        System.out.println("minimum");
        int expResult = -1;
        int result = intStream.min();
        assertEquals(expResult, result);
    }

    @Test
    public void testMaximum() {
        System.out.println("maximum");
        int expResult = 3;
        int result = intStream.max();
        assertEquals(expResult, result);
    }

    @Test
    public void testCount() {
        System.out.println("count");
        long expResult = 5;
        long result = intStream.count();
        assertEquals(expResult, result);
    }

    @Test
    public void testSum() {
        System.out.println("sum");
        int expResult = 5;
        long result = intStream.sum();
        assertEquals(expResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAverageException() {
        System.out.println("average exception");
        int[] voidIntArr = {};
        voidIntStream = AsIntStream.of(voidIntArr);
        voidIntStream.average();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMinException() {
        System.out.println("min exception");
        int[] voidIntArr = {};
        voidIntStream = AsIntStream.of(voidIntArr);
        voidIntStream.min();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMaxException() {
        System.out.println("max exception");
        int[] voidIntArr = {};
        voidIntStream = AsIntStream.of(voidIntArr);
        voidIntStream.max();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSumException() {
        System.out.println("sum exception");
        int[] voidIntArr = {};
        voidIntStream = AsIntStream.of(voidIntArr);
        voidIntStream.sum();
    }

    @Test
    public void testForEach() {
        System.out.println("forEach");
        String expResult = "212510";
        StringBuilder str = new StringBuilder();
        intStream.forEach(x -> str.append(x*x + 1));
        assertEquals(expResult, str.toString());
    }

    @Test
    public void testSimpleFilter() {
        System.out.println("simple filter");
        int[] expResult = {0, 1, 2, 3};
        IntStream result = intStream.filter(x -> x >= 0);
        assertArrayEquals(expResult, result.toArray());
    }

    @Test
    public void testComplexFilter() {
        System.out.println("complex filter");
        int[] expResult = {0, 1};
        IntStream result = intStream.filter(x -> x*x <= 1).filter(x -> x >= 0);
        assertArrayEquals(expResult, result.toArray());
    }

    @Test
    public void testSimpleMap() {
        System.out.println("simple map");
        int[] expResult = {-11, -10, -9, -8, -7};
        IntStream result = intStream.map(x -> x - 10);
        assertArrayEquals(expResult, result.toArray());
    }

    @Test
    public void testComplexMap() {
        System.out.println("complex map");
        int[] expResult = {121, 100, 81, 64, 49};
        IntStream result = intStream.map(x -> x - 10).map(x -> x*x);
        assertArrayEquals(expResult, result.toArray());
    }

    @Test
    public void testSimpleFlatMap() {
        System.out.println("simple flat map");
        int[] expResult = {-1, 1, -1, 0, 0, 0, 1, 1, 1, 2, 4, 8, 3, 9, 27};
        IntStream result = intStream
                .flatMap(x -> AsIntStream.of(x, x*x, x*x*x));
        assertArrayEquals(expResult, result.toArray());
    }

    @Test
    public void testUsageAllMethods() {
        System.out.println("simple flat map");
        int[] expResult = {2, 4, 3};
        IntStream result = intStream
                .flatMap(x -> AsIntStream.of(x, x*x, x*x*x))
                .map(x -> x % 10)
                .filter(x -> x > 1)
                .filter(x -> x < 5);
        assertArrayEquals(expResult, result.toArray());
    }

    @Test
    public void testReduce() {
        System.out.println("simple map");
        int expResult = 0;
        int result = intStream.reduce(1, (prod, x) -> prod *= x);
        assertEquals(expResult, result);
    }
}
