
package proyecto_final;


public class comprador {
    private String nombre;
    private String id;
    private String tipo;

    public comprador(String nombre, String id, String tipo) {
        this.nombre = nombre;
        this.id = id;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
}
