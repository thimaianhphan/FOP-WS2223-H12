package h12.h2;

import h12.json.JSONElement;
import h12.json.implementation.node.JSONNumberNode;
import h12.json.implementation.node.JSONStringNode;
import org.junit.jupiter.api.function.ThrowingConsumer;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;

import static h12.json.implementation.node.JSONObjectNode.JSONObjectEntryNode;
import static org.mockito.Mockito.spy;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.assertEquals;
import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.contextBuilder;

public class TutorTests_WriteJSONTest {
    public void testWriteJSONNode(JSONElement element, String expected, int indentation, ThrowingConsumer<JSONElement> verifier) throws Throwable {
        Context context = contextBuilder()
            .add("input", expected)
            .add("indentation", indentation)
            .subject(element.getClass().getSimpleName() + "#write(BufferedWriter, int)")
            .build();

        String actual = getActual(element, indentation);

        if (verifier != null) verifier.accept(element);

        assertEquals(expected, actual, context,
            TR -> "Method did not write the correct String to the BufferedWriter");
    }

    public String getActual(JSONElement element, int indentation) throws IOException {
        StringWriter writer = new StringWriter();
        try (BufferedWriter bufferedWriter = new BufferedWriter(writer)) {
            element.write(bufferedWriter, indentation);
        }

        return writer.getBuffer().toString();
    }

    public JSONNumberNode createMockedJSONNumber(Number number) {
        return spy(new JSONNumberNode(number));
    }

    public JSONStringNode createMockedJSONString(String string) {
        return spy(new JSONStringNode(string));
    }

    public JSONObjectEntryNode createMockedJSONObjectEntry(JSONStringNode jsonStringNode, JSONElement jsonElement) {
        return spy(new JSONObjectEntryNode(jsonStringNode, jsonElement));
    }
}
