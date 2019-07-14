package eg.edu.alexu.csd.filestructure.redblacktree.cs19_cs61;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.management.RuntimeErrorException;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;
import eg.edu.alexu.csd.filestructure.redblacktree.ITreeMap;

@SuppressWarnings("rawtypes")
public class TreeMap<T extends Comparable<T>, V> implements ITreeMap {

	@SuppressWarnings("unchecked")
	private IRedBlackTree<T, V> rbt = new RedBlackTree<>();
	private ArrayList<Entry<T, V>> headMapArr = new ArrayList<>();
	private Set<T> keys = new LinkedHashSet<>();
	private Set<T> values = new LinkedHashSet<>();
	private Set<Entry<T, V>> entries = new LinkedHashSet<>();

	@SuppressWarnings("unchecked")
	@Override
	public Entry ceilingEntry(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		INode x = ((RedBlackTree<T, V>) rbt).findCeil(key);
		if (x.isNull()) {
			return null;
		} else {
			Entry e = new AbstractMap.SimpleEntry(x.getKey(), x.getValue());
			return e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comparable ceilingKey(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		INode x = ((RedBlackTree<T, V>) rbt).findCeil(key);
		if (x.isNull()) {
			return null;
		} else {
			return x.getKey();
		}
	}

	@Override
	public void clear() {
		rbt.clear();
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean containsKey(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		return rbt.contains((T) key);
	}

	@Override
	public boolean containsValue(Object value) {
		if (value == null) {
			throw new RuntimeErrorException(null);
		}
		entries.clear();
		values.clear();
		printInorder(rbt.getRoot(), false, true, null, entries);
		return this.values.contains(value);
	}

	@Override
	public Set entrySet() {
		entries.clear();
		values.clear();
		printInorder(rbt.getRoot(), false, true, null, entries);
		return entries;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entry firstEntry() {
		if (rbt.isEmpty()) {
			return null;
		}
		INode x = ((RedBlackTree<T, V>) rbt).Minimum(rbt.getRoot());
		Entry e = new AbstractMap.SimpleEntry(x.getKey(), x.getValue());
		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comparable firstKey() {
		if (rbt.isEmpty()) {
			return null;
		}
		return (T) firstEntry().getKey();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entry floorEntry(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		INode x = ((RedBlackTree<T, V>) rbt).findFloor(key);
		if (x.isNull()) {
			return null;
		} else {
			Entry e = new AbstractMap.SimpleEntry(x.getKey(), x.getValue());
			return e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comparable floorKey(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		INode x = ((RedBlackTree<T, V>) rbt).findFloor(key);
		if (x.isNull()) {
			return null;
		} else {
			return x.getKey();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object get(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		return rbt.search((T) key);
	}

	@SuppressWarnings("unchecked")
	private void printInorder(INode node, boolean less, boolean inclusive, Comparable toKey, Collection arr) {
		if (node.isNull())
			return;

		/* first recur on left subtree */
		printInorder(node.getLeftChild(), less, inclusive, toKey, arr);

		/* then print data of node */
		if ((less && (node.getKey().compareTo(toKey) < 0 || (node.getKey().compareTo(toKey) == 0 && inclusive)))
				|| !less) {
			Entry e = new AbstractMap.SimpleEntry(node.getKey(), node.getValue());
			arr.add(e);
			if (!less) {
				values.add((T) e.getValue());
			}
		}

		/* then recur on right subtree */
		printInorder(node.getRightChild(), less, inclusive, toKey, arr);
	}

	@Override
	public ArrayList headMap(Comparable toKey) {
		return headMap(toKey, false);
	}

	@Override
	public ArrayList headMap(Comparable toKey, boolean inclusive) {
		if (toKey == null) {
			throw new RuntimeErrorException(null);
		}
		headMapArr = new ArrayList<>();
		INode x = rbt.getRoot();
		printInorder(x, true, inclusive, toKey, headMapArr);
		return headMapArr;
	}

	@Override
	public Set keySet() {
		return keys;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entry lastEntry() {
		if (rbt.isEmpty()) {
			return null;
		}
		INode x = ((RedBlackTree<T, V>) rbt).Maximum(rbt.getRoot());
		Entry e = new AbstractMap.SimpleEntry(x.getKey(), x.getValue());
		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comparable lastKey() {
		if (rbt.isEmpty()) {
			return null;
		}
		return (T) lastEntry().getKey();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entry pollFirstEntry() {
		if (rbt.isEmpty()) {
			return null;
		}
		Entry e = firstEntry();
		if (rbt.delete((T) firstKey())) {
			this.keys.remove(e.getKey());
			return e;
		} else {
			throw new RuntimeErrorException(null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Entry pollLastEntry() {
		if (rbt.isEmpty()) {
			return null;
		}
		Entry e = lastEntry();
		if (rbt.delete((T) lastKey())) {
			this.keys.remove(e.getKey());
			return e;
		} else {
			throw new RuntimeErrorException(null);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void put(Comparable key, Object value) {
		if (key == null || value == null) {
			throw new RuntimeErrorException(null);
		}
		values.add((T) value);
		rbt.insert((T) key, (V) value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(Map map) {
		if (map == null) {
			throw new RuntimeErrorException(null);
		}
		for (Entry<T, V> e : (Set<Entry<T, V>>) map.entrySet()) {
			rbt.insert(e.getKey(), e.getValue());
			keys.add(e.getKey());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		if (rbt.delete((T) key)) {
			keys.remove(key);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int size() {
		return ((RedBlackTree<T, V>) rbt).getSize();
	}

	@Override
	public Collection values() {
		return values;
	}

}
