package animals;

public class KnowledgeTree {
    
    private TreeNode root;
    private TreeNode current;

    public void reset() {
        current = root;
    }

    public boolean isAnimal() {
        return current.isLeaf();
    }

    public boolean isStatement() {
        return !isAnimal();
    }

    public TreeNode getCurrent() {
        return current;
    }

    public void next(final boolean direction) {
        current = direction ? current.getYes() : current.getNo();
    }

    public TreeNode getRoot() {
        return root;
    }

    public void setRoot(final TreeNode root) {
        this.root = root;
        this.current = root;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void addAnimal(final String animal, final String statement, final boolean isYes) {

        final TreeNode newAnimal = new TreeNode(animal);
        final TreeNode oldAnimal = new TreeNode(current.getData());
        current.setData(statement);
        current.setYes(isYes ? newAnimal : oldAnimal);
        current.setNo(isYes ? oldAnimal : newAnimal);
    }
    
}
