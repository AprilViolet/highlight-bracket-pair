package cn.aprilviolet.highlightbracketpair.util;

/**
 * Pair
 *
 * @author AprilViolet
 * @version v1.0.0
 * @date 2021.07.31 14:16
 * @since v1.0.0
 */
public class Pair<L, R> {
    private final L left;

    private final R right;

    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

    @Override
    public int hashCode() {
        return left.hashCode() ^ right.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pair pair)) {
            return false;
        }
        return this.left.equals(pair.getLeft()) && this.right.equals(pair.getRight());
    }
}

