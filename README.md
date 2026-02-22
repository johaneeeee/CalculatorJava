# Java Scientific Calculator GUI 

A scientific calculator with graphical user interface built in Java Swing. This project demonstrates GUI programming, event handling, and data structures.

## ✨ Features

### Basic Operations
- Addition (+), Subtraction (-), Multiplication (×), Division (÷)
- Parentheses, decimal points, percentage (%)

### Scientific Functions
- **Trigonometry**: sin, cos, tan
- **Logarithms**: log (base 10), ln (natural log)
- **Power**: x², √ (square root), 1/x
- **Constants**: π (Pi), e (Euler's number)

### Advanced
- ✅ Full expression evaluation with operator precedence
- ✅ Error handling (division by zero, invalid input)
- ✅ Clean GUI with 5×6 button layout

## 📊 Data Structures Used

### Stack (Main Data Structure)
```java
Stack<String> operators = new Stack<>();  // For operators and functions
Stack<Double> values = new Stack<>();     // For numbers
```

**Why Stack?**
- Perfect for expression parsing (Shunting Yard algorithm)
- Handles operator precedence naturally
- LIFO behavior matches mathematical evaluation

## 🔍 Algorithms Implemented

### 1. Infix to Postfix Conversion
Converts human-readable expressions to computer-friendly format:
```
Input: 3 + 4 × 2
Output: 3 4 2 × +
```

### 2. Postfix Evaluation
Evaluates the expression using a stack:
```
1. Push numbers onto stack
2. When operator found, pop numbers and calculate
3. Push result back
4. Final result on stack
```

### 3. Operator Precedence
```java
+, - : Level 1 (lowest)
*, / : Level 2
^    : Level 3 (highest)
```

## 🎯 What I Learned

- **Java Swing** for GUI development
- **Event-driven programming** with ActionListener
- **Stack data structure** implementation
- **Expression parsing algorithms**
- **Exception handling** for errors
- **Object-oriented design** in Java

## 🚀 How to Run

```bash
javac Calculator_Project.java
java Calculator_Project
```

## 📁 Files
- `Calculator_Project.java` - Main source code
- `Report.pdf` - Project documentation

## 👤 Author
Johane (28/07/2024)
