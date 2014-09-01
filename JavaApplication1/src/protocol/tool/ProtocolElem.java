/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.tool;

import java.awt.Dimension;
import java.util.Objects;
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
                frame.repaint();
            });
        });
    }

    private void setSize(String type) {
        this.type = type;
        this.setPreferredSize(new Dimension(100, sHeight));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.key);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProtocolElem other = (ProtocolElem) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        return true;
    }

}
