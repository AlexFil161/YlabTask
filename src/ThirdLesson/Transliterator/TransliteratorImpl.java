package ThirdLesson.Transliterator;

import java.util.Map;

import static java.util.Map.entry;

public class TransliteratorImpl implements Transliterator {

    private final Map<String, String> alphabet = Map.ofEntries(
            entry("А", "A"), entry("Б", "B"), entry("В", "V"),
            entry("Г", "G"), entry("Д", "D"), entry("Е", "E"),
            entry("Ё", "E"), entry("Ж", "ZH"), entry("З", "Z"),
            entry("И", "I"), entry("Й", "I"), entry("К", "K"),
            entry("Л", "L"), entry("М", "M"), entry("Н", "N"),
            entry("О", "O"), entry("П", "P"), entry("Р", "R"),
            entry("С", "S"), entry("Т", "T"), entry("У", "U"),
            entry("Ф", "F"), entry("Х", "KH"), entry("Ц", "TS"),
            entry("Ч", "CH"), entry("Ш", "SH"), entry("Щ", "SHCH"),
            entry("Ы", "Y"), entry("Ь", ""), entry("Ъ", "IE"),
            entry("Э", "E"), entry("Ю", "IU"), entry("Я", "IA")
    );

    @Override
    public String transliterate(String source) {

        char[] input = source.toCharArray();
        StringBuilder result = new StringBuilder();
        for (char c : input) {
            if (alphabet.containsKey(String.valueOf(c))) {
                String value = alphabet.get(String.valueOf(c));
                result.append(value);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }
}
