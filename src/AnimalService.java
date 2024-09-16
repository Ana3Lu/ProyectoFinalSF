import java.time.LocalDate;
import java.util.List;

public interface AnimalService {
    void registrarAnimal(Animal animal);
    void actualizarHistorialMedico(String nombreAnimal, String historialMedico);
    void registrarControl(String nombreAnimal, ControlAnimal control);
    List<Animal> obtenerAnimalesSinControlDelMes(LocalDate fechaReferencia);
    List<Animal> obtenerAnimalesParaFuturaRevision(LocalDate fechaReferencia);
}
