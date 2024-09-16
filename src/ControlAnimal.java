import java.time.LocalDate;

public class ControlAnimal {
    private LocalDate fechaControl;
    private String estadoAnimal;

    public ControlAnimal(LocalDate fechaControl, String estadoAnimal) {
        this.fechaControl = fechaControl;
        this.estadoAnimal = estadoAnimal;
    }

    public LocalDate getFechaControl() {
        return fechaControl;
    }

    public String getEstadoAnimal() {
        return estadoAnimal;
    }
}
