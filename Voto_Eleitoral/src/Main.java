import java.text.MessageFormat;

public class Main {
    public static void main(String[] args) {
        Voto voto = new Voto(800, 150, 50);
        System.out.println(MessageFormat.format("Total de votos válidos: {0,number,#.##}%", voto.votosValidos()));
        System.out.println(MessageFormat.format("Total de votos brancos: {0,number,#.##}%", voto.votosBrancos()));
        System.out.println(MessageFormat.format("Total de votos nulos: {0,number,#.##}%", voto.votosNulos()));
    }
}