package Controllers;

import org.neuroph.core.NeuralNetwork;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player implements Controller{
    Map<Integer, Boolean> keyStrokes;
    List<Integer> valid;
    String name;

    /**
     * Initializes keystrokes map and valid keys
     */
    public Player() {
        this.name = "Player";
        keyStrokes = new HashMap<>();
        keyStrokes.put(KeyEvent.VK_W, false);
        keyStrokes.put(KeyEvent.VK_A, false);
        keyStrokes.put(KeyEvent.VK_S, false);
        keyStrokes.put(KeyEvent.VK_D, false);
        keyStrokes.put(KeyEvent.VK_SPACE, false);
        valid = new ArrayList<>();
        valid.add(KeyEvent.VK_W);
        valid.add(KeyEvent.VK_A);
        valid.add(KeyEvent.VK_S);
        valid.add(KeyEvent.VK_D);
        valid.add(KeyEvent.VK_SPACE);
    }


    /**
     * Sets key to pressed down
     * @param e
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (valid.indexOf(key) != -1) {
            keyStrokes.put(key, true);
        }
    }

    /**
     * Sets key to released
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (valid.indexOf(key) != -1) {
            keyStrokes.put(key, false);
        }
    }

    /**
     * Returns commands for ship, based on pressed buttons
     * @param neuronInput
     * @return
     */
    @Override
    public ArrayList<Boolean> update(List<Double> neuronInput) {
        ArrayList<Boolean> ret = new ArrayList<>();
        ret.add(keyStrokes.get(KeyEvent.VK_W));
        ret.add(keyStrokes.get(KeyEvent.VK_S));
        ret.add(keyStrokes.get(KeyEvent.VK_A));
        ret.add(keyStrokes.get(KeyEvent.VK_D));
        ret.add(keyStrokes.get(KeyEvent.VK_SPACE));
        return ret;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public NeuralNetwork getNeuralNetwork() {
        return null;
    }
}
