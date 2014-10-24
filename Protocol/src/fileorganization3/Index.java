/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fileorganization3;

import fileorganizationi.IKey;

/**
 *
 * @author CPU
 */
public class Index {
	
	private IKey key;
	private int location;

	public Index(IKey key, int location) {
		this.key = key;
		this.location = location;
	}
	
}
