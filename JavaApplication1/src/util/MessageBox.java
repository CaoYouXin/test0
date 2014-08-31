/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author caolisheng
 */
public class MessageBox {
	
	private static final JDialog dialog = new JDialog();
	private static WindowAdapter wa = null;
	
	public static void showConfirmBox(JFrame p, String title, String message, BoxClosed fn) {
		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(300, 100));
		dialog.setContentPane(content);
		content.add(new JLabel(message));
		dialog.setTitle(title);
		dialog.pack();
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		if (null != wa)
			dialog.removeWindowListener(wa);
		wa = new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent e) {
				fn.boxClosed();
			}
			
		};
		dialog.addWindowListener(wa);
		dialog.setVisible(true);
	}
	
	public interface BoxClosed {
		void boxClosed();
	}
	
}
