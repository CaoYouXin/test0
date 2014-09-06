/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.tool;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.text.JTextComponent;
import util.CharUtils;
import util.Debugger;

/**
 *
 * @author caolisheng
 */
public class TypeComboBox extends JComboBox {

    public TypeComboBox(JFrame frame, EnterTyped fn) {
        setEditable(true);
        setModel(new MyFilterComboBoxModel(ComboElemPersistence.getComboData()));
        setPreferredSize(new java.awt.Dimension(100, 21));
        getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			@Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                // 为下拉菜单添加过滤功能
                char c = evt.getKeyChar();

                Debugger.debug(() -> {
                    System.out.println("char: " + (int) c);
                });

                if (65535 == c) {
                    return;
                }

                if (KeyEvent.VK_ENTER == c && null != fn) {
                    fn.enter((String) getSelectedItem());
                    return;
                }

                if (!CharUtils.isEnglishLetter(c) && KeyEvent.VK_BACK_SPACE != c) {
                    MessageBox.showConfirmBox(frame, "Error", "只能输入英文字母", () -> {
                        getEditor().setItem(null);
                    });
                    return;
                }

                String perfix = (String) getEditor().getItem();

                Debugger.debug(() -> {
                    System.out.println("perfix: " + perfix);
                });

                IFilterComboBoxModel model = (IFilterComboBoxModel) getModel();
                hidePopup();
                model.filter(perfix);
                showPopup();
                getEditor().setItem(perfix);
				Component editor = getEditor().getEditorComponent();
				if (editor instanceof JTextComponent) {
					JTextComponent jtc = (JTextComponent) editor;
					jtc.setSelectionStart(jtc.getText().length());
				}
            }
        });
//		getEditor().getEditorComponent().addFocusListener(new FocusAdapter() {
//			@Override
//			public void focusGained(FocusEvent e) {
//				Debugger.debug(() -> {
//					System.out.println("focus on " + e.getSource().getClass().getName());
//				});
//			}
//		});
    }

    public interface EnterTyped {
        void enter(String nowSelected);
    }

}
