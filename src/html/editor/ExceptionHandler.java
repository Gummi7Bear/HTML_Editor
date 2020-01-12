package html.editor;

/**
Обработчик исключительных ситуаций, который в дальнейшем можно переопределить.
 */

public class ExceptionHandler {

    /**Выводит в консоль краткое описание проблемы
     */
    public static void log(Exception e){
        System.out.println(e.toString());
    }
}
