public class LinkedListDeque<T> {
    private AnyNode sentinel;
    private int size;

    private class AnyNode {
        private AnyNode prev;
        private T item;
        private AnyNode next;
        public AnyNode(AnyNode m, T i, AnyNode n) {
            prev = m;
            item = i;
            next = n;
        }
    }
    public LinkedListDeque() {
        sentinel = new AnyNode(null, null, null);
        sentinel = new AnyNode(sentinel, null, sentinel);
        size = 0;
    }
    public void addFirst(T item) {
        sentinel.next = new AnyNode(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size++;
    }
    public void addLast(T item) {
        sentinel.prev = new AnyNode(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size++;
    }
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        AnyNode p = sentinel;
        for (int i = 1; i <= size; i++) {
            System.out.print(p.next.item + " ");
            p = p.next;
        }
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T p = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.next.prev = sentinel.next;
        sentinel.next.prev = sentinel;
        size -= 1;
        return p;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T p = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.prev.next = sentinel.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return p;
    }
    public T get(int index) {
        if (index >= size) {
            return null;
        }
        AnyNode p = sentinel.next;
        for (int i = 0; i < index; i++) {
            p = p.next;
        }
        return p.item;
    }
    public T getRecursive(int index) {
        if (index >= size) {
            return null;
        }
        int i = index;
        AnyNode p = sentinel.next;
        if (i == 0) {
            return p.item;
        }
        else {
            p = p.next;
            return getRecursive(i--);
        }
    }

}
