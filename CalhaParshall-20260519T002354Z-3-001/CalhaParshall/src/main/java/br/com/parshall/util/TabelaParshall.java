package br.com.parshall.util;

/**
 * Tabela normativa da Calha Parshall.
 *
 * Fonte: U.S. Bureau of Reclamation – Water Measurement Manual (3ª ed., 2001)
 *        e ISO 9826:1992 / ABNT NBR 13399.
 *
 * Todas as dimensões estão em METROS.
 * Coeficientes válidos para Q em m³/s e Ha em metros.
 *
 * Linha de cada tamanho:
 *   { W, A, B, C, D, E, F, G, K, M, N, L, X, Y,
 *     coefC, expoN, Qmin, Qmax, Hamin, Hamax, limSubm, nomeNominal }
 *
 * Notas de conversão:
 *   1 inch  = 0,0254 m
 *   1 foot  = 0,3048 m
 */
public final class TabelaParshall {

    private TabelaParshall() {}

    public static class DadosNormativos {
        public final double W;
        public final double A;
        public final double B;
        public final double C;
        public final double D;
        public final double E;
        public final double F;
        public final double G;
        public final double K;
        public final double M;
        public final double N;
        public final double L;
        public final double X;
        public final double Y;
        public final double coefC;
        public final double expoN;
        public final double Qmin;
        public final double Qmax;
        public final double Hamin;
        public final double Hamax;
        public final double limSubm;
        public final String nome;

        public DadosNormativos(
                double W, double A, double B, double C, double D,
                double E, double F, double G, double K, double M,
                double N, double L, double X, double Y,
                double coefC, double expoN,
                double Qmin, double Qmax, double Hamin, double Hamax,
                double limSubm, String nome) {
            this.W = W; this.A = A; this.B = B; this.C = C; this.D = D;
            this.E = E; this.F = F; this.G = G; this.K = K; this.M = M;
            this.N = N; this.L = L; this.X = X; this.Y = Y;
            this.coefC = coefC; this.expoN = expoN;
            this.Qmin = Qmin; this.Qmax = Qmax;
            this.Hamin = Hamin; this.Hamax = Hamax;
            this.limSubm = limSubm; this.nome = nome;
        }
    }
    public static final DadosNormativos[] TABELA = {

        new DadosNormativos(
            0.0254, 0.167, 0.093, 0.029, 0.061, 0.167, 0.093, 0.310, 0.000, 0.093, 0.057, 0.076, 0.051, 0.038,
            0.0604, 1.522, 0.000028, 0.00532, 0.015, 0.21, 0.50, "1\""),

        new DadosNormativos(
            0.0508, 0.214, 0.135, 0.043, 0.076, 0.214, 0.135, 0.397, 0.000, 0.135, 0.057, 0.076, 0.051, 0.038,
            0.1207, 1.580, 0.000057, 0.01108, 0.015, 0.24, 0.50, "2\""),

        new DadosNormativos(
            0.0762, 0.259, 0.178, 0.057, 0.092, 0.259, 0.178, 0.476, 0.000, 0.178, 0.076, 0.076, 0.051, 0.038,
            0.1771, 1.547, 0.000085, 0.01711, 0.015, 0.33, 0.50, "3\""),

        new DadosNormativos(
            0.1524, 0.467, 0.394, 0.114, 0.152, 0.467, 0.305, 0.788, 0.025, 0.394, 0.076, 0.152, 0.051, 0.076,
            0.3812, 1.580, 0.00142,  0.0793,  0.030, 0.45, 0.60, "6\""),

        new DadosNormativos(
            0.2286, 0.467, 0.381, 0.152, 0.152, 0.554, 0.305, 0.788, 0.025, 0.381, 0.076, 0.228, 0.051, 0.076,
            0.5354, 1.530, 0.00255,  0.1312,  0.030, 0.60, 0.60, "9\""),

        // ── Calhas médias (pés) ────────────────────────────────────────────────
        new DadosNormativos(
            0.3048, 0.552, 0.394, 0.203, 0.203, 0.610, 0.305, 0.914, 0.025, 0.394, 0.076, 0.305, 0.057, 0.076,
            0.6909, 1.522, 0.00708,  0.3682,  0.030, 0.75, 0.70, "1 ft"),

        new DadosNormativos(
            0.4572, 0.552, 0.381, 0.254, 0.254, 0.762, 0.305, 0.914, 0.051, 0.381, 0.076, 0.457, 0.057, 0.076,
            1.056,  1.522, 0.01416, 0.7080,  0.030, 0.75, 0.70, "1.5 ft"),

        new DadosNormativos(
            0.6096, 0.552, 0.394, 0.305, 0.305, 0.914, 0.305, 0.914, 0.051, 0.394, 0.076, 0.610, 0.057, 0.076,
            1.428,  1.522, 0.02550, 1.1328,  0.030, 0.75, 0.70, "2 ft"),

        new DadosNormativos(
            0.9144, 0.552, 0.381, 0.381, 0.381, 1.219, 0.381, 0.914, 0.051, 0.381, 0.076, 0.914, 0.057, 0.076,
            2.184,  1.522, 0.04814, 1.9880,  0.030, 0.75, 0.70, "3 ft"),

        new DadosNormativos(
            1.2192, 0.610, 0.394, 0.457, 0.457, 1.524, 0.457, 1.219, 0.051, 0.394, 0.076, 1.219, 0.057, 0.076,
            2.953,  1.522, 0.07080, 3.0880,  0.030, 0.75, 0.70, "4 ft"),

        new DadosNormativos(
            1.5240, 0.762, 0.381, 0.610, 0.610, 1.829, 0.610, 1.524, 0.051, 0.381, 0.076, 1.524, 0.057, 0.076,
            3.732,  1.522, 0.14158, 4.2476,  0.030, 0.75, 0.70, "5 ft"),

        new DadosNormativos(
            1.8288, 0.762, 0.394, 0.762, 0.762, 2.134, 0.762, 1.829, 0.051, 0.394, 0.076, 1.829, 0.057, 0.076,
            4.519,  1.522, 0.17670, 5.6634,  0.030, 0.75, 0.70, "6 ft"),

        new DadosNormativos(
            2.1336, 0.914, 0.381, 0.914, 0.914, 2.438, 0.914, 2.134, 0.051, 0.381, 0.076, 2.134, 0.057, 0.076,
            5.312,  1.522, 0.21222, 7.3710,  0.030, 0.75, 0.70, "7 ft"),

        new DadosNormativos(
            2.4384, 0.914, 0.394, 0.914, 0.914, 2.743, 0.914, 2.134, 0.051, 0.394, 0.076, 2.438, 0.057, 0.076,
            6.112,  1.522, 0.24502, 9.3310,  0.030, 0.75, 0.70, "8 ft"),

        new DadosNormativos(
            2.7432, 1.067, 0.381, 1.067, 1.067, 3.048, 1.067, 2.438, 0.051, 0.381, 0.076, 2.743, 0.057, 0.076,
            6.910,  1.522, 0.28320, 11.328,  0.030, 0.75, 0.70, "9 ft"),

        new DadosNormativos(
            3.0480, 1.372, 0.394, 1.372, 1.372, 3.658, 1.372, 3.048, 0.051, 0.394, 0.076, 3.048, 0.057, 0.076,
            7.463,  1.522, 0.31200, 14.158,  0.030, 0.75, 0.70, "10 ft"),

        // ── Calhas grandes ─────────────────────────────────────────────────────
        new DadosNormativos(
            3.6576, 1.524, 0.381, 1.524, 1.524, 4.267, 1.524, 3.658, 0.076, 0.381, 0.076, 3.658, 0.057, 0.076,
            9.300,  1.600, 0.45000, 17.500,  0.030, 0.75, 0.80, "12 ft"),

        new DadosNormativos(
            4.5720, 1.829, 0.381, 1.829, 1.829, 5.486, 1.829, 4.572, 0.076, 0.381, 0.076, 4.572, 0.057, 0.076,
            11.00,  1.600, 0.56700, 22.100,  0.030, 0.75, 0.80, "15 ft"),

        new DadosNormativos(
            6.0960, 2.134, 0.381, 2.134, 2.134, 7.315, 2.134, 6.096, 0.076, 0.381, 0.076, 6.096, 0.057, 0.076,
            14.45,  1.600, 0.76700, 30.600,  0.030, 0.75, 0.80, "20 ft"),

        new DadosNormativos(
            7.6200, 2.591, 0.381, 2.591, 2.591, 9.144, 2.591, 7.620, 0.076, 0.381, 0.076, 7.620, 0.057, 0.076,
            18.00,  1.600, 0.96700, 39.600,  0.030, 0.75, 0.80, "25 ft"),

        new DadosNormativos(
            9.1440, 3.048, 0.381, 3.048, 3.048, 10.973, 3.048, 9.144, 0.076, 0.381, 0.076, 9.144, 0.057, 0.076,
            21.69,  1.600, 1.18700, 47.500,  0.030, 0.75, 0.80, "30 ft"),

        new DadosNormativos(
            15.240, 4.572, 0.381, 4.572, 4.572, 18.288, 4.572, 15.240, 0.076, 0.381, 0.076, 15.240, 0.057, 0.076,
            36.59,  1.600, 1.98300, 85.000,  0.030, 0.75, 0.80, "50 ft"),
    };
    public static DadosNormativos buscarPorW(double W) {
        double tolerancia = 0.001;
        for (DadosNormativos d : TABELA) {
            if (Math.abs(d.W - W) <= tolerancia) return d;
        }
        return null;
    }
    public static DadosNormativos selecionarPorVazao(double Q) {
        for (DadosNormativos d : TABELA) {
            if (Q >= d.Qmin && Q <= d.Qmax) return d;
        }
        return null;
    }
    public static DadosNormativos[] getTodos() {
        return TABELA;
    }
}
