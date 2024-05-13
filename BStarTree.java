package BStar;
import java.util.ArrayList;
public class BStarTree {
    private BStarNode root;
    private int t;

    public BStarTree(int t) {
        this.root = new BStarNode(true);
        this.t = t;
    }

    public void insert(int key) {
        BStarNode r = root;
        if (r.keys.size() == 2 * t - 1) {
            BStarNode s = new BStarNode(false);
            root = s;
            s.children.add(r);
            splitChild(s, 0);
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    private void insertNonFull(BStarNode x, int key) {
        int i = x.keys.size() - 1;
        if (x.isLeaf) {
            x.keys.add(key);
            while (i >= 0 && key < x.keys.get(i)) {
                x.keys.set(i + 1, x.keys.get(i));
                i--;
            }
            x.keys.set(i + 1, key);
        } else {
            while (i >= 0 && key < x.keys.get(i)) {
                i--;
            }
            i++;
            if (x.children.get(i).keys.size() == 2 * t - 1) {
                splitChild(x, i);
                if (key > x.keys.get(i)) {
                    i++;
                }
            }
            insertNonFull(x.children.get(i), key);
        }
    }

    private void splitChild(BStarNode x, int i) {
        BStarNode z = new BStarNode(x.children.get(i).isLeaf);
        BStarNode y = x.children.get(i);
        x.children.add(i + 1, z);
        x.keys.add(i, y.keys.get(t - 1));
        z.keys.addAll(y.keys.subList(t, 2 * t - 1));
        y.keys.subList(t - 1, 2 * t - 1).clear();
        if (!y.isLeaf) {
            z.children.addAll(y.children.subList(t, 2 * t));
            y.children.subList(t, 2 * t).clear();
        }
    }

    public boolean search(int key) {
        return search(root, key);
    }

    private boolean search(BStarNode x, int key) {
        int i = 0;
        while (i < x.keys.size() && key > x.keys.get(i)) {
            i++;
        }
        if (i < x.keys.size() && key == x.keys.get(i)) {
            return true;
        }
        if (x.isLeaf) {
            return false;
        }
        return search(x.children.get(i), key);
    }

    public void delete(int key) {
        delete(root, key);
    }

    private void delete(BStarNode x, int key) {
        int i = 0;
        while (i < x.keys.size() && key > x.keys.get(i)) {
            i++;
        }
        if (i < x.keys.size() && key == x.keys.get(i)) {
            if (x.isLeaf) {
                x.keys.remove(i);
            }
        } else {
            if (x.isLeaf) {
                return;
            }
            boolean flag = (i == x.keys.size());
            if (x.children.get(i).keys.size() < t) {
                fill(x, i);
            }
            if (flag && i > x.keys.size()) {
                delete(x.children.get(i - 1), key);
            } else {
                delete(x.children.get(i), key);
            }
        }
    }

    private void fill(BStarNode x, int idx) {
        if (idx != 0 && x.children.get(idx - 1).keys.size() >= t) {
            borrowFromPrev(x, idx);
        } else if (idx != x.keys.size() && x.children.get(idx + 1).keys.size() >= t) {
            borrowFromNext(x, idx);
        } else {
            if (idx != x.keys.size()) {
                merge(x, idx);
            } else {
                merge(x, idx - 1);
            }
        }
    }

    private void borrowFromPrev(BStarNode x, int idx) {
        BStarNode child = x.children.get(idx);
        BStarNode sibling = x.children.get(idx - 1);
        child.keys.add(0, x.keys.get(idx - 1));
        if (!child.isLeaf) {
            child.children.add(0, sibling.children.get(sibling.children.size() - 1));
            sibling.children.remove(sibling.children.size() - 1);
        }
        x.keys.set(idx - 1, sibling.keys.get(sibling.keys.size() - 1));
        sibling.keys.remove(sibling.keys.size() - 1);
    }

    private void borrowFromNext(BStarNode x, int idx) {
        BStarNode child = x.children.get(idx);
        BStarNode sibling = x.children.get(idx + 1);
        child.keys.add(x.keys.get(idx));
        if (!child.isLeaf) {
            child.children.add(sibling.children.get(0));
            sibling.children.remove(0);
        }
        x.keys.set(idx, sibling.keys.get(0));
        sibling.keys.remove(0);
    }

    private void merge(BStarNode x, int idx) {
        BStarNode child = x.children.get(idx);
        BStarNode sibling = x.children.get(idx + 1);
        child.keys.add(x.keys.get(idx));
        child.keys.addAll(sibling.keys);
        if (!child.isLeaf) {
            child.children.addAll(sibling.children);
        }
        x.keys.remove(idx);
        x.children.remove(idx + 1);
    }
}
