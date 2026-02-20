
package proyecto_final;




public class Venta {
    private String cod_venta;
    private comprador compradores;
    private int anno;
    private String metodoPago;
    private obras obras;

    

    public Venta(String cod_venta, comprador compradores, int anno, String metodoPago,obras obras) {
        this.cod_venta = cod_venta;
        this.compradores = compradores;
        this.anno = anno;
        this.metodoPago = metodoPago;
          this.obras = obras;
    }

    public obras getObras() {
        return obras;
    }

    public void setObras(obras obras) {
        this.obras = obras;
    }

    public String getCod_venta() {
        return cod_venta;
    }

    public void setCod_venta(String cod_venta) {
        this.cod_venta = cod_venta;
    }

    public comprador getCompradores() {
        return compradores;
    }

    public void setCompradores(comprador compradores) {
        this.compradores = compradores;
    }

    public int getAnno() {
        return anno;
    }

    public void setAnno(int anno) {
        this.anno = anno;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
    
}
