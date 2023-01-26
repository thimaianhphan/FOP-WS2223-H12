package h12.h2;

import h12.json.JSONObject;
import h12.json.implementation.node.JSONNumberNode;
import h12.json.implementation.node.JSONObjectNode;
import h12.json.implementation.node.JSONStringNode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.ArrayList;
import java.util.List;

import static h12.json.JSONObject.JSONObjectEntry;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

@TestForSubmission
public class TutorTests_H2_WriteJSONObjectTest extends TutorTests_WriteJSONTest {

    @ParameterizedTest
    @CsvSource("a, 1, b, 2, c, 3, 1")
    public void testWriteJSONObject(String k1, int v1, String k2, int v2, String k3, int v3, int indentation) throws Throwable {
        JSONObjectNode.JSONObjectEntryNode mockedJSONObjectEntry1 = createMockedJSONObjectEntry(new JSONStringNode(k1), new JSONNumberNode(v1));
        JSONObjectNode.JSONObjectEntryNode mockedJSONObjectEntry2 = createMockedJSONObjectEntry(new JSONStringNode(k2), new JSONNumberNode(v2));
        JSONObjectNode.JSONObjectEntryNode mockedJSONObjectEntry3 = createMockedJSONObjectEntry(new JSONStringNode(k3), new JSONNumberNode(v3));

        JSONObject object = JSONObject.of(
            mockedJSONObjectEntry1,
            mockedJSONObjectEntry2,
            mockedJSONObjectEntry3
        );

        String actual = getActual(object, indentation);

        verify(mockedJSONObjectEntry1, times(1)).write(any(), anyInt());
        verify(mockedJSONObjectEntry2, times(1)).write(any(), anyInt());
        verify(mockedJSONObjectEntry3, times(1)).write(any(), anyInt());


        StringBuilder entryIndentation = new StringBuilder("  ");
        StringBuilder endIndentation = new StringBuilder();

        for (int i = 0; i < indentation; i++) {
            entryIndentation.append("  ");
            endIndentation.append("  ");
        }

        boolean withoutWhitespaceEqual = false;
        String closestExpected = "";

        List<JSONObjectEntry> entries = new ArrayList<>(object.getObjectEntries());
        for (List<JSONObjectEntry> permutation : getAllPermutations(entries)) { //the order of the elements is not relevant
            String expected = "{\n%s\"%s\": %d,\n%s\"%s\": %d,\n%s\"%s\": %d\n%s}".formatted(
                entryIndentation, permutation.get(0).getIdentifier(), permutation.get(0).getValue().getInteger(),
                entryIndentation, permutation.get(1).getIdentifier(), permutation.get(1).getValue().getInteger(),
                entryIndentation, permutation.get(2).getIdentifier(), permutation.get(2).getValue().getInteger(),
                endIndentation
            );

            if (expected.equals(actual)) return;

            if (expected.replace(" ", "").replace("\n", "")
                .equals(actual.replace(" ", "").replace("\n", ""))) {
                withoutWhitespaceEqual = true;
                closestExpected = expected;
            }

            if (!withoutWhitespaceEqual) {
                closestExpected = expected;
            }
        }

        Context context = contextBuilder()
            .add("input", closestExpected)
            .add("indentation", indentation)
            .subject("JSONObjectNode#write(BufferedWriter, int)")
            .build();

        String message = "Methode did not write the correct String to the BufferedWriter.";

        if (withoutWhitespaceEqual) {
            message += " The contents of the expected and actual json element are equal but the whitespaces do not match.";
        }

        message += "\nNote that the order of the object entries does not matter";

        String finalMessage = message;
        assertEquals(createJSONString(closestExpected), createJSONString(actual), context, TR -> finalMessage);
    }

    private static <T> List<List<T>> getAllPermutations(List<T> input) {
        if (input.size() <= 1) return new ArrayList<>(List.of(input));

        List<List<T>> result = new ArrayList<>();
        for (T entry : input) {
            List<T> copy = new ArrayList<>(input);
            copy.remove(entry);
            List<List<T>> permutations = getAllPermutations(copy);
            for (List<T> permutation : permutations) {
                permutation.add(0, entry);
                result.add(permutation);
            }
        }
        return result;
    }

}


