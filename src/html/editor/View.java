package html.editor;

import html.editor.listeners.FrameListener;
import html.editor.listeners.TabbedPaneChangeListener;
import html.editor.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View extends JFrame implements ActionListener {

    private Controller controller;
    private JTabbedPane tabbedPane = new JTabbedPane(); //панель с двумя вкладками
    private JTextPane htmlTextPane = new JTextPane(); //компонент для визуального редактирования html. Он будет размещен на первой вкладке.
    private JEditorPane plainTextPane = new JEditorPane(); //компонент для редактирования html в виде текста, он будет отображать код html (теги и их содержимое).
    private UndoManager undoManager = new UndoManager(); //управляет списком правок отмены и возврата
    private UndoListener undoListener = new UndoListener(undoManager);

    public View() {
        try {
            //Получает подходящий Look And Feel операционной системы
            String systemLookAndFeelClassName = UIManager.getSystemLookAndFeelClassName();
            // Устанавливает LookAndFeel
            UIManager.setLookAndFeel(systemLookAndFeelClassName);
        }
        catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public UndoListener getUndoListener() {
        return undoListener;
    }

    /**Будет вызваться при выборе пунктов меню, у которых view указано в виде слушателя событий.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Получает из события команду
        switch(e.getActionCommand())
        {
            case "Новый":
                controller.createNewDocument();
                break;
            case "Открыть":
                controller.openDocument();
                break;
            case "Сохранить":
                controller.saveDocument();
                break;
            case "Сохранить как...":
                controller.saveDocumentAs();
                break;
            case "Выход":
                controller.exit();
                break;
            case "О программе":
                showAbout();
                break;

            default:
                System.out.println("Ошибка с выбором пункта меню");
                break;
        }
    }

    /**инициализирует представления
     */
    public void init() {
        initGui();
        //Добавляет слушателя событий нашего окна
        FrameListener frameListener = new FrameListener(this);
        //добавляем слушателя
        addWindowListener(frameListener);

        setVisible(true);

    }

    public void exit() {
        controller.exit();
    }

    /**инициализирует меню
     */
    public void initMenuBar() {
        JMenuBar jMenuBar = new JMenuBar();
        MenuHelper.initFileMenu(this, jMenuBar);
        MenuHelper.initEditMenu(this, jMenuBar);
        MenuHelper.initStyleMenu(this, jMenuBar);
        MenuHelper.initAlignMenu(this, jMenuBar);
        MenuHelper.initColorMenu(this, jMenuBar);
        MenuHelper.initFontMenu(this, jMenuBar);
        MenuHelper.initHelpMenu(this, jMenuBar);

        //Добавить сверху панели контента текущего фрейма наше меню.
        getContentPane().add(jMenuBar, BorderLayout.NORTH);

    }

    /**инициализирует панели редактора
     */
    public void initEditor() {
        //Указываем типы документов, которые должен отображать редактор («text/html»)
        htmlTextPane.setContentType("text/html");
        //Создаем панель прокрутки
        JScrollPane jScrollPaneHtml = new JScrollPane(htmlTextPane);
        //Добавить вкладку в панель
        tabbedPane.add("HTML", jScrollPaneHtml);
        JScrollPane jScrollPanePlain = new JScrollPane(plainTextPane);
        tabbedPane.add( "Текст", jScrollPanePlain);
        //Устанавливает предпочтительный размер панели tabbedPane.
        tabbedPane.setPreferredSize(new Dimension(800, 800));
        //Создает объект Listener -a b устанавливает его в качестве слушателя изменений в tabbedPane
        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
        //Добавить по центру панели контента текущего фрейма нашу панель с вкладками.
        getContentPane().add(tabbedPane, BorderLayout.CENTER);

    }

    /**инициализирует графический интерфейс
     */
    public void initGui() {
        initMenuBar();
        initEditor();

        //устанавливает минимальный размер контейнера, который достаточен для отображения всех компонентов
        pack();
    }

    /**вызывается, когда произошла смена выбранной вкладки
     */
    public void selectedTabChanged() {
        //проверяет какая вкладка выбрана
        if (tabbedPane.getSelectedIndex() == 0) {
            //Получить текст из plainTextPane и установить его в контроллер
            controller.setPlainText(plainTextPane.getText());
        }
        else {
            //Получить текст у контроллера с помощью метода getPlainText() и установить его в панель plainTextPane
            plainTextPane.setText(controller.getPlainText());
        }
        resetUndo();
    }

    public boolean canUndo() {
        return undoManager.canUndo();
    }

    public boolean canRedo(){
        return undoManager.canRedo();
    }

    /**Отменяет последнее действие
     */
    public void undo() {
        try {
            undoManager.undo();
        }
        catch (CannotUndoException e) {
            ExceptionHandler.log(e);
        }
    }
    /**Возвращает ранее отмененное действие
     */
    public void redo() {
        try {
            undoManager.redo();
        }
        catch (CannotRedoException e) {
            ExceptionHandler.log(e);
        }

    }

    /**Должен сбрасывать все правки в менеджере undoManager
     */
    public void resetUndo() {
        undoManager.discardAllEdits();
    }

    /**Должен возвращать true, если выбрана вкладка, отображающая html в панели вкладок (html во вкладках под номером 0)
     */
    public boolean isHtmlTabSelected() {
        return tabbedPane.getSelectedIndex() == 0;
    }

    /**Выбрать html вкладку и сбросить все правки
     */
    public void selectHtmlTab() {
        tabbedPane.setSelectedIndex(0);
        resetUndo();
    }

    /**Получает документ у контроллера и устанавливает его в панель редактирования htmlTextPane
     */
    public void update() {
        htmlTextPane.setDocument(controller.getDocument());
    }

    /**Показывает диалоговое окно с информацией о программе
     */
    public void  showAbout() {
        JOptionPane.showMessageDialog(View.this,
                new String[] {"HTML Editor",
                        " - Версия 2.212",
                        " - Обновление от 30.10.2019"},
                "О программе",
                JOptionPane.INFORMATION_MESSAGE);

    }

}