package html.editor;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {

    @Override
    /**Возвращает true, если переданный файл - директория или содержит в конце имени ".html" или ".htm" без учета регистра.
     */
    public boolean accept(File f) {
        String fName = f.getName().toLowerCase();
        if(f.isDirectory() || fName.contains(".html".toLowerCase()) || fName.contains(".htm".toLowerCase())) {
            return true;
        }
        else
            return false;
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
