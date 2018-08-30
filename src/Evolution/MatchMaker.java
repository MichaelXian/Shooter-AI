package Evolution;

import org.neuroph.core.NeuralNetwork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Matches every neural net against every other one by keeping track of 2 lists, and iterating over them
 */
public class MatchMaker implements Iterable<List<NeuralNetwork>>{
    List<NeuralNetwork> networks;
    List<NeuralNetwork> networks2;
    MatchMaker(List<NeuralNetwork> networks) {
        networks = networks;
        // Create a clone of networks, but without the last element
        networks2 = new ArrayList<>();
        networks2.addAll(networks);
        networks2.remove(networks.get(networks.size()-1));
    }

    @Override
    public Iterator<List<NeuralNetwork>> iterator() {
        return new MatchIterator();
    }

    private class MatchIterator implements Iterator<List<NeuralNetwork>> {
        Iterator<NeuralNetwork> iterator1;
        Iterator<NeuralNetwork> iterator2;
        NeuralNetwork currentFirst;
        NeuralNetwork currentSecond;
        MatchIterator() {
            iterator1 = networks.iterator();
            iterator2 = networks2.iterator();

        }

        @Override
        public boolean hasNext() {
            return iterator1.hasNext() || iterator2.hasNext();
        }

        @Override
        public List<NeuralNetwork> next() {
            ArrayList ret = new ArrayList();
            if (currentFirst == null) { // If first call, then initialize currents
                currentFirst = iterator1.next();
                currentSecond = iterator2.next();
            } else {
                if (iterator2.hasNext()) { // If second iterator still has next, then no need to increment first
                    currentSecond = iterator2.next();
                    if (currentFirst == currentSecond) { // If they are the same neural net, next again
                        next();
                    }
                } else { // Due to hasNext() iterator1 must have a next
                    currentFirst = iterator1.next();
                    iterator2 = networks2.iterator();
                    currentSecond = iterator2.next();
                }
            }
            ret.add(currentFirst);
            ret.add(currentSecond);
            return ret;
        }
    }
}
