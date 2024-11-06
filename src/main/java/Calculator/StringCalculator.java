package Calculator;
import Extras.InvalidInputException;
import Extras.Validator;

public class StringCalculator {

    private final Validator validator;

    public StringCalculator(Validator validator) {
        this.validator = validator;
    }

    public int calculate(String sum) throws InvalidInputException {
        boolean isValid = validator.isValid(sum);
        if (!isValid) {
            throw new InvalidInputException("String is invalid");
        }

        // No operators in the String
        if (!sum.contains("+") && !sum.contains("*") && !sum.contains("/") && !sum.contains("-") && !sum.contains("^")) {
            return Integer.parseInt(sum.trim());
        }

        // Split numbers and operators
        String[] numbers = sum.split("[+\\-*/^]");
        String[] operators = sum.split("\\d+");

        // Evaluate based on precedence
        return resultAfterBodmasPriorities(numbers, operators);
    }

    private int resultAfterBodmasPriorities(String[] numbers, String[] operators) {

        int i = 1; // Operators array starts with an empty string, so we skip index 0
        while (i < operators.length) {
            String operator = operators[i].trim();
            if (operator.equals("^") || operator.equals("*") || operator.equals("/")) {
                int left = Integer.parseInt(numbers[i - 1].trim());
                int right = Integer.parseInt(numbers[i].trim());

                // Work out the first equation, this will change the length of the array (-1)
                // As well as return a result
                int intermediateResult = applyOperation(left, right, operator);

                // Update the number at i-1 with the result
                numbers[i - 1] = String.valueOf(intermediateResult);

                // If we've done 4 + 5, 4 has been changed to 9, now we need to push 5 to the end of the array
                for (int j = i; j < numbers.length - 1; j++) {
                    numbers[j] = numbers[j + 1];
                }
                for (int j = i; j < operators.length - 1; j++) {
                    operators[j] = operators[j + 1];
                }

                // And then pop it off the end
                numbers = java.util.Arrays.copyOf(numbers, numbers.length - 1);
                operators = java.util.Arrays.copyOf(operators, operators.length - 1);
            } else {
                i++;
            }
        }

        // Now, we process + and -
        int result = Integer.parseInt(numbers[0].trim());
        for (i = 1; i < operators.length; i++) {
            String operator = operators[i].trim();
            int nextNumber = Integer.parseInt(numbers[i].trim());
            result = applyOperation(result, nextNumber, operator);
        }

        return result;
    }

    private int applyOperation(int left, int right, String operator) {
        return switch (operator) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            case "/" -> left / right;
            case "^" -> (int) Math.pow(left, right);
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}