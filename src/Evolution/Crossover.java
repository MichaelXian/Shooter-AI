package Evolution;

import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Crossover {
    public static final String CLONE_PATH = "NeuralNets/temp/clone.nnet";
    static Random random = new Random();
    /**
     * Creates a new child from two random networks from the given list of networks
     * @param networks
     * @return the child
     */
    public static NeuralNetwork breed(List<NeuralNetwork> networks) {
        // Choose 2 random parents
        List<NeuralNetwork> networks2 = new ArrayList<>();
        networks2.addAll(networks);
        NeuralNetwork network = networks.get(random.nextInt(networks.size()));
        networks2.remove(network);
        NeuralNetwork network2 = networks2.get(random.nextInt(networks2.size()));

        return createChild(network, network2);
    }

    /**
     * Creates a child from the given 2 networks
     * @param network
     * @param network2
     */
    private static NeuralNetwork createChild(NeuralNetwork network, NeuralNetwork network2) {
        NeuralNetwork child = null;
        NeuralNetwork other = null;
        if (random.nextBoolean()) {
            child = clone(network);
            other = network2;
        } else {
            child = clone(network2);
            other = network;
        }
        for (int i = 0; i < 4; i++) {
            crossLayers(child.getLayerAt(i), other.getLayerAt(i));
        }
        return child;
    }

    /**
     * Crosses the given layers, changing the first given one
     * @param childLayer
     * @param otherLayer
     */
    private static void crossLayers(Layer childLayer, Layer otherLayer) {
        List<Neuron> childNeurons = childLayer.getNeurons();
        List<Neuron> otherNeurons = otherLayer.getNeurons();
        int smallerSize = smallerSize(childNeurons, otherNeurons);
        for (int i = 0; i < smallerSize; i++) {
            crossNeurons(childNeurons.get(i), otherNeurons.get(i));
        }
    }

    /**
     * Crosses the given neurons, changing the first given one
     * @param childNeuron
     * @param otherNeuron
     */
    private static void crossNeurons(Neuron childNeuron, Neuron otherNeuron) {
        List<Connection> childConnections = childNeuron.getOutConnections();
        List<Connection> otherConnections = otherNeuron.getOutConnections();
        int smallerSize = smallerSize(childConnections, otherConnections);
        for (int i = 0; i < smallerSize; i++) {
            if (random.nextBoolean()) {
                Connection childConnection = childConnections.get(i);
                Connection otherConnection = otherConnections.get(i);
                childConnection.setWeight(otherConnection.getWeight());
            }
        }
    }

    /**
     * returns the smaller size between two lists
     * @param list1
     * @param list2
     * @return
     */
    private static <T> int smallerSize(List<T> list1, List<T> list2) {
        return list1.size() < list2.size() ? list1.size() : list2.size();
    }

    /**
     * Creates a clone of the given network, and returns it
     * @param network
     * @return
     */
    private static NeuralNetwork clone(NeuralNetwork network) {
        network.save(CLONE_PATH);
        return NeuralNetwork.createFromFile(CLONE_PATH);
    }


}
