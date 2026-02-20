package proyecto_final;

public class Main extends javax.swing.JFrame {

    private Galeria_Control control = new Galeria_Control();
    private java.awt.CardLayout vistaCards = new java.awt.CardLayout();
    private javax.swing.JPanel contenedorPrincipal;
// Paneles para Compradores
    private javax.swing.JPanel pnlCompradores, pnlFormularioComprador;

// Componentes de la Tabla
    private javax.swing.JTable tablaCompradores;
    private javax.swing.table.DefaultTableModel modeloTablaCompradores;

// Campos del Formulario
    private javax.swing.JTextField txtCompradorId, txtCompradorNombre;
    private javax.swing.JComboBox<String> cbxTipoComprador; // Para elegir "Natural" o "Jurídico"
    private boolean esEdicionComprador = false;
    // Paneles de secciones
    private javax.swing.JPanel pnlMenu, pnlArtistas, pnlFormularioArtista, pnlObras;

    // Componentes para la Gestión de Artistas
    private javax.swing.JTable tablaArtistas;
    private javax.swing.table.DefaultTableModel modeloTabla;

    private javax.swing.JTextField txtId, txtNombre, txtPais, txtEdad, txtTecnica;

    private boolean esEdicion = false;

    // Componentes para la Gestión de Obras
    private javax.swing.JPanel pnlFormularioObra; // Nuevo panel para el formulario
    private javax.swing.JTable tablaObras;
    private javax.swing.table.DefaultTableModel modeloTablaObras;

// Campos del formulario de Obras
    private javax.swing.JTextField txtObraId, txtObraTitulo, txtObraCategoria, txtObraAnno, txtObraEstado, txtObraPrecio;
    private javax.swing.JCheckBox chkExpuesta;
    private javax.swing.JComboBox<String> cbxArtistas; 

    private boolean esEdicionObra = false; // Bandera para saber si editamos o creamos obra

// Paneles y componentes de Ventas
    private javax.swing.JPanel pnlVentas, pnlFormularioVenta;
    private javax.swing.JTable tablaVentas;
    private javax.swing.table.DefaultTableModel modeloTablaVentas;

// Campos del Formulario
    private javax.swing.JTextField txtCodVenta, txtAnnoVenta, txtMetodoPago;
    private javax.swing.JComboBox<String> cbxObraVenta, cbxCompradorVenta;

    public Main() {
        initComponents();
        configurarNavegacion(); // Lo que nosotros agregaremos
        this.setLocationRelativeTo(null); // Centrar ventana
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    private void configurarNavegacion() {
        contenedorPrincipal = new javax.swing.JPanel(vistaCards);

        crearPanelMenu();
        crearPanelArtistas();
        crearPanelFormularioArtista();
        crearPanelObras();
        crearPanelFormularioObra();
        crearPanelVentas();
        crearPanelFormularioVenta();
        crearPanelCompradores();
        crearPanelFormularioComprador();
        contenedorPrincipal.add(pnlCompradores, "COMPRADORES");
        contenedorPrincipal.add(pnlFormularioComprador, "FORM_COMPRADOR");
        contenedorPrincipal.add(pnlMenu, "MENU");
        contenedorPrincipal.add(pnlArtistas, "ARTISTAS");
        contenedorPrincipal.add(pnlFormularioArtista, "FORM_ARTISTA");
        contenedorPrincipal.add(pnlObras, "OBRAS");
        contenedorPrincipal.add(pnlFormularioObra, "FORM_OBRA");
        contenedorPrincipal.add(pnlVentas, "VENTAS");
        contenedorPrincipal.add(pnlFormularioVenta, "FORM_VENTA");

        this.setContentPane(contenedorPrincipal);
        vistaCards.show(contenedorPrincipal, "MENU");
    }

    private void crearPanelObras() {
        pnlObras = new javax.swing.JPanel(new java.awt.BorderLayout());

        // Botones laterales
        javax.swing.JPanel pnlBotones = new javax.swing.JPanel(new java.awt.GridLayout(5, 1, 0, 15));
        pnlBotones.setPreferredSize(new java.awt.Dimension(160, 0));
        pnlBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // --- BOTÓN AGREGAR OBRA 
        javax.swing.JLabel lblAgregar = crearBotonLabel("Agregar Obra");
        lblAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // 1. VALIDACIÓN: Verificar si existen artistas antes de abrir el formulario
                if (control.getArtistas().isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(contenedorPrincipal,
                            "No se pueden registrar obras porque NO hay artistas registrados.\n"
                            + "Por favor, vaya al menú de Artistas y registre uno primero.",
                            "Requisito Faltante",
                            javax.swing.JOptionPane.WARNING_MESSAGE);
                    return; // IMPORTANTE: Esto detiene el código y no abre el formulario
                }

                // 2. Si hay artistas, procedemos normalmente
                limpiarFormularioObras();
                esEdicionObra = false;
                txtObraId.setEditable(true);
                cargarComboArtistas(); // Llenar el selector de artistas desde la BD
                vistaCards.show(contenedorPrincipal, "FORM_OBRA");
            }
        });

        javax.swing.JLabel lblModificar = crearBotonLabel("Modificar Obra");
        lblModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prepararEdicionObras();
            }
        });

        javax.swing.JLabel lblEliminar = crearBotonLabel("Eliminar Obra");
        lblEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionEliminarObras();
            }
        });

        javax.swing.JLabel lblVolver = crearBotonLabel("Volver");
        lblVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vistaCards.show(contenedorPrincipal, "MENU");
            }
        });

        pnlBotones.add(lblAgregar);
        pnlBotones.add(lblModificar);
        pnlBotones.add(lblEliminar);
        pnlBotones.add(new javax.swing.JLabel(""));
        pnlBotones.add(lblVolver);

        // Tabla de Obras (Ajustada a tu SQL)
        modeloTablaObras = new javax.swing.table.DefaultTableModel(
                new Object[]{"ID", "Título", "Categoría", "Año", "Estado", "Precio", "Expuesta", "Artista ID"}, 0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaObras = new javax.swing.JTable(modeloTablaObras);
        pnlObras.add(pnlBotones, java.awt.BorderLayout.WEST);
        pnlObras.add(new javax.swing.JScrollPane(tablaObras), java.awt.BorderLayout.CENTER);
    }

    private void crearPanelMenu() {
        pnlMenu = new javax.swing.JPanel(new java.awt.GridBagLayout());

        // El GridLayout debe tener 6 filas ahora para que quepan todos los botones
        javax.swing.JPanel cajaMenu = new javax.swing.JPanel(new java.awt.GridLayout(6, 1, 10, 15));
        cajaMenu.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK, 2),
                javax.swing.BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));

        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("MENÚ PRINCIPAL", javax.swing.SwingConstants.CENTER);
        lblTitulo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 24));
        cajaMenu.add(lblTitulo);

        // Botón ARTISTAS
        javax.swing.JLabel btnArtistas = crearBotonLabel("ARTISTAS");
        btnArtistas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarTabla();
                vistaCards.show(contenedorPrincipal, "ARTISTAS");
            }
        });
        cajaMenu.add(btnArtistas);

        // Botón OBRAS
        javax.swing.JLabel btnObras = crearBotonLabel("OBRAS");
        btnObras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarTablaObras();
                vistaCards.show(contenedorPrincipal, "OBRAS");
            }
        });
        cajaMenu.add(btnObras);

        // ============================================================
        // NUEVO: BOTÓN COMPRADORES
        // ============================================================
        javax.swing.JLabel btnCompradores = crearBotonLabel("COMPRADORES");
        btnCompradores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarTablaCompradores(); // Refresca los datos de la DB
                vistaCards.show(contenedorPrincipal, "COMPRADORES"); // Cambia de vista
            }
        });
        cajaMenu.add(btnCompradores);
        // ============================================================

        // Botón VENTAS
        javax.swing.JLabel btnVentas = crearBotonLabel("VENTAS");
        btnVentas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                actualizarTablaVentas();
                vistaCards.show(contenedorPrincipal, "VENTAS");
            }
        });
        cajaMenu.add(btnVentas);

        // Botón SALIR
        javax.swing.JLabel btnSalir = crearBotonLabel("SALIR");
        btnSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                System.exit(0);
            }
        });
        cajaMenu.add(btnSalir);

        pnlMenu.add(cajaMenu);
    }

    private void crearPanelFormularioObra() {
        pnlFormularioObra = new javax.swing.JPanel(new java.awt.GridLayout(9, 2, 10, 10));
        pnlFormularioObra.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DE LA OBRA"));

        pnlFormularioObra.add(new javax.swing.JLabel("ID:"));
        txtObraId = new javax.swing.JTextField();
        pnlFormularioObra.add(txtObraId);

        pnlFormularioObra.add(new javax.swing.JLabel("Título:"));
        txtObraTitulo = new javax.swing.JTextField();
        pnlFormularioObra.add(txtObraTitulo);

        pnlFormularioObra.add(new javax.swing.JLabel("Categoría:"));
        txtObraCategoria = new javax.swing.JTextField();
        pnlFormularioObra.add(txtObraCategoria);

        pnlFormularioObra.add(new javax.swing.JLabel("Año:"));
        txtObraAnno = new javax.swing.JTextField();
        pnlFormularioObra.add(txtObraAnno);

        pnlFormularioObra.add(new javax.swing.JLabel("Estado:"));
        txtObraEstado = new javax.swing.JTextField();
        pnlFormularioObra.add(txtObraEstado);

        pnlFormularioObra.add(new javax.swing.JLabel("Precio:"));
        txtObraPrecio = new javax.swing.JTextField();
        pnlFormularioObra.add(txtObraPrecio);

        pnlFormularioObra.add(new javax.swing.JLabel("¿Está expuesta?"));
        chkExpuesta = new javax.swing.JCheckBox();
        pnlFormularioObra.add(chkExpuesta);

        pnlFormularioObra.add(new javax.swing.JLabel("Artista:"));
        cbxArtistas = new javax.swing.JComboBox<>();
        pnlFormularioObra.add(cbxArtistas);

        javax.swing.JLabel btnGuardar = crearBotonLabel("GUARDAR");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionGuardarObras();
            }
        });

        javax.swing.JLabel btnCancelar = crearBotonLabel("CANCELAR");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vistaCards.show(contenedorPrincipal, "OBRAS");
            }
        });

        pnlFormularioObra.add(btnGuardar);
        pnlFormularioObra.add(btnCancelar);
    }

    private void crearPanelArtistas() {
        pnlArtistas = new javax.swing.JPanel(new java.awt.BorderLayout());

        // --- IZQUIERDA: Botones ---
        javax.swing.JPanel pnlBotones = new javax.swing.JPanel(new java.awt.GridLayout(5, 1, 0, 15));
        pnlBotones.setPreferredSize(new java.awt.Dimension(160, 0));
        pnlBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 20, 10));

        // Botón AGREGAR
        javax.swing.JLabel lblAgregar = crearBotonLabel("Agregar Artista");
        lblAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                limpiarFormulario();
                esEdicion = false; // Importante: Modo crear
                txtId.setEditable(true); // El ID se puede escribir
                vistaCards.show(contenedorPrincipal, "FORM_ARTISTA");
            }
        });

        // Botón MODIFICAR
        javax.swing.JLabel lblModificar = crearBotonLabel("Modificar Datos");
        lblModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prepararEdicion(); // <--- Llamada al nuevo método
            }
        });

        // Botón ELIMINAR
        javax.swing.JLabel lblEliminar = crearBotonLabel("Eliminar Datos");
        lblEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionEliminar(); // <--- Llamada al nuevo método
            }
        });

        // Botón VOLVER
        javax.swing.JLabel lblVolver = crearBotonLabel("Volver");
        lblVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vistaCards.show(contenedorPrincipal, "MENU");
            }
        });

        pnlBotones.add(lblAgregar);
        pnlBotones.add(lblModificar);
        pnlBotones.add(lblEliminar);
        pnlBotones.add(new javax.swing.JLabel("")); // Espacio vacío
        pnlBotones.add(lblVolver);

        // --- DERECHA: Tabla ---
        // Definimos las columnas exactas
        modeloTabla = new javax.swing.table.DefaultTableModel(
                new Object[]{"ID", "Nombre", "País", "Edad", "Técnica"}, 0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            } // Tabla no editable directo
        };

        tablaArtistas = new javax.swing.JTable(modeloTabla);
        pnlArtistas.add(pnlBotones, java.awt.BorderLayout.WEST);
        pnlArtistas.add(new javax.swing.JScrollPane(tablaArtistas), java.awt.BorderLayout.CENTER);
    }

    private void crearPanelFormularioArtista() {
        pnlFormularioArtista = new javax.swing.JPanel(new java.awt.GridLayout(7, 2, 10, 10)); // Cambié a 7 filas
        pnlFormularioArtista.setBorder(javax.swing.BorderFactory.createTitledBorder("DATOS DEL ARTISTA"));

        // Campos existentes
        pnlFormularioArtista.add(new javax.swing.JLabel("ID:"));
        txtId = new javax.swing.JTextField();
        pnlFormularioArtista.add(txtId);

        pnlFormularioArtista.add(new javax.swing.JLabel("Nombre:"));
        txtNombre = new javax.swing.JTextField();
        pnlFormularioArtista.add(txtNombre);

        // NUEVOS CAMPOS QUE FALTABAN
        pnlFormularioArtista.add(new javax.swing.JLabel("País:"));
        txtPais = new javax.swing.JTextField();
        pnlFormularioArtista.add(txtPais);

        pnlFormularioArtista.add(new javax.swing.JLabel("Edad:"));
        txtEdad = new javax.swing.JTextField();
        pnlFormularioArtista.add(txtEdad);

        pnlFormularioArtista.add(new javax.swing.JLabel("Técnica:"));
        txtTecnica = new javax.swing.JTextField();
        pnlFormularioArtista.add(txtTecnica);

        javax.swing.JLabel btnGuardar = crearBotonLabel("GUARDAR");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionGuardar();
            }
        });

        // Botón para cancelar/volver
        javax.swing.JLabel btnCancelar = crearBotonLabel("CANCELAR");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vistaCards.show(contenedorPrincipal, "ARTISTAS");
            }
        });

        pnlFormularioArtista.add(btnGuardar);
        pnlFormularioArtista.add(btnCancelar);
    }

    private void actualizarTablaObras() {
        modeloTablaObras.setRowCount(0);
        int n = control.getObras().size();

        for (int i = 0; i < n; i++) {
            obras o = control.getObras().Poll();

            if (!control.obraEstaVendida(o.getId())) {
                // 1. Creamos el texto legible para la columna "Expuesta"
                // Usamos isExpuesta() (o getExpuesta() según tu clase obras)
                String textoExpuesta = o.isExpuesta() ? "Expuesta" : "No expuesta";

                // 2. Agregamos la fila con los 8 datos en el orden correcto
                modeloTablaObras.addRow(new Object[]{
                    o.getId(), // Columna 0
                    o.getTitulo(), // Columna 1
                    o.getCategoria(), // Columna 2
                    o.getAño(), // Columna 3
                    o.getEstado(), // Columna 4
                    o.getPrecio(), // Columna 5
                    textoExpuesta, // Columna 7 (Estado de Exposición)
                    o.getArtista().getId() // Columna 6 (ID Artista)                
                });
            }
            control.getObras().add(o); // Mantenemos la rotación de la cola
        }
    }

    ///
    private void cargarComboArtistas() {
        cbxArtistas.removeAllItems();
        try (java.sql.Connection con = Conexion.conectar(); java.sql.Statement st = con.createStatement(); java.sql.ResultSet rs = st.executeQuery("SELECT id FROM artista")) {
            while (rs.next()) {
                cbxArtistas.addItem(rs.getString("id"));
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error combo artistas: " + e.getMessage());
        }
    }

    private void accionGuardarObras() {
        try {
            String id = txtObraId.getText().trim();

            // 🔴 VALIDACIÓN DE ID DUPLICADO
            if (!esEdicionObra && control.existeObra(id)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Ya existe una obra con ese ID.",
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            String titulo = txtObraTitulo.getText();
            String cat = txtObraCategoria.getText();
            int anno = Integer.parseInt(txtObraAnno.getText());
            String est = txtObraEstado.getText();
            double precio = Double.parseDouble(txtObraPrecio.getText());
            boolean exp = chkExpuesta.isSelected();

            String artId = cbxArtistas.getSelectedItem().toString();
            artista artistaTemp = new artista("", artId, "", 0, "");

            obras nuevaObra = new obras(id, titulo, cat, anno, est, precio, artistaTemp, exp);

            if (esEdicionObra) {
                control.modificarObra(nuevaObra);
                javax.swing.JOptionPane.showMessageDialog(this, "Obra actualizada.");
            } else {
                control.agregarObra(nuevaObra);
                javax.swing.JOptionPane.showMessageDialog(this, "Obra creada.");
            }

            actualizarTablaObras();
            vistaCards.show(contenedorPrincipal, "OBRAS");

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Año o precio inválidos.");
        }
    }

    private void accionEliminarObras() {
        int fila = tablaObras.getSelectedRow();
        if (fila >= 0) {
            String titulo = modeloTablaObras.getValueAt(fila, 1).toString();
            int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de eliminar la obra: " + titulo + "?",
                    "Confirmar Eliminación", javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                String id = modeloTablaObras.getValueAt(fila, 0).toString();

                // Llamamos al controlador (asegúrate de tener este método en Galeria_Control)
                control.eliminarObra(id);

                actualizarTablaObras(); // Refrescamos la lista
                javax.swing.JOptionPane.showMessageDialog(this, "Obra eliminada correctamente.");
            }
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona una obra para eliminar.");
        }
    }

    private void limpiarFormularioObras() {
        txtObraId.setText("");
        txtObraTitulo.setText("");
        txtObraCategoria.setText("");
        txtObraAnno.setText("");
        txtObraEstado.setText("");
        txtObraPrecio.setText("");
        chkExpuesta.setSelected(false);
    }

// Implementar prepararEdicionObras() y accionEliminarObras() siguiendo la misma lógica de artistas
    private javax.swing.JLabel crearBotonLabel(String texto) {
        javax.swing.JLabel lbl = new javax.swing.JLabel(texto, 0);
        lbl.setOpaque(true);
        lbl.setBackground(java.awt.Color.LIGHT_GRAY);
        lbl.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.BLACK));
        lbl.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        return lbl;
    }

    private void limpiarFormulario() {
        txtId.setText("");
        txtNombre.setText("");
        txtPais.setText("");
        txtEdad.setText("");
        txtTecnica.setText("");
    }

// Lógica para el botón MODIFICAR
    private void prepararEdicion() {
        int fila = tablaArtistas.getSelectedRow();
        if (fila >= 0) {
            // 1. Obtener datos de la fila seleccionada
            String id = modeloTabla.getValueAt(fila, 0).toString();
            String nom = modeloTabla.getValueAt(fila, 1).toString();
            String pais = modeloTabla.getValueAt(fila, 2).toString();
            // Nota: Asegúrate que tu consulta SQL traiga Edad (col 3) y Técnica (col 4)
            String edad = modeloTabla.getValueAt(fila, 3).toString();
            String tec = modeloTabla.getValueAt(fila, 4).toString();

            // 2. Rellenar el formulario
            txtId.setText(id);
            txtNombre.setText(nom);
            txtPais.setText(pais);
            txtEdad.setText(edad);
            txtTecnica.setText(tec);

            // 3. Configurar modo edición
            esEdicion = true;
            txtId.setEditable(false); // No dejar cambiar el ID (Clave Primaria)

            // 4. Cambiar pantalla
            vistaCards.show(contenedorPrincipal, "FORM_ARTISTA");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un artista de la lista primero.");
        }
    }

    private void accionEliminar() {
        int fila = tablaArtistas.getSelectedRow();
        if (fila >= 0) {

            String id = modeloTabla.getValueAt(fila, 0).toString();
            String nombre = modeloTabla.getValueAt(fila, 1).toString();

            // 🔴 VALIDACIÓN FK
            if (control.artistaTieneObras(id)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "No se puede eliminar el artista \"" + nombre + "\".\n"
                        + "Primero debes eliminar o reasignar sus obras.",
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            int confirm = javax.swing.JOptionPane.showConfirmDialog(this,
                    "¿Seguro que deseas eliminar a " + nombre + "?",
                    "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);

            if (confirm == javax.swing.JOptionPane.YES_OPTION) {
                control.eliminarArtista(id);
                actualizarTabla();
                javax.swing.JOptionPane.showMessageDialog(this, "Artista eliminado.");
            }

        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona un artista para eliminar.");
        }
    }

    // Método GUARDAR actualizado (Maneja Crear y Modificar)
    private void accionGuardar() {
        try {
            String id = txtId.getText().trim();
            String nom = txtNombre.getText();
            String pais = txtPais.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            String tec = txtTecnica.getText();

            artista a = new artista(nom, id, pais, edad, tec);

            //VALIDACIÓN DE ID DUPLICADO
            if (!esEdicion && control.existeArtista(id)) {
                javax.swing.JOptionPane.showMessageDialog(this,
                        "Ya existe un artista con ese ID.",
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (esEdicion) {
                control.modificarArtista(a);
                javax.swing.JOptionPane.showMessageDialog(this, "Artista actualizado.");
            } else {
                control.agregarArtista(a);
                javax.swing.JOptionPane.showMessageDialog(this, "Artista creado.");
            }

            actualizarTabla();
            vistaCards.show(contenedorPrincipal, "ARTISTAS");

        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "La edad debe ser un número.");
        }
    }

    
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        try (java.sql.Connection con = Conexion.conectar(); java.sql.Statement st = con.createStatement(); java.sql.ResultSet rs = st.executeQuery("SELECT * FROM artista ORDER BY nombre ASC")) {

            while (rs.next()) {
                modeloTabla.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("nombre"),
                    rs.getString("pais"),
                    rs.getInt("edad"), 
                    rs.getString("tecnica")
                });
            }
        } catch (java.sql.SQLException e) {
            System.err.println("Error tabla: " + e.getMessage());
        }
    }

    private void prepararEdicionObras() {
        int fila = tablaObras.getSelectedRow();
        if (fila >= 0) {
            
            String id = modeloTablaObras.getValueAt(fila, 0).toString();
            String titulo = modeloTablaObras.getValueAt(fila, 1).toString();
            String categoria = modeloTablaObras.getValueAt(fila, 2).toString();
            String anno = modeloTablaObras.getValueAt(fila, 3).toString();
            String estado = modeloTablaObras.getValueAt(fila, 4).toString();
            String precio = modeloTablaObras.getValueAt(fila, 5).toString();
            boolean expuesta = (boolean) modeloTablaObras.getValueAt(fila, 6);
            String artistaId = modeloTablaObras.getValueAt(fila, 7).toString();

            
            txtObraId.setText(id);
            txtObraTitulo.setText(titulo);
            txtObraCategoria.setText(categoria);
            txtObraAnno.setText(anno);
            txtObraEstado.setText(estado);
            txtObraPrecio.setText(precio);
            chkExpuesta.setSelected(expuesta);


            cargarComboArtistas(); 
            cbxArtistas.setSelectedItem(artistaId);

         
            esEdicionObra = true;
            txtObraId.setEditable(false);

         
            vistaCards.show(contenedorPrincipal, "FORM_OBRA");
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, selecciona una obra de la tabla.");
        }

    }

    private void crearPanelCompradores() {
        pnlCompradores = new javax.swing.JPanel(new java.awt.BorderLayout());

       
        javax.swing.JPanel pnlBotones = new javax.swing.JPanel(new java.awt.GridLayout(6, 1, 0, 15));
        pnlBotones.setPreferredSize(new java.awt.Dimension(180, 0));
        pnlBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 20, 10));

        javax.swing.JLabel lblAgregar = crearBotonLabel("Agregar Comprador");
        lblAgregar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                esEdicionComprador = false;
                limpiarFormularioComprador();
                txtCompradorId.setEditable(true);
                vistaCards.show(contenedorPrincipal, "FORM_COMPRADOR");
            }
        });

        javax.swing.JLabel lblModificar = crearBotonLabel("Modificar Datos");
        lblModificar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prepararEdicionComprador();
            }
        });

        javax.swing.JLabel lblEliminar = crearBotonLabel("Eliminar Datos");
        lblEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionEliminarComprador();
            }
        });

        javax.swing.JLabel lblVolver = crearBotonLabel("Volver al Menú");
        lblVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vistaCards.show(contenedorPrincipal, "MENU");
            }
        });

        pnlBotones.add(lblAgregar);
        pnlBotones.add(lblModificar);
        pnlBotones.add(lblEliminar);
        pnlBotones.add(new javax.swing.JLabel("")); 
        pnlBotones.add(lblVolver);

       
        modeloTablaCompradores = new javax.swing.table.DefaultTableModel(
                new Object[]{"ID", "Nombre Full", "Tipo de Persona"}, 0
        ) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaCompradores = new javax.swing.JTable(modeloTablaCompradores);
        pnlCompradores.add(pnlBotones, java.awt.BorderLayout.WEST);
        pnlCompradores.add(new javax.swing.JScrollPane(tablaCompradores), java.awt.BorderLayout.CENTER);
    }

    private void crearPanelFormularioComprador() {
        pnlFormularioComprador = new javax.swing.JPanel(new java.awt.GridLayout(5, 2, 10, 20));
        pnlFormularioComprador.setBorder(javax.swing.BorderFactory.createTitledBorder("GESTIÓN DE COMPRADOR"));

        pnlFormularioComprador.add(new javax.swing.JLabel("Identificación (ID):"));
        txtCompradorId = new javax.swing.JTextField();
        pnlFormularioComprador.add(txtCompradorId);

        pnlFormularioComprador.add(new javax.swing.JLabel("Nombre Completo:"));
        txtCompradorNombre = new javax.swing.JTextField();
        pnlFormularioComprador.add(txtCompradorNombre);

        pnlFormularioComprador.add(new javax.swing.JLabel("Tipo de Comprador:"));
        cbxTipoComprador = new javax.swing.JComboBox<>(new String[]{"Natural", "Jurídico"});
        pnlFormularioComprador.add(cbxTipoComprador);

        javax.swing.JLabel btnGuardar = crearBotonLabel("GUARDAR");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionGuardarComprador();
            }
        });

        javax.swing.JLabel btnCancelar = crearBotonLabel("CANCELAR");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vistaCards.show(contenedorPrincipal, "COMPRADORES");
            }
        });

        pnlFormularioComprador.add(btnGuardar);
        pnlFormularioComprador.add(btnCancelar);
    }

    private void accionGuardarComprador() {
        String id = txtCompradorId.getText().trim();
        String nombre = txtCompradorNombre.getText().trim();
        String tipo = cbxTipoComprador.getSelectedItem().toString();

        
        if (id.isEmpty() || nombre.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.");
            return;
        }

        
        if (!esEdicionComprador) {
           
            if (existeCompradorEnDB(id)) {
                javax.swing.JOptionPane.showMessageDialog(this, "Error: Ya existe un comprador con ese ID.");
                return;
            }
            control.agregarComprador(new comprador(nombre, id, tipo));
        } else {
            control.modificarComprador(new comprador(nombre, id, tipo));
        }

        actualizarTablaCompradores();
        vistaCards.show(contenedorPrincipal, "COMPRADORES");
    }

    private boolean existeCompradorEnDB(String id) {
       
        return control.existeComprador(id);
    }

    private void actualizarTablaCompradores() {
        if (modeloTablaCompradores == null) {
            return; 
        }
        modeloTablaCompradores.setRowCount(0);
        control.refrescarCompradores(); 

        linkedQue<comprador> cola = control.getCompradores();
        int total = cola.size();

        
        for (int i = 0; i < total; i++) {
            comprador c = cola.Poll(); 
            if (c != null) {
                modeloTablaCompradores.addRow(new Object[]{
                    c.getId(),
                    c.getNombre(),
                    c.getTipo()
                });
                cola.add(c); 
            }
        }

        tablaCompradores.revalidate();
        tablaCompradores.repaint();
    }

    private void limpiarFormularioComprador() {
        txtCompradorId.setText("");
        txtCompradorNombre.setText("");
        cbxTipoComprador.setSelectedIndex(0);
    }

    private void prepararEdicionComprador() {
        int fila = tablaCompradores.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Por favor, selecciona un comprador de la tabla.");
            return;
        }

        String id = tablaCompradores.getValueAt(fila, 0).toString();
        String nombre = tablaCompradores.getValueAt(fila, 1).toString();
        String tipo = tablaCompradores.getValueAt(fila, 2).toString();

        txtCompradorId.setText(id);
        txtCompradorNombre.setText(nombre);
        cbxTipoComprador.setSelectedItem(tipo);

        esEdicionComprador = true;
        txtCompradorId.setEditable(false);
        vistaCards.show(contenedorPrincipal, "FORM_COMPRADOR");
    }

    private void accionEliminarComprador() {
        int fila = tablaCompradores.getSelectedRow();
        if (fila == -1) {
            javax.swing.JOptionPane.showMessageDialog(this, "Selecciona al comprador que deseas eliminar.");
            return;
        }

        String id = tablaCompradores.getValueAt(fila, 0).toString();

       
        if (control.compradorTieneVentas(id)) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No se puede borrar un comprador si este esta asociado a una venta.",
                    "Error al eliminar", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
       

        int confirmar = javax.swing.JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de eliminar al comprador con ID: " + id + "?",
                "Confirmar Eliminación", javax.swing.JOptionPane.YES_NO_OPTION);

        if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
            control.eliminarComprador(id);
            actualizarTablaCompradores();
            javax.swing.JOptionPane.showMessageDialog(this, "Comprador eliminado con éxito.");
        }
    }

    private void actualizarTablaVentas() {
        modeloTablaVentas.setRowCount(0);

       
        linkedStack<Venta> original = control.getVentas();
        linkedStack<Venta> temp = new linkedStack<>();

        while (!original.isEmpty()) {
            Venta v = original.Pop(); // Tu clase usa Pop() con P mayúscula
            modeloTablaVentas.addRow(new Object[]{
                v.getCod_venta(),
                v.getObras().getTitulo(),
                v.getCompradores().getNombre(),
                v.getAnno()
            });
            temp.Push(v);
        }

        // Restaurar la pila original
        while (!temp.isEmpty()) {
            original.Push(temp.Pop());
        }
    }

// =======================================================
    // PEGAR AL FINAL DE LA CLASE MAIN (DENTRO DE LA CLASE)
    // =======================================================
    private void crearPanelVentas() {
        pnlVentas = new javax.swing.JPanel(new java.awt.BorderLayout());

        // PANEL IZQUIERDO: Botones
        javax.swing.JPanel pnlBotones = new javax.swing.JPanel(new java.awt.GridLayout(6, 1, 0, 15));
        pnlBotones.setPreferredSize(new java.awt.Dimension(200, 0));
        pnlBotones.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 20, 10));

        javax.swing.JLabel btnNueva = crearBotonLabel("Agregar Venta");
        btnNueva.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                prepararNuevaVenta();
            }
        });

        javax.swing.JLabel btnCancelar = crearBotonLabel("Cancelar Última");
        btnCancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionCancelarUltima();
            }
        });

        javax.swing.JLabel btnVaciar = crearBotonLabel("Vaciar Historial");
        btnVaciar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionVaciarHistorial();
            }
        });

        javax.swing.JLabel btnMonto = crearBotonLabel("Monto Anual");
        btnMonto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionMontoAnual();
            }
        });

        javax.swing.JLabel btnVolver = crearBotonLabel("Volver");
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vistaCards.show(contenedorPrincipal, "MENU");
            }
        });

        pnlBotones.add(btnNueva);
        pnlBotones.add(btnCancelar);
        pnlBotones.add(btnVaciar);
        pnlBotones.add(btnMonto);
        pnlBotones.add(new javax.swing.JLabel(""));
        pnlBotones.add(btnVolver);

      
        modeloTablaVentas = new javax.swing.table.DefaultTableModel(
                new Object[]{"Cod Venta", "Obra", "Comprador", "Año"}, 0
        );
        tablaVentas = new javax.swing.JTable(modeloTablaVentas);

        pnlVentas.add(pnlBotones, java.awt.BorderLayout.WEST);
        pnlVentas.add(new javax.swing.JScrollPane(tablaVentas), java.awt.BorderLayout.CENTER);
    }

    private void crearPanelFormularioVenta() {
        pnlFormularioVenta = new javax.swing.JPanel(new java.awt.GridLayout(6, 2, 10, 20));
        pnlFormularioVenta.setBorder(javax.swing.BorderFactory.createTitledBorder("REGISTRO DE VENTA"));

        txtCodVenta = new javax.swing.JTextField();
        txtAnnoVenta = new javax.swing.JTextField();
        cbxObraVenta = new javax.swing.JComboBox<>();
        cbxCompradorVenta = new javax.swing.JComboBox<>();

        pnlFormularioVenta.add(new javax.swing.JLabel("  Código de Venta:"));
        pnlFormularioVenta.add(txtCodVenta);
        pnlFormularioVenta.add(new javax.swing.JLabel("  Año de Venta:"));
        pnlFormularioVenta.add(txtAnnoVenta);
        pnlFormularioVenta.add(new javax.swing.JLabel("  Seleccione Obra:"));
        pnlFormularioVenta.add(cbxObraVenta);
        pnlFormularioVenta.add(new javax.swing.JLabel("  Seleccione Comprador:"));
        pnlFormularioVenta.add(cbxCompradorVenta);

        javax.swing.JLabel btnGuardar = crearBotonLabel("GUARDAR VENTA");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                accionGuardarVenta();
            }
        });

        javax.swing.JLabel btnVolver = crearBotonLabel("CANCELAR");
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                vistaCards.show(contenedorPrincipal, "VENTAS");
            }
        });

        pnlFormularioVenta.add(btnGuardar);
        pnlFormularioVenta.add(btnVolver);
    }

    private void prepararNuevaVenta() {
        if (control.getObras().isEmpty() || control.getCompradores().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this,
                    "No se puede realizar venta.\nFalta cargar Obras o Compradores.\n(Revisa si Galeria_Control carga la BD correctamente)",
                    "Datos Faltantes", javax.swing.JOptionPane.WARNING_MESSAGE);
            return;
        }

        txtCodVenta.setText("");
        txtAnnoVenta.setText("");
        cbxObraVenta.removeAllItems();
        cbxCompradorVenta.removeAllItems();

        linkedQue<obras> qObras = control.getObras();
        int sizeO = qObras.size();
        for (int i = 0; i < sizeO; i++) {
            obras o = qObras.Poll();
            cbxObraVenta.addItem(o.getId() + " - " + o.getTitulo());
            qObras.add(o);
        }

        linkedQue<comprador> qComp = control.getCompradores();
        int sizeC = qComp.size();
        for (int i = 0; i < sizeC; i++) {
            comprador c = qComp.Poll();
            cbxCompradorVenta.addItem(c.getId() + " - " + c.getNombre());
            qComp.add(c);
        }
        vistaCards.show(contenedorPrincipal, "FORM_VENTA");
    }

    private void accionGuardarVenta() {
        try {
            String cod = txtCodVenta.getText().trim();
            int anno = Integer.parseInt(txtAnnoVenta.getText().trim());

            if (cod.isEmpty() || anno <= 0) {
                throw new Exception("Datos inválidos");
            }

            String idObra = cbxObraVenta.getSelectedItem().toString().split(" - ")[0];
            String idComp = cbxCompradorVenta.getSelectedItem().toString().split(" - ")[0];

            obras o = buscarObraPorId(idObra);
            comprador c = buscarCompradorPorId(idComp);

            if (o != null && c != null) {
                Venta nuevaVenta = new Venta(cod, c, anno, "Efectivo", o);
                control.agregarVenta(nuevaVenta);
                actualizarTablaVentas();
                vistaCards.show(contenedorPrincipal, "VENTAS");
            }
        } catch (NumberFormatException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "El año debe ser numérico.");
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private obras buscarObraPorId(String id) {
        linkedQue<obras> cola = control.getObras();
        obras encontrada = null;
        int tam = cola.size();
        for (int i = 0; i < tam; i++) {
            obras temp = cola.Poll();
            if (temp.getId().equals(id)) {
                encontrada = temp;
            }
            cola.add(temp);
        }
        return encontrada;
    }

    private comprador buscarCompradorPorId(String id) {
        linkedQue<comprador> cola = control.getCompradores();
        comprador encontrado = null;
        int tam = cola.size();
        for (int i = 0; i < tam; i++) {
            comprador temp = cola.Poll();
            if (temp.getId().equals(id)) {
                encontrado = temp;
            }
            cola.add(temp);
        }
        return encontrado;
    }

    private void accionMontoAnual() {
        String input = javax.swing.JOptionPane.showInputDialog(this, "Ingrese el año a consultar:");
        if (input == null || input.isEmpty()) {
            return;
        }
        try {
            int annoBusqueda = Integer.parseInt(input);
            double total = 0;
            int contador = 0;
            linkedStack<Venta> temp = new linkedStack<>();
            linkedStack<Venta> original = control.getVentas();

            while (!original.isEmpty()) {
                Venta v = original.Pop();
                if (v.getAnno() == annoBusqueda) {
                    total += v.getObras().getPrecio();
                    contador++;
                }
                temp.Push(v);
            }
            while (!temp.isEmpty()) {
                original.Push(temp.Pop());
            }

            if (contador > 0) {
                String reporte = String.format("Año: %d\nTotal Ganancias: $%.2f", annoBusqueda, total);
                javax.swing.JOptionPane.showMessageDialog(this, reporte);
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "No hay ventas en " + annoBusqueda);
            }
        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Año inválido.");
        }
    }

    private void accionCancelarUltima() {
        if (control.getVentas().isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "No hay ventas para cancelar.");
            return;
        }
        int confirmar = javax.swing.JOptionPane.showConfirmDialog(this, "Eliminar última venta?", "Confirmar", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
            control.eliminarUltimaVenta();
            actualizarTablaVentas();
        }
    }

    private void accionVaciarHistorial() {
        int confirmar = javax.swing.JOptionPane.showConfirmDialog(this, "Borrar TODO el historial?", "Advertencia", javax.swing.JOptionPane.YES_NO_OPTION);
        if (confirmar == javax.swing.JOptionPane.YES_OPTION) {
            control.vaciarHistorial();
            actualizarTablaVentas();
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
