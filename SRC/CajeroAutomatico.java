import java.util.Scanner;

public class CajeroAutomatico {

    static String titular = "Arturo Pérez";
    static String cuenta = "22044";
    static final int PIN = 2026;
    static double saldo = 1000.00;
    static final double COMISION = 10.00;


    static int depositosExitosos = 0;
    static double totalDepositado = 0;
    static int retirosExitosos = 0;
    static double totalRetirado = 0;
    static double totalComisiones = 0;
    static int operacionesRechazadas = 0;
    static int opcionesInvalidas = 0;
    static double saldoInicial = saldo;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!validarAcceso(sc)) return;

        int opcion;
        do {
            mostrarMenu();
            opcion = leerOpcion(sc);
            procesarOpcion(opcion, sc);
        } while(opcion != 6);
    }


    public static boolean validarAcceso(Scanner sc) {
        for(int i=1; i<=3; i++) {
            System.out.print("Ingrese su PIN: ");
            int pinIngresado = sc.nextInt();
            if(pinIngresado == PIN) {
                System.out.println("Bienvenido " + titular + "!");
                return true;
            } else {
                System.out.println("PIN incorrecto. Intentos restantes: " + (3-i));
            }
        }
        System.out.println("Cuenta bloqueada por exceso de intentos.");
        return false;
    }


    public static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Consultar saldo");
        System.out.println("2. Depositar dinero");
        System.out.println("3. Retiro normal");
        System.out.println("4. Retiro con comisión");
        System.out.println("5. Resumen de sesión");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }


    public static int leerOpcion(Scanner sc) {
        return sc.nextInt();
    }


    public static void procesarOpcion(int opcion, Scanner sc) {
        switch(opcion) {
            case 1: consultarSaldo(); break;
            case 2: depositar(sc); break;
            case 3: retirar(sc); break;
            case 4: retirar(sc, COMISION); break;
            case 5: mostrarResumen(); break;
            case 6:
                mostrarResumen();
                System.out.println("Gracias por usar el cajero. ¡Hasta pronto!");
                break;
            default:
                System.out.println("Opción inválida.");
                opcionesInvalidas++;

        }
    }


    public static void consultarSaldo() {
        System.out.printf("Titular: %s\nCuenta: %s\nSaldo disponible: Q%.2f\n", titular, cuenta, saldo);
    }


    public static void depositar(Scanner sc) {
        double monto;
        while(true) {
            System.out.print("Ingrese monto a depositar: ");
            monto = sc.nextDouble();
            if(monto <= 0) {
                System.out.println("El monto debe ser mayor que Q0.00");
            } else if(monto > 5000) {
                System.out.println("El monto no puede superar Q5,000.00");
            } else {
                break;
            }
        }
        double saldoAnterior = saldo;
        saldo += monto;
        depositosExitosos++;
        totalDepositado += monto;
        System.out.printf("Depósito: Q%.2f\nSaldo anterior: Q%.2f\nSaldo actualizado: Q%.2f\n", monto, saldoAnterior, saldo);
    }


    public static void retirar(Scanner sc) {
        System.out.print("Ingrese monto a retirar: ");
        int monto = sc.nextInt();
        if(monto <= 0) {
            System.out.println("El monto debe ser mayor que Q0.00");
            operacionesRechazadas++;
            return;
        }
        if(monto % 20 != 0) {
            System.out.println("El monto debe ser múltiplo de Q20.00");
            operacionesRechazadas++;
            return;
        }
        if(monto > 2000) {
            System.out.println("El monto no puede superar Q2,000.00");
            operacionesRechazadas++;
            return;
        }
        if(monto > saldo) {
            System.out.println("Fondos insuficientes.");
            operacionesRechazadas++;
            return;
        }
        double saldoAnterior = saldo;
        saldo -= monto;
        retirosExitosos++;
        totalRetirado += monto;
        System.out.printf("Retiro: Q%d\nSaldo anterior: Q%.2f\nTotal debitado: Q%d\nSaldo actualizado: Q%.2f\n", monto, saldoAnterior, monto, saldo);
    }


    public static void retirar(Scanner sc, double comision) {
        System.out.print("Ingrese monto a retirar (con comisión): ");
        int monto = sc.nextInt();
        double total = monto + comision;
        if(monto <= 0 || monto % 20 != 0 || monto > 2000 || total > saldo) {
            System.out.println("Operación rechazada. Verifique monto y fondos.");
            operacionesRechazadas++;
            return;
        }
        double saldoAnterior = saldo;
        saldo -= total;
        retirosExitosos++;
        totalRetirado += monto;
        totalComisiones += comision;
        System.out.printf("Retiro: Q%d\nComisión: Q%.2f\nSaldo anterior: Q%.2f\nTotal debitado: Q%.2f\nSaldo actualizado: Q%.2f\n", monto, comision, saldoAnterior, total, saldo);
    }


    public static void mostrarResumen() {
        System.out.println("\n--- RESUMEN DE LA SESIÓN ---");
        System.out.printf("Saldo inicial: Q%.2f\n", saldoInicial);
        System.out.println("Depósitos exitosos: " + depositosExitosos);
        System.out.printf("Total depositado: Q%.2f\n", totalDepositado);
        System.out.println("Retiros exitosos: " + retirosExitosos);
        System.out.printf("Total entregado en retiros: Q%.2f\n", totalRetirado);
        System.out.printf("Total cobrado en comisiones: Q%.2f\n", totalComisiones);
        System.out.println("Operaciones rechazadas: " + operacionesRechazadas);
        System.out.println("Opciones inválidas: " + opcionesInvalidas);
        System.out.printf("Saldo actual: Q%.2f\n", saldo);
    }
}

