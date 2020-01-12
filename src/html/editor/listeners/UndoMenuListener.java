package html.editor.listeners;

import html.editor.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**Этот слушатель будет следить за моментом, когда меню редактирования будет выбрано пользователем.
 В этот момент он будет запрашивать у представления можем ли мы сейчас отменить или вернуть
 какое-то действие, и в зависимости от этого делать доступными или не доступными пункты меню "Отменить" и "Вернуть".
 */

public class UndoMenuListener implements MenuListener {

    private View view;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;

    public UndoMenuListener(View view, JMenuItem undoMenuItem, JMenuItem redoMenuItem) {
        this.view = view;
        this.undoMenuItem = undoMenuItem;
        this.redoMenuItem = redoMenuItem;
    }

    @Override
    /**
     * Спрашивает у представления можем ли мы отменить действие с помощью метода boolean canUndo()
     * Делает доступным или не доступным пункт меню undoMenuItem и redoMenuItem в зависимости от того, что нам вернуло представление.
     */
    public void menuSelected(MenuEvent e) {

        if (view.canUndo()) {
            undoMenuItem.setEnabled(true);
        } else {
            undoMenuItem.setEnabled(false);
        }

        if (view.canRedo()) {
            redoMenuItem.setEnabled(true);
        } else {
            redoMenuItem.setEnabled(false);
        }

    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
