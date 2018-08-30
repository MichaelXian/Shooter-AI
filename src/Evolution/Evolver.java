package Evolution;

import org.neuroph.core.NeuralNetwork;
import Game.Game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Evolver {
    private final int POPULATION_SIZE = 10;
    private final String FILEPATH = "NeuralNets/net";
    private final String EXTENSION = ".nnet";
    List<NeuralNetwork> networks;
    List<NeuralNetwork> children;
    MatchMaker matchMaker;
    Iterator<List<NeuralNetwork>> iterator;
    Selection selection;

    public Evolver() {
        loadNets();
        matchMaker = new MatchMaker(networks);
        iterator = matchMaker.iterator();
        selection = new Selection(networks);
        children = new ArrayList<>();
    }

    /**
     * Loads the neural networks from the NeuralNets folder
     */
    private void loadNets() {
        for (int i = 0; i < POPULATION_SIZE; i ++) {
            networks.add(NeuralNetwork.createFromFile(FILEPATH + i + EXTENSION));
        }
    }

    /**
     * Does evolution and increments the generation if there are no more matches for this generation, then returns the
     * next matchup
     */
    private List<NeuralNetwork> next(Game game, List<NeuralNetwork> currentMatchup) {
        selection.grade(currentMatchup, game);
        if (!iterator.hasNext()) {
            evolution();
            resetEvolver();
        }
        return iterator.next(); // after resetting iterator, it will have a next
    }

    /**
     * Creates a new selection, matchmaker, and iterator (for the new neural networks after evolution())
     */
    private void resetEvolver() {
        selection = new Selection(networks);
        matchMaker = new MatchMaker(networks); // resets iterator
        iterator = matchMaker.iterator();
    }

    /**
     * Selects, then crossover/mutates
     */
    private void evolution() {
        networks = selection.select((int) POPULATION_SIZE/2);
        repopulate();
        mutate();
    }

    /**
     * Crossovers to create children until POPULATION_SIZE has been reached
     */
    private void repopulate() {
        while (networks.size() + children.size() < POPULATION_SIZE) {
            children.add(Crossover.breed(networks));
        }
        networks.addAll(children);
    }


    /**
     * Mutates all networks
     */
    private void mutate() {
        for (NeuralNetwork network: networks) {
            Mutator.mutate(network);
        }
    }


}
