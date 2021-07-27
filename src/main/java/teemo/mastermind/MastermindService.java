package teemo.mastermind;

import org.apache.commons.lang3.Validate;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class MastermindService {
    private String secret;

    private final String POSSIBLE_VALUES = "0123456789";
    private final Integer SECRET_SIZE = 4;

    public MastermindService() {};

    protected MastermindService(String secret) {
        this.secret = secret;
    }

    public void initializeSecret() {
        this.secret = IntStream.range(0, SECRET_SIZE)
                .mapToObj(number -> POSSIBLE_VALUES.charAt(new Random().nextInt(10)))
                .collect(Collector.of(
                        StringBuilder::new,
                        StringBuilder::append,
                        StringBuilder::append,
                        StringBuilder::toString));
    }

    public String compareInputWithSecret(String input) {
        Validate.notNull(input, "The input can't be null");
        Validate.isTrue(input.length() == SECRET_SIZE, "The input doesn't have the right length");

        var positionNotProperlyMatched = IntStream.range(0, SECRET_SIZE)
                .filter(position -> input.charAt(position) != secret.charAt(position))
                .boxed()
                .collect(Collectors.toList());

        var result = new StringBuilder();
        IntStream.range(0, SECRET_SIZE - positionNotProperlyMatched.size()).forEach(iteration -> result.append('+'));

        //Although mostly overkill for up to 4 digits, this remain the fastest way
        var inputCharacterOccurrence = new HashMap<Character, Integer>(SECRET_SIZE);
        positionNotProperlyMatched.forEach(position -> {
            var character = input.charAt(position);
            if (!inputCharacterOccurrence.containsKey(character)) {
                inputCharacterOccurrence.put(character, 0);
            }
            inputCharacterOccurrence.put(character, inputCharacterOccurrence.get(character) + 1);
        });

        var numberOfImproperlyMatched = positionNotProperlyMatched.stream()
                .map(position -> {
                    var character = secret.charAt(position);
                    if (inputCharacterOccurrence.containsKey(character)) {
                        inputCharacterOccurrence.put(character, inputCharacterOccurrence.get(character) - 1);
                        if (inputCharacterOccurrence.get(character) == 0) inputCharacterOccurrence.remove(character);
                        return true;
                    }
                    return false;
                })
                .filter(Boolean::booleanValue)
                .count();

        IntStream.range(0, (int) numberOfImproperlyMatched).forEach(iteration -> result.append('-'));

        return result.toString();
    }
}
