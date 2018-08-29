package Utility;

import Game.Game;
import Game.Ship;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataToDouble {
    private final static double X = 1/Game.WIDTH;
    private final static double Y = 1/Game.HEIGHT;
    private final static double VEL = 1/300;

    /**
     * Turns the data into an arraylist that can be inputted into neurons;
     * made the data closer to 0 so that it doesn't have to have really small weights,
     * which would cause small changes to actually be large changes
     * @param data
     * @param ship
     * @return
     */
    public static List<Double> toDouble(Map<String, Double> data, Ship ship) {
        ArrayList<Double> ret = new ArrayList<>();
        boolean first = ship.isFirst();
        Double distanceX = data.get("ship1X") - data.get("ship2X");
        Double distanceY = data.get("ship1X") - data.get("ship2X");
        Double posX;
        Double posY;
        Double velX;
        Double velY;
        Double bulX;
        Double bulY;
        Double bulVelX;
        Double bulVelY;
        Double enemyVelX;
        Double enemyVelY;
        if (first) {
            distanceX = -distanceX;
            distanceY = -distanceY;
            posX = data.get("ship1X") * X;
            posY = data.get("ship1Y") * Y;
            velX = data.get("ship1VelX") * VEL;
            velY = data.get("ship1VelY") * VEL;
            enemyVelX = data.get("ship2VelX") * VEL;
            enemyVelY = data.get("ship2VelY") * VEL;
            bulX = data.get("bul2X") * X;
            bulY = data.get("bul2Y") * Y;
            bulVelX = data.get("bul2VelX") * VEL;
            bulVelY = data.get("bul2VelY") * VEL;
        } else {
            posX = data.get("ship2X") * X;
            posY = data.get("ship2Y") * Y;
            velX = data.get("ship2VelX") * VEL;
            velY = data.get("ship2VelY") * VEL;
            enemyVelX = data.get("ship1VelX") * VEL;
            enemyVelY = data.get("ship1VelY") * VEL;
            bulX = data.get("bul1X") * X;
            bulY = data.get("bul1Y") * Y;
            bulVelX = data.get("bul1VelX") * VEL;
            bulVelY = data.get("bul1VelY") * VEL;
        }
        ret.add(distanceX);
        ret.add(distanceY);
        ret.add(posX);
        ret.add(posY);
        ret.add(velX);
        ret.add(velY);
        ret.add(enemyVelX);
        ret.add(enemyVelY);
        ret.add(bulX);
        ret.add(bulY);
        ret.add(bulVelX);
        ret.add(bulVelY);
        return ret;
    }

}
