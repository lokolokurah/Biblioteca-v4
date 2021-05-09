package org.iesalandalus.programacion.biblioteca.mvc.vista.grafica.controladoresvistas;



import org.iesalandalus.programacion.biblioteca.mvc.controlador.IControlador;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.AudioLibro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.Libro;
import org.iesalandalus.programacion.biblioteca.mvc.modelo.dominio.LibroEscrito;
import org.iesalandalus.programacion.biblioteca.mvc.vista.grafica.utilidades.Dialogos;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ControladorAnadirLibro {
	
	private IControlador controladorMVC;
	private ObservableList<Libro> libros;
	
	@FXML private TextField tfTitulo;
	@FXML private TextField tfAutor;
	@FXML private TextField tfPD;
	@FXML private Label pDLabel;
	@FXML private Button btAnadir;
	@FXML private Button btCancelar;
	@FXML private ComboBox<String> cbLibro;
	
	@FXML
	private void initialize() {
		tfTitulo.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(tfTitulo));
		tfAutor.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(tfAutor));
		tfPD.textProperty().addListener((ob, ov, nv) -> compruebaCampoTexto(tfPD));
		cbLibro.valueProperty().addListener((ob, ov, nv) -> actualizarLibro(cbLibro));
	}
	
	public void setControladorMVC(IControlador controladorMVC) {
		this.controladorMVC = controladorMVC;
	}
	
	public void setLibros(ObservableList<Libro> libros) {
		this.libros = libros;
	}
	
	@FXML
	private void anadirLibro() {
		Libro libro = null;
		try {
			libro = getLibro();
			controladorMVC.insertar(libro);
			libros.setAll(controladorMVC.getLibros());
			Stage propietario = ((Stage) btAnadir.getScene().getWindow());
			Dialogos.mostrarDialogoInformacion("Añadir Libro", "Libro añadido satisfactoriamente", propietario);
		} catch (Exception e) {
			Dialogos.mostrarDialogoError("Añadir Libro", e.getMessage());
		}	
	}
	
	@FXML
	private void cancelar() {
		((Stage) btCancelar.getScene().getWindow()).close();
	}
	
    public void inicializa() {
    	cbLibro.getItems().setAll("Libro Escrito","Audiolibro");
    	cbLibro.getSelectionModel().select("Libro Escrito");
    	tfTitulo.setText("");
    	compruebaCampoTexto(tfTitulo);
    	tfAutor.setText("");
    	compruebaCampoTexto(tfAutor);
    	tfPD.setText("");
    	compruebaCampoTexto(tfPD);
    }
    
    private void actualizarLibro(ComboBox<String> tipo) {
    	if (tipo.getValue()=="Libro Escrito") {
    		pDLabel.setText("Páginas");
    	} else {
    		pDLabel.setText("Duración (m)");
    	}
    }
    
	private void compruebaCampoTexto(TextField campoTexto) {	
		String texto = campoTexto.getText();
		if (!texto.isEmpty()) {
			campoTexto.setStyle("-fx-border-color: green; -fx-border-radius: 5;");
		}
		else {
			campoTexto.setStyle("-fx-border-color: red; -fx-border-radius: 5;");
		}
	}
	
	private Libro getLibro() {
		String titulo = tfTitulo.getText();
		String autor = tfAutor.getText();
		String pD = tfPD.getText();
		if (cbLibro.getValue()=="Libro Escrito") {
			return new LibroEscrito(titulo, autor, Integer.parseInt(pD));
		} else {
			return new AudioLibro(titulo, autor, Integer.parseInt(pD));
		}
	}

}
