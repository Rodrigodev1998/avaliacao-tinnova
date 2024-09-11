public class Fatorial {

    public int calcularFatorial(int numero){
        if (numero < 0){
            throw new IllegalArgumentException("Digite um número maior ou igual a zero");
        }
        return  calcularFatorialPositivo(numero);
    }

    private int calcularFatorialPositivo(int numero) {
        int fatorial = 1;
        for (int i = 2; i <= numero; i++) {
            fatorial *= i;
        }
        return fatorial;
    }
}
