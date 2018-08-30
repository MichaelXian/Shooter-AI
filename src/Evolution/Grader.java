package Evolution;

import org.neuroph.core.NeuralNetwork;
import Game.Game;

import java.util.*;

public class Grader {
    private Map<NeuralNetwork, Integer> grades;
    private List<NeuralNetwork> networks;
    
    Grader(List<NeuralNetwork> networks) {
        grades = new HashMap<>();
        this.networks = networks;
        for (NeuralNetwork neuralnetwork: networks) {
            grades.put(neuralnetwork, 0);
        }
    }

    /**
     * Grades the performance of the two neural networks by the outcome of the game
     * @param net1
     * @param net2
     * @param game
     */
    public void Grade(NeuralNetwork net1, NeuralNetwork net2, Game game) {
        Integer scoreChange1 = 0;
        Integer scoreChange2 = 0;
        if (game.getWinner() == Game.FIRST_WIN) {
            scoreChange2 = -1;
            scoreChange1 = game.isKilled() ? 1 : 0;
        } else if (game.getWinner() == Game.SECOND_WIN) {
            scoreChange1 = -1;
            scoreChange1 = game.isKilled() ? 1 : 0;
        }
        grades.replace(net1, grades.get(net1) + scoreChange1);
        grades.replace(net2, grades.get(net2) + scoreChange2);
    }

    /**
     * Returns a sorted list of neural networks, from lowest performance to highest. If there are multiple with the same
     * grade, then the first one to appear in networks is put first.
     * @return
     */
    public List<NeuralNetwork> Sort() {
        List<NeuralNetwork> ret = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        for (NeuralNetwork network: networks) {
            values.add(grades.get(network));
        }
        Collections.sort(values);
        for (Integer i:  values) {
            for (NeuralNetwork network : networks) {
                if (grades.get(network) == i) {
                    ret.add(network);
                }
            }
        }
        return ret;
    }

}
