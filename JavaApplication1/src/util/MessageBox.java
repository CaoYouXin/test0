/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 * @desc 这个类是单线程的。为了防止同一时间弹出多个相同的对话框，JDialog对象是单例的，只是会替换若干次内容面板。
 * @special 这里有一点要特别指出，这里用到了回调。
 * @author caolisheng
 */
public class MessageBox {

    private static final JDialog dialog = new JDialog();
    private static WindowAdapter wa = null;

	private static void commonSettings() {
        dialog.pack();
        dialog.setAlwaysOnTop(true);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        if (null != wa) {
            dialog.removeWindowListener(wa);
        }
	}
	
	private static void commonShow() {
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
                if (null != fn)
                    fn.boxClosed();
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
		
		JTextField jKey = new JTextField(10);
		JComboBox jType = new JComboBox();
		
        JPanel content = new JPanel();
        content.setPreferredSize(new Dimension(100, 100));
        
		dialog.setContentPane(content);
        
        wa = new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {
                if (null != fn)
                    fn.boxClosed(jKey.getText(), (String) jType.getSelectedItem());
                p.setVisible(true);
            }

        };
		
		commonShow();
	}
	
	public interface BoxClosed2 {
		void boxClosed(String newKey, String newType);
	}
	
}
