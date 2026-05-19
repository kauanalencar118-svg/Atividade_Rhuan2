package br.com.parshall.util;

import br.com.parshall.model.CalhaParshall;

public class RelatorioParshall {

    private static final String SEP  = "═".repeat(64);
    private static final String SEP2 = "─".repeat(64);

    public static String gerar(CalhaParshall c) {
        StringBuilder sb = new StringBuilder();

        sb.append("\n").append(SEP).append("\n");
        sb.append("  RELATÓRIO – CALHA PARSHALL\n");
        sb.append(SEP).append("\n\n");

        secao(sb, "1. IDENTIFICAÇÃO DO TAMANHO");
        linha(sb, "Tamanho nominal",  c.getTamanhoNominal());
        linha(sb, "Largura garganta (W)", m(c.getLarguraGarganta_W()));
        linha(sb, "Faixa de vazão",
            String.format("%.6f  a  %.6f  m³/s", c.getVazaoMinima(), c.getVazaoMaxima()));
        linha(sb, "Faixa de lâmina Ha",
            String.format("%.3f  a  %.3f  m", c.getLaminaMinima(), c.getLaminaMaxima()));
        sb.append("\n");

        secao(sb, "2. PARÂMETROS DE ENTRADA / VAZÃO");
        linha(sb, "Vazão de projeto (Q)", String.format("%.6f m³/s  =  %.3f L/s", c.getVazao_Q(), c.getVazao_Q()*1000));
        linha(sb, "Lâmina Ha calculada",  m(c.getLamina_Ha()));
        linha(sb, "Classe de vazão",      c.getClasseVazao());
        sb.append("\n");

        secao(sb, "3. EQUAÇÃO DE VAZÃO   Q = C × Ha^n");
        linha(sb, "Coeficiente C",  String.format("%.4f", c.getCoeficienteC()));
        linha(sb, "Expoente n",     String.format("%.4f", c.getExpoente_n()));
        linha(sb, "Verificação Q",
            String.format("%.4f × %.4f^%.4f = %.6f m³/s",
                c.getCoeficienteC(), c.getLamina_Ha(), c.getExpoente_n(),
                c.getCoeficienteC() * Math.pow(c.getLamina_Ha(), c.getExpoente_n())));
        sb.append("\n");

        secao(sb, "4. DIMENSÕES CONSTRUTIVAS (todas em metros)");
        sb.append("   (Referência: eixo longitudinal da calha)\n\n");

        linha(sb, "A – Comprimento seção convergente",     m(c.getA()));
        linha(sb, "B – Largura total na entrada",          m(c.getB()));
        linha(sb, "C – Comprimento soleiro na garganta",   m(c.getC()));
        linha(sb, "D – Profundidade total da estrutura",   m(c.getD()));
        linha(sb, "E – Largura total na saída",            m(c.getE()));
        linha(sb, "F – Comprimento seção divergente",      m(c.getF()));
        linha(sb, "G – Comprimento total (A + L + F)",     m(c.getG()));
        linha(sb, "K – Desnível soleiro/fundo canal",      m(c.getK()));
        linha(sb, "M – Largura nível d'água na entrada",   m(c.getM()));
        linha(sb, "N – Drop inclinado do soleiro",         m(c.getN()));
        linha(sb, "L – Comprimento da garganta",           m(c.getL()));
        linha(sb, "X – Posição medição Ha (a montante)",   m(c.getX()));
        linha(sb, "Y – Profundidade da régua/sensor Ha",   m(c.getY()));
        sb.append("\n");

        secao(sb, "5. RESULTADOS HIDRÁULICOS");
        linha(sb, "Velocidade na garganta (V)",
            String.format("%.4f m/s", c.getVelocidadeGarganta()));
        linha(sb, "Número de Froude (Fr)",
            String.format("%.4f  %s", c.getNumeroDeFroude(),
                c.getNumeroDeFroude() > 1 ? "✔ supercrítico (esperado)" : "⚠ subcrítico (verificar)"));
        sb.append("\n");

        secao(sb, "6. ANÁLISE DE SUBMERGÊNCIA");
        linha(sb, "Limite de submergência (S_crit)",
            String.format("%.2f  (%.0f%%)", c.getLimiteSubmergencia(), c.getLimiteSubmergencia()*100));
        linha(sb, "Razão Hb/Ha medida",
            c.getLamina_Hb() > 0
                ? String.format("%.4f", c.getRazaoSubmergencia())
                : "Hb não informado");
        linha(sb, "Regime de escoamento", c.getRegimeEscoamento());
        linha(sb, "Perda de carga mínima (dH)",
            String.format("%.4f m  (Ha × (1 – S_crit))", c.getPerda_dH()));
        sb.append("\n");

        secao(sb, "7. REGRAS E REQUISITOS DE INSTALAÇÃO");
        sb.append(regrasInstalacao()).append("\n");

        secao(sb, "8. NORMAS DE REFERÊNCIA");
        sb.append("   • ABNT NBR 13399 – Medição de vazão em canais abertos\n");
        sb.append("   • ISO 9826:1992  – Measurement of liquid flow in open channels\n");
        sb.append("   • U.S. Bureau of Reclamation – Water Measurement Manual (3ª ed.)\n");
        sb.append("\n").append(SEP).append("\n");

        return sb.toString();
    }

    private static String regrasInstalacao() {
        return
            "   CANAL DE APROXIMAÇÃO\n" +
            "   • Comprimento mínimo a montante: 10 × largura do canal (10 B)\n" +
            "   • Seção uniforme, sem perturbações ou curvas próximas\n" +
            "   • Fundo do canal de montante nivelado (declive < 0,2%)\n\n" +

            "   NÍVEL DE REFERÊNCIA\n" +
            "   • O soleiro da calha deve ser instalado nivelado (±1 mm)\n" +
            "   • Desnível K em relação ao fundo do canal conforme tabela\n\n" +

            "   PONTO DE MEDIÇÃO DE Ha\n" +
            "   • Ha deve ser medido à distância X a montante da garganta\n" +
            "   • Usar régua ou sensor de nível (ultrassônico/boia) na posição Y\n" +
            "   • Precisão mínima da régua: ± 1 mm\n\n" +

            "   SUBMERGÊNCIA\n" +
            "   • Garantir perda de carga mínima dH = Ha × (1 – S_crit)\n" +
            "   • Para W ≤ 3\" (0,076 m): S_crit = 0,70  →  dH ≥ 0,30 × Ha\n" +
            "   • Para W ≥ 6\" (0,152 m): S_crit = 0,80  →  dH ≥ 0,20 × Ha\n" +
            "   • Escoamento submerso aumenta erro de medição para > 5%\n\n" +

            "   VELOCIDADE DE APROXIMAÇÃO\n" +
            "   • Velocidade no canal de montante ≤ 0,6 m/s (recomendado)\n" +
            "   • Seção de entrada suficientemente ampla para garantir isso\n\n" +

            "   MATERIAL E ACABAMENTO\n" +
            "   • Paredes da garganta: lisas, verticais, paralelas (± 1 mm)\n" +
            "   • Ângulo da seção convergente: exatamente 26,57° (cotg = 2:1)\n" +
            "   • Ângulo da seção divergente: exatamente 9,46° (cotg = 6:1)\n" +
            "   • Material: concreto, aço inox, PVC rígido ou PRFV\n\n" +

            "   INCERTEZA DE MEDIÇÃO (ISO 9826)\n" +
            "   • Escoamento livre:    ± 2 a 5 %\n" +
            "   • Escoamento submerso: ± 5 a 15 % (evitar sempre que possível)\n";
    }

    private static void secao(StringBuilder sb, String titulo) {
        sb.append(SEP2).append("\n");
        sb.append("  ").append(titulo).append("\n");
        sb.append(SEP2).append("\n");
    }

    private static void linha(StringBuilder sb, String chave, String valor) {
        sb.append(String.format("  %-42s %s%n", chave, valor));
    }

    private static String m(double v) {
        return String.format("%.4f m", v);
    }
}
