package neural;

import com.linusbauer.neural.Matrix;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {

    @Test
    void testAddition() {
        Matrix m1 = new Matrix(3, 3, 0);
        Matrix m2 = new Matrix(3, 3, 0);
        m2.set(0, 0, 1);
        m2.set(1, 1, 1);
        m2.set(1, 2, 1);
        m1.add(m2);
        assertEquals(m1, m2);
    }

    @Test
    void testEquals() {
        Matrix m1 = new Matrix(3, 3, 0);
        Matrix m2 = new Matrix(3, 3, 0);
        assertEquals(m1, m2);
    }

    @Test
    void testMultiply() {
        Matrix m1 = new Matrix(3, 3, 0);
        Matrix m2 = new Matrix(3, 3, 0);
       m1.set(0, 0, 1);
       m1.set(0, 1, 2);
       m1.set(2, 2, 5);
       m2.set(0, 2, 7);
       m2.set(1, 0, 2);
       m2.set(2, 1, 3);
       System.out.println(m1.multiply(m2));
    }

    @Test
    void testTranspose() {
        Matrix m1 = new Matrix(3, 3, 0);
        Matrix m2 = new Matrix(3, 3, 0);
        m1.set(0, 0, 2);
        m1.set(0, 1, 0);
        m1.set(0, 2, 5);
        m1.set(1, 0, 3);
        m1.set(1, 1, 2);
        m1.set(1, 2, 7);
        m1.set(2, 0, -2);
        m1.set(2, 1, 7);
        m1.set(2, 2, -2);
        Matrix result = m1.transpose();
        m2.set(0, 0, 2);
        m2.set(0, 1, 3);
        m2.set(0, 2, -2);
        m2.set(1, 0, 0);
        m2.set(1, 1, 2);
        m2.set(1, 2, 7);
        m2.set(2, 0, 5);
        m2.set(2, 1, 7);
        m2.set(2, 2, -2);
        assertEquals(m2, result);
    }
}
