/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.tool;

import javax.swing.JButton;
import javax.swing.JFrame;
import util.MessageBox;

/**
 * @desc 这是个自定义的可视化组件
 * @author caolisheng
 */
public class ProtocolElem extends JButton {
    
	private static final int sHeight = 12;
	
	private String key;
	private String type;

	public ProtocolElem(JFrame frame, String key, String type) {
		super(key);
		this.setSize(type);
		this.addActionListener((action) -> {
			MessageBox.showPEInfoBox(frame, key, type, (String newKey, String newType) -> {
				ProtocolElem.this.setText(newKey);
				ProtocolElem.this.setSize(newType);
				ProtocolElem.this.getParent().repaint();
			});
		});		
	}
	
	private void setSize(String type) {
		this.type = type;
	}
    
}
