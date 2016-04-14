package neuralNet;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
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

    Vector<Vector<Double>> lastOutputs = new Vector<>();

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
        Vector<Double> weights = new Vector<>();
        for(NeuronLayer nL : vecLayers) {
            for(Neuron n : nL.vecNeurons) {
                for(Double weight : n.vecWeights) {
                    weights.add(weight);
                }
            }
        }
        return weights;
    }

    public Vector<String> getRoundedWeights() {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.GERMAN);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator(',');
        DecimalFormat df = new DecimalFormat("0.000", otherSymbols);
        Vector<Double> weights = getWeights();
        Vector<String> result = new Vector<>(weights.size());
        for(int i = 0; i < weights.size(); i++) {
            result.add(df.format(weights.get(i)));
        }
        return result;
    }

    public int getNumberOfWeights() {
        return -1;
    }

    public void putWeights(Vector<Double> newWeights) {
        putWeights(newWeights, false);
    }

    public void putWeights(Vector<Double> newWeights, boolean changeBiases) {
        int weightCounter = 0;
        int neuronCounter = 0;
        int layerCounter = 0;

        for(int i = 0; i < newWeights.size(); i++) {
            vecLayers.get(layerCounter).vecNeurons.get(neuronCounter).vecWeights.set(weightCounter, newWeights.get(i));
            weightCounter++;
            if(     changeBiases && (weightCounter == vecLayers.get(layerCounter).vecNeurons.get(neuronCounter).numInputs)
                ||
                    !changeBiases && (weightCounter == vecLayers.get(layerCounter).vecNeurons.get(neuronCounter).numInputs-1)
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
        lastOutputs = new Vector<>();
        Vector<Double> outputs = null;
        int cWeight;

        if(inputs.size() != Params.inputs) {
            return outputs;
        }
        outputs = new Vector<Double>();

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
                netInput = sigmoid(netInput);

                outputs.add(netInput);
                cWeight = 0;
            }

            lastOutputs.add((Vector<Double>)outputs.clone());
        }

        return outputs;
    }

    public Vector<Double> backpropagation(Vector<Double> input, Vector<Double> desiredOutput, double errorRate) {

        // Wenn nur ein Neuron output ist:

        Double actualOutput = update(input).elementAt(0);
        Double error = desiredOutput.elementAt(0) - actualOutput;

        main.getInstance().addToConsole("Test (" + input + " -> " + desiredOutput + ") [" + ((Math.abs(error)<errorRate)? "SUCCESS" : "FAIL") + "] Error: " + error);

        Vector<Double> testWeights = new Vector<>();

        for(int iLayer = vecLayers.size() - 1; iLayer >= 0; iLayer--) {
            for(int iNeuron = 0; iNeuron < vecLayers.get(iLayer).numNeurons; iNeuron++) { // TODO numInputs-1 schliesst bias aus..
                for(int iInput = 0; iInput < vecLayers.get(iLayer).vecNeurons.get(iNeuron).numInputs-1; iInput++) {
                    double oldWeight = vecLayers.get(iLayer).vecNeurons.get(iNeuron).vecWeights.get(iInput);
                    double output0;
                    if(iLayer == 0) {
                        output0 = input.get(iInput);
                    } else {
                        output0 = lastOutputs.get(iLayer - 1).get(iInput);
                    }
                    double output1 = lastOutputs.get(iLayer).get(iNeuron);
                    double output1Q = 1 - output1;
                    double newWeight = oldWeight + (Params.learningRate * error * output0 * output1 * output1Q);
                    testWeights.add(newWeight);

                    vecLayers.get(iLayer).vecNeurons.get(iNeuron).vecWeights.set(iInput, newWeight);
                }
            }
        }
        return testWeights;
    }

    public void train(Vector<Vector<Vector<Double>>> trainingData, double errorRate, double overallError) {
        int counter = 0;
        double actualErrorRate = Double.MAX_VALUE;
        while(actualErrorRate >= overallError) {
            int testCounter = 0;
            int failCounter = 0;

            main.getInstance().addToConsole(getRoundedWeights() + "\n");
            for (Vector<Vector<Double>> tupel : trainingData) {
                Vector<Double> input = tupel.get(0);
                Vector<Double> output = tupel.get(1);
                Vector<Double> actualOutput = update(input);

                boolean error = false;
                for(int i = 0; i < actualOutput.size(); i++) {
                    if(Math.abs(output.get(i) - actualOutput.get(i)) > errorRate)
                        error = true;
                }
                if(error)
                    failCounter++;
                testCounter++;
                backpropagation(input, output, errorRate);
            }

            actualErrorRate = failCounter / (testCounter * 1.);
            counter++;
            main.getInstance().addToConsole(failCounter + " of " + testCounter + " Tests failed (" + Math.round(actualErrorRate*10000)/100. + "%)");
            main.getInstance().addToConsole("Number of Test: " + counter);
            main.getInstance().updateConsole();


        }
    }

    private static double sigmoid(double input) {
        return 1 / (1 + Math.exp(-input));
    }

    private static final double sigmoidPrime(double input) {
        return sigmoid(input) * (1 - sigmoid(input));
    }

    private static final Vector<Double> sigmoidPrime(Vector<Double> input) {
        Vector<Double> result = new Vector<>();
        for(Double d : input) {
            result.add(sigmoidPrime(d));
        }
        return result;
    }



    // ----- Private Classes -----

    private class Neuron {
        int numInputs;
        Vector<Double> vecWeights; // incoming

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
