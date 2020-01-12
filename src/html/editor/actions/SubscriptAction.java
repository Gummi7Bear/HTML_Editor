package html.editor.actions;

import javax.swing.*;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;
import java.awt.event.ActionEvent;

public class SubscriptAction extends StyledEditorKit.StyledTextAction{
    /**
     * Класс отвечает за стиль текста "Подстрочный знак"
     */
    public SubscriptAction() {
        super(StyleConstants.Subscript.toString());
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        //получаем объект редактора из переданного события
        JEditorPane editor = getEditor(actionEvent);

        if (editor != null) {
            //получаем атрибуты текущего стиля и сохраняем в mutableAttributeSet
            MutableAttributeSet mutableAttributeSet = getStyledEditorKit(editor).getInputAttributes();
            //создаем пустое множество (HаshTable), которое имплементирует MutableAttributeSet (т.е. упрощенный вариант mutableAttributeSet)
            SimpleAttributeSet simpleAttributeSet = new SimpleAttributeSet();
            //устанавливает пустому множеству атрибут Зачеркивания, если в исходном стиле не содержится атрибут Зачеркивания
            StyleConstants.setSubscript(simpleAttributeSet, !StyleConstants.isSubscript(mutableAttributeSet));
            //устанавливает editor'у, стиль множества, без замены существующих атрибутов
            setCharacterAttributes(editor, simpleAttributeSet, false);
        }

    }
}