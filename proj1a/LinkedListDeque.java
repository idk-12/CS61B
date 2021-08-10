public class LinkedListDeque<T> {
    private anyNode sentinel;
    private int size;

    private class anyNode {
        public anyNode prev;
        public T item;
        public anyNode next;
        public anyNode(anyNode m, T i, anyNode n) {
            prev = m;
            item = i;
            next = n;
        }
    }
    public LinkedListDeque(){
        sentinel = new anyNode(null, null, null);
        sentinel = new anyNode(sentinel, null, sentinel);
        size = 0;
    }
    public void addFirst(T item){
        sentinel.next.prev = new anyNode(sentinel, item, sentinel.next);
        sentinel.next = sentinel.next.prev;
        size++;
    }
    public void addLast(T item){
        sentinel.prev.next = new anyNode(sentinel.prev, item, sentinel);
        sentinel.prev = sentinel.prev.next;
        size++;
    }
    public boolean isEmpty(){
        if(size == 0){
            return true;
        }
        return false;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        anyNode p = sentinel;
        for(int i = 1; i <= size; i++){
            System.out.print(p.next.item + " ");
            p = p.next;
        }
    }
    public T removeFirst(){
        if (size == 0) {
            return null;
        }
        T p = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size -= 1;
        return p;
    }
    public T removeLast(){
        if (size == 0) {
            return null;
        }
        T p = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size -= 1;
        return p;
    }
    public T get(int index){
        if(index >= size){
            return null;
        }
        anyNode p = sentinel.next;
        for(int i = 0; i < index; i++){
            p = p.next;
        }
        return p.item;
    }
    public T getRecursive(int index){
        if(index >= size){
            return null;
        }
        int i = 0;
        anyNode p = sentinel.next;
        while(i < index ){
            p = p.next;
            i++;
        }
        return p.item;
    }

}
