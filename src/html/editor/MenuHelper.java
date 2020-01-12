package html.editor;

import html.editor.actions.*;
import html.editor.listeners.TextEditMenuListener;
import html.editor.listeners.UndoMenuListener;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuHelper {

    /**Метод создает и возвращает пункты меню
     @param parent - меню в которое мы добавляем пункт
     @param text - текст добавляемого пункта
     @param actionListener - слушатель действий добавляемого пункта
     */
    public static JMenuItem addMenuItem(JMenu parent, String text, ActionListener actionListener) {

        //Создать новый пункт меню
        JMenuItem menuItem = new JMenuItem(text);
        //Добавить к нему слушателя
        menuItem.addActionListener(actionListener);
        //Добавить в меню созданный пункт
        parent.add(menuItem);
        return menuItem;
    }

    /**Метод создает и возвращает пункты меню
     @param parent - меню в которое мы добавляем пункт
     @param action - действие, которое необходимо выполнить
     */
    public static JMenuItem addMenuItem(JMenu parent, Action action) {
        JMenuItem menuItem = new JMenuItem(action);
        parent.add(menuItem);
        return menuItem;
    }

    /**Метод создает и возвращает пункты меню
     @param parent - меню в которое мы добавляем пункт
     @param text - текст добавляемого пункта
     @param action - действие, которое необходимо выполнить
     */
    public static JMenuItem addMenuItem(JMenu parent, String text, Action action) {
        JMenuItem menuItem = addMenuItem(parent, action);
        menuItem.setText(text);
        parent.add(menuItem);
        return menuItem;

    }

    /**Метод создает и добавляет в главное меню пункты выпадающего меню Help
     */
    public static void initHelpMenu(View view, JMenuBar menuBar) {
        //Создает пункт выпадающего меню и добавляет в главное меню
        JMenu helpMenu = new JMenu("Помощь");
        menuBar.add(helpMenu);

        //создаем и добавляем пункт для выпадающего меню
        addMenuItem(helpMenu, "О программе", view);
    }

    /**Метод создает и добавляет в главное меню пункты выпадающего меню Font
     */
    public static void initFontMenu(View view, JMenuBar menuBar) {
        //Создает пункт выпадающего меню и добавляет в главное меню
        JMenu fontMenu = new JMenu("Шрифт");
        menuBar.add(fontMenu);

        //Создает пункт в выпадающем меню и добавляет в него элементы меню со своим шрифтом
        JMenu fontTypeMenu = new JMenu("Шрифт");
        fontMenu.add(fontTypeMenu);
        String[] fontTypes = {Font.SANS_SERIF, Font.SERIF, Font.MONOSPACED, Font.DIALOG, Font.DIALOG_INPUT};
        for (String fontType : fontTypes) {
            addMenuItem(fontTypeMenu, fontType, new StyledEditorKit.FontFamilyAction(fontType, fontType));
        }

        //Создает пункт в выпадающем меню и добавляет в него элементы меню со своим размером
        JMenu fontSizeMenu = new JMenu("Размер шрифта");
        fontMenu.add(fontSizeMenu);
        String[] fontSizes = {"6", "8", "10", "12", "14", "16", "20", "24", "32", "36", "48", "72"};
        for (String fontSize : fontSizes) {
            //создает элемент меню добавляя как действие класс, который устанавливает размер шрифта
            addMenuItem(fontSizeMenu, fontSize, new StyledEditorKit.FontSizeAction(fontSize, Integer.parseInt(fontSize)));
        }

        //добавляет слушателя
        fontMenu.addMenuListener(new TextEditMenuListener(view));
    }

    /**Метод создает и добавляет в главное меню пункты выпадающего меню Color
     */
    public static void initColorMenu(View view, JMenuBar menuBar) {
        JMenu colorMenu = new JMenu("Цвет");
        menuBar.add(colorMenu);

        //создает элемент меню добавляя как действие класс, который устанавливает цвет переднего плана
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Красный", Color.red));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Оранжевый", Color.orange));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Желтый", Color.yellow));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Зеленый", Color.green));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Синий", Color.blue));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Голубой", Color.cyan));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Пурпурный", Color.magenta));
        addMenuItem(colorMenu, new StyledEditorKit.ForegroundAction("Черный", Color.black));

        //добавляет слушателя
        colorMenu.addMenuListener(new TextEditMenuListener(view));

    }

    /**Метод создает и добавляет в главное меню пункты выпадающего меню Align
     */
    public static void initAlignMenu(View view, JMenuBar menuBar) {
        JMenu alignMenu = new JMenu("Выравнивание");
        menuBar.add(alignMenu);

        //создает элемент меню добавляя как действие класс для установки выравнивания абзаца.
        addMenuItem(alignMenu, new StyledEditorKit.AlignmentAction("По левому краю", StyleConstants.ALIGN_LEFT));
        addMenuItem(alignMenu, new StyledEditorKit.AlignmentAction("По центру", StyleConstants.ALIGN_CENTER));
        addMenuItem(alignMenu, new StyledEditorKit.AlignmentAction("По правому краю", StyleConstants.ALIGN_RIGHT));

        //добавляет слушателя
        alignMenu.addMenuListener(new TextEditMenuListener(view));

    }

    /**Метод создает и добавляет в главное меню пункты выпадающего меню Style
     */
    public static void initStyleMenu(View view, JMenuBar menuBar) {

        JMenu styleMenu = new JMenu("Стиль");
        menuBar.add(styleMenu);

        //создает элемент меню добавляя как действие класс для установки жирного шрифта, подчеркивания и курсива.
        addMenuItem(styleMenu, "Полужирный", new StyledEditorKit.BoldAction());
        addMenuItem(styleMenu, "Подчеркнутый", new StyledEditorKit.UnderlineAction());
        addMenuItem(styleMenu, "Курсив", new StyledEditorKit.ItalicAction());

        //добавляет разделитель
        styleMenu.addSeparator();

        addMenuItem(styleMenu, "Подстрочный знак", new SubscriptAction());
        addMenuItem(styleMenu, "Надстрочный знак", new SuperscriptAction());
        addMenuItem(styleMenu, "Зачеркнутый", new StrikeThroughAction());

        styleMenu.addMenuListener(new TextEditMenuListener(view));

    }

    /**Метод создает и добавляет в главное меню пункты выпадающего меню Edit
     */
    public static void initEditMenu(View view, JMenuBar menuBar) {
        JMenu editMenu = new JMenu("Редактировать");
        menuBar.add(editMenu);

        JMenuItem undoItem = addMenuItem(editMenu, "Отменить", new UndoAction(view));
        JMenuItem redoItem = addMenuItem(editMenu, "Вернуть", new RedoAction(view));
        addMenuItem(editMenu, "Вырезать", new DefaultEditorKit.CutAction());
        addMenuItem(editMenu, "Копировать", new DefaultEditorKit.CopyAction());
        addMenuItem(editMenu, "Вставить", new DefaultEditorKit.PasteAction());

        editMenu.addMenuListener(new UndoMenuListener(view, undoItem, redoItem));

    }

    /**Метод создает и добавляет в главное меню пункты выпадающего меню File
     */
    public static void initFileMenu(View view, JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("Файл");
        menuBar.add(fileMenu);

        addMenuItem(fileMenu, "Новый", view);
        addMenuItem(fileMenu, "Открыть", view);
        addMenuItem(fileMenu, "Сохранить", view);
        addMenuItem(fileMenu, "Сохранить как...", view);
        fileMenu.addSeparator();
        addMenuItem(fileMenu, "Выход", view);

    }
}
