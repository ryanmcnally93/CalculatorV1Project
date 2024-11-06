package Calculator;
import Extras.InvalidInputException;
import Extras.Validator;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RunCalculator {

    public static void main(String[] args) throws InvalidInputException {
        StringCalculator newcalc = new StringCalculator(new Validator());
        System.out.println("These prove single numbers return as they are");
        System.out.println("2");
        System.out.println("24");
        System.out.println("234");
        System.out.println("These prove one operand works");
        System.out.println("4 + 2 = " + newcalc.calculate("4+2")); // 6
        System.out.println("4 * 2 = " + newcalc.calculate("4*2")); // 8
        System.out.println("4 - 2 = " + newcalc.calculate("4-2")); // 2
        System.out.println("4 / 2 = " + newcalc.calculate("4/2")); // 2
        System.out.println("These prove more than one operand works");
        System.out.println("3 * 4 - 2 = " + newcalc.calculate("3*4-2")); // 10
        System.out.println("8 / 4 + 6 = " + newcalc.calculate("8/4+6")); // 8
        System.out.println("2 * 4 + 2 - 4 = " + newcalc.calculate("2*4+2-4")); // 6
        System.out.println("These prove that BODMAS is in operation");
        System.out.println("10 - 4 * 2 = " + newcalc.calculate("10-4*2")); // 2
        System.out.println("8 / 4 + 6 * 3 = " + newcalc.calculate("8/4+6*3")); // 20
        System.out.println("1 + 2^3 * 4 - 2 = " + newcalc.calculate("1+2^3*4-10")); //23
    }
}
