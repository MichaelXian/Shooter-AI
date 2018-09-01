package Controllers.NeuralNetwork.Visualization;

import Utility.Circle;
import Utility.Vector;
import org.neuroph.core.Connection;
import org.neuroph.core.Neuron;
import org.neuroph.nnet.comp.neuron.InputNeuron;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;


public class NeuronVisual {
    private static final Double RADIUS = 15d;
    private List<ConnectionVisual> connectionVisuals;
    private Shape shape;
    private Neuron neuron;
    private Double opacity;
    private Double x;
    private Double y;
    private boolean isTop;

    NeuronVisual(Neuron neuron, Double x, Double y, boolean isTop) {
        this.neuron = neuron;
        this.isTop = isTop;
        this.x = x;
        this.y = y;
        this.shape = Circle.toCircle(x, y, RADIUS);
        this.connectionVisuals = new ArrayList<>();
        update();
    }


    /**
     * Updates this neuron's opacity, based on it's current input
     */
    public void update() {
        opacity = neuron.getOutput();

        opacity = min(1, max(opacity, 0));
    }

    /**
     * Updates and gets the color of the neuron
     * @return
     */
    private Color getColor() {
        update();
        if (isTop) {
            if (this.neuron instanceof InputNeuron) {
                return new Color(1f, 0f, 0f, new Float(opacity));
            }
            if (opacity > 0.5) {
                opacity = 2 * (opacity - 0.5); // make opacity go from 0.5-1 to 0-1
                return new Color(1f, 0f, 0f, new Float(opacity));
            } else {
                opacity = (1 - 2 * opacity); // make opacity go from 0.5-0 to 0-1
                return new Color(0f, 1f, 0f, new Float(opacity));
            }
        } else {
            if (this.neuron instanceof InputNeuron) {
                return new Color(0f, 0f, 1f, new Float(opacity));
            }
            if (opacity > 0.5) {
                opacity = 2 * (opacity - 0.5); // make opacity go from 0.5-1 to 0-1
                return new Color(0f, 0f, 1f, new Float(opacity));
            } else {
                opacity = (1 - 2 *opacity); // make opacity go from 0.5-0 to 0-1
                return new Color(1f, 0.31f, 0f, new Float(opacity));
            }
        }
    }


    public void addConnection(Connection connection, NeuronVisual neuronVisual) {
        connectionVisuals.add(new ConnectionVisual(connection, getPosition(), neuronVisual.getPosition(), isTop));
    }

    /**
     * Returns the position as a vector
     * @return
     */
    public Vector getPosition() {
        return new Vector(x, y);
    }

    /**
     * Gets graphics of all outgoing connections from this neuron
     * @return
     */
    public List<Graphic> getConnectionGraphics() {
        ArrayList<Graphic> ret = new ArrayList<>();
        for (ConnectionVisual connection: connectionVisuals) {
            ret.add(connection.getGraphic());
        }
        return ret;
    }

    /**
     * Creates and returns the graphic for this neuronVisual
     * @return
     */
    public Graphic getGraphic() {
        return new Graphic(shape, getColor());
    }


    public Neuron getNeuron() {
        return neuron;
    }




}
