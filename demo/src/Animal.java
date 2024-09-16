import java.util.ArrayList;
import java.util.List;

public class Animal {
    private String especie;
    private String nombre;
    private int edad;
    private String historialMedico;
    private String dieta;
    private String procedencia;
    private List<ControlAnimal> controles;

    public Animal(String especie, String nombre, int edad, String historialMedico, String dieta, String procedencia) {
        this.especie = especie;
        this.nombre = nombre;
        this.edad = edad;
        this.historialMedico = historialMedico;
        this.dieta = dieta;
        this.procedencia = procedencia;
        this.controles = new ArrayList<>();
    }

    public String getEspecie() {
        return especie;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getHistorialMedico() {
        return historialMedico;
    }

    public String getDieta() {
        return dieta;
    }

    public String getProcedencia() {
        return procedencia;
    }

    public List<ControlAnimal> getControles() {
        return controles;
    }

    public void agregarControl(ControlAnimal control) {
        this.controles.add(control);
    }
}
