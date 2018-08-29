package Controllers;


import Controllers.NeuralNetwork.Visualization.NeuralNetworkVisual;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public interface Controller {


    ArrayList<Boolean> update (List<Double> neuronInput);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);

    NeuralNetworkVisual getNeuralNetworkVisual();



    String getName();
}
