/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileorganization3;

import fileorganizationi.IFileOrganization;
import fileorganizationi.IKey;
import fileorganizationi.IRecord;

/**
 *
 * @author CPU
 */
public class SingleIndexFileOrganization implements IFileOrganization {

	private IIndexManager im;
	
	public SingleIndexFileOrganization(IIndexManager im) {
		this.im = im;
	}
	
	@Override
	public IRecord search(IKey key) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public boolean insert(IRecord record) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
