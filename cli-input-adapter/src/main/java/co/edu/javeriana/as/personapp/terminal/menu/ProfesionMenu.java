package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.domain.Profesion;
import co.edu.javeriana.as.personapp.terminal.adapter.ProfesionInputAdapterCli;
import lombok.extern.slf4j.Slf4j;
import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class ProfesionMenu {

    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;

    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_TODO = 1;
    private static final int OPCION_CREAR_PROFESION=2;
    private static final int OPCION_BORRAR_PROFESION=3;
    private static final int OPCION_ACTUALIZAR_PROFESION=4;

    private static final int UPDATE_NOMBRE=1;
    private static final int UPDATE_DESCRIPCION=2;

    public void iniciarMenu(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuMotorPersistencia();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MODULOS:
                        isValid = true;
                        break;
                    case PERSISTENCIA_MARIADB:
                        profesionInputAdapterCli.setProfesionOutputPortInjection("MARIA");
                        menuOpciones(profesionInputAdapterCli,keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        profesionInputAdapterCli.setProfesionOutputPortInjection("MONGO");
                        menuOpciones(profesionInputAdapterCli,keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            }  catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
        boolean isValid = false;
        do {
            try {
                mostrarMenuOpciones();
                int opcion = leerOpcion(keyboard);
                switch (opcion) {
                    case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
                        isValid = true;
                        break;
                    case OPCION_VER_TODO:
                        profesionInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR_PROFESION:
                        Scanner data = new Scanner(System.in);

                        System.out.println("Ingrese el id:");
                        Integer id = data.nextInt();
                        data.nextLine();  // Agregar esta línea

                        System.out.println("Ingrese el nombre:");
                        String nombre = data.nextLine();

                        System.out.println("Ingrese la descripcion:");
                        String des = data.nextLine();

                        System.out.println("Datos recolectados");
                        profesionInputAdapterCli.crearP(id,nombre,des);
                        break;

                    case OPCION_BORRAR_PROFESION:
                        Scanner data2 = new Scanner(System.in);

                        System.out.println("Ingrese el id de la profesion a eliminar:");
                        Integer deletecc = data2.nextInt();
                        data2.nextLine();  // Agregar esta línea
                        profesionInputAdapterCli.borrarP(deletecc);

                    case OPCION_ACTUALIZAR_PROFESION:

                        Scanner data3 = new Scanner(System.in);
                        System.out.println("Ingrese el id de la profesion a editar:");
                        Integer update = data3.nextInt();
                        data3.nextLine();  // Agregar esta línea
                        menuOpcionesMod(profesionInputAdapterCli,keyboard,update);

                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InputMismatchException e) {
                log.warn("Solo se permiten números.");
            }
        } while (!isValid);
    }

    private void menuOpcionesMod(ProfesionInputAdapterCli profesionInputAdapterCli,Scanner keyboard,Integer id)
    {
        boolean isValid = false;
        do {
            try {

                Profesion up = profesionInputAdapterCli.find(id);
                if(up.getIdentification()!=null)
                {
                    mostrarMenuOpcionesMod();
                    int opcion = leerOpcion(keyboard);
                    Scanner data1 = new Scanner(System.in);
                    switch (opcion)
                    {

                        case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
                            isValid = true;
                            break;
                        case UPDATE_NOMBRE:
                            System.out.println("Ingrese el nuevo nombre: ");
                            String name = data1.nextLine();
                            up.setName(name);
                            profesionInputAdapterCli.updateP(id,up);
                            break;
                        case UPDATE_DESCRIPCION:
                            System.out.println("Ingrese la nueva descripcion: ");
                            String des = data1.nextLine();
                            up.setDescription(des);
                            profesionInputAdapterCli.updateP(id,up);
                            break;

                        default:
                            log.warn("La opción elegida no es válida.");
                    }
                }
                else{
                    isValid=true;
                }

            } catch (InputMismatchException e) {
                log.warn("Solo se permiten números.");
            }
        } while (!isValid);
    }
    private void mostrarMenuOpciones() {
        System.out.println("----------------------");
        System.out.println(OPCION_VER_TODO + " para ver todas las profesiones");
        System.out.println(OPCION_CREAR_PROFESION+" para crear una profesion");
        System.out.println(OPCION_BORRAR_PROFESION+" para eliminar una profesion");
        System.out.println(OPCION_ACTUALIZAR_PROFESION+ " para modificar una profesion");
        System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
    }

    private void mostrarMenuOpcionesMod()
    {
        System.out.println("----------------------");

        System.out.println(UPDATE_NOMBRE+" para modificar el nombre");
        System.out.println(UPDATE_DESCRIPCION+" para modificar las descripcion");
        System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
    }

    private void mostrarMenuMotorPersistencia() {
        System.out.println("----------------------");
        System.out.println(PERSISTENCIA_MARIADB + " para MariaDB");
        System.out.println(PERSISTENCIA_MONGODB + " para MongoDB");
        System.out.println(OPCION_REGRESAR_MODULOS + " para regresar");
    }

    private int leerOpcion(Scanner keyboard) {
        try {
            System.out.print("Ingrese una opción: ");
            return keyboard.nextInt();
        } catch (InputMismatchException e) {
            log.warn("Solo se permiten números.");
            return leerOpcion(keyboard);
        }
    }

}


