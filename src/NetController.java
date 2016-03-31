import neuralNet.Neuron;
import neuralNet.Synapse;

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

    private NetController() {

    }

    public void createNet() {
        Neuron n0 = new Neuron();
        Neuron n1 = new Neuron();
        Neuron n2 = new Neuron();

        Synapse s1 = new Synapse(n1, n2, 0.5);
        Synapse s2 = new Synapse(n0, n2, 10);

        n0.setInput(0.04);
        n1.setInput(10);

        System.out.println(n2.getSignal());
    }
}
