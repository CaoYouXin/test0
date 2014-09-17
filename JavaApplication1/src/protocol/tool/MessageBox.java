/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.tool;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @desc 这个类是单线程的。为了防止同一时间弹出多个相同的对话框，JDialog对象是单例的，只是会替换若干次内容面板。
 * @special 这里有一点要特别指出，这里用到了回调。
 * @author caolisheng
 */
public class MessageBox {

    private static final JDialog dialog = new JDialog();
    private static WindowAdapter wa = null;

    private static void commonSettings() {
        dialog.setAlwaysOnTop(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        if (null != wa) {
            dialog.removeWindowListener(wa);
        }
    }

    private static void commonShow() {
        dialog.pack();
        dialog.addWindowListener(wa);
        dialog.setVisible(true);
    }

    public static void showConfirmBox(JFrame p, String title, String message, BoxClosed fn) {
        commonSettings();

        p.setVisible(false);

        dialog.setTitle(title);

        JPanel content = new JPanel();
        content.setPreferredSize(new Dimension(300, 100));
        content.add(new JLabel(message));
        dialog.setContentPane(content);

        wa = new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                if (null != fn) {
                    fn.boxClosed();
                }
                p.setVisible(true);
            }

        };

        commonShow();
    }

    public interface BoxClosed {

        void boxClosed();
    }

    public static void showPEInfoBox(JFrame p, String key, String type, BoxClosed2 fn) {
        commonSettings();

        p.setVisible(false);

        dialog.setTitle("Info");

        JTextField jKey = new JTextField(9);
        jKey.setText(key);
        TypeComboBox jType = new TypeComboBox(p, (nowSelected) -> {
            dialog.dispose();
            if (null != fn) {
                fn.boxClosed(jKey.getText(), nowSelected);
            }
            p.setVisible(true);
        });
        jType.setSelectedItem(type);
        
        jKey.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                char c = e.getKeyChar();
                if (KeyEvent.VK_ENTER != c) {
                    return;
                }
                
                dialog.dispose();
                if (null != fn) {
                    fn.boxClosed(jKey.getText(), (String) jType.getSelectedItem());
                }
                p.setVisible(true);
            }
            
        });

        JPanel content = new JPanel(new FlowLayout());
        content.setPreferredSize(new Dimension(300, 100));
        content.add(new JLabel("键"));
        content.add(jKey);
        content.add(new JLabel("类型"));
        content.add(jType);
        dialog.setContentPane(content);

        wa = new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                if (null != fn) {
                    fn.boxClosed(jKey.getText(), (String) jType.getSelectedItem());
                }
                p.setVisible(true);
            }

        };

        commonShow();
    }

    public interface BoxClosed2 {

        void boxClosed(String newKey, String newType);
    }

}
