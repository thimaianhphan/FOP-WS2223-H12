package h12.h1;

import h12.json.LookaheadReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class TutorTests_H1_2_LookaheadReaderTest {

    @ParameterizedTest
    @CsvSource("012345")
    public void testLookaheadReader(String input) throws IOException {

        StringReader reader = new StringReader(input);
        LookaheadReader lookaheadReader = new LookaheadReader(new BufferedReader(reader));

        for (int i = 0; i < input.length() + 1; i++) {
            int expected = i < input.length() ? input.charAt(i) : -1;

            for (int j = 0; j < input.length() - i; j++) {
                testPeek(lookaheadReader, expected, i, input);
            }

            testRead(lookaheadReader, expected, i, input);
        }

        lookaheadReader.close();
    }

    @Test
    public void testEmptyLookaheadReader() throws IOException {
        LookaheadReader lookaheadReader = new LookaheadReader(new BufferedReader(new StringReader("")));

        testPeek(lookaheadReader, -1, 0, "empty String");
        testRead(lookaheadReader, -1, 0, "empty String");

        lookaheadReader.close();
    }

    private void testPeek(LookaheadReader reader, int expected, int amount, String input) throws IOException {
        assertEquals(charToString(expected), charToString(reader.peek()),
            contextBuilder().add("inputSequence", input).subject("LookaheadReader#peek()").build(),
            TR -> "Method did not return the correct value when read() has already been called %d times"
                .formatted(amount));
    }

    private void testRead(LookaheadReader reader, int expected, int amount, String input) throws IOException {
        assertEquals(charToString(expected), charToString(reader.read()),
            contextBuilder().add("inputSequence", input).subject("LookaheadReader#read").build(),
            TR -> "Method did not return the correct value when read() has already been called %d times"
                .formatted(amount));
    }

    private String charToString(int integer) {
        if (integer == -1) return "-1";
        return Character.toString(integer);
    }

}
