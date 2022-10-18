package h12.gui.components;

import h12.gui.shapes.ColorHelper;
import h12.gui.shapes.MyShape;
import h12.json.JSONObject;
import h12.json.implementation.node.JSONArrayNode;
import h12.json.implementation.node.JSONObjectNode;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * A class handling the saving of a canvas to a JSON file.
 */
public class SaveCanvasHandler extends FileOperationHandler {

    private final List<MyShape> contents;
    private final Color background;

    /**
     * Creates a new {@link SaveCanvasHandler}-Instance.
     *
     * @param controlPanel The {@link ControlPanel} this {@link SaveCanvasHandler} belongs to.
     */
    public SaveCanvasHandler(ControlPanel controlPanel) {
        super(controlPanel);
        contents = controlPanel.getMainFrame().getContentPanel().getShapes();
        background = controlPanel.getMainFrame().getContentPanel().getBackground();
    }

    /**
     * Shows a {@link JFileChooser} dialog to the user and tries to save the current canvas to the selected file.
     */
    public void save() {
        crash(); //TODO H5.4 - remove if implemented
    }

    /**
     * Converts the contents of the associated {@link ContentPanel} to a {@link JSONObject}. The {@link JSONObject} contains the following entries:
     * <p> background: The background color of the canvas as a {@link JSONArrayNode}.
     * <p> shapes: The shapes on the canvas as a {@link JSONArrayNode}.
     *
     * @return A {@link JSONObjectNode} containing the contents of the associated {@link ContentPanel}.
     * @see ColorHelper#toJSON(Color)
     * @see MyShape#toJSON()
     */
    public JSONObject canvasToJSONObject() {
        return crash(); //TODO H5.4 - remove if implemented
    }


}
