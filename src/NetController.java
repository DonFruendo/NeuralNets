import neuralNet.Neuron;
import neuralNet.Synapse;

import java.util.ArrayList;

/**
 * NetController
 *
 * Single Controller over a NeuralNet, consisting of Neurons and Synapses
 *
 * Created by Don on 31.03.2016.
 */
public class NetController {
    private static NetController ourInstance = new NetController();

    public static NetController getInstance() {
        return ourInstance;
    }


    private ArrayList<ArrayList<Neuron>> allLayers = new ArrayList<>();
    private ArrayList<Synapse> allSynapses = new ArrayList<>();

    private NetController() {

    }

    public void createNet() {
        ArrayList<Integer> layerSizes = new ArrayList<>();
        layerSizes.add(5);
        layerSizes.add(4);
        createMeshedNet(layerSizes);
    }

    private ArrayList<Neuron> getInputNeurons() {
        return allLayers.get(0);
    }

    private ArrayList<Neuron> getOutputNeurons() {
        return allLayers.get(allLayers.size()-1);
    }

    public ArrayList<Synapse> getAllSynapses() { return allSynapses; }

    private void createMeshedNet(ArrayList<Integer> layerSizes) {
        if(layerSizes.size() >= 2) {
            for(int layer = 0; layer < layerSizes.size(); layer++) {
                ArrayList<Neuron> layerList = new ArrayList<>();
                for(int i = 0; i < layerSizes.get(layer); i++) {
                    Neuron neuron;
                    if(layer == 0) {
                        neuron = new Neuron("IN"+ i);
                    } else if(layer == layerSizes.size()-1) {
                        neuron = new Neuron("OUT" + i);
                    } else {
                        neuron = new Neuron();
                    }
                    if(layer != 0) {
                        for(Neuron lowerLayerNeuron : allLayers.get(layer - 1)) {
                            Synapse synapse = new Synapse(lowerLayerNeuron, neuron, 0.0008);
                            allSynapses.add(synapse);
                        }
                    }
                    layerList.add(neuron);
                }
                allLayers.add(layerList);
            }
        }
    }

    public void updateInputs(ArrayList<Double> inputs) {
        ArrayList<Neuron> inputLayer = allLayers.get(0);
        if(inputs.size() == inputLayer.size()) {
            for (int i = 0; i < inputLayer.size(); i++) {
                Neuron neuron = inputLayer.get(i);
                neuron.setInput(inputs.get(i));
            }
        }
    }

    public ArrayList<Double> calculateNet() {
        ArrayList<Double> results = new ArrayList<>();
        for(Neuron outNeuron : allLayers.get(allLayers.size()-1)) {
            results.add(outNeuron.getSignal());
        }
        mainGUI.getInstance().setValues(results);
        return results;
    }

    public void learn(ArrayList<Double> inputs, String expectedWinner) {
        // TODO Improve the learning method
        for(int loop = 0; loop < 10; loop++) {
            // find the highest values
            ArrayList<Neuron> highest = new ArrayList<>();
            double highValue = Double.MIN_VALUE;
            ArrayList<Double> results = calculateNet();
            for (int i = 0; i < results.size(); i++) {
                if (highValue < results.get(i)) {
                    highest = new ArrayList<>();
                    highValue = results.get(i);
                }
                if(highValue == results.get(i))
                    highest.add(getOutputNeurons().get(i));
                }

            for(Neuron neuron : highest) {
                // is it correct?
                if (expectedWinner.equals(neuron.getID())) {
                    // if yes, encourage the participating synapses
                    neuron.giveTreats();
                } else {
                    // if no, discourage the participating synapses
                    neuron.giveSours();
                }
            }
        }
    }
}
