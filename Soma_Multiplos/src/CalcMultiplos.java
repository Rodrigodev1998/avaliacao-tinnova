public class CalcMultiplos {
    private final int x;

    public CalcMultiplos(int x){
        this.x = x;
    }
    public int calcularSomaMultiplos() {
        int soma = 0;

        for (int i = x - 1; i > 0; i--) {
            if (i % 3 == 0 || i % 5 == 0) {
                soma += i;
            }
        }
        return soma;
    }
    public void exibirResultado() {
        int resultado = calcularSomaMultiplos();
        System.out.println("Total da Soma dos Múltiplos de 3 ou 5 = " + resultado);
    }

    public int getX() {
        return x;
    }
}
