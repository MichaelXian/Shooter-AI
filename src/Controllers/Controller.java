package Controllers;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public interface Controller {


    ArrayList<Boolean> update (List<Double> neuronInput);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);

    String getName();
}
