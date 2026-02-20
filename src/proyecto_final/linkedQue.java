package proyecto_final;

public class linkedQue<E> implements IQueue<E> {

    private Node<E> first;
    private Node<E> last;

    public linkedQue() {
        this.first=null;
        this.last=null;
    }

    
    @Override
    public boolean isEmpty() {
        return first == null && last == null;
        
    }
    
    

    @Override
public E Poll() {
    if (first == null) { 
        return null;
    }
    E item = first.getInfo();
    first = first.getNext();
    
    
    if (first == null) { 
        last = null;
    }
   
    
    return item;
}
   
    
    @Override
    public void add(E item){
    Node<E> n = new Node(item);
    if (isEmpty()){
    last= n;
    first=n;
    } else{
    last.setNext(n);
    last =n;
    }
    }
    
    //elimina
    @Override
    public E peek(){
    if(isEmpty()){
        return null;
    } else {
        return first.getInfo();
    }
    }
   @Override
public int size() {
    int count = 0;
    Node<E> current = first;
    while (current != null) {
        count++;
        current = current.getNext();
    }
    return count;
} 
@Override
public void clear() {
    this.first = null;
    this.last = null;
    // Al no haber referencias a 'first', Java elimina toda la cadena de nodos automáticamente.
}
}
