/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.tool;

import java.awt.Component;
import javax.swing.JPanel;

/**
 *
 * @author caolisheng
 */
public class ProtocolPanel extends JPanel {

    @Override
    public Component add(Component comp) {
        return super.add(comp, this.calIndex(comp));
    }
    
    /**
     * Oh fuck, 具体排序算法还有待后端的完成
     * @param comp
     * @return 
     */
    private int calIndex(Component comp) {
        Component[] comps = super.getComponents();
        for (int i = 0; i < comps.length; i++) {
            
        }
        return -1;
    }
    
}
