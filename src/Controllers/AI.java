package Controllers;

import org.neuroph.core.NeuralNetwork;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class AI implements Controller {
    String name;
    NeuralNetwork neuralNetwork;
    private static final Double THRESHOLD = 0.5;
    public AI(NeuralNetwork neuralNetwork) {
        this.neuralNetwork = neuralNetwork;
        this.name = "abcdef";
    }



    @Override
    public ArrayList<Boolean> update(List<Double> neuronInput) {
        //Double[] input = neuronInput.toArray(new Double[neuronInput.size()]);
        neuralNetwork.setInput(
                neuronInput.get(0),
                neuronInput.get(1),
                neuronInput.get(2),
                neuronInput.get(3),
                neuronInput.get(4),
                neuronInput.get(5),
                neuronInput.get(6),
                neuronInput.get(7),
                neuronInput.get(8),
                neuronInput.get(9),
                neuronInput.get(10),
                neuronInput.get(11)
        );
        neuralNetwork.calculate();
        double[] output = neuralNetwork.getOutput();
        ArrayList<Boolean> ret = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            ret.add(output[i] > THRESHOLD);
        }
        return ret;
    }



    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public String getName() {
        return name;
    }
}
