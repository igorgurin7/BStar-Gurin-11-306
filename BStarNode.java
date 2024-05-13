package BStar;

import java.util.ArrayList;

public class BStarNode {
    ArrayList<Integer> keys;
    ArrayList<BStarNode> children;
    boolean isLeaf;

    public BStarNode(boolean leaf) {
        this.keys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.isLeaf = leaf;
    }
}
