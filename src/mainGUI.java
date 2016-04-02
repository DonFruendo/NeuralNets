import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * NeuralNets
 * Created by Don on 31.03.2016.
 */
public class mainGUI extends JFrame {
    public static mainGUI instance;
    public static mainGUI getInstance() {
        return instance;
    }

    private JPanel consolePanel;
    private JPanel inputPanel;
    private JPanel sliderLabelPanel;
    private JPanel mainPanel;
    private JSlider sliderLeft;
    private JSlider sliderFrontLeft;
    private JSlider sliderFront;
    private JSlider sliderFrontRight;
    private JSlider sliderRight;
    private JTextArea console;
    private JLabel labelLeft;
    private JLabel labelFrontLeft;
    private JLabel labelFront;
    private JLabel labelFrontRight;
    private JLabel labelRight;
    private JPanel outputPanel;
    private JLabel outForward;
    private JLabel outBackwards;
    private JLabel outRight;
    private JLabel outLeft;
    private JButton learnStuffButton;


    private final double MAX_SLIDER_INPUT = 1;
    private final static NetController netController = NetController.getInstance();

    public static void main(String[] args) {
        instance = new mainGUI("mainGUI");
        instance.setContentPane(instance.mainPanel);
        instance.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        instance.pack();
        instance.setVisible(true);

        netController.createNet();
    }

    private mainGUI(String caption) {
        super(caption);
        sliderLeft.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateNetController();
            }
        });
        sliderFrontLeft.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateNetController();
            }
        });
        sliderFront.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateNetController();
            }
        });
        sliderFrontRight.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateNetController();
            }
        });
        sliderRight.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                updateNetController();
            }
        });
        learnStuffButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleQuestion learnDialog = new SimpleQuestion();
                learnDialog.pack();
                learnDialog.setVisible(true);
            }
        });
    }

    private void updateNetController() {
        ArrayList<Double> netValues = getValues();
        labelLeft.setText(netValues.get(0) + "");
        labelFrontLeft.setText(netValues.get(1) + "");
        labelFront.setText(netValues.get(2) + "");
        labelFrontRight.setText(netValues.get(3) + "");
        labelRight.setText(netValues.get(4) + "");
        netController.updateInputs(netValues);
        netController.calculateNet();
    }

    public void setValues(ArrayList<Double> values) {
        final double factor = 1000;
        outForward.setText(Math.round(values.get(0)*factor)/factor + "");
        outBackwards.setText(Math.round(values.get(1)*factor)/factor + "");
        outRight.setText(Math.round(values.get(2)*factor)/factor + "");
        outLeft.setText(Math.round(values.get(3)*factor)/factor + "");
    }

    public ArrayList<Double> getValues() {
        ArrayList<Double> result = new ArrayList<>();
        result.add(sliderLeft.getValue() / MAX_SLIDER_INPUT);
        result.add(sliderFrontLeft.getValue() / MAX_SLIDER_INPUT);
        result.add(sliderFront.getValue() / MAX_SLIDER_INPUT);
        result.add(sliderFrontRight.getValue() / MAX_SLIDER_INPUT);
        result.add(sliderRight.getValue() / MAX_SLIDER_INPUT);

        return result;
    }

    public void addToConsole(String line) {
        console.setText(line + "\n" + console.getText());
    }
}
