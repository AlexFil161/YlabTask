package SecondLesson.ComplexNumbers;

public interface ComplexNumber {

    ComplexNumberImpl amount(ComplexNumberImpl other);

    ComplexNumberImpl subtract(ComplexNumberImpl other);

    ComplexNumberImpl multiply(ComplexNumberImpl other);

    double getModulo();
}
