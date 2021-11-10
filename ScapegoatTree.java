package structures;

public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound;


  @Override
  public void add(T t) {
    // TODO: Implement the add() method
    if (t == null){
      throw new NullPointerException();
    }
		upperBound++;
		BSTNode<T> newNode = new BSTNode<T>(t, null, null);
		root = addToSubtree(root, newNode);
		if (height() > Math.log(upperBound) / Math.log((double)3/2)) {
			BSTNode<T> child = newNode;
			BSTNode<T> tempParent = newNode.parent;
			while (((double)subtreeSize(child)/ subtreeSize(tempParent) <= (double)2/3)) {
				tempParent = tempParent.parent;
				child = child.parent;
			}
			ScapegoatTree<T> subtree = new ScapegoatTree<T>();
			subtree.root = tempParent;
			BSTNode<T> originalParent = tempParent.parent;
			subtree.balance();
			if (originalParent.getLeft() == tempParent){
        originalParent.setLeft(subtree.root);
      }
			else{
         originalParent.setRight(subtree.root);
      }
		}
  }

  @Override
  public boolean remove(T element) {
    // TODO: Implement the remove() method
    if (element == null) {
			throw new NullPointerException();
		}
		boolean result = contains(element);
		if (result) {
			root = removeFromSubtree(root, element);
		}
		if (upperBound > 2*size()) {
			balance();
			upperBound = size();
		}
		return result;
  }

  public static void main(String[] args) {
    BSTInterface<String> tree = new ScapegoatTree<String>();
    /*
    You can test your Scapegoat tree here.
    for (String r : new String[] {"0", "1", "2", "3", "4"}) {
      tree.add(r);
      System.out.println(toDotFormat(tree.getRoot()));
    }
    */
    for (String r : new String[] {"0", "1", "2", "3", "4"}) {
      tree.add(r);
      System.out.println(toDotFormat(tree.getRoot()));
    }
  }
}
