package neural;

import com.linusbauer.neural.Matrix;
import com.linusbauer.neural.NeuralNetwork;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class NeuralNetworkTest {
    @Test
    void testForward() {
        List<Integer> layers = new ArrayList<>();
        layers.add(10);
        layers.add(28);
        layers.add(28);
        layers.add(10);
        List<String> outputs = new ArrayList<>();
        outputs.add("0");
        outputs.add("1");
        outputs.add("2");
        outputs.add("3");
        outputs.add("4");
        outputs.add("5");
        outputs.add("6");
        outputs.add("7");
        outputs.add("8");
        outputs.add("9");
        NeuralNetwork network = new NeuralNetwork(layers, outputs);
        Matrix input = new Matrix(10, 1);
        input.set(0, 0, 0);
        input.set(1, 0, 1);
        input.set(2, 0, 0);
        input.set(3, 0, 0);
        input.set(4, 0, 0);
        input.set(5, 0, 0);
        input.set(6, 0, 0);
        input.set(7, 0, 0);
        input.set(8, 0, 0);
        input.set(9, 0, 0);
        network.forward(input);
    }

}
