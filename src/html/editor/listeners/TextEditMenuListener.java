package html.editor.listeners;

import html.editor.View;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;

public class TextEditMenuListener implements MenuListener {

    private View view;

    public TextEditMenuListener(View view) {
        this.view = view;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        //Из переданного параметра получает объект, над которым было совершено действие.
        JMenu jMenu = (JMenu)e.getSource();
        //У полученного меню получает список пунктов меню
        Component[] components = jMenu.getMenuComponents();

        //Для каждого компонента включаем или выключаем пункты меню.
        // Т.е. пункты стиль, выравнивание, цвет и шрифт включены,когда активна закладка HTML и выключены для закладки Текст.
        for (Component component : components) {
            component.setEnabled(view.isHtmlTabSelected());

        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {

    }

    @Override
    public void menuCanceled(MenuEvent e) {

    }
}
