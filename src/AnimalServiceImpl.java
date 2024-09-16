import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AnimalServiceImpl implements AnimalService {
    private List<Animal> animales = new ArrayList<>();

    @Override
    public void registrarAnimal(Animal animal) {
        animales.add(animal);
    }

    @Override
    public void actualizarHistorialMedico(String nombreAnimal, String historialMedico) {
        Animal animal = buscarAnimalPorNombre(nombreAnimal);
        if (animal != null) {
            animal.agregarControl(new ControlAnimal(LocalDate.now(), historialMedico));
        }
    }

    @Override
    public void registrarControl(String nombreAnimal, ControlAnimal control) {
        Animal animal = buscarAnimalPorNombre(nombreAnimal);
        if (animal != null) {
            animal.agregarControl(control);
        }
    }

    @Override
    public List<Animal> obtenerAnimalesSinControlDelMes(LocalDate fechaReferencia) {
        return animales.stream()
                .filter(animal -> animal.getControles().stream()
                        .noneMatch(control -> control.getFechaControl().getMonth() == fechaReferencia.getMonth() &&
                                control.getFechaControl().getYear() == fechaReferencia.getYear()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Animal> obtenerAnimalesParaFuturaRevision(LocalDate fechaReferencia) {
        LocalDate proximaRevision = fechaReferencia.plusMonths(1);
        return animales.stream()
                .filter(animal -> animal.getControles().stream()
                        .noneMatch(control -> control.getFechaControl().isAfter(proximaRevision)))
                .collect(Collectors.toList());
    }

    private Animal buscarAnimalPorNombre(String nombre) {
        return animales.stream()
                .filter(animal -> animal.getNombre().equals(nombre))
                .findFirst().orElse(null);
    }
}
