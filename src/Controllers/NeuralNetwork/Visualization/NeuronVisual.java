package Controllers.NeuralNetwork.Visualization;

import Utility.Circle;
import org.neuroph.core.Neuron;

import java.awt.*;

public class NeuronVisual {
    private static final Double RADIUS = 20d;
    private Double x;
    private Double y;
    private Double opacity;
    private Neuron neuron;
    private boolean isTop;

    public NeuronVisual(Neuron neuron, Double x, Double y, boolean isBottom) {
        this.neuron = neuron;
        this.isTop = isBottom;
        this.x = x;
        this.y = y;
        update();
    }


    public void update() {
        this.opacity = neuron.getNetInput();
    }


    public Color getColor() {
        update();
        if (isTop) {
            return new Color(1f, 0f, 0f, new Float(opacity));
        } else {
            return new Color (0f, 0f, 1f, new Float(opacity));
        }
    }

    public Shape getShape() {
        return Circle.toCircle(x, y, RADIUS);
    }
}
