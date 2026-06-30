// Import tools for colors, fonts, margins, and window layouts
import java.awt.*;
// Import tools to listen for mouse clicks on the buttons
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// Import tools to create buttons, text fields, and window frames
import javax.swing.*;

public class CalculatorEngine {
    // Shared memory boxes for storing numbers and operators
    static double num1 = 0.0;
    static double num2 = 0.0;
    static double result = 0.0;
    static String opr = "";
    static boolean isOperatorClicked = false;

    public static void main(String[] args) {
        // 1. WINDOW FRAME SETUP
        JFrame frame = new JFrame();
        frame.setTitle("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(370, 600);
        frame.setLocation(100, 100);
        frame.setResizable(false);

        // 2. CONTAINER LAYER SETUP
        Container c = frame.getContentPane(); // Give me access to the inner canvas board (c) so I can start sticking my
                                              // calculator buttons and screen onto it.
        c.setLayout(null); // Keep using manual pixel coordinates (No layout grids)
        c.setBackground(new Color(32, 32, 44)); // Dark background matching the Windows style

        // 3. DISPLAY SCREEN (The Text Field)
        JTextField t1 = new JTextField();
        t1.setBounds(15, 30, 325, 70);
        t1.setText("0"); // Starts at 0
        t1.setFont(new Font("Segoe UI", Font.BOLD, 42));
        t1.setHorizontalAlignment(JTextField.RIGHT); // Numbers align to the far right edge
        t1.setEditable(false);
        t1.setBackground(new Color(32, 32, 44));
        t1.setForeground(Color.WHITE);
        c.add(t1); // Drop the screen onto our canvas

        // 4. STYLE PRESETS FOR BUTTONS
        Font btnFont = new Font("Segoe UI", Font.PLAIN, 18);
        Color opColor = new Color(43, 43, 56);
        Color numColor = new Color(53, 53, 67);
        Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);

        // ACTION WIRE 1: FOR ALL NUMBER KEYS (0-9)
        // This is the code that listens for clicks on your number buttons and figures
        // out exactly which number you just pressed.
        ActionListener numberListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clickedNumber = e.getActionCommand();

                // Is the text screen (t1) currently showing just a single "0"?
                // OR did the user just click a math operator like +?
                if (t1.getText().equals("0") || isOperatorClicked) {
                    // if the screen said 0 and you click 7, the screen instantly changes to 7.
                    // We also turn off the isOperatorClicked flag so the computer knows we are
                    // actively typing a number now.
                    t1.setText(clickedNumber);
                    isOperatorClicked = false; // Reset flag
                } else {
                    t1.setText(t1.getText() + clickedNumber); // Glue numbers together side-by-side
                }
            }
        };

        // // ACTION WIRE 2: FOR STANDARD MATH OPERATORS (+, -, *, /)
        // this is the brain of operators

        ActionListener operatorListener = new ActionListener() {
            @Override // doesn't change how your code works it is a safety feature that acts like a
                      // spellcheck to make sure your calculator buttons connect perfectly to your
                      // backend code!
            public void actionPerformed(ActionEvent e) {
                // Convert text on screen to a number and save to num1. The screen holds text
                // digits like "78".
                // You cannot do math on text words. This line reads the text "78", converts it
                // into a real math decimal number (78.0),
                // and stores it safely inside your global variable box named num1
                num1 = Double.parseDouble(t1.getText());

                // This checks exactly which symbol button you clicked. It grabs the text
                // written on the button face (like "×" or "–").
                String label = e.getActionCommand();

                // Map visual button symbols to math logic strings
                if (label.equals("×"))
                    opr = "*";
                else if (label.equals("–"))
                    opr = "-";
                else if (label.equals("÷"))
                    opr = "/";
                else
                    opr = label;

                isOperatorClicked = true; // Flag screen to clear on next number input click
            }
        };

        // ROW 1: This row handles your layout setup for the top utility keys: %
        // (Percentage), CE (Clear Entry), C (Master Clear), and ⌫ (Backspace).

        // for percentage button
        JButton btnPerc = new JButton("%");
        btnPerc.setBounds(15, 130, 75, 60);
        btnPerc.setFont(btnFont);
        btnPerc.setBackground(opColor);
        btnPerc.setForeground(Color.WHITE);
        btnPerc.setBorderPainted(false);
        btnPerc.setContentAreaFilled(false);
        btnPerc.setOpaque(true); // Make this button completely solid. Stop letting Windows blend its defaul colors or styles over it.
        btnPerc.setCursor(handCursor);
        btnPerc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(t1.getText());
                t1.setText(String.valueOf(value / 100.0));
            }
        });
        c.add(btnPerc); // is the line that actually places the button onto your window so you can see it.

        // for clear entry button (CE)
        JButton btnCE = new JButton("CE");
        btnCE.setBounds(95, 130, 75, 60);
        btnCE.setFont(btnFont);
        btnCE.setBackground(opColor);
        btnCE.setForeground(Color.WHITE);
        btnCE.setBorderPainted(false);
        btnCE.setContentAreaFilled(false);
        btnCE.setOpaque(true);
        btnCE.setCursor(handCursor);
        btnCE.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // CE only clears the number currently on the screen.
                // It leaves num1 and the arithmetic operator safe in background memory!
                t1.setText("0");
            }
        });
        c.add(btnCE);

        // for clear button (C)
        JButton btnC = new JButton("C");
        btnC.setBounds(175, 130, 75, 60);
        btnC.setFont(btnFont);
        btnC.setBackground(opColor);
        btnC.setForeground(Color.WHITE);
        btnC.setBorderPainted(false);
        btnC.setContentAreaFilled(false);
        btnC.setOpaque(true);
        btnC.setCursor(handCursor);
        btnC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t1.setText("0");
                num1 = 0;
                num2 = 0;
                result = 0;
                opr = "";
            }
        });
        c.add(btnC);

        // for backspace button
        JButton btnBack = new JButton("⌫");
        btnBack.setBounds(255, 130, 85, 60);
        btnBack.setFont(btnFont);
        btnBack.setBackground(opColor);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setOpaque(true);
        btnBack.setCursor(handCursor);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String currentText = t1.getText();
                if (currentText.length() > 1 && !currentText.equals("Error")) {
                    t1.setText(currentText.substring(0, currentText.length() - 1));
                } else {
                    t1.setText("0");
                }
            }
        });
        c.add(btnBack); // is the line that actually places the button onto your window so you can see it.

        // ROW 2: 1/x, x², √x, ÷ (Y coordinate drops to 195)

        JButton btnRecip = new JButton("¹/x");
        btnRecip.setBounds(15, 195, 75, 60);
        btnRecip.setFont(btnFont);
        btnRecip.setBackground(opColor);
        btnRecip.setForeground(Color.WHITE);
        btnRecip.setBorderPainted(false);
        btnRecip.setContentAreaFilled(false);
        btnRecip.setOpaque(true);
        btnRecip.setCursor(handCursor);
        btnRecip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(t1.getText());
                if (value != 0) {
                    t1.setText(String.valueOf(1.0 / value));
                } else {
                    t1.setText("Error");
                }
            }
        });
        c.add(btnRecip);

        JButton btnSq = new JButton("x²");
        btnSq.setBounds(95, 195, 75, 60);
        btnSq.setFont(btnFont);
        btnSq.setBackground(opColor);
        btnSq.setForeground(Color.WHITE);
        btnSq.setBorderPainted(false);
        btnSq.setContentAreaFilled(false);
        btnSq.setOpaque(true);
        btnSq.setCursor(handCursor);
        btnSq.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(t1.getText());
                t1.setText(String.valueOf(value * value));
            }
        });
        c.add(btnSq);

        JButton btnSqrt = new JButton("²√x");
        btnSqrt.setBounds(175, 195, 75, 60);
        btnSqrt.setFont(btnFont);
        btnSqrt.setBackground(opColor);
        btnSqrt.setForeground(Color.WHITE);
        btnSqrt.setBorderPainted(false);
        btnSqrt.setContentAreaFilled(false);
        btnSqrt.setOpaque(true);
        btnSqrt.setCursor(handCursor);
        btnSqrt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(t1.getText());
                if (value >= 0) {
                    t1.setText(String.valueOf(Math.sqrt(value)));
                } else {
                    t1.setText("Error");
                }
            }
        });
        c.add(btnSqrt);

        JButton btnDiv = new JButton("÷");
        btnDiv.setBounds(255, 195, 85, 60);
        btnDiv.setFont(btnFont);
        btnDiv.setBackground(opColor);
        btnDiv.setForeground(Color.WHITE);
        btnDiv.setBorderPainted(false);
        btnDiv.setContentAreaFilled(false);
        btnDiv.setOpaque(true);
        btnDiv.setCursor(handCursor);
        btnDiv.addActionListener(operatorListener);
        c.add(btnDiv);

        // ROW 3: Keys 7, 8, 9, × (Y coordinate drops to 260)

        JButton btn7 = new JButton("7");
        btn7.setBounds(15, 260, 75, 60);
        btn7.setFont(btnFont);
        btn7.setBackground(numColor);
        btn7.setForeground(Color.WHITE);
        btn7.setCursor(handCursor);
        btn7.setBorderPainted(false);
        btn7.setContentAreaFilled(false);
        btn7.setOpaque(true);
        btn7.addActionListener(numberListener);
        c.add(btn7);

        JButton btn8 = new JButton("8");
        btn8.setBounds(95, 260, 75, 60);
        btn8.setFont(btnFont);
        btn8.setBackground(numColor);
        btn8.setForeground(Color.WHITE);
        btn8.setCursor(handCursor);
        btn8.setBorderPainted(false);
        btn8.setContentAreaFilled(false);
        btn8.setOpaque(true);
        btn8.addActionListener(numberListener);
        c.add(btn8); // final step of physically gluing the button onto your user interface.

        JButton btn9 = new JButton("9");
        btn9.setBounds(175, 260, 75, 60);
        btn9.setFont(btnFont);
        btn9.setBackground(numColor);
        btn9.setForeground(Color.WHITE);
        btn9.setBorderPainted(false);
        btn9.setContentAreaFilled(false);
        btn9.setOpaque(true);
        btn9.setCursor(handCursor);
        btn9.addActionListener(numberListener);
        c.add(btn9);

        JButton btnMult = new JButton("×");
        btnMult.setBounds(255, 260, 85, 60);
        btnMult.setFont(btnFont);
        btnMult.setBackground(opColor);
        btnMult.setForeground(Color.WHITE);
        btnMult.setBorderPainted(false);
        btnMult.setContentAreaFilled(false);
        btnMult.setOpaque(true);
        btnMult.setCursor(handCursor);
        btnMult.addActionListener(operatorListener);
        c.add(btnMult);

        // ROW 4: Keys 4, 5, 6, – (Y coordinate drops to 325) is a positioning
        JButton btn4 = new JButton("4");
        btn4.setBounds(15, 325, 75, 60);
        btn4.setFont(btnFont);
        btn4.setBackground(numColor);
        btn4.setForeground(Color.WHITE);
        btn4.setBorderPainted(false);
        btn4.setContentAreaFilled(false);
        btn4.setOpaque(true);
        btn4.setCursor(handCursor);
        btn4.addActionListener(numberListener);
        c.add(btn4);

        JButton btn5 = new JButton("5");
        btn5.setBounds(95, 325, 75, 60);
        btn5.setFont(btnFont);
        btn5.setBackground(numColor);
        btn5.setForeground(Color.WHITE);
        btn5.setBorderPainted(false);
        btn5.setContentAreaFilled(false);
        btn5.setOpaque(true);
        btn5.setCursor(handCursor);
        btn5.addActionListener(numberListener);
        c.add(btn5);

        JButton btn6 = new JButton("6");
        btn6.setBounds(175, 325, 75, 60);
        btn6.setFont(btnFont);
        btn6.setBackground(numColor);
        btn6.setForeground(Color.WHITE);
        btn6.setBorderPainted(false);
        btn6.setContentAreaFilled(false);
        btn6.setOpaque(true);
        btn6.setCursor(handCursor);
        btn6.addActionListener(numberListener);
        c.add(btn6);

        JButton btnSub = new JButton("–");
        btnSub.setBounds(255, 325, 85, 60);
        btnSub.setFont(btnFont);
        btnSub.setBackground(opColor);
        btnSub.setForeground(Color.WHITE);
        btnSub.setBorderPainted(false);
        btnSub.setContentAreaFilled(false);
        btnSub.setOpaque(true);
        btnSub.setCursor(handCursor);
        btnSub.addActionListener(operatorListener);
        c.add(btnSub);

        // ROW 5: Keys 1, 2, 3, + (Y coordinate drops to 390) is a positioning
        JButton btn1 = new JButton("1");
        btn1.setBounds(15, 390, 75, 60);
        btn1.setFont(btnFont);
        btn1.setBackground(numColor);
        btn1.setForeground(Color.WHITE);
        btn1.setBorderPainted(false);
        btn1.setContentAreaFilled(false);
        btn1.setOpaque(true);
        btn1.setCursor(handCursor);
        btn1.addActionListener(numberListener);
        c.add(btn1);

        JButton btn2 = new JButton("2");
        btn2.setBounds(95, 390, 75, 60);
        btn2.setFont(btnFont);
        btn2.setBackground(numColor);
        btn2.setForeground(Color.WHITE);
        btn2.setBorderPainted(false);
        btn2.setContentAreaFilled(false);
        btn2.setOpaque(true);
        btn2.setCursor(handCursor);
        btn2.addActionListener(numberListener);
        c.add(btn2);

        JButton btn3 = new JButton("3");
        btn3.setBounds(175, 390, 75, 60);
        btn3.setFont(btnFont);
        btn3.setBackground(numColor);
        btn3.setForeground(Color.WHITE);
        btn3.setBorderPainted(false);
        btn3.setContentAreaFilled(false);
        btn3.setOpaque(true);
        btn3.setCursor(handCursor);
        btn3.addActionListener(numberListener);
        c.add(btn3);

        JButton btnPlus = new JButton("+");
        btnPlus.setBounds(255, 390, 85, 60);
        btnPlus.setFont(btnFont);
        btnPlus.setBackground(opColor);
        btnPlus.setForeground(Color.WHITE);
        btnPlus.setBorderPainted(false);
        btnPlus.setContentAreaFilled(false);
        btnPlus.setOpaque(true);
        btnPlus.setCursor(handCursor);
        btnPlus.addActionListener(operatorListener);
        c.add(btnPlus);

        // ROW 6: +/-, 0, ., = (Y coordinate drops to 455) is a positioning
        JButton btnSign = new JButton("+/–");
        btnSign.setBounds(15, 455, 75, 60);
        btnSign.setFont(btnFont);
        btnSign.setBackground(numColor);
        btnSign.setForeground(Color.WHITE);
        btnSign.setBorderPainted(false);
        btnSign.setContentAreaFilled(false);
        btnSign.setOpaque(true);
        btnSign.setCursor(handCursor);
        btnSign.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double value = Double.parseDouble(t1.getText());
                t1.setText(String.valueOf(value * -1));
            }
        });
        c.add(btnSign);

        JButton btn0 = new JButton("0");
        btn0.setBounds(95, 455, 75, 60);
        btn0.setFont(btnFont);
        btn0.setBackground(numColor);
        btn0.setForeground(Color.WHITE);
        btn0.setBorderPainted(false);
        btn0.setContentAreaFilled(false);
        btn0.setOpaque(true);
        btn0.setCursor(handCursor);
        btn0.addActionListener(numberListener);
        c.add(btn0);

        JButton btnDot = new JButton(".");
        btnDot.setBounds(175, 455, 75, 60);
        btnDot.setFont(btnFont);
        btnDot.setBackground(numColor);
        btnDot.setForeground(Color.WHITE);
        btnDot.setBorderPainted(false);
        btnDot.setContentAreaFilled(false);
        btnDot.setOpaque(true);
        btnDot.setCursor(handCursor);
        btnDot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!t1.getText().contains(".")) {
                    t1.setText(t1.getText() + ".");
                }
            }
        });
        c.add(btnDot);

        JButton btnEquals = new JButton("=");
        btnEquals.setBounds(255, 455, 85, 60);
        btnEquals.setFont(btnFont);
        btnEquals.setCursor(handCursor);
        btnEquals.setBackground(new Color(118, 185, 237)); 
        btnEquals.setForeground(Color.BLACK); 
        btnEquals.setBorderPainted(false);
        btnEquals.setContentAreaFilled(false);
        btnEquals.setOpaque(true);
        btnEquals.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                num2 = Double.parseDouble(t1.getText());
                boolean validCalculation = true;

                switch (opr) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 == 0) {
                            t1.setText("Error");
                            validCalculation = false;
                        } else {
                            result = num1 / num2;
                        }
                        break;
                    default:
                        validCalculation = false;
                }

                if (validCalculation) {
                    t1.setText(String.valueOf(result));
                }
            }
        });
        c.add(btnEquals);
        frame.setVisible(true);
    }
}