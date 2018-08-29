package Controllers.NeuralNetwork.Visualization;

import Utility.Circle;
import org.neuroph.core.Neuron;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class NeuronVisual {
    private static final Double RADIUS = 20d;
    private Double x;
    private Double y;
    private Shape shape;
    private Double opacity;
    private Neuron neuron;
    private boolean isTop;
    private Graphic graphic;
    private List<ConnectionVisual> connectionVisuals;

    public NeuronVisual(Neuron neuron, Double x, Double y, boolean isBottom) {
        this.neuron = neuron;
        this.isTop = isBottom;
        this.x = x;
        this.y = y;
        this.shape = Circle.toCircle(x, y, RADIUS);
        this.connectionVisuals = new ArrayList<>();
        update();
    }


    public void update() {
        this.opacity = neuron.getNetInput();
    }

    public Graphic getGraphic() {
        this.graphic = new Graphic(shape, getColor());
        return graphic;
    }

    private Color getColor() {
        update();
        if (isTop) {
            return new Color(1f, 0f, 0f, new Float(opacity));
        } else {
            return new Color (0f, 0f, 1f, new Float(opacity));
        }
    }

}
