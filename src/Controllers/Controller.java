package Controllers;


import org.neuroph.core.NeuralNetwork;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public interface Controller {


    ArrayList<Boolean> update (List<Double> neuronInput);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);

    NeuralNetwork getNeuralNetwork();

    String getName();
}
