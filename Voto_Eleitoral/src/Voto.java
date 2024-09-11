import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Voto {
    private int totalVotos;
    private int votosValidos;
    private int votosBrancos;
    private int votosNulos;

    public Voto(int votosValidos, int votosBrancos, int votosNulos) {
        this.votosValidos = votosValidos;
        this.votosBrancos = votosBrancos;
        this.votosNulos = votosNulos;
        this.totalVotos = votosValidos + votosBrancos + votosNulos;
    }

    public double votosValidos() {
        return calcularPorcentagem(votosValidos);
    }

    public double votosBrancos() {
        return calcularPorcentagem(votosBrancos);
    }

    public double votosNulos() {
        return calcularPorcentagem(votosNulos);
    }

    private double calcularPorcentagem(int quantidade) {
        if (totalVotos == 0) {
            return 0;
        }
        BigDecimal quantidadeBD = new BigDecimal(quantidade);
        BigDecimal totalVotosBD = new BigDecimal(totalVotos);
        BigDecimal porcentagem = quantidadeBD.multiply(new BigDecimal(100)).divide(totalVotosBD, 2, RoundingMode.HALF_UP);

        return porcentagem.doubleValue();
    }
}
