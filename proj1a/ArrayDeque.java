public class ArrayDeque<T>{
    private T[] items;
    private int size;
    private int nextFirst = 4;
    private int nextLast = 5;
    private double usage;

    public ArrayDeque(){
        items = (T []) new Object[8];
        for(int i = 0; i < items.length; i++) {
            items[i] = null;
        }
        size = 0;
    }
    public void resize(int length){
        T[] temp = (T []) new Object[length];
        for (int i = 0; i < size; i++){
            nextFirst = (nextFirst + 1) % items.length;
            temp[i] = items[nextFirst];
        }
        items = temp;
        nextFirst = length - 1;
        nextLast = size;
    }
    public void addFirst(T item){
        if (size == items.length){
            resize(2*items.length);
        }
        items[nextFirst] = item;
        size++;
        nextFirst = (nextFirst - 1 + items.length) % items.length;
    }
    public void addLast(T item){
        if (size == items.length){
            resize(2*items.length);
        }
        items[nextLast] = item;
        nextLast = (nextLast + 1 + items.length) % items.length;
        size++;
    }
    public boolean isEmpty(){
        if(size ==0)
            return true;
        return false;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        for (int i =0; i < size; i++){
            System.out.print(items[i] + " ");
        }
    }
    public T removeFirst(){
        if(isEmpty()) {
            return null;
        }
        nextFirst = (nextFirst + 1) % items.length;
        T i = items[nextFirst];
        items[nextFirst] = null;
        if ((double) size / items.length < 0.25 && items.length > 16){
            resize(items.length / 2);
        }
        size --;
        return i;
    }
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = (nextLast - 1) % items.length;
        T i = items[nextLast];
        items[nextLast] = null;
        if ((double) sieze / items.length < 0.25 && items.length > 16){
            resize(items.length / 2);
        }
        size --;
        return i;
    }
    public T get(int index){
        if (items[index] == null){
            return null;
        }
        return items[index];
    }

}
