package UI;

import Controllers.NeuralNetwork.NeuralNetworkFactory;
import org.neuroph.core.NeuralNetwork;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class NewGenOneNetworks {
    static NeuralNetwork neuralNetwork;

    /**
     * Creates new gen1 neural networks, and resets the generation to 1
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 100; i ++) {
            neuralNetwork = NeuralNetworkFactory.randomNeuralNet();
            neuralNetwork.save("NeuralNets/net" + i + ".nnet");
        }

        try {
            File generationFile = new File(ShooterAI.GENERATION_FILE_PATH);
            PrintWriter writer = new PrintWriter(generationFile);
            writer.print(1);
            writer.close();
        } catch (FileNotFoundException e) {
            throw new NullPointerException("File not found");
        }

        //neuralNetwork = NeuralNetworkFactory.emptyNeuralNet();
        //neuralNetwork.save("NeuralNets/net2.nnet");
    }


}
