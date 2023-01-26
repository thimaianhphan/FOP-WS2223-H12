package h12.json.parser.implementation.node;

import h12.exceptions.InvalidConstantException;
import h12.json.JSONConstant;
import h12.json.JSONConstants;
import h12.json.implementation.node.JSONConstantNode;

import java.io.IOException;

/**
 * A parser based on a node implementation that parses a {@link h12.json.JSONConstant}.
 * <p>
 * <p> Example:
 * <p> Input: false
 * <p> Output: {@code JSONConstant.FALSE}
 */
public class JSONConstantNodeParser implements JSONNodeParser {

    private final JSONElementNodeParser parser;

    /**
     * Creates a new {@link JSONConstantNodeParser}-Instance.
     *
     * @param parser The main {@link JSONElementNodeParser}.
     */
    public JSONConstantNodeParser(JSONElementNodeParser parser) {
        this.parser = parser;
    }

    /**
     * Parses a {@link JSONConstant}.
     *
     * @return The parsed {@link JSONConstantNode}.
     * @throws IOException              If an {@link IOException} occurs while reading the contents of the reader.
     * @throws InvalidConstantException If the read {@link JSONConstant} is not a valid constant as defined in {@link JSONConstants}.
     */
    @Override
    public JSONConstantNode parse() throws IOException, InvalidConstantException {
        String ans = parser.readUntil(i -> !Character.isLetter(i));
        if (ans.equals(JSONConstants.TRUE.getSpelling())) return new JSONConstantNode(JSONConstants.TRUE);
        else if (ans.equals(JSONConstants.FALSE.getSpelling())) return new JSONConstantNode(JSONConstants.FALSE);
        else if (ans.equals(JSONConstants.NULL.getSpelling())) return new JSONConstantNode(JSONConstants.NULL);
        else throw new InvalidConstantException(ans);
    }

}
