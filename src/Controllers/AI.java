package Controllers;

import java.util.ArrayList;

public class AI implements Controller {


    @Override
    public ArrayList<Boolean> update(ArrayList<Double> data) {
        ArrayList<Boolean> ret = new ArrayList<>();
        ret.add(false);
        ret.add(false);
        ret.add(false);
        ret.add(false);
        ret.add(false);
        return ret;
    }
}
