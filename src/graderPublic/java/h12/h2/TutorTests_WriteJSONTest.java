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

        String message = "Methode did not write the correct String to the BufferedWriter.";

        if (actual.replace(" ", "").replace("\n", "")
            .equals(expected.replace(" ", "").replace("\n", ""))) {
            message += " The contents of the expected and actual json element are equal but the whitespaces do not match.";
        }

        String finalMessage = message;
        assertEquals(createJSONString(expected), createJSONString(actual), context, TR -> finalMessage);
    }

    public String createJSONString(String json) {
        return "\\<span style=\"white-space: pre;\"\\>\n" + json + "\n\\</span\\>";
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
