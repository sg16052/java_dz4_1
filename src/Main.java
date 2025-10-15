import java.util.Scanner;

// Абстрактный класс операции
abstract class Operation<T extends Number> {
    protected T a;
    protected T b;

    public Operation(T a, T b) {
        this.a = a;
        this.b = b;
    }

    // Абстрактный метод выполнения операции
    public abstract double calculate();
}

// Класс для сложения
class Addition<T extends Number> extends Operation<T> {
    public Addition(T a, T b) {
        super(a, b);
    }

    @Override
    public double calculate() {
        return a.doubleValue() + b.doubleValue();
    }
}

// Класс для вычитания
class Subtraction<T extends Number> extends Operation<T> {
    public Subtraction(T a, T b) {
        super(a, b);
    }

    @Override
    public double calculate() {
        return a.doubleValue() - b.doubleValue();
    }
}

// Класс для умножения
class Multiplication<T extends Number> extends Operation<T> {
    public Multiplication(T a, T b) {
        super(a, b);
    }

    @Override
    public double calculate() {
        return a.doubleValue() * b.doubleValue();
    }
}

// Класс для деления
class Division<T extends Number> extends Operation<T> {
    public Division(T a, T b) {
        super(a, b);
    }

    @Override
    public double calculate() {
        if (b.doubleValue() == 0) {
            throw new ArithmeticException("Ошибка: обнаружено деление на ноль");
        }
        return a.doubleValue() / b.doubleValue();
    }
}

// Класс для целочисленного деления
class intDivision<T extends Number> extends Operation<T> {
    public intDivision(T a, T b) {
        super(a, b);
    }

    @Override
    public double calculate() {
        if (b.intValue() == 0) {
            throw new ArithmeticException("Ошибка: обнаружено деление на ноль");
        }
        return (int) (a.intValue() / b.intValue());
    }
}

// Класс для возведения в степень
class Exponentiation<T extends Number> extends Operation<T> {
    public Exponentiation(T a, T b) {
        super(a, b);
    }

    @Override
    public double calculate() {
        return Math.pow(a.doubleValue(), b.doubleValue());
    }
}

// Класс для вычисления остатка от деления
class Reminder<T extends Number> extends Operation<T> {
    public Reminder(T a, T b) {
        super(a, b);
    }

    @Override
    public double calculate() {
        if (b.doubleValue() == 0) {
            throw new ArithmeticException("Ошибка: обнаружено деление на ноль");
        }
        return a.doubleValue() % b.doubleValue();
    }
}

// Класс валидации выражения
class ExprValidator {

    // Проверка корректности ввода
    public boolean isValidInput(String[] parts) {
        return parts.length == 3 &&
                isNumber(parts[0]) &&
                isNumber(parts[2]) &&
                isValidOperator(parts[1]);
    }

    // Проверка, является ли строка числом
    private static boolean isNumber(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Проверка корректности оператора
    private static boolean isValidOperator(String operator) {
        return operator.equals("+") ||
                operator.equals("-") ||
                operator.equals("*") ||
                operator.equals("/") ||
                operator.equals("//") ||
                operator.equals("^") ||
                operator.equals("%");
    }

}

// Класс калькулятора
class Calculator {
    private Operation<? extends Number> operation;

    //Сеттер для класса
    public void setOperation(Operation<? extends Number> operation) {
        this.operation = operation;
    }

    public double calculate() {
        return operation.calculate();
    }
}

public class Main {
    public static void main(String[] args) {

        //Объявление переменных для считывания строки и выхода
        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;

        //Создание экземпляров классов калькулятора и валидатора
        Calculator calculator = new Calculator();
        ExprValidator validator = new ExprValidator();

        // Вывод информации о поддерживаемых операциях
        System.out.println("Калькулятор поддерживает операции:");
        System.out.println("+ - сложение");
        System.out.println("- - вычитание");
        System.out.println("* - умножение");
        System.out.println("/ - деление");
        System.out.println("// - целочисленное деление");
        System.out.println("^ - возведение в степень");
        System.out.println("% - остаток от деления");
        System.out.println("Для выхода введите 'exit' ");

        // Цикл, пока не будет введена команда exit
        while (isRunning) {
            System.out.print("Введите выражение: ");
            String input = scanner.nextLine();

            // Проверка команды выхода
            if (input.equalsIgnoreCase("exit")) {
                isRunning = false;
                continue;
            }

            // Разделение выражения на операнды и операцию
            String[] parts = input.split("\\s+");

            // Проверка корректности выражения
            if (!validator.isValidInput(parts)) {
                System.out.println("Неверное выражение. Введите еще раз.");
                continue;
            }

            double num1 = Double.parseDouble(parts[0]);
            String operator = parts[1];
            double num2 = Double.parseDouble(parts[2]);
            // Выполнение операции
            try {
                Operation operation = getOperation(operator, num1, num2);
                calculator.setOperation(operation);
                double result = calculator.calculate();
                System.out.println("Результат: " + result);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Выход...");
        scanner.close();

    }

    //Метод для операций
    private static Operation getOperation(String operator, double num1, double num2) {
        Operation operation;
        switch (operator) {
            case "+" -> operation = new Addition(num1, num2);
            case "-" -> operation = new Subtraction(num1, num2);
            case "*" -> operation = new Multiplication(num1, num2);
            case "/" -> operation = new Division(num1, num2);
            case "//" -> operation = new intDivision((int) num1, (int) num2);
            case "^" -> operation = new Exponentiation(num1, num2);
            case "%" -> operation = new Reminder((int) num1, (int) num2);
            default -> throw new IllegalArgumentException("Некорректная операция");
        }
        return operation;
    }
}