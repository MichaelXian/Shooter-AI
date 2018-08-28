package Controllers;

import java.util.ArrayList;
import java.util.Map;

public class AI implements Controller {


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
}
