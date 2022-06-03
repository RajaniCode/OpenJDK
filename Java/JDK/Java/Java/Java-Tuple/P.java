class Pair<L,R> {

    private final L left;
    private final R right;
    
    public Pair(L left, R right) {
        this.left = left;
        this.right = right;
    }
        
    public L getLeft() { return left; }
    public R getRight() { return right; }
    
    @Override
    public int hashCode() { return left.hashCode() ^ right.hashCode(); }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Pair)) return false;
        Pair pairo = (Pair) o;
        return this.left.equals(pairo.getLeft()) && this.right.equals(pairo.getRight());
    }

}

class P {

    public static void main(String[] args) {
        Pair<Integer,Integer> pr = new Pair<>(10, 100);
        long l = pr.hashCode();
        System.out.println(l);
        System.out.println(10 ^ 100);
    }

}