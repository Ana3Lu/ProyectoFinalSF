import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class AnimalController {
    private AnimalService animalService;
    private Scanner scanner;

    public AnimalController(AnimalService animalService) {
        this.animalService = animalService;
        this.scanner = new Scanner(System.in);
    }

    public void registrarNuevoAnimal() {
        System.out.println("Ingrese la especie del animal:");
        String especie = scanner.nextLine();
        System.out.println("Ingrese el nombre del animal:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese la edad del animal:");
        int edad = scanner.nextInt();
        scanner.nextLine();  // Limpiar buffer
        System.out.println("Ingrese la procedencia del animal:");
        String procedencia = scanner.nextLine();

        Animal animal = new Animal(especie, nombre, edad, null, null, procedencia);
        animalService.registrarAnimal(animal);
        System.out.println("Animal registrado exitosamente.");
    }

    public void registrarNuevoControl() {
        System.out.println("Ingrese el nombre del animal:");
        String nombreAnimal = scanner.nextLine();
        System.out.println("Ingrese la fecha del control (formato: yyyy-MM-dd):");
        String fechaStr = scanner.nextLine();
        LocalDate fechaControl = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.println("Ingrese el estado del animal:");
        String estado = scanner.nextLine();

        ControlAnimal control = new ControlAnimal(fechaControl, estado);
        animalService.registrarControl(nombreAnimal, control);
        System.out.println("Control registrado exitosamente.");
    }

    public void mostrarAnimalesSinControlDelMes() {
        System.out.println("Ingrese la fecha de referencia (formato: yyyy-MM-dd):");
        String fechaStr = scanner.nextLine();
        LocalDate fechaReferencia = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);

        List<Animal> animalesSinControl = animalService.obtenerAnimalesSinControlDelMes(fechaReferencia);
        if (animalesSinControl.isEmpty()) {
            System.out.println("Todos los animales tienen control en este mes.");
        } else {
            System.out.println("Animales sin control este mes:");
            animalesSinControl.forEach(animal -> System.out.println(animal.getNombre()));
        }
    }

    public void mostrarAnimalesParaFuturaRevision() {
        System.out.println("Ingrese la fecha de referencia (formato: yyyy-MM-dd):");
        String fechaStr = scanner.nextLine();
        LocalDate fechaReferencia = LocalDate.parse(fechaStr, DateTimeFormatter.ISO_LOCAL_DATE);

        List<Animal> animalesParaRevisar = animalService.obtenerAnimalesParaFuturaRevision(fechaReferencia);
        if (animalesParaRevisar.isEmpty()) {
            System.out.println("No hay animales pendientes para futura revisión.");
        } else {
            System.out.println("Animales para futura revisión:");
            animalesParaRevisar.forEach(animal -> System.out.println(animal.getNombre()));
        }
    }
}
