package Evolution;

import Controllers.NeuralNetwork.NeuronFactory;
import org.neuroph.core.Connection;
import org.neuroph.core.Layer;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.Neuron;
import org.neuroph.nnet.comp.neuron.BiasNeuron;

import java.util.List;
import java.util.Random;

public class Mutator {
    private static final Double MULTIPLIER = 1.0;
    private static final Double REMOVE_NEURON_CHANCE = 0.05 * MULTIPLIER;
    private static final Double ADD_NEURON_CHANCE = 0.05 * MULTIPLIER;
    private static final Double REMOVE_CONNECTION_CHANCE = 0.01 * MULTIPLIER;
    private static final Double ADD_CONNECTION_CHANCE = 0.01 * MULTIPLIER;
    private static final Double WEIGHT_DELTA = 0.01 * MULTIPLIER;
    private static Random random = new Random();
    /**
     * Mutates the given network
     * @param network
     */
    public static void mutate(NeuralNetwork network) {
        List<Layer> layers = network.getLayers();
        for (int i = 0; i < 4; i++) {
            mutateLayer(layers.get(i), network, i);
        }
    }

    /**
     * Mutates the given layer, growing/pruning neurons, mutating neurons
     * @param layer
     * @param network
     * @param i the current layer number
     */
    private static void mutateLayer(Layer layer, NeuralNetwork network, int i) {
        List<Neuron> neurons = layer.getNeurons();
        if (removeNeuronChance(neurons.size()) && i != 0) { // Don't delete input neurons
            if (neurons.size() > 0) {
                layer.removeNeuron(randomList(neurons));
            }
        } else if (addNeuronChance(neurons.size()) && i != 0) { // Don't add input neurons
            neurons.add(NeuronFactory.randomNeuron());
        }
        for (Neuron neuron: neurons) {
            mutateNeuron(neuron, network, i);
        }
    }

    /**
     * Mutates the given neuron, growing/pruning connections, changing weights
     * @param neuron
     * @param network
     * @param i the current layer number
     */
    private static void mutateNeuron(Neuron neuron, NeuralNetwork network, int i) {
        List<Connection> connections = neuron.getOutConnections();
        if (removeConnectionChance(connections.size())) {
            if (connections.size() > 0) {
                neuron.removeOutputConnectionTo(randomList(connections).getToNeuron());
            }
        } else if (addConnectionChance(connections.size())) {
            addConnection(neuron, network, i);
        }
        for (Connection connection: connections) {
            mutateConnection(connection);
        }
        removeAllBiasNeurons(network);
    }

    /**
     * Removes all bias neurons
     * @param network
     */
    private static void removeAllBiasNeurons(NeuralNetwork network) {
        for (Object object: network.getLayers()) {
            Layer layer = (Layer) object;
            for (Neuron neuron: layer.getNeurons()) {
                if (neuron instanceof BiasNeuron) {
                    layer.removeNeuron(neuron);
                    System.out.println("Bias neuron removed");
                }
            }
        }
    }

    /**
     * Adds a random outgoing connection from the neuron
     * @param neuron
     * @param network
     * @param i the current layer number
     */
    private static void addConnection(Neuron neuron, NeuralNetwork network, int i) {
        int size = neuron.getOutConnections().size();
        for (int j = i + 1; j < network.getLayers().size(); j++) {
            Layer layer = network.getLayerAt(j);
            for (Neuron neuron1: layer.getNeurons()) {
                if (addConnectionChance(size)) {
                    neuron1.addInputConnection(neuron);
                }
            }
        }
    }

    /**
     * Mutates the given connection (changes weight)
     * @param connection
     */
    private static void mutateConnection(Connection connection) {
        connection.getWeight().inc(WEIGHT_DELTA * 2 * (Math.random() - 1/2));
    }

    /**
     * Returns a random item from the list
     * @param list the list to get a random item from
     * @param <T> Whatever type is in the list
     * @return
     */
    private static <T> T randomList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }

    /**
     * Randomly chooses whether a neuron should be removed or not
     * @param size current number of neurons
     * @return
     */
    private static boolean removeNeuronChance(int size) {
        return Math.random() < REMOVE_NEURON_CHANCE/(7-size);
    }

    /**
     * Randomly chooses whether a neuron should be added or not
     * @param size current number of neurons
     * @return
     */
    private static boolean addNeuronChance(int size) {
        return Math.random() < ADD_NEURON_CHANCE/(size + 0.2);
    }

    /**
     * Randomly chooses whether a connection should be removed or not
     * @param size current number of connections
     * @return
     */
    private static boolean removeConnectionChance(int size) {
        return Math.random() < ADD_CONNECTION_CHANCE/(4.00001 - size);
    }

    /**
     * Randomly chooses whether a connection should be added or not
     * @param size current number of connections
     * @return
     */
    private static boolean addConnectionChance(int size) {
        return Math.random() < REMOVE_CONNECTION_CHANCE/(size + 0.2);
    }



}
