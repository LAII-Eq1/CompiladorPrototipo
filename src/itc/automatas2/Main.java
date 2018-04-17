package itc.automatas2;

import itc.automatas2.lexico.AnalizadorLexico;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        if (args.length == 0) {
            System.err.println("ERROR: Proporcione la ruta del archivo a analizar.");
            System.exit(1);
        }
        AnalizadorLexico a = new AnalizadorLexico();
        System.out.printf("ANÁLISIS DEL PROGRAMA \"%s\"\n", args[0]);
        if (a.analizar(args[0]))
            System.out.println("El analizador declaró el código como válido");
        else
            System.err.println("El analizador declaró el código como inválido");
        a.imprimirErrores();
        Thread.sleep(100);
        a.imprimirSimbolos();
    }
}
