/*
_ 28/07/2024 _ 

Name: Johane
Purpose: Calculator GUI in Java

*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Stack;

public class Calculator_Project extends JFrame {

    private JTextField display; 
    private StringBuilder expressionBuilder; // To build and store the current expression

    public Calculator_Project() {
        super("Calculator");
        setSize(600, 400); 

        expressionBuilder = new StringBuilder(); // Initialize the expression builder

        // Create and configure the display
        display = new JTextField();
        display.setEditable(false); 
        display.setHorizontalAlignment(JTextField.RIGHT); 
        display.setColumns(6); // Set the number of columns
        display.setFont(new Font("Arial", Font.PLAIN, 45)); 

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 6, 2, 2)); // 5 rows, 6 columns, with 2-pixel gaps

        // Create operation buttons and add action listeners
        createButton(panel, "C", e -> {
            display.setText(""); 
            expressionBuilder.setLength(0); // Clear the expression builder
        });

        // Add buttons for different functions and operations
        // First row
        createButton(panel, "\u221A"); // √
        createButton(panel, "(");
        createButton(panel, ")");
        createButton(panel, "%");
        createButton(panel, "\u00F7"); // ÷

        // Second row
        createButton(panel, "Sin");
        createButton(panel, "Cos");
        createButton(panel, "7");
        createButton(panel, "8");
        createButton(panel, "9");
        createButton(panel, "\u00D7"); // ×

        // Third row
        createButton(panel, "Ln");
        createButton(panel, "1/x");
        createButton(panel, "4");
        createButton(panel, "5");
        createButton(panel, "6");
        createButton(panel, "-");

        // Fourth row
        createButton(panel, "Tan");
        createButton(panel, "log");
        createButton(panel, "1");
        createButton(panel, "2");
        createButton(panel, "3");
        createButton(panel, "+");

        // Fifth row
        createButton(panel, "x\u00B2"); // x^2
        createButton(panel, "e", e -> {
            expressionBuilder.append(Math.E); // Append e to expression
            display.setText(expressionBuilder.toString()); // Update display
        });
        createButton(panel, "\u03C0", e -> {
            expressionBuilder.append(Math.PI); // Append Pi to expression
            display.setText(expressionBuilder.toString()); // Update display
        });
        createButton(panel, "0");
        createButton(panel, ".");
        createButton(panel, "=", e -> {
            try {
                String expression = expressionBuilder.toString(); // Get the current expression
                double result = evaluateExpression(expression); // Evaluate the expression
                display.setText(String.format("%.2f", result)); // Display the result
                expressionBuilder.setLength(0); // Clear the expression builder
            } catch (Exception ex) {
                display.setText("Error"); 
            }
        });


        add(display, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Method to create a button and add it to the panel with default action
    private void createButton(JPanel panel, String label) {

        JButton button = new JButton(label); // Create a new button
        button.setFont(new Font("Arial", Font.PLAIN, 20)); // Set button font size
        button.setBackground(Color.WHITE); // Set button background color
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                String text = button.getText(); 

                // Convert button text to corresponding expression part

                if (text.equals("\u00D7")) {
                    text = "*";
                } else if (text.equals("\u00F7")) {
                    text = "/";
                } else if (text.equals("Sin")) {
                    text = "sin(";
                } else if (text.equals("Cos")) {
                    text = "cos(";
                } else if (text.equals("Ln")) {
                    text = "ln(";
                } else if (text.equals("Tan")) {
                    text = "tan(";
                } else if (text.equals("log")) {
                    text = "log(";
                } else if (text.equals("\u221A")) { // √
                    text = "sqrt(";
                } else if (text.equals("x\u00B2")) { // x^2
                    text = "^2";
                } else if (text.equals("1/x")) { // 1/x
                    text = "recip(";
                }
                expressionBuilder.append(text); // Append text to expression builder
                display.setText(expressionBuilder.toString()); // Update display
            }
        });
        panel.add(button); // Add button to the panel
    }

    // Method to create a button with a custom action listener
    private void createButton(JPanel panel, String label, ActionListener listener) {
        JButton button = new JButton(label);                  
        button.setFont(new Font("Arial", Font.PLAIN, 20));     
        button.setBackground(Color.WHITE);                    
        button.addActionListener(listener);                  
        panel.add(button);                                  
    }

    // Method to evaluate a mathematical expression
    private double evaluateExpression(String expression) {
        expression = expression.replaceAll(" ", ""); // Remove spaces
        String postfix = infixToPostfix(expression); // Convert to postfix notation
        return evaluatePostfix(postfix); // Evaluate postfix expression
    }

    // Method to convert infix expression to postfix notation
    private String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<String> operators = new Stack<>();

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);

            if (Character.isDigit(ch) || ch == '.') {
                while (i < infix.length() && (Character.isDigit(infix.charAt(i)) || infix.charAt(i) == '.')) {
                    postfix.append(infix.charAt(i++));
                }
                postfix.append(' '); // Separate numbers
                i--;
            } else if (ch == '(') {
                operators.push(String.valueOf(ch)); // Push '(' to stack
            } else if (ch == ')') {
                while (!operators.isEmpty() && !operators.peek().equals("(")) {
                    postfix.append(operators.pop()).append(' '); // Pop until '('
                }
                operators.pop(); // Remove '('
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '^') {
                while (!operators.isEmpty() && precedence(ch) <= precedence(operators.peek().charAt(0))) {
                    postfix.append(operators.pop()).append(' '); // Pop operators with higher or equal precedence
                }
                operators.push(String.valueOf(ch)); // Push current operator
            } else if (Character.isLetter(ch)) {
                StringBuilder func = new StringBuilder();
                while (i < infix.length() && (Character.isLetter(infix.charAt(i)) || infix.charAt(i) == '^')) {
                    func.append(infix.charAt(i++)); // Collect function name
                }
                i--; // Adjust for next character
                operators.push(func.toString()); // Push function to stack
            }
        }

        while (!operators.isEmpty()) {
            postfix.append(operators.pop()).append(' '); // Pop remaining operators
        }

        return postfix.toString();
    }

    // Method to get the precedence of operators
    private int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    // Method to evaluate postfix expression
    private double evaluatePostfix(String postfix) {
        Stack<Double> stack = new Stack<>();
        String[] tokens = postfix.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            if (Character.isDigit(token.charAt(0)) || token.charAt(0) == '.') {
                stack.push(Double.parseDouble(token)); // Push number to stack
            } else {
                switch (token) {
                    case "sin":
                        double angle = stack.pop();
                        stack.push(Math.sin(Math.toRadians(angle))); // Calculate sin
                        break;
                    case "cos":
                        angle = stack.pop(); 
                        stack.push(Math.cos(Math.toRadians(angle))); // Calculate cos
                        break;
                    case "tan":
                        angle = stack.pop();
                        stack.push(Math.tan(Math.toRadians(angle))); // Calculate tangent
                        break;
                    case "ln":
                        stack.push(Math.log(stack.pop())); // Calculate natural logarithm
                        break;
                    case "log":
                        stack.push(Math.log10(stack.pop())); // Calculate base-10 logarithm
                        break;
                    case "sqrt":
                        stack.push(Math.sqrt(stack.pop())); // Calculate square root
                        break;
                    case "recip":
                        stack.push(1 / stack.pop()); // Calculate reciprocal
                        break;
                    case "^2":
                        stack.push(Math.pow(stack.pop(), 2)); // Calculate square of the number
                        break;
                    default:
                        double b = stack.pop(); // Get second operand
                        double a = stack.pop(); // Get first operand
                        switch (token.charAt(0)) {
                            case '+':
                                stack.push(a + b); // Addition
                                break;
                            case '-':
                                stack.push(a - b); // Subtraction
                                break;
                            case '*':
                                stack.push(a * b); // Multiplication
                                break;
                            case '/':
                                if (b == 0) {
                                    System.out.println("Cannot divide by zero");
                                }
                                stack.push(a / b); // Division
                                break;
                            case '^':
                                stack.push(Math.pow(a, b)); // Power
                                break;
                            default:
                                System.out.println("Unsupported operator: " + token); // Handle unsupported operators
                        }
                        break;
                }
            }
        }

        return stack.pop(); 
    }

    public static void main(String[] args) {
        new Calculator_Project();  
   }
}
