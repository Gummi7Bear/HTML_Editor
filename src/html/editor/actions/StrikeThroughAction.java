package html.editor.actions;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;

public class StrikeThroughAction extends StyledEditorKit.StyledTextAction {
    /**
     * Класс отвечает за стиль текста "Зачеркнутый"
     */
    public StrikeThroughAction() {
        //добавляем значение"Зачеркнутый"
        super(StyleConstants.StrikeThrough.toString());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        JEditorPane editor = getEditor(actionEvent);
        if (editor != null) {
            //получаем атрибуты текущего стиля и сохраняем в mutableAttributeSet
            MutableAttributeSet mutableAttributeSet = getStyledEditorKit(editor).getInputAttributes();

            //создаем пустое множество (HаshTable), которое имплементирует MutableAttributeSet (т.е. упрощенный вариант mutableAttributeSet)
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();

            //устанавливает пустому множеству атрибут Зачеркивания, если в исходном стиле не содержится атрибут Зачеркивания
            StyleConstants.setStrikeThrough(simpleAttributeSet, !StyleConstants.isStrikeThrough(mutableAttributeSet));

            //устанавливает editor'у, стиль множества, без замены существующих атрибутов
            setCharacterAttributes(editor, simpleAttributeSet, false);
        }
    }
}
