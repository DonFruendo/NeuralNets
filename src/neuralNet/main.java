package neuralNet;

import javax.swing.*;
import java.util.Vector;

/**
 * Created by Don on 06.04.2016.
 */
public class main extends JFrame{
    private JTextArea console;
    private JPanel mainPanel;
    private JScrollPane consolePane;
    private JLabel consoleLabel;

    private String consoleText;


    private static main instance = new main("Neural Net");

    public static main getInstance() {return instance;}

    public static void main(String[] args) {
        instance.setContentPane(instance.mainPanel);
        instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        instance.pack();
        instance.setVisible(true);

        NeuralNet nn = new NeuralNet();


        Vector<Double> vec = new Vector<>();/*
        vec.add(0.3);
        vec.add(-0.8);
        vec.add(-0.2);

        vec.add(0.6);
        vec.add(0.1);
        vec.add(-0.1);

        vec.add(0.4);
        vec.add(0.5);//*/

        vec.add(0.62);
        vec.add(0.55);
        vec.add(0.29);

        vec.add(0.42);
        vec.add(-0.17);
        vec.add(0.1);


        vec.add(0.35);
        vec.add(0.81);
        vec.add(-0.73);


        instance.addToConsole(vec.toString());

        //nn.putWeights(vec, true);

        Vector<Double> input = new Vector<>();
        input.add(0.);
        input.add(1.);
        //input.add(1.);

        Vector<Double> output = nn.update(input);
        instance.addToConsole(output.toString());

        Vector<Double> vecOutput = new Vector<>();
        vecOutput.add(0.);
        instance.addToConsole(nn.backpropagation(input, vecOutput, Params.uniformErrorRate).toString());


        Vector<Double> input0 = new Vector<>();
        input0.add(0.);
        input0.add(1.);
        Vector<Double> output0 = new Vector<>();
        output0.add(0.);
        Vector<Vector<Double>> data0 = new Vector<>();
        data0.add(input0);
        data0.add(output0);

        Vector<Double> input1 = new Vector<>();
        input1.add(1.);
        input1.add(1.);
        Vector<Double> output1 = new Vector<>();
        output1.add(1.);
        Vector<Vector<Double>> data1 = new Vector<>();
        data1.add(input1);
        data1.add(output1);

        Vector<Vector<Vector<Double>>> trainingData = new Vector<>();
        trainingData.add(data0);
        trainingData.add(data1);

        nn.train(trainingData, 0.05, 0.5);
        instance.addToConsole("Done");
    }

    public main(String title) {
        super(title);
    }

    public void addToConsole(String line) {
        consoleText = line + "\n" + consoleText;
    }

    public void updateConsole() {
        console.setText(consoleText);
    }
}
