package neural;

import com.linusbauer.neural.Matrix;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MatrixTest {

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
}
