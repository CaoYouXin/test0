/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.tool;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.JButton;
import javax.swing.JFrame;
import util.Debugger;
import util.StringUtils;

/**
 * @desc 这是个自定义的可视化组件
 * @author caolisheng
 */
public final class ProtocolElem extends JButton {

    private static final int sHeight = 15;

    private ProtocolElem thisPE = this;

    private String id;
    private String key;
    private String type;

    public ProtocolElem(JFrame frame, String key, String type) {
        this.id = StringUtils.Md5("" + System.nanoTime());

        this.setText(key, type);
        this.setSize(type);
        this.addMouseListener(new MouseAdapter() {

            private void button1Clicked() {
                MessageBox.showPEInfoBox(frame, thisPE.key, thisPE.type, (String newKey, String newType) -> {
                    if (StringUtils.isEmpty(newKey)) {
                        MessageBox.showConfirmBox(frame, "Error", "键不能为空", null);
                        return;
                    }

                    if (StringUtils.isEmpty(newType)) {
                        MessageBox.showConfirmBox(frame, "Error", "类型不能为空", null);
                        return;
                    }

                    Debugger.debug(() -> {
                        System.out.println(String.format("%s:%s vs. %s:%s", thisPE.key, thisPE.type, newKey, newType));
                    });

                    thisPE.setText(newKey, newType);
                    thisPE.setSize(newType);
                    frame.repaint();
                });
            }

            private void button2Clicked() {
                thisPE.getParent().remove(thisPE);
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

        });
    }

    private void setText(String key, String type) {
        this.key = key;
        this.setText(String.format("[%s/%s]", key, type));
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
        hash = 19 * hash + Objects.hashCode(this.id);
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
        return Objects.equals(this.id, other.id);
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
            for (TypeSize ats : TypeSize.values()) {
                String aname = ats.name();
                if (aname.equalsIgnoreCase(type)) {
                    return ats;
                }
            }
            return Other;
        }

    }

}
