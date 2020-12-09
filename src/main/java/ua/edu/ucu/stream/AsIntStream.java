package ua.edu.ucu.stream;

import ua.edu.ucu.function.*;

import java.util.ArrayList;
import java.util.Arrays;

public class AsIntStream implements IntStream {
    private int[] stream;
    private int sum;
    private int maximum;
    private int minimum;

    private AsIntStream() {
        this.stream = new int[0];
        this.sum = 0;
    }

    public static IntStream of(int... values) {
        AsIntStream newStream = new AsIntStream();
        newStream.stream = values;
        for (int value: values) {
            newStream.sum += value;
            if (value < newStream.minimum) {
                newStream.minimum = value;
            }
            if (value > newStream.maximum) {
                newStream.maximum = value;
            }
        }
        return newStream;
    }

    @Override
    public Double average() {
        if (stream.length == 0) {
            throw new IllegalArgumentException();
        }
        return (double) (sum / count());
    }

    @Override
    public Integer max() {
        if (stream.length == 0) {
            throw new IllegalArgumentException();
        }
        return maximum;
    }

    @Override
    public Integer min() {
        if (stream.length == 0) {
            throw new IllegalArgumentException();
        }
        return minimum;
    }

    @Override
    public long count() {
        return stream.length;
    }

    @Override
    public Integer sum() {
        if (stream.length == 0) {
            throw new IllegalArgumentException();
        }
        return sum;
    }

    @Override
    public IntStream filter(IntPredicate predicate) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        for (int value: stream) {
            if (predicate.test(value)) {
                integerArrayList.add(value);
            }
        }
        int[] filtered = new int[integerArrayList.size()];
        for (int i = 0; i < integerArrayList.size(); i++) {
            filtered[i] = integerArrayList.get(i);
        }
        return of(filtered);
    }

    @Override
    public void forEach(IntConsumer action) {
        for (int value: stream) {
            action.accept(value);
        }
    }

    @Override
    public IntStream map(IntUnaryOperator mapper) {
         int[] newIntStream = new int[stream.length];
         for (int i = 0; i < stream.length; i++) {
             newIntStream[i] = mapper.apply(stream[i]);
         }
         return of(newIntStream);
    }

    @Override
    public IntStream flatMap(IntToIntStreamFunction func) {
        IntStream[] streamsArr = new IntStream[stream.length];
        long totalSize = 0;
        for (int i = 0; i < stream.length; i++) {
            IntStream newStream = func.applyAsIntStream(stream[i]);
            streamsArr[i] = newStream;
            totalSize += newStream.count();
        }
        int[] combinedStream = new int[(int) totalSize];
        int index = 0;
        for (int i = 0; i < stream.length; i++) {
            System.arraycopy(streamsArr[i].toArray(), 0,
                            combinedStream,
                            index,
                            (int) streamsArr[i].count());
            index += (int) streamsArr[i].count();
        }
        return of(combinedStream);
    }

    @Override
    public int reduce(int identity, IntBinaryOperator op) {
        int reducedValue = identity;
        for (int value: stream) {
            reducedValue = op.apply(reducedValue, value);
        }
        return reducedValue;
    }

    @Override
    public int[] toArray() {
        return Arrays.copyOf(stream, stream.length);
    }
}
