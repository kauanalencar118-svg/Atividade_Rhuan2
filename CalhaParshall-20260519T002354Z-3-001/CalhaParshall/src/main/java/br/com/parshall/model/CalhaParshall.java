package br.com.parshall.model;

public class CalhaParshall {

    private String tamanhoNominal;      // ex: "1\"", "6\"", "1 ft"

    private double larguraGarganta_W;   // W  – largura da garganta (m)
    private double vazao_Q;             // Q  – vazão de projeto (m³/s)
    private double lamina_Ha;           // Ha – lâmina d'água na seção de medição (m)
    private double lamina_Hb;           // Hb – lâmina d'água na saída (submergência) (m)

    private double A;   // Comprimento da seção convergente
    private double B;   // Largura total na entrada do canal
    private double C;   // Comprimento do soleiro na garganta
    private double D;   // Profundidade total da estrutura
    private double E;   // Largura total na saída
    private double F;   // Comprimento da seção divergente
    private double G;   // Comprimento total da calha
    private double K;   // Desnível do soleiro em relação ao fundo do canal
    private double M;   // Largura do nível d'água na entrada
    private double N;   // Desnível (drop) da parte inclinada do soleiro
    private double L;   // Comprimento da garganta (seção de controle)
    private double X;   // Distância horizontal do ponto de medição Ha
    private double Y;   // Profundidade do ponto de medição Ha

    private double coeficienteC;        // C – coeficiente:  Q = C × Ha^n
    private double expoente_n;          // n – expoente

    private double razaoSubmergencia;   // S = Hb / Ha
    private double limiteSubmergencia;  // 0,70 (W ≤ 3") | 0,80 (demais)
    private boolean escoamentoLivre;
    private double velocidadeGarganta;  // V (m/s)
    private double numeroDeFroude;      // Fr na garganta
    private double perda_dH;            // Perda de carga mínima (m)
    private String regimeEscoamento;    // "Livre" ou "Submerso"
    private String classeVazao;         // Indica se Q está na faixa correta

    private double vazaoMinima;         // m³/s
    private double vazaoMaxima;         // m³/s
    private double laminaMinima;        // Ha mínima (m)
    private double laminaMaxima;        // Ha máxima (m)

    public CalhaParshall() {}

    public String getTamanhoNominal() { return tamanhoNominal; }
    public void setTamanhoNominal(String v) { this.tamanhoNominal = v; }

    public double getLarguraGarganta_W() { return larguraGarganta_W; }
    public void setLarguraGarganta_W(double v) { this.larguraGarganta_W = v; }

    public double getVazao_Q() { return vazao_Q; }
    public void setVazao_Q(double v) { this.vazao_Q = v; }

    public double getLamina_Ha() { return lamina_Ha; }
    public void setLamina_Ha(double v) { this.lamina_Ha = v; }

    public double getLamina_Hb() { return lamina_Hb; }
    public void setLamina_Hb(double v) { this.lamina_Hb = v; }

    public double getA() { return A; } public void setA(double v) { this.A = v; }
    public double getB() { return B; } public void setB(double v) { this.B = v; }
    public double getC() { return C; } public void setC(double v) { this.C = v; }
    public double getD() { return D; } public void setD(double v) { this.D = v; }
    public double getE() { return E; } public void setE(double v) { this.E = v; }
    public double getF() { return F; } public void setF(double v) { this.F = v; }
    public double getG() { return G; } public void setG(double v) { this.G = v; }
    public double getK() { return K; } public void setK(double v) { this.K = v; }
    public double getM() { return M; } public void setM(double v) { this.M = v; }
    public double getN() { return N; } public void setN(double v) { this.N = v; }
    public double getL() { return L; } public void setL(double v) { this.L = v; }
    public double getX() { return X; } public void setX(double v) { this.X = v; }
    public double getY() { return Y; } public void setY(double v) { this.Y = v; }

    public double getCoeficienteC() { return coeficienteC; }
    public void setCoeficienteC(double v) { this.coeficienteC = v; }

    public double getExpoente_n() { return expoente_n; }
    public void setExpoente_n(double v) { this.expoente_n = v; }

    public double getRazaoSubmergencia() { return razaoSubmergencia; }
    public void setRazaoSubmergencia(double v) { this.razaoSubmergencia = v; }

    public double getLimiteSubmergencia() { return limiteSubmergencia; }
    public void setLimiteSubmergencia(double v) { this.limiteSubmergencia = v; }

    public boolean isEscoamentoLivre() { return escoamentoLivre; }
    public void setEscoamentoLivre(boolean v) { this.escoamentoLivre = v; }

    public double getVelocidadeGarganta() { return velocidadeGarganta; }
    public void setVelocidadeGarganta(double v) { this.velocidadeGarganta = v; }

    public double getNumeroDeFroude() { return numeroDeFroude; }
    public void setNumeroDeFroude(double v) { this.numeroDeFroude = v; }

    public double getPerda_dH() { return perda_dH; }
    public void setPerda_dH(double v) { this.perda_dH = v; }

    public String getRegimeEscoamento() { return regimeEscoamento; }
    public void setRegimeEscoamento(String v) { this.regimeEscoamento = v; }

    public String getClasseVazao() { return classeVazao; }
    public void setClasseVazao(String v) { this.classeVazao = v; }

    public double getVazaoMinima() { return vazaoMinima; }
    public void setVazaoMinima(double v) { this.vazaoMinima = v; }

    public double getVazaoMaxima() { return vazaoMaxima; }
    public void setVazaoMaxima(double v) { this.vazaoMaxima = v; }

    public double getLaminaMinima() { return laminaMinima; }
    public void setLaminaMinima(double v) { this.laminaMinima = v; }

    public double getLaminaMaxima() { return laminaMaxima; }
    public void setLaminaMaxima(double v) { this.laminaMaxima = v; }

    @Override
    public String toString() {
        return String.format(
            "CalhaParshall{W=%s (%.4f m), Q=%.6f m³/s, Ha=%.4f m, regime=%s}",
            tamanhoNominal, larguraGarganta_W, vazao_Q, lamina_Ha, regimeEscoamento);
    }
}
