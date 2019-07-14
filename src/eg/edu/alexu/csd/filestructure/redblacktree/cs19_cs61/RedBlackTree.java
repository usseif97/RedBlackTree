package eg.edu.alexu.csd.filestructure.redblacktree.cs19_cs61;

import javax.management.RuntimeErrorException;

import eg.edu.alexu.csd.filestructure.redblacktree.INode;
import eg.edu.alexu.csd.filestructure.redblacktree.IRedBlackTree;

@SuppressWarnings("rawtypes")
public class RedBlackTree<T extends Comparable<T>, V> implements IRedBlackTree {

	private INode<T, V> root;
	private INode<T, V> dummy;
	private int size = 0;

	@SuppressWarnings("unchecked")
	public RedBlackTree() {
		dummy= new Node<>();
		dummy.setValue(null);
		dummy.setKey(null);
		dummy.setColor(false);
		dummy.setParent(null);
		dummy.setLeftChild(null);
		dummy.setRightChild(null);
		root = new Node<>();
		root.setColor(false);
		root.setParent(new Node<>());
		root.setLeftChild(new Node<>());
		root.setRightChild(new Node<>());
		size = 0;
	}

	public int getSize() {
		return size;
	}
	
	@Override
	public INode getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		} else {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clear() {
		if (size != 0) {
			root = new Node<>();
			root.setColor(false);
			root.setParent(new Node<>());
			root.setLeftChild(new Node<>());
			root.setRightChild(new Node<>());
			size = 0;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object search(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		INode<T, V> node = new Node<>();
		if ((node = this.find(key)) == null) {
			return null;
		}
		return node.getValue();
	}

	@Override
	public boolean contains(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		if (this.find(key) == null) {
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void insert(Comparable key, Object value) {
		if (key == null || value == null) {
			throw new RuntimeErrorException(null);
		}
		INode temp = root;
		if (root.isNull()) {
			INode<T, V> z = new Node<>();
			z.setKey((T) key);
			z.setValue((V) value);
			z.setColor(false);
			z.setParent(dummy);
			z.setRightChild(dummy);
			z.setLeftChild(dummy);
			root = z;
			size++;
		} else {
			while (true) {
				if (key.compareTo((T) temp.getKey()) < 0) {
					if (temp.getLeftChild().isNull()) {
						INode<T, V> z = new Node<>();
						z.setKey((T) key);
						z.setValue((V) value);
						z.setParent(temp);
						z.setRightChild(dummy);
						z.setLeftChild(dummy);
						z.setColor(true);
						temp.setLeftChild(z);
						size++;
						RB_Insert_FixUp(z);
						break;
					} else {
						temp = temp.getLeftChild();
					}
				} else if (key.compareTo((T) temp.getKey()) > 0) {
					if (temp.getRightChild().isNull()) {
						INode<T, V> z = new Node<>();
						z.setKey((T) key);
						z.setValue((V) value);
						z.setParent(temp);
						z.setRightChild(dummy);
						z.setLeftChild(dummy);
						z.setColor(true);
						temp.setRightChild(z);
						size++;
						RB_Insert_FixUp(z);
						break;
					} else {
						temp = temp.getRightChild();
					}
				} else {
					temp.setValue(value);
					break;
				}
			}
			
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean delete(Comparable key) {
		if (key == null) {
			throw new RuntimeErrorException(null);
		}
		INode<T, V> z = new Node<>();
		INode<T, V> y = new Node<>();
		INode<T, V> x = new Node<>();
		if ((z = this.find(key)) == null) {
			return false;
		}
		y = z;
		boolean yColor = y.getColor();
		if (z.getLeftChild().isNull()) {
			x = z.getRightChild();
			this.Transplant(z, z.getRightChild());
		} else if (z.getRightChild().isNull()) {
			x = z.getLeftChild();
			this.Transplant(z, z.getLeftChild());
		} else {
			y = this.Minimum(z.getRightChild());
			yColor = y.getColor();
			x = y.getRightChild();
			if (y.getParent() == z) {
				x.setParent(y);
			} else {
				this.Transplant(y, y.getRightChild());
				y.setRightChild(z.getRightChild());
				y.getRightChild().setParent(y);
			}
			this.Transplant(z, y);
			y.setLeftChild(z.getLeftChild());
			y.getLeftChild().setParent(y);
			y.setColor(z.getColor());
		}

		if (yColor == false) {
			this.DeleteFixUp(x);
		}
		size--;
		return true;

	}

	private void RB_Insert_FixUp(INode z) {
		while (z.getParent().getColor() == true) {
			INode uncle = null;
			if (z.getParent() == z.getParent().getParent().getLeftChild()) {
				uncle = z.getParent().getParent().getRightChild();
				if (!uncle.isNull() && uncle.getColor() == true) {
					z.getParent().setColor(false);
					uncle.setColor(false);
					z.getParent().getParent().setColor(true);
					z = z.getParent().getParent();
					continue;
				}
				if (z == z.getParent().getRightChild()) {
					// Double rotation needed
					z = z.getParent();
					Left_Rotate(z);
				}
				z.getParent().setColor(false);
				z.getParent().getParent().setColor(true);
				// if the "else if" code hasn't executed, this
				// is a case where we only need a single rotation
				Right_Rotate(z.getParent().getParent());
			} else {
				uncle = z.getParent().getParent().getLeftChild();
				if (!uncle.isNull() && uncle.getColor() == true) {
					z.getParent().setColor(false);
					uncle.setColor(false);
					z.getParent().getParent().setColor(true);
					z = z.getParent().getParent();
					continue;
				}
				if (z == z.getParent().getLeftChild()) {
					// Double rotation needed
					z = z.getParent();
					Right_Rotate(z);
				}
				z.getParent().setColor(false);
				z.getParent().getParent().setColor(true);
				// if the "else if" code hasn't executed, this
				// is a case where we only need a single rotation
				Left_Rotate(z.getParent().getParent());
			}
		}
		root.setColor(false);
	}

	private void DeleteFixUp(INode<T, V> x) {
		while (x != this.root && x.getColor() == false) {
			if (x == x.getParent().getLeftChild()) {
				INode w = x.getParent().getRightChild();
				if (w.getColor()) {
					w.setColor(false);
					x.getParent().setColor(true);
					this.Left_Rotate(x.getParent());
					w = x.getParent().getRightChild();
				}
				if (w.getLeftChild().getColor() == false && w.getRightChild().getColor() == false) {
					w.setColor(true);
					x = x.getParent();
				} else if (w.getRightChild().getColor() == false) {
					w.getLeftChild().setColor(false);
					w.setColor(true);
					this.Right_Rotate(w);
					w = x.getParent().getRightChild();
				}
				if (w.getRightChild().getColor() == true) {
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(false);
					w.getRightChild().setColor(false);
					this.Left_Rotate(x.getParent());
					x = this.root;
				}
			} else {
				INode w = x.getParent().getLeftChild();
				if (w.getColor()) {
					w.setColor(false);
					x.getParent().setColor(true);
					this.Right_Rotate(x.getParent());
					w = x.getParent().getLeftChild();
				}
				if (w.getRightChild().getColor() == false && w.getLeftChild().getColor() == false) {
					w.setColor(true);
					x = x.getParent();
				} else if (w.getLeftChild().getColor() == false) {
					w.getRightChild().setColor(false);
					w.setColor(true);
					this.Left_Rotate(w);
					w = x.getParent().getLeftChild();
				}
				if (w.getLeftChild().getColor() == true) {
					w.setColor(x.getParent().getColor());
					x.getParent().setColor(false);
					w.getLeftChild().setColor(false);
					this.Right_Rotate(x.getParent());
					x = this.root;
				}
			}
		}
		x.setColor(false);
	}

	@SuppressWarnings({ "unchecked" })
	private void Left_Rotate(INode node) {
		if (!node.getParent().isNull()) {
			if (node == node.getParent().getLeftChild()) {
				node.getParent().setLeftChild(node.getRightChild());
			} else {
				node.getParent().setRightChild(node.getRightChild());
			}
			node.getRightChild().setParent(node.getParent());
			node.setParent(node.getRightChild());
			if (!node.getRightChild().getLeftChild().isNull()) {
				node.getRightChild().getLeftChild().setParent(node);
			}
			node.setRightChild(node.getRightChild().getLeftChild());
			node.getParent().setLeftChild(node);
		} else {// Need to rotate root
			/*INode<T, V> dummy = new Node<>();
			dummy.setColor(false);
			dummy.setKey(null);
			dummy.setValue(null);*/
			INode right = root.getRightChild();
			root.setRightChild(right.getLeftChild());
			right.getLeftChild().setParent(root);
			root.setParent(right);
			right.setLeftChild(root);
			right.setParent(dummy);
			root = right;
		}
	}

	@SuppressWarnings({ "unchecked" })
	private void Right_Rotate(INode node) {
		 if (!node.getParent().isNull()) {
	            if (node == node.getParent().getLeftChild()) {
	                node.getParent().setLeftChild(node.getLeftChild());
	            } else {
	            	node.getParent().setRightChild(node.getLeftChild());
	            }
	            node.getLeftChild().setParent(node.getParent());
	            node.setParent(node.getLeftChild());
	            if (!node.getLeftChild().getRightChild().isNull()) {
	                node.getLeftChild().getRightChild().setParent(node);
	            }
	            node.setLeftChild(node.getLeftChild().getRightChild());
	            node.getParent().setRightChild(node);
	        } else {//Need to rotate root
				/*INode<T, V> dummy = new Node<>();
				dummy.setColor(false);
				dummy.setKey(null);
				dummy.setValue(null);*/
	            INode left = root.getLeftChild();
	            root.setLeftChild(root.getLeftChild().getRightChild());
	            left.getRightChild().setParent(root);
	            root.setParent(left);
	            left.setRightChild(root);
	            left.setParent(dummy);
	            root = left;
	        }
	}

	@SuppressWarnings("unchecked")
	public INode Minimum(INode<T, V> z) {
		INode<T, V> y = new Node<>();
		y = z;
		while (!y.getLeftChild().isNull()) {
			y = y.getLeftChild();
		}
		return y;
	}
	
	@SuppressWarnings("unchecked")
	public INode Maximum(INode<T, V> z) {
		INode<T, V> y = new Node<>();
		y = z;
		while (!y.getRightChild().isNull()) {
			y = y.getRightChild();
		}
		return y;
	}

	public void Transplant(INode<T, V> x, INode<T, V> y) {
		if (x.getParent().isNull()) {
			this.root = y;
		} else if (x == x.getParent().getLeftChild()) {
			x.getParent().setLeftChild(y);
		} else {
			x.getParent().setRightChild(y);
		}
		y.setParent(x.getParent());

	}

	@SuppressWarnings("unchecked")
	public INode find(Comparable key) {
		INode<T, V> x = root;
		while (!x.isNull()) {
			if (x.getKey().compareTo((T) key) == 0) {
				return x;
			} else if (x.getKey().compareTo((T) key) > 0) {
				x = x.getLeftChild();
			} else if (x.getKey().compareTo((T) key) < 0) {
				x = x.getRightChild();
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public INode findCeil(Comparable key) {
		INode<T, V> x = root;
		while (!x.isNull()) {
			if (x.getKey().compareTo((T) key) == 0) {
				return x;
			} else if (x.getKey().compareTo((T) key) > 0) {
				x = x.getLeftChild();
			} else if (x.getKey().compareTo((T) key) < 0) {
				x = x.getRightChild();
			}
		}
		// successor
		if (!x.getRightChild().isNull()) {
			return this.Minimum(x.getRightChild());
		} else {
			INode y = x.getParent();
			while (!y.isNull() && x == y.getRightChild()) {
				x = y;
				y = y.getParent();
			}
			return y;
		}
	}
	
	@SuppressWarnings("unchecked")
	public INode findFloor(Comparable key) {
		INode<T, V> x = root;
		while (!x.isNull()) {
			if (x.getKey().compareTo((T) key) == 0) {
				return x;
			} else if (x.getKey().compareTo((T) key) > 0) {
				x = x.getLeftChild();
			} else if (x.getKey().compareTo((T) key) < 0) {
				x = x.getRightChild();
			}
		}
		// predecessor
		if (!x.getLeftChild().isNull()) {
			return this.Maximum(x.getLeftChild());
		} else {
			INode y = x.getParent();
			while (!y.isNull() && x == y.getLeftChild()) {
				x = y;
				y = y.getParent();
			}
			return y;
		}

	}

	////////////////////////////////////////// Node implementation ////////////////////////////////////////////
	@SuppressWarnings("hiding")
	public class Node<T extends Comparable<T>, V> implements INode {

		private INode parent = null;
		private INode leftChild = null;
		private INode rightChild = null;
		private T key = null;
		private V value = null;
		private boolean color = false;

		@Override
		public void setParent(INode parent) {
			this.parent = parent;
		}

		@Override
		public INode getParent() {
			return this.parent;
		}

		@Override
		public void setLeftChild(INode leftChild) {
			this.leftChild = leftChild;

		}

		@Override
		public INode getLeftChild() {
			return this.leftChild;
		}

		@Override
		public void setRightChild(INode rightChild) {
			this.rightChild = rightChild;

		}

		@Override
		public INode getRightChild() {
			return this.rightChild;
		}

		@Override
		public Comparable getKey() {
			return this.key;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setKey(Comparable key) {
			this.key = (T) key;
		}

		@Override
		public Object getValue() {
			return this.value;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void setValue(Object value) {
			this.value = (V) value;
		}

		@Override
		public boolean getColor() {
			return this.color;
		}

		@Override
		public void setColor(boolean color) {
			this.color = color;

		}

		@Override
		public boolean isNull() {
			if (this.key == null) {
				return true;
			} else {
				return false;
			}
		}

	}

}
