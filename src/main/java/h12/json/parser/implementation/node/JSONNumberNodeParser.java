package h12.json.parser.implementation.node;

import h12.exceptions.InvalidNumberException;
import h12.json.JSONNumber;
import h12.json.implementation.node.JSONNumberNode;

import java.io.IOException;

/**
 * A parser based on a node implementation that parses a {@link h12.json.JSONNumber}.
 *
 * <p> Example:
 * <p> Input: -69.420
 * <p> Output: {@code JSONNumber.of(-69.420)}
 */
public class JSONNumberNodeParser implements JSONNodeParser {

    private final JSONElementNodeParser parser;

    /**
     * Creates a new {@link JSONNumberNodeParser}-Instance.
     *
     * @param parser The main {@link JSONElementNodeParser}.
     */
    public JSONNumberNodeParser(JSONElementNodeParser parser) {
        this.parser = parser;
    }

    /**
     * Parses a {@link JSONNumber}.
     *
     * @return The parsed {@link JSONNumberNode}.
     * @throws IOException            If an {@link IOException} occurs while reading the contents of the reader.
     * @throws InvalidNumberException If the parsed {@link JSONNumber} is invalid.
     */
    @Override
    public JSONNumberNode parse() throws IOException, InvalidNumberException {
        String res = parser.readUntil(i -> !isSpecialCharacter(i) && !Character.isDigit(i));
        boolean hasPoint = res.contains(".");
        Number number;
        try {
            if (hasPoint) number = Double.parseDouble(res);
            else number =  Integer.parseInt(res);
        } catch (NumberFormatException ex) {
            throw new InvalidNumberException(res);
        }
        return new JSONNumberNode(number);
    }

    private boolean isSpecialCharacter(int i) {
        return switch(i) {
            case 43, 45, 46 -> true;
            default -> false;
        };
    }

}
