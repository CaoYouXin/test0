/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.tool;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author caolisheng
 */
public class ComboElemPersistence {
    
    private static final String FILE_NAME = "ComboData";
    
    public static List<String> getComboData() {
        List<String> comboData = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new FileInputStream(FILE_NAME))) {
            while(dis.available() > 0) {
                comboData.add(dis.readUTF());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComboElemPersistence.class.getName()).log(Level.SEVERE, "File Not Found.", ex);
            return comboData;
        } catch (IOException ex) {
            Logger.getLogger(ComboElemPersistence.class.getName()).log(Level.SEVERE, "IO Error.", ex);
        }
        return comboData;
    }
    
    public static boolean addComboData(String aCombo) {
        File f = new File(FILE_NAME);
        if (!f.exists()) {
            try {
                if (!f.createNewFile()) {
                    return false;
                }
            } catch (IOException ex) {
                Logger.getLogger(ComboElemPersistence.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(f, true))) {
            dos.writeUTF(aCombo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ComboElemPersistence.class.getName()).log(Level.SEVERE, "File Not Found.", ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(ComboElemPersistence.class.getName()).log(Level.SEVERE, "IO Error.", ex);
            return false;
        }
        return true;
    }
    
    public static void main(String[] args) {
        ComboElemPersistence.addComboData("a");
        ComboElemPersistence.addComboData("AB");
        System.err.print(Arrays.toString(ComboElemPersistence.getComboData().toArray()));
    }
    
}
