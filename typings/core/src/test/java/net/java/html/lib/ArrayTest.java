package net.java.html.lib;

/*
 * #%L
 * DukeScript Libraries - a library from the DukeScript project.
 * Visit http://dukescript.com for support and commercial license.
 * %%
 * Copyright (C) 2015 Eppleton IT Consulting
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */

import java.io.IOException;
import java.util.Arrays;
import net.java.html.lib.Function.A1;
import net.java.html.lib.Function.A2;
import net.java.html.junit.BrowserRunner;
import org.junit.Assert;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(BrowserRunner.class)
public class ArrayTest {
    public ArrayTest() {
    }

    @Test
    public void arrayForEach() throws Exception {
        Array<java.lang.Number> arr = new Array<>();
        assertNumber(arr.length.call(), 0.0, "Empty at first");
        assertForEach(arr);
    }

    @Test
    public void arrayConcat() throws Exception {
        Array<java.lang.Number> arr = new Array<>();
        assertNumber(arr.length.call(), 0.0, "Empty at first");
        assertConcat(arr);
    }

    @Test
    public void arrayWithValues() throws Exception {
        Array<java.lang.Number> arr = new Array<>(1.0, 2.0);
        assertNumber(arr.length.call(), 2.0, "Two at first: " + Arrays.toString(arr.slice()));
        assertNumber(arr.shift(), 1.0);
        assertNumber(arr.shift(), 2.0);
        assertNumber(arr.length.call(), 0.0, "Empty now");
        assertForEach(arr);
    }

    @Test
    public void arrayWithLength() throws Exception {
        Array<java.lang.Number> arr = new Array<>(2);
        assertNumber(arr.length.call(), 2.0, "Two at first: " + Arrays.toString(arr.slice()));
        assertNull(arr.shift());
        assertNull(arr.shift());
        assertForEach(arr);
    }

    @Test
    public void arrayForEachLoop() throws Exception {
        Array<java.lang.Number> arr = new Array<>(5, 4, 3, 2, 1);
        assertNumber(arr.length.call(), 5.0, "Five at first: " + Arrays.toString(arr.slice()));

        int sum = 0;
        for (java.lang.Number n : arr) {
            sum += n.intValue();
        }

        assertEquals(sum, 15);
    }

    private void assertForEach(Array<java.lang.Number> arr) {
        double length = arr.push(1.1, 2.2, 3);
        assertEquals(length, 3.0, "length is 3");
        assertEquals(arr.$get(0), 1.1, "1.1 is on position 0");
        assertEquals(arr.$get(1), 2.2, "2.2 is on position 1");
        assertEquals(arr.$get(2), 3.0, "3 is on position 2");
        boolean[] called = { false };
        double[] sum = { 0.0 };
        arr.forEach(new net.java.html.lib.Function.A1<java.lang.Number,Void>() {
            @Override
            public Void call(java.lang.Number p1) {
                called[0] = true;
                sum[0] += p1.doubleValue();
                return null;
            }
        });
        assertTrue(called[0], "forEach invoked");
        assertEquals(sum[0], 6.3, 0.1, "Sum computed");
        assertEquals(arr.pop(), 3.0);
        assertEquals(arr.pop(), 2.2);
        assertEquals(arr.pop(), 1.1);
    }

    private void assertConcat(Array<java.lang.Number> arr) {
        arr = arr.concat(1.1, 2.2, 3);
        assertNumber(arr.length.call(), 3.0, "Three items");
        assertEquals(arr.$get(0), 1.1, "1.1 is on position 0");
        assertEquals(arr.$get(1), 2.2, "2.2 is on position 1");
        assertEquals(arr.$get(2), 3.0, "3 is on position 2");
        boolean[] called = { false };
        double[] sum = { 0.0 };
        arr.forEach(new net.java.html.lib.Function.A1<java.lang.Number,Void>() {
            @Override
            public Void call(java.lang.Number p1) {
                called[0] = true;
                sum[0] += p1.doubleValue();
                return null;
            }
        });
        assertTrue(called[0], "forEach invoked");
        assertEquals(sum[0], 6.3, 0.1, "Sum computed");
        arr.push(2.2);
        assertEquals(arr.indexOf(2.2), 1.0, "2nd item is 2.2");
        assertEquals(arr.indexOf(2.2, 2), 3.0, "4th item is second 2.2");
        assertEquals(arr.lastIndexOf(2.2), 3.0, "4th item is last 2.2");
        assertEquals(arr.lastIndexOf(2.2, 2), 1.0, "2nd 2.2 is at 1");
    }

    @Test
    public void join() throws IOException {
        Array<java.lang.String> arr = new Array<>("Hello", "World");
        assertEquals(arr.join(), "Hello,World");
        assertEquals(arr.join(" "), "Hello World");
    }

    @Test
    public void reverseJoin() throws IOException {
        Array<java.lang.String> arr = new Array<>("World", "Hello");
        Array<java.lang.String> snd = arr.reverse();
        assertEquals(arr.join(), "Hello,World");
        assertEquals(arr.join(" "), "Hello World");
        assertEquals(snd.join(), "Hello,World");
        assertEquals(snd.join(" "), "Hello World");
    }
    @Test
    public void spliceInsert() throws IOException {
        Array<java.lang.String> arr = new Array<>("A", "B", "C", "D", "E", "F", "G", "H");
        Array<java.lang.String> removed = arr.splice(2, 3, "X", "Y", "Z");

        Object[] originalArray = arr.toArray();
        Object[] removedArray = removed.toArray();

        assertEquals(originalArray.length, 8, "Eight elements: " + Arrays.toString(originalArray));
        assertEquals(originalArray[0], "A");
        assertEquals(originalArray[1], "B");
        assertEquals(originalArray[2], "X");
        assertEquals(originalArray[3], "Y");
        assertEquals(originalArray[4], "Z");
        assertEquals(originalArray[5], "F");
        assertEquals(originalArray[6], "G");
        assertEquals(originalArray[7], "H");

        assertEquals(removedArray.length, 3, "Three elements removed: " + Arrays.toString(removedArray));
        assertEquals(removedArray[0], "C");
        assertEquals(removedArray[1], "D");
        assertEquals(removedArray[2], "E");
    }
    @Test
    public void spliceOnly() throws IOException {
        Array<java.lang.String> arr = new Array<>("A", "B", "C", "D", "E", "F", "G", "H");
        Array<java.lang.String> snd = arr.splice(2);

        Object[] left = arr.toArray();
        Object[] removed = snd.toArray();

        assertEquals(left.length, 2, "Two elements left: " + Arrays.toString(left));
        assertEquals(left[0], "A");
        assertEquals(left[1], "B");

        assertEquals(removed.length, 6, "Six elements removed: " + Arrays.toString(removed));
    }

    @Test
    public void some() throws Exception {
        Array<java.lang.Number> arr = new Array<>(5, 4, 3, 2, 1);

        class Seek implements A1<java.lang.Number,java.lang.Boolean> {
            private final int exp;

            public Seek(int exp) {
                this.exp = exp;
            }

            @Override
            public java.lang.Boolean call(java.lang.Number p1) {
                return p1.intValue() == exp;
            }
        }
        Seek seekThree = new Seek(3);
        Seek seekTen = new Seek(10);

        assertTrue(arr.some(seekThree), "Found 3");
        assertFalse(arr.some(seekTen), "10 not found");
    }

    @Test
    public void reduce() throws Exception {
        Array<java.lang.Number> arr = new Array<>(5, 4, 3, 2, 1);

        class Hold {
            final int value;

            Hold(int value) {
                this.value = value;
            }
        }

        class PickThree implements A2<Hold,java.lang.Number,Hold> {
            int counter = 3;
            @Override
            public Hold call(Hold p2, java.lang.Number p1) {
                if (counter-- <= 0) {
                    return p2;
                }
                return new Hold(p1.intValue() + p2.value);
            }
        }
        Hold res = arr.reduce(new PickThree(), new Hold(0));
        assertEquals(res.value, 12, "Sum of 1st 3 should be 12");

        Hold back = arr.reduceRight(new PickThree(), new Hold(0));
        assertEquals(back.value, 6, "Sum of last 3 should be 6");
    }

    @Test
    public void map() throws Exception {
        Array<java.lang.Number> arr = new Array<>(5, 4, 3, 2, 1);

        class ToStr implements A1<java.lang.Number,java.lang.String> {
            @Override
            public java.lang.String call(java.lang.Number p1) {
                return p1.toString();
            }
        }

        Array<java.lang.String> res = arr.map(new ToStr());
        assertNotNull(res);
        assertNumber(res.length.call(), 5);

        assertStartsWith(res.$get(0), "5");
        assertStartsWith(res.$get(1), "4");
        assertStartsWith(res.$get(2), "3");
        assertStartsWith(res.$get(3), "2");
        assertStartsWith(res.$get(4), "1");
    }

    @Test
    public void filter() throws Exception {
        Array<java.lang.Number> arr = new Array<>(5, 4, 3, 2, 1);

        class Odd implements A1<java.lang.Number,java.lang.Boolean> {
            @Override
            public java.lang.Boolean call(java.lang.Number p1) {
                return p1.intValue() % 2 == 1;
            }
        }

        Array<java.lang.Number> res = arr.filter(new Odd());
        assertNotNull(res);
        assertNumber(res.length.call(), 3);

        assertNumber(res.$get(0), 5);
        assertNumber(res.$get(1), 3);
        assertNumber(res.$get(2), 1);
    }

    @Test
    public void arrayToString() {
        Array<java.lang.String> arr = new Array<>("A", "B", "C");
        assertEquals(arr.toString(), "A,B,C");
    }

    @Test
    public void arrayToLocaleString() {
        Array<java.lang.String> arr = new Array<>("A", "B", "C");
        assertEquals(arr.toLocaleString(), "A,B,C");
    }

    private static void assertNumber(java.lang.Number actual, double expected) {
        assertEquals(actual.doubleValue(), expected);
    }

    private static void assertNumber(java.lang.Number actual, double expected, String msg) {
        assertEquals(actual.doubleValue(), expected, msg);
    }

    private static void assertStartsWith(java.lang.String actual, java.lang.String expected) {
        if (!actual.startsWith(expected)) {
            fail("Should start with " + expected + " but was " + actual);
        }
    }
    private static void assertTrue(boolean cond, String msg) {
        Assert.assertTrue(msg, cond);
    }

    private static void assertFalse(boolean cond, String msg) {
        Assert.assertFalse(msg, cond);
    }

    private static void assertEquals(double a, double b, double c, String msg) {
        Assert.assertEquals(msg, b, a, c);
    }

    private static void assertEquals(Object actual, Object expected, String msg) {
        Assert.assertEquals(msg, expected, actual);
    }

    private static void assertEquals(Object actual, Object expected) {
        Assert.assertEquals(expected, actual);
    }
}
