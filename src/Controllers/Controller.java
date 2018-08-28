package Controllers;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

public interface Controller {
    ArrayList<Boolean> update (Map<String, Double> data);

    void keyPressed(KeyEvent e);

    void keyReleased(KeyEvent e);
}
