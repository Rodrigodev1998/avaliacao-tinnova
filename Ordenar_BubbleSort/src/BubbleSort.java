import java.util.Arrays;

public class BubbleSort {

    public void ordenaNumeros(int[] vetor){
        boolean troca;

        for (int i = 0; i < vetor.length - 1; i++){
            troca = false;
            for (int j = 0; j < vetor.length - 1 - i; j++){
                if (vetor[j] > vetor[j + 1]){
                    int aux = vetor[j];
                    vetor[j] = vetor[j + 1];
                    vetor[j + 1] = aux;
                    troca = true;
                }
            }
            if (!troca){
                break;
            }
        }
    }
    public void exibirVetor(int[] vetor){
        System.out.println(Arrays.toString(vetor));
    }
}
