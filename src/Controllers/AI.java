package Controllers;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Map;

public class AI implements Controller {
    String name;

    public AI() {
        this.name = "abcdef";
    }



    @Override
    public ArrayList<Boolean> update(Map<String, Double> data) {
        ArrayList<Boolean> ret = new ArrayList<>();
        ret.add(false);
        ret.add(false);
        ret.add(false);
        ret.add(false);
        ret.add(false);
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
