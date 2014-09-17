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
import util.Debugger;

/**
 * @desc 带过滤功能的Model
 * @author caolisheng
 */
public class MyFilterComboBoxModel extends DefaultComboBoxModel<String> implements IFilterComboBoxModel<String> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
        super.removeAllElements();
        allElems.forEach((String elem) -> {
            if (elem.startsWith(perfix))
                super.addElement(elem);
        });
		Debugger.debug(() -> {
			System.out.println("selected: " + super.getSelectedItem());
		});
//        super.setSelectedItem(null);
        return true;
    }

    @Override
    public void addElement(String anObject) {
        Debugger.debug(() -> {
            System.out.println("my add Element working.");
        });
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
