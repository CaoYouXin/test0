/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package protocol.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 * @desc 带过滤功能的Model
 * @author caolisheng
 */
public class MyFilterComboBoxModel extends DefaultComboBoxModel<String> implements IFilterComboBoxModel {

    private final List<String> allElems;
    
    public MyFilterComboBoxModel(String[] elems) {
        super(elems);
        allElems = new ArrayList<>();
        allElems.addAll(Arrays.asList(elems));
    }
    
    public MyFilterComboBoxModel(List<String> elems) {
        super(elems.toArray(new String[0]));
        allElems = new ArrayList<>(elems);
    }

    @Override
    public boolean filter(String perfix) {
        super.setSelectedItem(null);
        super.removeAllElements();
        allElems.forEach((String elem) -> {
            if (elem.startsWith(perfix))
                super.addElement(elem);
        });
        return true;
    }

    @Override
    public void addElement(String anObject) {
        if (-1 == super.getIndexOf(anObject)) {
            allElems.add(anObject);
            super.addElement(anObject);
            if (!ComboElemPersistence.addComboData(anObject)) {
                throw new ComboDataNotSyncException("combo data persistence failed");
            }
        }
        super.setSelectedItem(anObject);
    }
    
}
