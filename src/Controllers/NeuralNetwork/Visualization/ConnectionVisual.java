package Controllers.NeuralNetwork.Visualization;

import Utility.Line;
import Utility.Vector;
import org.neuroph.core.Connection;

import java.awt.*;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ConnectionVisual {

    private Shape shape;
    private boolean isTop;
    private Double opacity;
    private Connection connection;
    /**
     * Contsructs a visualization of the connection
     * @param position1
     * @param position2
     */
    public ConnectionVisual(Connection connection, Vector position1, Vector position2, boolean isTop) {
        this.connection = connection;
        this.isTop = isTop;
        Double width = 5 * Math.pow(connection.getWeight().value, 1/3);
        width = Math.min(Math.max(width, -20), 20); // constrain width between -20 and 20
        this.shape = Line.lineToRect(position1.x(), position1.y(),
                position2.x(), position2.y(),
                width);
        update();
    }

    /**
     * Updates opacity of line
     */
    private void update() {
        opacity = connection.getInput();
        opacity = min(1, max(-1, opacity));
    }

    /**
     * Updates then gets the color of the line
     */
    private Color getColor() {
        update();
        if (isTop) {
            if (opacity > 0) {
                return new Color(1f, 0f, 0f, new Float(opacity));
            }
                return new Color(0f, 1f, 0f, new Float(-opacity));
        } else {
            if (opacity > 0) {
                return new Color(0f, 0f, 1f, new Float(opacity));
            }
            return new Color(1f, 0.31f, 0f, new Float(-opacity));
        }
    }

    public Graphic getGraphic() {
        return new Graphic(shape, getColor());
    }
}
