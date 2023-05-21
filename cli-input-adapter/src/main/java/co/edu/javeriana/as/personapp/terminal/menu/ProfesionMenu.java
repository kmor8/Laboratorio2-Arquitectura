package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
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
                        profesionInputAdapterCli.setProfessionOutputPortInjection("MARIA");
                        menuOpciones(profesionInputAdapterCli,keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        profesionInputAdapterCli.setProfessionOutputPortInjection("MONGO");
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
/*
                        Scanner data = new Scanner(System.in);

                        System.out.println("Ingrese el cc:");
                        Integer cc = data.nextInt();
                        data.nextLine();  // Agregar esta línea

                        System.out.println("Ingrese el nombre:");
                        String nombre = data.nextLine();

                        System.out.println("Ingrese el apellido:");
                        String apellido = data.nextLine();

                        System.out.println("Ingrese el genero:");
                        String line = data.nextLine();
                        Character genero = line.charAt(0); // Obtener el primer carácter

                        System.out.println("Ingrese la edad:");
                        Integer edad = data.nextInt();
                        data.nextLine();  // Agregar esta línea

                        System.out.println("Datos recolectados");

                        personaInputAdapterCli.crearP(cc,nombre,apellido,genero,edad);
*/
                        break;

                    case OPCION_BORRAR_PROFESION:
                        /*Scanner data2 = new Scanner(System.in);

                        System.out.println("Ingrese el cc de la persona a eliminar:");
                        Integer deletecc = data2.nextInt();
                        data2.nextLine();  // Agregar esta línea

                        personaInputAdapterCli.borrarP(deletecc);*/

                    case OPCION_ACTUALIZAR_PROFESION:
                        /*
                        Scanner data3 = new Scanner(System.in);
                        System.out.println("Ingrese el cc de la persona a editar:");
                        Integer update = data3.nextInt();
                        data3.nextLine();  // Agregar esta línea
                        menuOpcionesMod(personaInputAdapterCli,keyboard,update);*/

                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InputMismatchException e) {
                log.warn("Solo se permiten números.");
            }
        } while (!isValid);
    }

    private void menuOpcionesMod(ProfesionInputAdapterCli profesionInputAdapterCli,Scanner keyboard,Integer cc)
    {
        boolean isValid = false;
        do {
            try {

                //Person up = personaInputAdapterCli.find(cc);

                /*if(up.getIdentification()!=null)
                {
                    mostrarMenuOpcionesMod();
                    int opcion = leerOpcion(keyboard);
                    Scanner data1 = new Scanner(System.in);
                    switch (opcion)
                    {

                        case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
                            isValid = true;
                            break;
                        /*case UPDATE_NOMBRE:
                            System.out.println("Ingrese el nuevo nombre: ");
                            String name = data1.nextLine();
                            up.setFirstName(name);
                            personaInputAdapterCli.updateP(cc,up);
                            break;
                        case UPDATE_APELLIDO:
                            System.out.println("Ingrese el nuevo apellido: ");
                            String ape = data1.nextLine();
                            up.setLastName(ape);
                            personaInputAdapterCli.updateP(cc,up);
                            break;

                        case UPDATE_EDAD:
                            System.out.println("Ingrese la nueva edad: ");
                            Integer edad = data1.nextInt();
                            up.setAge(edad);
                            personaInputAdapterCli.updateP(cc,up);
                            break;

                        case UPDATE_GENERO:
                            System.out.println("Ingrese el nuevo genero: ");
                            String line = data1.nextLine();
                            Gender gen = Gender.OTHER;
                            if(line.equals("F"))
                                gen = Gender.FEMALE;
                            else if (line.equals("M"))
                                gen = Gender.MALE;
                            up.setGender(gen);
                            personaInputAdapterCli.updateP(cc,up);

                            break;

                        default:
                            log.warn("La opción elegida no es válida.");
                    }
                }
                else{
                    isValid=true;
                }*/

            } catch (InputMismatchException e) {
                log.warn("Solo se permiten números.");
            }
        } while (!isValid);
    }
    private void mostrarMenuOpciones() {
        System.out.println("----------------------");
        System.out.println(OPCION_VER_TODO + " para ver todas las personas");
        System.out.println(OPCION_CREAR_PROFESION+" para crear una persona");
        System.out.println(OPCION_BORRAR_PROFESION+" para eliminar una persona");
        System.out.println(OPCION_ACTUALIZAR_PROFESION+ " para modificar una persona");
        System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
    }

    private void mostrarMenuOpcionesMod()
    {
        System.out.println("----------------------");
        /*
        System.out.println(UPDATE_NOMBRE+" para modificar el nombre");
        System.out.println(UPDATE_APELLIDO+" para modificar el apellido");
        System.out.println(UPDATE_EDAD+" para modificar la edad");
        System.out.println(UPDATE_GENERO+" para modificar el genero");
        System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");*/
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


