import java.io.*;
import java.util.Objects;
import java.util.Scanner;

/**
 * ListaAviones es una clase que contiene una la
 * lista de aviones y los metodos necesarios para menejar
 * estos
 *
 * @author  Isaac Lopez
 * @author  Fedor Kunin
 * @version     1.0
 */
public class ListaAviones {

    /**
     * Constructor of the class
     *
     * @param capacidad el numero de aviones maximo en la lista
     * @param listaAviones lista de aviones
     */

    private int capacidad;
    private Avion[] listaAviones;

    public ListaAviones(int capacidad){
        this.capacidad = capacidad;
        this.listaAviones = new Avion[capacidad];
        for (int i = 0; i < capacidad; i++){
            listaAviones[i] = null;
        }
    };
    public int getOcupacion(){
        int ocupacion = 0;
        for (Avion avion: listaAviones
        ) {
            if (avion != null) ocupacion++;
        }
        return ocupacion;
    };
    public boolean estaLlena(){
        return this.getOcupacion() == capacidad;
        };
    public Avion getAvion(int posicion){
        return listaAviones[posicion];
    };

    /**
     * Introduce un avion en la lista comprobando que no este
     * llena antes
     * @param avion avion a introducir en la lista
     * @return boolean indicando si se ha podido introducir o no
     */
    public boolean insertarAvion(Avion avion){
        if (!this.estaLlena()){
            this.listaAviones[this.getOcupacion()] = avion;
            return true;
        }
        return false;
    };

    /**
     * Busca un avion en la lista usando su matricula
     * @param matricula del avion a buscar en la lista
     * @return avion de tipo Avion
     */
    public Avion buscarAvion(String matricula){
        Avion avionBuscado = null;
        for (Avion avion: listaAviones
        ) {
            if (avion != null){
                if (avion.getMatricula().equals(matricula)){
                    avionBuscado = avion;
                }
            }
        }
        return avionBuscado;
    };

    /**
     * Permite seleccionar un avión existente a partir de su matrícula, y comprueba si dispone de un alcance mayor o igual que el pasado como argumento,
     * usando el mensaje pasado como argumento para la solicitud y siguiendo el orden y los textos mostrados en el enunciado
     * La función solicita repetidamente la matrícula del avión hasta que se introduzca uno con alcance suficiente
     * @param teclado Scanner
     * @param mensaje usado para pedir la informacion
     * @param alcance del viaje
     * @return el avion tipo Avion que cumple las caracteristicas necesarias
     */
    public Avion seleccionarAvion(Scanner teclado, String mensaje, double alcance){
        String matricula;
        do {
            System.out.print(mensaje);
            matricula = teclado.next();
            teclado.nextLine();
            if (this.buscarAvion(matricula) == null) {
                System.out.println("Matrícula de avión no encontrada.");
            } else if ((this.buscarAvion(matricula).getAlcance() <alcance)) System.out.printf(
                    "Avión seleccionado con alcance insuficiente (menor que %.3f km).\n",
                    alcance
            );
        } while (this.buscarAvion(matricula) == null || (this.buscarAvion(matricula).getAlcance() < alcance));

        return this.buscarAvion(matricula);
    };

    /**
     * Genera un fichero CSV con la lista de aviones, sobreescribiendolo
     * @param nombre del fichero donde se quiere escribir
     * @return boolean indicando si se ha podido escribir o no
     */
    public boolean escribirAvionesCsv(String nombre){
        PrintWriter salida = null;
        boolean resultado = false;
        String linea = "";
        try {
            salida = new PrintWriter(nombre);
            for (int i = 0; i < getOcupacion(); i++) {
//                salida.printf("%s;%s;%s;%d;%d;%f",listaAviones[i].getMarca(),
//                        listaAviones[i].getModelo(),
//                        listaAviones[i].getMatricula(),
//                        listaAviones[i].getColumnas(),
//                        listaAviones[i].getFilas(),
//                        listaAviones[i].getAlcance());

                linea = listaAviones[i].getMarca() + ";" + listaAviones[i].getModelo() + ";" + listaAviones[i].getMatricula() + ";" + listaAviones[i].getColumnas() + ";" + listaAviones[i].getFilas() + ";" + listaAviones[i].getAlcance();

                salida.println(linea);
            }
            resultado = true;
        } catch (FileNotFoundException _exc){
            System.out.println("Fichero Aviones no encontrado.");

        } catch (Exception _exc){
            System.out.println("Error de escritura de fichero Aviones.");

        } finally {
            if (salida != null){
                try {
                    salida.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Aviones.");
                }
            }
        }
        return resultado;
    };

    //Métodos estáticos

    /**
     * Genera una lista de aviones a partir del fichero CSV, usando el argumento como
     * capacidad máxima de la lista
     * @param fichero del cual se quiere leer la informacion
     * @param capacidad numero de aviones maximos que se pueden leer
     * @return boolean indicando si se ha podido leer o no
     */
    public static ListaAviones leerAvionesCsv(String fichero, int capacidad){
        Scanner entrada = null;
        ListaAviones lista = new ListaAviones(capacidad);
        int lineas = 0;
        if (Utilidades.contarLineasFichero(fichero, "Aviones") != -1){
            lineas = Utilidades.contarLineasFichero(fichero, "Aviones");
        }
        try {
            entrada = new Scanner(new FileReader(fichero));
            for (int i = 0; i < Math.min(lineas, capacidad); i++){
                String[] linea = entrada.nextLine().split(";");
                String marca = linea[0];
                String modelo = linea[1];
                String matricula = linea[2];
                int filas = Integer.parseInt(linea[3]);
                int columnas = Integer.parseInt(linea[4]);
                double alcance = Double.parseDouble(linea[5]);

                lista.listaAviones[i] = new Avion(marca,modelo,matricula,columnas,filas,alcance);

            }
        } catch (FileNotFoundException _exc){

            System.out.println("Fichero Aviones no encontrado.");

        } catch (Exception _exc){

            System.out.println("Error de lectura de fichero Aviones.");

        } finally {
            if (entrada != null){
                try {
                    entrada.close();
                } catch (Exception _exc){
                    System.out.println("Error de cierre de fichero Aviones.");
                }
            }
        }
        return lista;
    };
}
