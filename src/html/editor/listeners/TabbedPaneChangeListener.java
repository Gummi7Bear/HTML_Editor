package html.editor.listeners;

import html.editor.View;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**Будет слушать и обрабатывать изменения состояния панели вкладок.
 *
 */

public class TabbedPaneChangeListener implements ChangeListener {

    private View view;

    public TabbedPaneChangeListener(View view) {
        this.view = view;
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        view.selectedTabChanged();
    }
}
