import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AnimalService animalService = new AnimalServiceImpl();
        AnimalController controller = new AnimalController(animalService);

        while (true) {
            System.out.println("=== Gestión de Animales ===");
            System.out.println("1. Registrar nuevo animal");
            System.out.println("2. Registrar control de bienestar");
            System.out.println("3. Ver animales sin control mensual");
            System.out.println("4. Ver animales para futura revisión");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    controller.registrarNuevoAnimal();
                    break;
                case 2:
                    controller.registrarNuevoControl();
                    break;
                case 3:
                    controller.mostrarAnimalesSinControlDelMes();
                    break;
                case 4:
                    controller.mostrarAnimalesParaFuturaRevision();
                    break;
                case 5:
                    System.out.println("Saliendo...");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }
}
