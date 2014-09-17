/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol.tool;

import java.awt.BorderLayout;

import static java.awt.BorderLayout.*;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;

import util.Debugger;
import util.StringUtils;

/**
 *
 * @author caolisheng
 */
public class ManualMainFrame extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8501286891758802468L;
	
	private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;

    public ManualMainFrame() {
        initComponents();
    }

    private void initComponents() {
        this.setPreferredSize(new Dimension(627, 436));

        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new TypeComboBox(this, (nowSelected) -> {
            jButton1ActionPerformed(null);
        });
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("前后端通信规则配置生成器");

        jTextField1.setEditable(false);
        jTextField1.setName("statebar"); // NOI18N

        jTextField2.setPreferredSize(new java.awt.Dimension(200, 21));
        
        jComboBox1.setPreferredSize(new java.awt.Dimension(200, 21));

        jLabel1.setText("键");

        jLabel2.setText("类型");

        jButton1.setText("追加");
        jButton1.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton1ActionPerformed(evt);
        });
        
        jButton2.setText("生成");
        jButton2.addActionListener((java.awt.event.ActionEvent evt) -> {
            jButton2ActionPerformed(evt);
        });

        JPanel content = new JPanel(new BorderLayout());
        this.setContentPane(content);

        content.add(jPanel1, CENTER);

        JPanel north = new JPanel(new FlowLayout(FlowLayout.LEFT));
        content.add(north, NORTH);

        north.add(jLabel1);
        north.add(jTextField2);
        north.add(jLabel2);
        north.add(jComboBox1);
        north.add(jButton1);
        
        JPanel south = new JPanel(new BorderLayout());
        content.add(south, SOUTH);
        
        south.add(jTextField1, CENTER);
        south.add(jButton2, EAST);

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // 追加一段协议内容
        String key = jTextField2.getText().trim();
        if (StringUtils.isEmpty(key)) {
            MessageBox.showConfirmBox(this, "Error", "键不能为空", null);
            return;
        }
        
        if (!StringUtils.isEnglish(key)) {
            MessageBox.showConfirmBox(this, "Error", "键必须由英文字母组成", null);
            return;
        }
        
        String type = ((String) jComboBox1.getEditor().getItem()).trim();
        if (StringUtils.isEmpty(type)) {
            MessageBox.showConfirmBox(this, "Error", "类型不能为空", null);
            return;
        }

        MutableComboBoxModel<String> model = (MutableComboBoxModel<String>) jComboBox1.getModel();
        model.addElement(type);

        jPanel1.add(new ProtocolElem(this, jTextField2.getText().trim(), ((String) jComboBox1.getSelectedItem()).trim()));
        this.paintComponents(this.getGraphics());
        Debugger.debug(() -> {
            Component[] comps = jPanel1.getComponents();
            System.out.println(String.format("p size: %d, [%d, %d]", comps.length, jPanel1.getWidth(), jPanel1.getHeight()));
            System.out.println("c0 type: " + (comps.length > 0 ? comps[0].getClass() : String.class).getName());
            System.out.println(String.format("c0 size: [%d, %d]", comps[0].getWidth(), comps[0].getHeight()));
        });
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // 先选模版，然后生成（另建立一个JFrame吧
        
    }

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ManualMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new ManualMainFrame().setVisible(true);
        });
    }

}
