package co.edu.javeriana.as.personapp.terminal.menu;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.domain.Gender;

import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.PersonaEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.PersonaMapperMaria;
import co.edu.javeriana.as.personapp.terminal.adapter.PersonaInputAdapterCli;
import co.edu.javeriana.as.personapp.terminal.adapter.TelefonoInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

import java.util.InputMismatchException;
import java.util.Scanner;

@Slf4j
public class TelefonoMenu {
    private static final int OPCION_REGRESAR_MODULOS = 0;
    private static final int PERSISTENCIA_MARIADB = 1;
    private static final int PERSISTENCIA_MONGODB = 2;

    private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
    private static final int OPCION_VER_TODO = 1;
    private static final int OPCION_CREAR_TELEFONO=2;
    private static final int OPCION_BORRAR_TELEFONO=3;
    private static final int OPCION_ACTUALIZAR_TELEFONO=4;

    private static final int UPDATE_OPER =1;
    private static final int UPDATE_DUENIO =2;



    // mas opciones

    public void iniciarMenu(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard,PersonaInputAdapterCli personaInputAdapterCli) {
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
                        telefonoInputAdapterCli.setPhoneOutputPortInjection("MARIA");
                        menuOpciones(telefonoInputAdapterCli,keyboard,personaInputAdapterCli);
                        break;
                    case PERSISTENCIA_MONGODB:
                        telefonoInputAdapterCli.setPhoneOutputPortInjection("MONGO");
                        menuOpciones(telefonoInputAdapterCli,keyboard,personaInputAdapterCli);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            }  catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(TelefonoInputAdapterCli telefonoInputAdapterCli, Scanner keyboard,PersonaInputAdapterCli personaInputAdapterCli) {
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
                        telefonoInputAdapterCli.historial();
                        break;
                    case OPCION_CREAR_TELEFONO:

                        Scanner data = new Scanner(System.in);

                        System.out.println("Ingrese el numero:");
                        String num = data.nextLine();
                        data.nextLine();  // Aggregator esta línea

                        System.out.println("Ingrese el operador:");
                        String oper = data.nextLine();

                        System.out.println("Ingrese el cc del dueño:");
                        Integer duenio = data.nextInt();

                        System.out.println("Datos recolectados");
                        PersonaMapperMaria maper = new PersonaMapperMaria();
                        PersonaEntity duenio2 = maper.fromDomainToAdapter(personaInputAdapterCli.find(duenio));
                        telefonoInputAdapterCli.crearT(num,oper,duenio2);

                        break;

                    case OPCION_BORRAR_TELEFONO:
                        Scanner data2 = new Scanner(System.in);

                        System.out.println("Ingrese el numero del telefono a eliminar:");
                        String deletecc = data2.nextLine();
                        data2.nextLine();  // Agregar esta línea

                        telefonoInputAdapterCli.borrarT(deletecc);

                    case OPCION_ACTUALIZAR_TELEFONO:

                        Scanner data3 = new Scanner(System.in);
                        System.out.println("Ingrese el numero del telefono a editar:");
                        String update = data3.nextLine();
                        data3.nextLine();  // Agregar esta línea
                        menuOpcionesMod(telefonoInputAdapterCli,keyboard,update);

                    default:
                        log.warn("La opción elegida no es válida.");
                }
            } catch (InputMismatchException e) {
                log.warn("Solo se permiten números.");
            }
        } while (!isValid);
    }

    private void menuOpcionesMod(TelefonoInputAdapterCli telefonoInputAdapterCli,Scanner keyboard,String cc)
    {
        boolean isValid = false;
        do {
            try {

                Phone up = telefonoInputAdapterCli.find(cc);

                if(up.getNumber()!=null)
                {
                    mostrarMenuOpcionesMod();
                    int opcion = leerOpcion(keyboard);
                    Scanner data1 = new Scanner(System.in);
                    switch (opcion)
                    {

                        case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
                            isValid = true;
                            break;
                        case UPDATE_OPER:
                            System.out.println("Ingrese el nuevo operador: ");
                            String oper = data1.nextLine();
                            up.setCompany(oper);
                            telefonoInputAdapterCli.updateT(cc,up);
                            break;
                        case UPDATE_DUENIO:
                            System.out.println("Ingrese el nuevo cc del dueño: ");
                            Integer due = data1.nextInt();
                            Person nuevo= new Person();
                            nuevo.setIdentification(due);
                            up.setOwner(nuevo);
                            telefonoInputAdapterCli.updateT(cc,up);
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
        System.out.println(OPCION_VER_TODO + " para ver todas los telefonos");
        System.out.println(OPCION_CREAR_TELEFONO+" para crear un telefono");
        System.out.println(OPCION_BORRAR_TELEFONO+" para eliminar un telefono");
        System.out.println(OPCION_ACTUALIZAR_TELEFONO+ " para modificar un telefono");
        System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
    }

    private void mostrarMenuOpcionesMod()
    {
        System.out.println("----------------------");
        System.out.println(UPDATE_OPER+" para modificar el operador");
        System.out.println(UPDATE_DUENIO+" para modificar el duenio");
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
