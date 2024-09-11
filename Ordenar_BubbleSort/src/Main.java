public class Main {
    public static void main(String[] args) {
        int[] numeros = {5, 3, 2, 4, 7, 1, 0, 6};

        BubbleSort bubbleSort = new BubbleSort();
        bubbleSort.ordenaNumeros(numeros);
        bubbleSort.exibirVetor(numeros);
    }
}