package PAThree;

public class KDTreeNode<T> {
    private T item;
    private KDTreeNode<T> left;
    private KDTreeNode<T> right;

    public KDTreeNode(T item){
        this.item = item;
        this.left = null;
        this.right = null;
    }

    public KDTreeNode(T item, KDTreeNode<T> left, KDTreeNode<T> right){
        this.item=item;
        this.left=left;
        this.right=right;
    }

    public void setLeft(KDTreeNode<T> node){ left = node; }
    public void setRight(KDTreeNode<T> node){ right = node; }

    public void setItem(T item){this.item = item;}

    public T getItem(){return item;}
    public KDTreeNode<T> getLeft(){ return left; }
    public KDTreeNode<T> getRight(){ return right; }

    @Override
    public String toString(){ return item.toString(); }
}
