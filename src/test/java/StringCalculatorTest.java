import Calculator.StringCalculator;
import Extras.InvalidInputException;
import Extras.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Mockito stubbing: Telling the mock what to return when it gets called later on
 * Verify: Verify that a method call was made. If it wasn't, the test will fail.
 *
 * In the session, someone suggested that we add to the validator to check if it contains a '+'
 * This is okay for the current requirement, but later on, we add substraction, so a valid sum
 * might not contain a +. It might instead contain a -.
 *
 * I had the validator return a boolean so I could demonstrate stubbing. A better solution might be
 * to make the validator void, and to have it throw an exception if it is invalid.
 */
@RunWith(MockitoJUnitRunner.class)
public class StringCalculatorTest {

    @Mock
    private Validator validator;

    @InjectMocks
    StringCalculator calculator;

    @Test
    public void shouldCallValidator() throws InvalidInputException {
        when(validator.isValid("1")).thenReturn(true);
        calculator.calculate("1");
        verify(validator).isValid(anyString());
    }

    @Test
    public void shouldThrowException_whenValidatorReturnsFalse() {
        assertThrows(InvalidInputException.class, () -> calculator.calculate(""));
    }

    @Test
    public void shouldHandleSingleNumber() throws InvalidInputException {
        when(validator.isValid("1")).thenReturn(true);
        int result = calculator.calculate("1");
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void shouldHandleAnotherSingleNumber() throws InvalidInputException {
        when(validator.isValid("2")).thenReturn(true);
        int result = calculator.calculate("2");
        assertThat(result).isEqualTo(2);
    }

    @Test
    public void shouldHandleAnotherTwoDigitNumber() throws InvalidInputException {
        when(validator.isValid("78")).thenReturn(true);
        int result = calculator.calculate("78");
        assertThat(result).isEqualTo(78);
    }

    @Test
    public void shouldHandleANumberWithSpaces() throws InvalidInputException {
        when(validator.isValid("345 ")).thenReturn(true);
        int result = calculator.calculate("345 ");
        assertThat(result).isEqualTo(345);
    }

    @Test
    public void shouldHandleTwoNumbers() throws InvalidInputException {
        String num = "2+3";
        when(validator.isValid(num)).thenReturn(true);
        int result = calculator.calculate(num);
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void shouldHandleTwoNumbersWithSpaces() throws InvalidInputException {
        String num = "4 + 5";
        when(validator.isValid(num)).thenReturn(true);
        int result = calculator.calculate(num);
        assertThat(result).isEqualTo(9);
    }

    @Test
    public void shouldHandleMultipleAdditionsInSum() throws InvalidInputException {
        when(validator.isValid("2+4+5")).thenReturn(true);
        int result = calculator.calculate("2+4+5");
        assertThat(result).isEqualTo(11);
    }

    @Test
    public void shouldHandleMultipleAdditionsWithDoubleDigitNumbersInSum() throws InvalidInputException {
        when(validator.isValid("2+45+575")).thenReturn(true);
        int result = calculator.calculate("2+45+575");
        assertThat(result).isEqualTo(622);
    }

    @Test
    public void shouldHandleMultipleAdditionsWithSpacesInSum() throws InvalidInputException {
        when(validator.isValid("7 + 6 + 32")).thenReturn(true);
        int result = calculator.calculate("7 + 6 + 32");
        assertThat(result).isEqualTo(45);
    }

    @Test
    public void shouldHandleMultipleOperationsInSum() throws InvalidInputException {
        when(validator.isValid("10 - 8 / 4")).thenReturn(true);
        int result = calculator.calculate("10 - 8 / 4");
        assertThat(result).isEqualTo(8);
    }

    @Test
    public void shouldHandlePowersInSum() throws InvalidInputException {
        when(validator.isValid("10 ^ 1 / 2")).thenReturn(true);
        int result = calculator.calculate("10 ^ 1 / 2");
        assertThat(result).isEqualTo(5);
    }

    @Test
    public void shouldHandleMorePowersInSum() throws InvalidInputException {
        when(validator.isValid("10 ^ 3 / 2")).thenReturn(true);
        int result = calculator.calculate("10 ^ 3 / 2");
        assertThat(result).isEqualTo(500);
    }

}