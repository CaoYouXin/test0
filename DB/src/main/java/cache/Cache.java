/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cache;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import shutdown.BeforeShutdown;

/**
 *
 * @author CPU
 *
 *         没办法处理强行关机等异常情况。。。所以建议构建Cache时，count为1。 也就是说，这里只做读缓存。。。。。
 *
 *         唉，还是不要异步更新数据库了，反正不做写缓存，就同步更新数据库吧
 */
public final class Cache<K, V> implements BeforeShutdown {

	@Override
	public boolean beforeShutdown() {
		mods.syncToDB();
		return true;
	}

	private class Modification {

		boolean isPut;
		V value;

		// public Modification(boolean isPut, V value) {
		// this.isPut = isPut;
		// this.value = value;
		// }

	}

	private class Modifications {

		Map<K, Modification> mods = new ConcurrentHashMap<>();

		// public synchronized void addModification(K key, Modification mod) {
		// mods.put(key, mod);
		// if (maxModCache <= mods.size()) {
		// syncToDB();
		// }
		// }

		void syncToDB() {
			Map<K, Modification> toMods = mods;
			mods = new ConcurrentHashMap<>();
			new Thread(() -> {
				for (Map.Entry<K, Modification> entry : toMods.entrySet()) {
					K k = entry.getKey();
					Modification modification = entry.getValue();
					if (modification.isPut) {
						synchronizer.put(k, modification.value);
					} else {
						synchronizer.remove(k, modification.value);
					}
				}
			}).start();
		}

	}

	// private int maxModCache;
	private Synchronizer<K, V> synchronizer;
	private Map<K, V> data = new ConcurrentHashMap<>();
	private Modifications mods = new Modifications();
	private boolean inited = false;

	public Cache(Synchronizer<K, V> synchronizer) {
		this(1, synchronizer);
	}

	private Cache(int maxModCache, Synchronizer<K, V> synchronizer) {
		// this.maxModCache = maxModCache;
		this.synchronizer = synchronizer;
	}

	public void init() {
		if (!inited) {
			synchronizer.init(data);
			inited = true;
		}
	}

	public boolean put(K key, V value) {
		if (!inited) {
			init();
		}
		if (synchronizer.put(key, value)) {
			data.put(key, value);
			return true;
		}
		return false;
	}

	public void putButNotSync(K key, V value) {
		if (!inited) {
			init();
		}
		data.put(key, value);
	}

	public boolean remove(K key) {
		if (!inited) {
			init();
		}
		V remove = data.get(key);
		if (synchronizer.remove(key, remove)) {
			data.remove(key);
			return true;
		}
		return false;
	}

	public V get(K key) {
		if (!inited) {
			init();
		}
		return data.get(key);
	}

	public Set<K> getKeys() {
		if (!inited) {
			init();
		}
		return data.keySet();
	}

	public Collection<V> getValues() {
		if (!inited) {
			init();
		}
		return data.values();
	}

	public Set<Entry<K, V>> getEntries() {
		if (!inited) {
			init();
		}
		return data.entrySet();
	}

}
