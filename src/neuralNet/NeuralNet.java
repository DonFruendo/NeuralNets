package neuralNet;

import java.util.Vector;

/**
 * Neural Net
 *
 * Capable of learning
 *
 * Created by Don on 06.04.2016.
 */
public class NeuralNet {
    Vector<NeuronLayer> vecLayers = new Vector<>();

    public NeuralNet() {
        createNet();
    }

    public void createNet() {
        NeuronLayer layer = new NeuronLayer();
        for(int i = 0; i < Params.neuronsPerHiddenLayer; i++) {
            layer.vecNeurons.add(new Neuron(Params.inputs));
            layer.numNeurons = Params.neuronsPerHiddenLayer;
        }
        vecLayers.add(layer);

        for(int l = 0; l < Params.hiddenLayers; l++) {
            layer = new NeuronLayer();
            for(int i = 0; i < Params.neuronsPerHiddenLayer; i++) {
                layer.vecNeurons.add(new Neuron(Params.neuronsPerHiddenLayer));
                layer.numNeurons = Params.neuronsPerHiddenLayer;
            }
            vecLayers.add(layer);
        }

        layer = new NeuronLayer();
        for(int i = 0; i < Params.outputNeurons; i++) {
            layer.vecNeurons.add(new Neuron(Params.neuronsPerHiddenLayer));
            layer.numNeurons = Params.outputNeurons;
        }
        vecLayers.add(layer);

    }

    public Vector<Double> getWeights() {
        return null;
    }

    public int getNumberOfWeights() {
        return -1;
    }

    public void putWeights(Vector<Double> newWeights) {
        int weightCounter = 0;
        int neuronCounter = 0;
        int layerCounter = 0;

        for(int i = 0; i < newWeights.size(); i++) {
            vecLayers.get(layerCounter).vecNeurons.get(neuronCounter).vecWeights.set(weightCounter, newWeights.get(i));
            weightCounter++;
            if(     ((layerCounter == 0)
                        &&
                    (weightCounter == Params.inputs))
                ||
                    ((layerCounter != 0)
                            &&
                    (weightCounter == Params.neuronsPerHiddenLayer))
                ){
                weightCounter = 0;
                neuronCounter++;
                if(neuronCounter == Params.neuronsPerHiddenLayer) {
                    neuronCounter = 0;
                    layerCounter++;
                }
            }
        }
    }

    public Vector<Double> update(Vector<Double> inputs) {
        Vector<Double> outputs = null;
        int cWeight;

        if(inputs.size() != Params.inputs) {
            return outputs;
        }
        outputs = new Vector();

        for(int i = 0; i < Params.hiddenLayers+2; i++) {
            if(i > 0) {
                inputs = (Vector<Double>)outputs.clone();
            }

            outputs.clear();
            cWeight = 0;


            for(int j = 0; j < vecLayers.get(i).numNeurons; j++) {
                double netInput = 0;
                int n_numInputs = vecLayers.get(i).vecNeurons.get(j).numInputs;

                for(int k = 0; k < n_numInputs-1; k++) {
                    netInput += vecLayers.get(i).vecNeurons.get(j).vecWeights.get(k) * inputs.get(cWeight++);
                }

                netInput += vecLayers.get(i).vecNeurons.get(j).vecWeights.get(n_numInputs - 1) * Params.bias;
                //netInput = sigmoid(netInput);

                outputs.add(netInput);
                cWeight = 0;
            }
        }

        return outputs;
    }

    private static final double sigmoid(double input) {
        return 1 / (1 + Math.exp(-input));
    }



    // ----- Private Classes -----

    private class Neuron {
        int numInputs;
        Vector<Double> vecWeights;

        public Neuron(int inputs) {
            numInputs = inputs+1;
            vecWeights = new Vector<>();
            for(int i = 0; i < inputs+1; i++) {
                vecWeights.add((Math.random()*2) - 1);
            }
        }
    }

    private class NeuronLayer {
        int numNeurons;
        Vector<Neuron> vecNeurons = new Vector<>();
    }
}
