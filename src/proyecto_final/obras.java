
package proyecto_final;


public class obras {
    private String id;
      private String Titulo;
        private String categoria;
         private int año;
         private String estado;
          private double precio;
           private artista artista;
            private boolean expuesta;

    public obras(String id, String Titulo, String categoria, int año, String estado, double precio, artista artista, boolean expuesta) {
        this.id = id;
        this.Titulo = Titulo;
        this.categoria = categoria;
        this.año = año;
        this.estado = estado;
        this.precio = precio;
        this.artista = artista;
        this.expuesta = expuesta;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return Titulo;
    }

    public void setTitulo(String Titulo) {
        this.Titulo = Titulo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public artista getArtista() {
        return artista;
    }

    public void setArtista(artista artista) {
        this.artista = artista;
    }

    public boolean isExpuesta() {
        return expuesta;
    }

    public void setExpuesta(boolean expuesta) {
        this.expuesta = expuesta;
    }
           
}
    