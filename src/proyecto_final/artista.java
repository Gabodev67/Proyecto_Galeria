
package proyecto_final;


public class artista {
    private String nombre;
    private String id;
    private String pais;
    private int edad;
    private String tecnica;

    public artista(String nombre, String id, String pais, int edad, String tecnica) {
        this.nombre = nombre;
        this.id = id;
        this.pais = pais;
        this.edad = edad;
        this.tecnica = tecnica;
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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTecnica() {
        return tecnica;
    }

    public void setTecnica(String tecnica) {
        this.tecnica = tecnica;
    }
    
    
}
