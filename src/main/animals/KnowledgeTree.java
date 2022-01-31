package main.animals;

public class KnowledgeTree {
    private TreeNode<String> root;
    private TreeNode<String> current;

    public void reset() {
        current = root;
    }

    public boolean isAnimal() {
        return current.isLeaf();
    }

    public boolean isStatement() {
        return !isAnimal();
    }

    public TreeNode<String> getCurrent() {
        return current;
    }

    public void next(final boolean direction) {
        current = direction ? current.getRight() : current.getLeft();
    }

    public TreeNode<String> getRoot() {
        return root;
    }

    public void setRoot(final TreeNode<String> root) {
        this.root = root;
        this.current = root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void addAnimal(final String animal, final String statement, final boolean isRight) {

        final TreeNode<String> newAnimal = new TreeNode<>(animal);
        final TreeNode<String> oldAnimal = new TreeNode<>(current.getData());
        current.setData(statement);
        current.setRight(isRight ? newAnimal : oldAnimal);
        current.setLeft(isRight ? oldAnimal : newAnimal);
    }

    public boolean deleteAnimal(final String animal) {

        final boolean isSuccessful = deleteAnimal(animal, root, null);

        return isSuccessful;
    }

    private boolean deleteAnimal(final String animal, final TreeNode<String> child, final TreeNode<String> parent) {
        if (child.isLeaf() && animal.equals(child.getData())) {
            final TreeNode<String> source = parent.getRight() == child ? parent.getLeft() : parent.getRight();
            parent.setData(source.getData());
            parent.setRight(source.getRight());
            parent.setLeft(source.getLeft());
            return true;
        }
        return !child.isLeaf() && (deleteAnimal(animal, child.getRight(), child) || deleteAnimal(animal, child.getLeft(), child));
    }
    
}
