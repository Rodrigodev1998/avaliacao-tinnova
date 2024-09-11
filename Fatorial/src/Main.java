public class Main {
    public static void main(String[] args) {
        Fatorial fatorial = new Fatorial();

        try {
            int resultado = fatorial.calcularFatorial(5);
            System.out.println("O fatorial deste número é: " + resultado);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}