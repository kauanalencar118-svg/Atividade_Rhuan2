package br.com.parshall.service;

import br.com.parshall.model.CalhaParshall;
import br.com.parshall.util.TabelaParshall;
import br.com.parshall.util.TabelaParshall.DadosNormativos;

public class CalhaParshallService {

    private static final double G = 9.81;
    public CalhaParshall calcularPorQ(double Q_m3s, double W_m) {
        validarEntrada(Q_m3s, W_m);

        DadosNormativos dados;
        if (W_m <= 0) {
            dados = TabelaParshall.selecionarPorVazao(Q_m3s);
            if (dados == null)
                throw new IllegalArgumentException(
                    String.format("Vazão %.6f m³/s está fora da faixa de qualquer tamanho padrão.", Q_m3s));
        } else {
            dados = TabelaParshall.buscarPorW(W_m);
            if (dados == null)
                throw new IllegalArgumentException(
                    String.format("Largura W = %.4f m não corresponde a nenhum tamanho normalizado.", W_m));
        }

        double Ha = Math.pow(Q_m3s / dados.coefC, 1.0 / dados.expoN);

        return preencherCalha(Q_m3s, Ha, dados);
    }

    public CalhaParshall calcularPorHa(double Ha_m, double W_m) {
        if (Ha_m <= 0) throw new IllegalArgumentException("Ha deve ser positivo.");
        if (W_m <= 0)  throw new IllegalArgumentException("W deve ser positivo.");

        DadosNormativos dados = TabelaParshall.buscarPorW(W_m);
        if (dados == null)
            throw new IllegalArgumentException(
                String.format("W = %.4f m não é um tamanho normalizado.", W_m));

        double Q = dados.coefC * Math.pow(Ha_m, dados.expoN);

        return preencherCalha(Q, Ha_m, dados);
    }

    public CalhaParshall analisarSubmergencia(CalhaParshall calha, double Hb_m) {
        calha.setLamina_Hb(Hb_m);
        double Ha = calha.getLamina_Ha();
        double S  = (Ha > 0 && Hb_m > 0) ? Hb_m / Ha : 0.0;
        calha.setRazaoSubmergencia(S);

        boolean livre = S < calha.getLimiteSubmergencia();
        calha.setEscoamentoLivre(livre);
        calha.setRegimeEscoamento(livre ? "Livre" : "Submerso (medição prejudicada)");
        return calha;
    }

    public DadosNormativos selecionarTamanho(double Q_m3s) {
        DadosNormativos d = TabelaParshall.selecionarPorVazao(Q_m3s);
        if (d == null)
            throw new IllegalArgumentException(
                String.format("Q = %.6f m³/s fora da faixa de todos os tamanhos padrão.", Q_m3s));
        return d;
    }

    private CalhaParshall preencherCalha(double Q, double Ha, DadosNormativos d) {
        CalhaParshall c = new CalhaParshall();

        // Identificação
        c.setTamanhoNominal(d.nome);
        c.setLarguraGarganta_W(d.W);

        // Entrada
        c.setVazao_Q(Q);
        c.setLamina_Ha(Ha);

        // Dimensões construtivas (direto da tabela)
        c.setA(d.A); c.setB(d.B); c.setC(d.C); c.setD(d.D);
        c.setE(d.E); c.setF(d.F); c.setG(d.G); c.setK(d.K);
        c.setM(d.M); c.setN(d.N); c.setL(d.L); c.setX(d.X);
        c.setY(d.Y);

        // Coeficientes
        c.setCoeficienteC(d.coefC);
        c.setExpoente_n(d.expoN);

        // Limites de vazão
        c.setVazaoMinima(d.Qmin);
        c.setVazaoMaxima(d.Qmax);
        c.setLaminaMinima(d.Hamin);
        c.setLaminaMaxima(d.Hamax);

        // Limite de submergência
        c.setLimiteSubmergencia(d.limSubm);

        // Perda de carga mínima (dH = Ha × (1 – Scrit))
        double dH = Ha * (1.0 - d.limSubm);
        c.setPerda_dH(dH);

        // Velocidade na garganta: V = Q / (W × Ha)
        double V = (d.W > 0 && Ha > 0) ? Q / (d.W * Ha) : 0.0;
        c.setVelocidadeGarganta(V);

        // Número de Froude: Fr = V / sqrt(g × Ha)
        double Fr = (Ha > 0) ? V / Math.sqrt(G * Ha) : 0.0;
        c.setNumeroDeFroude(Fr);

        // Regime padrão (sem Hb): assumir livre
        c.setEscoamentoLivre(true);
        c.setRegimeEscoamento("Livre (Hb não informado)");
        c.setRazaoSubmergencia(0.0);

        // Classe de vazão
        if (Q < d.Qmin) {
            c.setClasseVazao("ABAIXO do mínimo recomendado (Q < " + fmt(d.Qmin) + " m³/s)");
        } else if (Q > d.Qmax) {
            c.setClasseVazao("ACIMA do máximo recomendado (Q > " + fmt(d.Qmax) + " m³/s)");
        } else {
            c.setClasseVazao("Dentro da faixa operacional [" + fmt(d.Qmin) + " – " + fmt(d.Qmax) + "] m³/s");
        }

        return c;
    }

    private void validarEntrada(double Q, double W) {
        if (Q <= 0)
            throw new IllegalArgumentException("Vazão Q deve ser positiva (recebido: " + Q + ").");
        if (W < 0)
            throw new IllegalArgumentException("Largura W não pode ser negativa (recebido: " + W + ").");
    }

    private String fmt(double v) {
        return String.format("%.6f", v);
    }
}
