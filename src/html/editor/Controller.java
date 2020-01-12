package html.editor;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

public class Controller {
    private View view; //представление
    private HTMLDocument document; // модель
    private File currentFile; // отвечает за файл, который сейчас открыт в нашем редакторе (текущий файл)

    public Controller(View view) {
        this.view = view;
    }

    public HTMLDocument getDocument() {
        return document;
    }

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();
    }

    /**инициализация контроллера
     */
    public void init() {
        createNewDocument();
    }

    public void exit() {
        System.exit(0);
    }

    /**Сбрасывает текущий документ и\или добавляет новый
     */
    public void resetDocument() {
        if(document != null) {
            //удаляет у текущего документа document слушателя правок
            document.removeUndoableEditListener(view.getUndoListener());
        }
        //Создает новый документ по умолчанию и присваивать его полю document
        document = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        //Добавляет документу слушателя правок
        document.addUndoableEditListener(view.getUndoListener());
        //Добавляет документ в панель редактирования
        view.update();
    }

    /**Будет записывать переданный текст с html тегами в документ document
     */
    public void setPlainText(String text) {

        resetDocument();
        StringReader reader = new StringReader(text);
        try {
            //read вычитает данные из реадера в документ document
            new HTMLEditorKit().read(reader, document, 0);
        }
        catch (Exception e) {
            ExceptionHandler.log(e);
        }
    }

    /**получает текст из документа со всеми html тегами.
     */
    public String getPlainText() {
        StringWriter writer = new StringWriter();
        try {
            //Перепиcывает все содержимое из документа document в созданный объект writer
            new HTMLEditorKit().write(writer, document, 0, document.getLength());
        }
        catch (Exception e) {
            ExceptionHandler.log(e);
        }
        return writer.toString();
    }

    public void createNewDocument() {
        //выбирает html вкладку
        view.selectHtmlTab();
        //сбрасывает документ
        resetDocument();
        //Устанавливает новый заголовок окна
        view.setTitle("HTML редактор");
        //сбрасывает правки
        view.resetUndo();
        currentFile = null;
    }

    public void openDocument() {
        //Переключает представление на html вкладку
        view.selectHtmlTab();
        //Создавет новый объект для выбора файла JFileChooser.
        //Устанавливает ему в качестве фильтра объект HTMLFileFilter
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new HTMLFileFilter());
        //Показывает диалоговое окно "Открыть файл" для выбора файла
        int result = jFileChooser.showOpenDialog(view);

        //Если пользователь подтвердит выбор файла:
        if (result == JFileChooser.APPROVE_OPTION) {
            //Сохраняет выбранный файл
            currentFile = jFileChooser.getSelectedFile();
            resetDocument();
            //Установить имя файла в заголовок у представления
            view.setTitle(currentFile.getName());

            try {
                FileReader fileReader = new FileReader(currentFile);
                //Вычитать данные из FileReader-а в документ document
                new HTMLEditorKit().read(fileReader, document, 0);
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
            view.resetUndo();

        }
    }

    public void saveDocument() {
        if(currentFile != null) {
            //Переключает представление на html вкладку
            view.selectHtmlTab();

            try {
                FileWriter fileWriter = new FileWriter(currentFile);
                //Переписыват данные из документа document в объекта FileWriter
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
        else {
            saveDocumentAs();
        }
    }

    public void saveDocumentAs() {

        //Переключает представление на html вкладку
        view.selectHtmlTab();
        //Создавет новый объект для выбора файла JFileChooser.
        //Устанавливает ему в качестве фильтра объект HTMLFileFilter
        JFileChooser jFileChooser = new JFileChooser();
        jFileChooser.setFileFilter(new HTMLFileFilter());
        //Показывает диалоговое окно "Сохранить файл" для выбора файла
        int result = jFileChooser.showSaveDialog(view);

        //Если пользователь подтвердит выбор файла:
        if (result == JFileChooser.APPROVE_OPTION) {
            //Сохраняет выбранный файл
            currentFile = jFileChooser.getSelectedFile();
            //Устанавливает имя файла в качестве заголовка окна представления
            view.setTitle(currentFile.getName());

            try {
                FileWriter fileWriter = new FileWriter(currentFile);
                //Переписыват данные из документа document в объекта FileWriter
                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());
            }
            catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }

}

