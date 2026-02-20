package proyecto_final;

public class linkedStack<E> implements IStack<E> {

    private Node<E> top;

    public linkedStack() {
        top = null;
    }

    
    @Override
    public boolean isEmpty() {
        return top == null;
    }
    
    

    @Override
    public E Top() {
        if (isEmpty()) {
            return null;
        } else {
          return   top.getInfo();
        }
    }
    

    
    @Override
    public void Push(E item){
    Node<E> n = new Node(item);
    if (isEmpty()){
    top = n;
    } else{
    n.setNext(top);
    top =n;
    }
    }
    
   
    @Override
    public E Pop(){
    if(isEmpty()){
        return null;
    } else {
        E aux =top.getInfo();
        top = top.getNext();
        return aux;
    }
    }
}
