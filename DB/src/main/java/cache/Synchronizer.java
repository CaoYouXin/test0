/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cache;

import java.util.Map;

/**
 *
 * @author CPU
 */
public interface Synchronizer<K, V> {

	boolean put(K key, V value);

	boolean remove(K key, V value);

	void init(Map<K, V> data);
	
}
