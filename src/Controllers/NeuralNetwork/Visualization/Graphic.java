package Controllers.NeuralNetwork.Visualization;

import java.awt.*;

public class Graphic {
    private Shape shape;
    private Color color;


    public Graphic(Shape shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getColor() {
        return color;
    }
}
