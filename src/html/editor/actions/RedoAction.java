package html.editor.actions;

import html.editor.View;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Класс возврата действия
 */

public class RedoAction extends AbstractAction {

    private View view;

    public RedoAction(View view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        view.redo();
    }
}
