/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @desc 这个类是单线程的。为了防止同一时间弹出多个相同的对话框，JDialog对象是单例的，只是会替换若干次内容面板。
 * @special 这里有一点要特别指出，这里用到了回调。
 * @author caolisheng
 */
public class MessageBox {

    private static final JDialog dialog = new JDialog();
    private static WindowAdapter wa = null;

    public static void showConfirmBox(JFrame p, String title, String message, BoxClosed fn) {
        p.setVisible(false);
        JPanel content = new JPanel();
        content.setPreferredSize(new Dimension(300, 100));
        content.add(new JLabel(message));
        dialog.setContentPane(content);
        dialog.setTitle(title);
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        if (null != wa) {
            dialog.removeWindowListener(wa);
        }
        wa = new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                if (null != fn)
                    fn.boxClosed();
                p.setVisible(true);
            }

        };
        dialog.addWindowListener(wa);
        dialog.setVisible(true);
    }

    public interface BoxClosed {

        void boxClosed();
    }

}
