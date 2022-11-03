package tst;

public class TreeWalker {

    void printTreeVal(Tree tree) {
        if (tree.leftTree != null) {
            printTreeVal(tree.leftTree);
        }
        System.out.println(tree.rootVal);
        if (tree.rightTree != null) {
            printTreeVal(tree.rightTree);
        }
    }

    static class Tree {
        public Tree(int rootVal, Tree leftTree, Tree rightTree) {
            this.rootVal = rootVal;
            this.leftTree = leftTree;
            this.rightTree = rightTree;
        }

        int rootVal;
        Tree leftTree;
        Tree rightTree;
    }

    public static void main(String[] args) {
        Tree zeroTree = new Tree(0, null, null);
        Tree twoTree = new Tree(2, null, null);
        Tree fourTree = new Tree(4, null, null);
        Tree sixTree = new Tree(6, null, null);
        Tree oneTree = new Tree(1, zeroTree, twoTree);
        Tree fiveTree = new Tree(5, fourTree, sixTree);
        Tree threeTree = new Tree(3, oneTree, fiveTree);

        new TreeWalker().printTreeVal(threeTree);
    }
}
