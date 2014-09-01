/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.tool;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import util.Debugger;
import util.MessageBox;
import util.StringUtils;

/**
 * @desc 这是个自定义的可视化组件
 * @author caolisheng
 */
public class ProtocolElem extends JButton {

    private static final int sHeight = 15;

    private String key;
    private String type;

    public ProtocolElem(JFrame frame, String key, String type) {
        super(key);
        this.setSize(type);
        this.addMouseListener(new MouseListener(){
            
            @Override
            public void mouseClicked(MouseEvent e) {
                
            }
            
            private void button1Clicked() {
                MessageBox.showPEInfoBox(frame, key, type, (String newKey, String newType) -> {
                    if (StringUtils.isEmpty(newKey)) {
                        MessageBox.showConfirmBox(frame, "Error", "键不能为空", null);
                        return;
                    }

                    if (StringUtils.isEmpty(newType)) {
                        MessageBox.showConfirmBox(frame, "Error", "类型不能为空", null);
                        return;
                    }

                    ProtocolElem.this.setText(newKey);
                    ProtocolElem.this.setSize(newType);
                    frame.repaint();
                });
            }

            private void button2Clicked() {
                ProtocolElem pe = ProtocolElem.this;
                pe.getParent().remove(pe);
                frame.paintComponents(frame.getGraphics());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                Debugger.debug(() -> {
                    System.out.println(e.getButton() == MouseEvent.BUTTON1);
                    System.out.println(e.getButton() == MouseEvent.BUTTON3);
                });
                switch (e.getButton()) {
                    case MouseEvent.BUTTON1:
                        button1Clicked();
                        break;
                    case MouseEvent.BUTTON3:
                        button2Clicked();
                        break;
                    default:
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
            
        });
    }

    private void setSize(String type) {
        this.type = type;
        this.setPreferredSize(new Dimension(getSize(type), sHeight));
        Debugger.debug(() -> {
            System.out.println("button size set.");
        });
    }

    private int getSize(String type) {
        return TypeSize.val(type).size;
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

    private enum TypeSize {
        
        Boolean(20),
        Byte(20),
        Char(40),
        Short(40),
        Integer(80),
        Long(120),
        Float(80),
        Double(120),
        Other(150);
            
        private int size;
        
        private TypeSize(int size) {
            this.size = size;
        }
        
        public static TypeSize val(String type) {
            for(TypeSize ats : TypeSize.values()) {
                String aname = ats.name();
                if (aname.equalsIgnoreCase(type))
                    return ats;
            }
            return Other;
        }
        
    }
    
}
