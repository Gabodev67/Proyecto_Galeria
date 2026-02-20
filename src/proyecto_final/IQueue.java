
package proyecto_final;

public interface IQueue <E>{
    void add(E item);
    E peek();
    E Poll();
    boolean isEmpty();
    public int size();
    void clear(); 
}
