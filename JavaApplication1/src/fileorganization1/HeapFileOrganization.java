/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileorganization1;

import fileorganizationi.IFileOrganization;
import fileorganizationi.IKey;
import fileorganizationi.IRecord;

/**
 *
 * @author caolisheng
 */
public class HeapFileOrganization implements IFileOrganization {

    @Override
    public IRecord search(IKey key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insert(IRecord record) {
        byte[] content = record.write();
        return true;
    }

    @Override
    public boolean modify(IRecord record) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(IKey key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
