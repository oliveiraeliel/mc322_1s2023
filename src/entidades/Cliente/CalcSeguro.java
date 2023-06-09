package entidades.Cliente;

public enum CalcSeguro {
    VALOR_BASE(10.0),
    FATOR_18_30(1.25),
    FATOR_30_60(1.0),
    FATOR_60_90(1.5);

    private double valor;

    CalcSeguro(double valor) {
        this.valor = valor;
    }

    public static double fatorIdade(int idade) {
        if (idade >= 18 && idade < 30) {
            return FATOR_18_30.getValor();
        }
        if (idade >= 30 && idade < 60) {
            return FATOR_30_60.getValor();
        }
        return FATOR_60_90.getValor();
    }

    public double getValor() {
        return valor;
    }

}