package proyecto_final;

import java.sql.*;

public class Galeria_Control {

    private linkedQue<obras> Obras = new linkedQue<obras>();
    private linkedQue<artista> Artistas = new linkedQue<artista>();
    private linkedStack<Venta> ventas = new linkedStack<Venta>();
    private linkedQue<comprador> compradores = new linkedQue<comprador>();

    public Galeria_Control() {
       
        cargarDatosDesdeDB();
    }

   
    public void agregarArtista(artista a) {
        String sql = "INSERT INTO artista (id, nombre, pais, edad, tecnica) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, a.getId());
            pst.setString(2, a.getNombre());
            pst.setString(3, a.getPais());
            pst.setInt(4, a.getEdad());
            pst.setString(5, a.getTecnica());
            pst.executeUpdate();
            
            
            Artistas.add(a);
        } catch (SQLException e) {
            System.err.println("Error al agregar artista: " + e.getMessage());
        }
    }

    public void modificarArtista(artista a) {
        String sql = "UPDATE artista SET nombre=?, pais=?, edad=?, tecnica=? WHERE id=?";
        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, a.getNombre());
            pst.setString(2, a.getPais());
            pst.setInt(3, a.getEdad());
            pst.setString(4, a.getTecnica());
            pst.setString(5, a.getId());
            pst.executeUpdate();
            
       
            refrescarArtistas();
        } catch (SQLException e) {
            System.err.println("Error al modificar artista: " + e.getMessage());
        }
    }

    public void eliminarArtista(String id) {
        String sql = "DELETE FROM artista WHERE id = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, id);
            pst.executeUpdate();
            refrescarArtistas();
        } catch (SQLException e) {
            System.err.println("Error al eliminar: " + e.getMessage());
        }
    }

    
    public void agregarObra(obras o) {
        String sql = "INSERT INTO obras (id, titulo, categoria, anno, estado, precio, expuesta, artista_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, o.getId());
            pst.setString(2, o.getTitulo());
            pst.setString(3, o.getCategoria());
            pst.setInt(4, o.getAño());
            pst.setString(5, o.getEstado());
            pst.setDouble(6, o.getPrecio());
            pst.setBoolean(7, o.isExpuesta());
            pst.setString(8, o.getArtista().getId());
            pst.executeUpdate();
            Obras.add(o);
        } catch (SQLException e) {
            System.err.println("Error obra: " + e.getMessage());
        }
    }

    public void modificarObra(obras o) {
        String sql = "UPDATE obras SET titulo=?, categoria=?, anno=?, estado=?, precio=?, expuesta=? WHERE id=?";
        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, o.getTitulo());
            pst.setString(2, o.getCategoria());
            pst.setInt(3, o.getAño());
            pst.setString(4, o.getEstado());
            pst.setDouble(5, o.getPrecio());
            pst.setBoolean(6, o.isExpuesta());
            pst.setString(7, o.getId());
            pst.executeUpdate();
            refrescarObras();
        } catch (SQLException e) {
            System.err.println("Error update obra: " + e.getMessage());
        }
    }

    public void eliminarObra(String id) {
        String sql = "DELETE FROM obras WHERE id = ?";
        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, id);
            pst.executeUpdate();
            refrescarObras();
        } catch (SQLException e) {
            System.err.println("Error delete obra: " + e.getMessage());
        }
    }

   


public void agregarVenta(Venta v) {
   
    
    String sql = "INSERT INTO venta (cod_venta, comprador_id, anno, metodo_pago, obra_id) VALUES (?, ?, ?, ?, ?)";
    
    try (Connection con = Conexion.conectar(); 
         PreparedStatement pst = con.prepareStatement(sql)) {
        
        pst.setString(1, v.getCod_venta());
        pst.setString(2, v.getCompradores().getId()); 
        pst.setInt(3, v.getAnno());
        pst.setString(4, v.getMetodoPago());
        pst.setString(5, v.getObras().getId());       
        
        pst.executeUpdate();
        ventas.Push(v); 
        
    } catch (SQLException e) {
        System.err.println("Error al guardar venta: " + e.getMessage());
    }
}

public void eliminarUltimaVenta() {
    if (ventas.isEmpty()) return;

    Venta v = ventas.Pop(); 
    String idObra = v.getObras().getId();

    try (Connection con = Conexion.conectar()) {
        con.setAutoCommit(false);
        
     
        String sqlVenta = "DELETE FROM venta WHERE cod_venta = ?";
        PreparedStatement pstV = con.prepareStatement(sqlVenta);
        pstV.setString(1, v.getCod_venta());
        pstV.executeUpdate();

    
        String sqlObra = "DELETE FROM obras WHERE id = ?";
        PreparedStatement pstO = con.prepareStatement(sqlObra);
        pstO.setString(1, idObra);
        pstO.executeUpdate();

        con.commit();
        
     
        removerObraDeColaLocal(idObra);
        
    } catch (SQLException e) {
        System.err.println("Error al deshacer venta: " + e.getMessage());
    }
}

private void removerObraDeColaLocal(String id) {
    int n = Obras.size();
    for (int i = 0; i < n; i++) {
        obras o = Obras.Poll();
        if (!o.getId().equals(id)) {
            Obras.add(o);
        }
    }
}

public void vaciarHistorial() {
    try (Connection con = Conexion.conectar()) {
        con.setAutoCommit(false);

       
        String sqlObras = "DELETE FROM obras WHERE id IN (SELECT id_obra FROM venta)";
        con.createStatement().executeUpdate(sqlObras);

    
        String sqlVentas = "DELETE FROM venta";
        con.createStatement().executeUpdate(sqlVentas);

        con.commit();
        
 
        while (!ventas.isEmpty()) ventas.Pop();
        cargarDatosDesdeDB(); 
        
    } catch (SQLException e) {
        System.err.println("Error al vaciar historial: " + e.getMessage());
    }
}

public boolean existeVenta(String cod) {
    String sql = "SELECT 1 FROM venta WHERE cod_venta = ?";
    try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, cod);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    } catch (SQLException e) { return false; }
}


    
    public void agregarComprador(comprador c) {
        String sql = "INSERT INTO comprador (id, nombre, tipo) VALUES (?, ?, ?)";
        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, c.getId());
            pst.setString(2, c.getNombre());
            pst.setString(3, c.getTipo());
            pst.executeUpdate();
            compradores.add(c);
        } catch (SQLException e) {
            System.err.println("Error comprador: " + e.getMessage());
        }
    }

    public void modificarComprador(comprador c) {
        String sql = "UPDATE comprador SET nombre=?, tipo=? WHERE id=?";
        try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, c.getNombre());
            pst.setString(2, c.getTipo());
            pst.setString(3, c.getId());
            pst.executeUpdate();
            refrescarCompradores();
        } catch (SQLException e) {
            System.err.println("Error update comprador: " + e.getMessage());
        }
    }
public void eliminarComprador(String id) {
    String sql = "DELETE FROM comprador WHERE id = ?";
    try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, id);
        pst.executeUpdate();
        refrescarCompradores(); 
    } catch (SQLException e) {
        System.err.println("Error al eliminar comprador: " + e.getMessage());
    }
}

public void refrescarCompradores() {
    compradores.clear(); 
    String sql = "SELECT * FROM comprador";
    try (Connection con = Conexion.conectar(); 
         Statement st = con.createStatement(); 
         ResultSet rs = st.executeQuery(sql)) {
        while (rs.next()) {
            comprador c = new comprador(
                rs.getString("nombre"),
                rs.getString("id"),
                rs.getString("tipo")
            );
            compradores.add(c);
        }
    } catch (SQLException e) {
        System.err.println("Error al refrescar lista: " + e.getMessage());
    }
}

public boolean existeComprador(String id) {
    String sql = "SELECT 1 FROM comprador WHERE id = ?";
    try (Connection con = Conexion.conectar(); PreparedStatement pst = con.prepareStatement(sql)) {
        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next();
    } catch (SQLException e) { return false; }
}
    
    
   
    
    private void cargarDatosDesdeDB() {
        refrescarArtistas();
        refrescarCompradores();
        refrescarObras();
        refrescarVentas();
    }

    private void refrescarArtistas() {
        Artistas = new linkedQue<>(); 
        try (Connection con = Conexion.conectar(); 
             Statement st = con.createStatement(); 
             ResultSet rs = st.executeQuery("SELECT * FROM artista")) {
            while (rs.next()) {
                Artistas.add(new artista(rs.getString("nombre"), rs.getString("id"), rs.getString("pais"), rs.getInt("edad"), rs.getString("tecnica")));
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    
    
    private void refrescarObras() {
    Obras = new linkedQue<>(); 
    String sql = "SELECT * FROM obras";
    
    try (Connection con = Conexion.conectar();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        
        while (rs.next()) {
          
            String idArt = rs.getString("artista_id");
            artista artObj = buscarArtistaEnCola(idArt);
            
            obras o = new obras(
                rs.getString("id"),
                rs.getString("titulo"),
                rs.getString("categoria"),
                rs.getInt("anno"),
                rs.getString("estado"),
                rs.getDouble("precio"),
                artObj,
                rs.getBoolean("expuesta")
            );
            Obras.add(o);
        }
    } catch (SQLException e) {
        System.err.println("Error al cargar Obras: " + e.getMessage());
    }
}
    
  private void refrescarVentas() {
    ventas = new linkedStack<>();
   
    String sql = "SELECT * FROM venta"; 
    
    try (Connection con = Conexion.conectar();
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        
        while (rs.next()) {
          
            String idComp = rs.getString("comprador_id");
            String idObra = rs.getString("obra_id");     
            
            
            comprador c = buscarCompradorEnCola(idComp);
            obras o = buscarObraEnCola(idObra);
            
            if (c != null && o != null) {
                Venta v = new Venta(
                    rs.getString("cod_venta"),
                    c,
                    rs.getInt("anno"),
                    rs.getString("metodo_pago"),
                    o
                );
                ventas.Push(v);
            }
        }
    } catch (SQLException e) {
        System.err.println("Error al cargar Ventas: " + e.getMessage());
    }
}
 
public boolean existeArtista(String id) {
    String sql = "SELECT 1 FROM artista WHERE id = ?";
    try (Connection con = Conexion.conectar();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next(); 

    } catch (SQLException e) {
        return false;
    }
}

public boolean existeObra(String id) {
    String sql = "SELECT 1 FROM obras WHERE id = ?";
    try (Connection con = Conexion.conectar();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, id);
        ResultSet rs = pst.executeQuery();
        return rs.next();

    } catch (SQLException e) {
        return false;
    }
}
public boolean artistaTieneObras(String artistaId) {
    String sql = "SELECT 1 FROM obras WHERE artista_id = ?";
    try (Connection con = Conexion.conectar();
         PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, artistaId);
        ResultSet rs = pst.executeQuery();
        return rs.next();

    } catch (SQLException e) {
        return false;
    }
}

    public linkedQue<obras> getObras() {
        return Obras;
    }

    public void setObras(linkedQue<obras> Obras) {
        this.Obras = Obras;
    }

    public linkedQue<artista> getArtistas() {
        return Artistas;
    }

    public void setArtistas(linkedQue<artista> Artistas) {
        this.Artistas = Artistas;
    }

    public linkedStack<Venta> getVentas() {
        return ventas;
    }

    public void setPilaVentas(linkedStack<Venta> ventas) {
        this.ventas = ventas;
    }

  

    public linkedQue<comprador> getCompradores() {
        return compradores;
    }

    public void setCompradores(linkedQue<comprador> compradores) {
        this.compradores = compradores;
    }




private artista buscarArtistaEnCola(String id) {
    artista encontrado = null;
    int n = Artistas.size();
    for (int i = 0; i < n; i++) {
        artista a = Artistas.Poll();
        if (a.getId().equals(id)) encontrado = a;
        Artistas.add(a); 
    }
   
    if (encontrado == null) return new artista("Desc", id, "?", 0, "?");
    return encontrado;
}

private obras buscarObraEnCola(String id) {
    obras encontrado = null;
    int n = Obras.size();
    for (int i = 0; i < n; i++) {
        obras o = Obras.Poll();
        if (o.getId().equals(id)) encontrado = o;
        Obras.add(o);
    }
    return encontrado;
}

private comprador buscarCompradorEnCola(String id) {
    comprador encontrado = null;
    int n = compradores.size();
    for (int i = 0; i < n; i++) {
        comprador c = compradores.Poll();
        if (c.getId().equals(id)) encontrado = c;
        compradores.add(c);
    }
    return encontrado;
}
  

public boolean compradorTieneVentas(String idComprador) {
    linkedStack<Venta> temp = new linkedStack<>();
    boolean tiene = false;
    
    
    while (!ventas.isEmpty()) {
        Venta v = ventas.Pop();
        if (v.getCompradores().getId().equals(idComprador)) {
            tiene = true;
        }
        temp.Push(v);
    }
    
    
    while (!temp.isEmpty()) {
        ventas.Push(temp.Pop());
    }
    
    return tiene;
}

public boolean obraEstaVendida(String idObra) {
    linkedStack<Venta> temp = new linkedStack<>();
    boolean vendida = false;
    while (!ventas.isEmpty()) {
        Venta v = ventas.Pop();
        if (v.getObras().getId().equals(idObra)) vendida = true;
        temp.Push(v);
    }
    while (!temp.isEmpty()) ventas.Push(temp.Pop());
    return vendida;
}

}
    

    
