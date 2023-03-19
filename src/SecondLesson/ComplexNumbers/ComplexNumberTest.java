package SecondLesson.ComplexNumbers;

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumberImpl firstComplexNumber = new ComplexNumberImpl(2.0);// Пример создания экземпляра класса с 1 параметром.
        System.out.println(firstComplexNumber);

        ComplexNumberImpl secondComplexNumber = new ComplexNumberImpl(5.0, 7.0);
        ComplexNumberImpl thirdComplexNumber = new ComplexNumberImpl(-3.0, 9.0);

        ComplexNumberImpl testAmount = secondComplexNumber.amount(thirdComplexNumber);
        System.out.println("a + b = " + testAmount);

        ComplexNumberImpl testSubtract = secondComplexNumber.subtract(thirdComplexNumber);
        System.out.println("a - b = " + testSubtract);

        ComplexNumberImpl testMultiply = secondComplexNumber.multiply(thirdComplexNumber);
        System.out.println("a * b = " + testMultiply);

        double moduloSecondComplexNumber = secondComplexNumber.getModulo();
        System.out.println("|a| = " + moduloSecondComplexNumber);

        double moduloThirdComplexNumber = thirdComplexNumber.getModulo();
        System.out.println("|b| = " + moduloThirdComplexNumber);
    }
}
