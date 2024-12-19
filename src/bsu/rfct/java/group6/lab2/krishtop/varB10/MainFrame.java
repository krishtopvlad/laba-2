package bsu.rfct.java.group6.lab2.krishtop.varB10;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.util.function.Consumer;



// Главный класс приложения, он же класс фрейма
public class MainFrame extends JFrame {

    // Размеры окна приложения в виде констант
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;

    double memX;
    double memY;
    double memZ;
    int curMem=0;
    double[] mem= new double[3];




    private final JTextField textFieldX;
    private final JTextField textFieldY;
    private final JTextField textFieldZ;


    private final JTextField textField1;
    private final JTextField textField2;
    private final JTextField textField3;

    private final JTextField textFieldResult;

    // Группа радио-кнопок для обеспечения уникальности выделения в группе
    private final ButtonGroup radioButtons = new ButtonGroup();
    private final ButtonGroup radioButtons2 = new ButtonGroup();

    // Контейнер для отображения радио-кнопок
    private final Box hboxFormulaType = Box.createHorizontalBox();
    private final Box hboxMemType = Box.createHorizontalBox();
    private int formulaId = 1;



    // Вычисления функций
    public Double calculate1(Double x, Double y,Double z) {
        return (Math.sin(Math.PI*y*y)+Math.log(y*y))/(Math.sin(Math.PI*z*z)+Math.sin(x)+Math.log(z*z)+x*x+Math.exp(Math.cos(z*x)));

    }
    public Double calculate2(Double x, Double y, Double z) {
        return Math.pow(Math.sin(Math.pow(z,y)),2.0)/Math.pow(1+x*x*x,0.5);
    }

    public void updateTextField(int curMem, Consumer<JTextField> operation) {
        switch (curMem) {
            case 0:
                operation.accept(textField1);
                break;
            case 1:
                operation.accept(textField2);
                break;

            case 2:
                operation.accept(textField3);
                break;
            default:
                System.out.println("Oh no");
                break;
        }
    }



    private void addRadioButton(String buttonName, Box curBox, ButtonGroup bg, Consumer<ActionEvent> action) {
        JRadioButton button = new JRadioButton(buttonName);
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                action.accept(ev);
            }
        });
        bg.add(button);
        curBox.add(button);
    }

    // Конструктор класса
    public MainFrame() {
        super("Вычисление формулы");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        // Отцентрировать окно приложения на экране
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);

        hboxFormulaType.add(Box.createHorizontalGlue());
        addRadioButton("Формула 1", hboxFormulaType, radioButtons, ev->formulaId = 1);
        addRadioButton("Формула 2", hboxFormulaType, radioButtons, ev->formulaId = 2);




        radioButtons.setSelected(
                radioButtons.getElements().nextElement().getModel(), true);

        hboxFormulaType.add(Box.createHorizontalGlue());
        // Создать область с полями ввода для X и Y
        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("0", 25);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("0", 25);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("0", 25);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());
        Box hboxVariables = Box.createHorizontalBox();
        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(labelForX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldX);


        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(labelForY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldY);

        hboxVariables.add(labelForZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldZ);
        hboxVariables.add(Box.createHorizontalGlue());

        // Создать область для вывода результата
        JLabel labelForResult = new JLabel("Результат:");
        //labelResult = new JLabel("0");
        textFieldResult = new JTextField("0", 25);
        textFieldResult.setMaximumSize(
                textFieldResult.getPreferredSize());
        Box hboxResult = Box.createHorizontalBox();
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.add(labelForResult);
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(textFieldResult);
        hboxResult.add(Box.createHorizontalGlue());
        // Создать область для кнопок
        JButton buttonCalc = new JButton("Вычислить");
        JButton buttonReset = new JButton("Очистить поля");
        JButton buttonMSum = new JButton("M+");
        JButton buttonMC = new JButton("MC");


        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    Double x = Double.parseDouble(textFieldX.getText());
                    Double y = Double.parseDouble(textFieldY.getText());
                    Double z = Double.parseDouble(textFieldZ.getText());
                    Double result;
                    if (formulaId==1)
                        result = calculate1(x, y, z);
                    else
                        result = calculate2(x, y, z);

                    textFieldResult.setText(result.toString());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldResult.setText("0");

            }
        });




        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalStrut(30));
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());


        // memory
        Box Mbuttonbox = Box.createHorizontalBox();

        buttonMSum.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                Double x = Double.parseDouble(textFieldX.getText());
                Double y = Double.parseDouble(textFieldY.getText());
                Double z = Double.parseDouble(textFieldZ.getText());
                Double result;
                if (formulaId==1)
                    result = calculate1(x, y,z);
                else
                    result = calculate2(x, y,z);

                mem[curMem]+=result;
                updateTextField(curMem, textField -> {
                    double currentValue = mem[curMem];
                    textField.setText(Double.toString(currentValue));
                });

            };
        });

        buttonMC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                mem[curMem]=0;
                updateTextField(curMem, textField -> {
                    textField.setText("0");
                });
            };
        });

        Mbuttonbox.add(buttonMC);
        Mbuttonbox.add(buttonMSum);


        addRadioButton("Переменная 1", hboxMemType, radioButtons2, ev-> curMem=0);
        addRadioButton("Переменная 2", hboxMemType, radioButtons2, ev-> curMem=1);
        addRadioButton("Переменная 3", hboxMemType, radioButtons2, ev-> curMem=2);


        Box MemBox = Box.createHorizontalBox();

        JLabel labelFor1 = new JLabel("Mem1");
        textField1 = new JTextField("0", 25);
        textField1.setMaximumSize(textField1.getPreferredSize());

        JLabel labelFor2 = new JLabel("Mem2");
        textField2 = new JTextField("0", 25);
        textField2.setMaximumSize(textField2.getPreferredSize());

        JLabel labelFor3 = new JLabel("Mem3");
        textField3 = new JTextField("0", 25);
        textField3.setMaximumSize(textField3.getPreferredSize());

        MemBox.add(labelFor1);
        MemBox.add(Box.createHorizontalStrut(10));
        MemBox.add(textField1);
        MemBox.add(Box.createHorizontalStrut(10));


        MemBox.add(Box.createHorizontalGlue());

        MemBox.add(labelFor2);
        MemBox.add(Box.createHorizontalStrut(10));
        MemBox.add(textField2);

        MemBox.add(Box.createHorizontalGlue());

        MemBox.add(labelFor3);
        MemBox.add(Box.createHorizontalStrut(10));
        MemBox.add(textField3);


        radioButtons2.setSelected(
                radioButtons2.getElements().nextElement().getModel(), true);



        // Связать области воедино в компоновке BoxLayout
        Box contentBox = Box.createVerticalBox();

        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxFormulaType);
        contentBox.add(hboxVariables);
        contentBox.add(hboxResult);
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(Mbuttonbox);
        contentBox.add(hboxMemType);
        contentBox.add(MemBox);
        contentBox.add(hboxButtons);

        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    // Главный метод класса
    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

}