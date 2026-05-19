package br.com.parshall.main;

import br.com.parshall.model.CalhaParshall;
import br.com.parshall.service.CalhaParshallService;
import br.com.parshall.util.RelatorioParshall;
import br.com.parshall.util.TabelaParshall;
import br.com.parshall.util.TabelaParshall.DadosNormativos;

import java.util.Scanner;

/**
 * Aplicação de linha de comando para dimensionamento de Calha Parshall.
 *
 * Modos disponíveis:
 *   1. Calcular Ha a partir de Q e W (dimensionamento)
 *   2. Calcular Q a partir de Ha medido e W (operação)
 *   3. Seleção automática de tamanho por Q
 *   4. Listar todos os tamanhos normalizados
 */
public class Main {

    private static final CalhaParshallService servico = new CalhaParshallService();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        cabecalho();

        boolean continuar = true;
        while (continuar) {
            menu();
            int opcao = lerInt("Opção");

            switch (opcao) {
                case 1 -> modoQeW();
                case 2 -> modoHaeW();
                case 3 -> modoSelecaoAutomatica();
                case 4 -> listarTamanhos();
                case 0 -> continuar = false;
                default -> System.out.println("  ⚠ Opção inválida.\n");
            }
        }

        System.out.println("\n  Encerrando... Até logo!\n");
        sc.close();
    }

    private static void modoQeW() {
        System.out.println("\n  ── Modo 1: Dimensionamento (Q → Ha) ──\n");

        double Q = lerDouble("  Informe a vazão Q (m³/s)");
        System.out.println("  Tamanhos disponíveis: (deixe 0 para seleção automática)");
        listarTamanhosResumido();
        double W = lerDouble("  Informe a largura da garganta W (m) [0 = auto]");

        try {
            CalhaParshall calha = servico.calcularPorQ(Q, W);
            calha = pedirHb(calha);
            System.out.println(RelatorioParshall.gerar(calha));
        } catch (IllegalArgumentException e) {
            System.out.println("\n  ❌ Erro: " + e.getMessage() + "\n");
        }
    }
    private static void modoHaeW() {
        System.out.println("\n  ── Modo 2: Cálculo de vazão (Ha → Q) ──\n");

        listarTamanhosResumido();
        double W  = lerDouble("  Informe a largura da garganta W (m)");
        double Ha = lerDouble("  Informe a lâmina Ha medida (m)");

        try {
            CalhaParshall calha = servico.calcularPorHa(Ha, W);
            calha = pedirHb(calha);
            System.out.println(RelatorioParshall.gerar(calha));
        } catch (IllegalArgumentException e) {
            System.out.println("\n  ❌ Erro: " + e.getMessage() + "\n");
        }
    }

    private static void modoSelecaoAutomatica() {
        System.out.println("\n  ── Modo 3: Seleção automática de tamanho ──\n");

        double Q = lerDouble("  Informe a vazão de projeto Q (m³/s)");

        try {
            DadosNormativos d = servico.selecionarTamanho(Q);
            System.out.printf("%n  Tamanho recomendado: %-8s  W = %.4f m%n", d.nome, d.W);
            System.out.printf("  Faixa de vazão:  %.6f  a  %.6f  m³/s%n%n", d.Qmin, d.Qmax);

            System.out.println("  Deseja calcular o dimensionamento completo? (s/n): ");
            String resp = sc.nextLine().trim().toLowerCase();
            if (resp.equals("s")) {
                CalhaParshall calha = servico.calcularPorQ(Q, d.W);
                calha = pedirHb(calha);
                System.out.println(RelatorioParshall.gerar(calha));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("\n  ❌ Erro: " + e.getMessage() + "\n");
        }
    }

    private static void listarTamanhos() {
        System.out.println("\n  ── Tamanhos Normalizados de Calha Parshall ──\n");
        System.out.printf("  %-10s %-10s %-14s %-14s %-10s %-10s %-6s%n",
            "Nome", "W (m)", "Qmin (m³/s)", "Qmax (m³/s)", "Hamin (m)", "Hamax (m)", "S_crit");
        System.out.println("  " + "─".repeat(70));

        for (DadosNormativos d : TabelaParshall.getTodos()) {
            System.out.printf("  %-10s %-10.4f %-14.6f %-14.4f %-10.3f %-10.3f %-6.2f%n",
                d.nome, d.W, d.Qmin, d.Qmax, d.Hamin, d.Hamax, d.limSubm);
        }
        System.out.println();
    }
    private static CalhaParshall pedirHb(CalhaParshall calha) {
        System.out.print("\n  Informe a lâmina Hb na saída (m) para verificar submergência [Enter = pular]: ");
        String entrada = sc.nextLine().trim();
        if (!entrada.isEmpty()) {
            try {
                double Hb = Double.parseDouble(entrada.replace(",", "."));
                calha = servico.analisarSubmergencia(calha, Hb);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Hb ignorado (valor inválido).");
            }
        }
        return calha;
    }
    private static void listarTamanhosResumido() {
        System.out.println();
        int i = 0;
        for (DadosNormativos d : TabelaParshall.getTodos()) {
            System.out.printf("   %-8s W=%.4f m", d.nome, d.W);
            if (++i % 3 == 0) System.out.println();
        }
        System.out.println("\n");
    }

    private static double lerDouble(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Valor inválido. Tente novamente.");
            }
        }
    }

    private static int lerInt(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  ⚠ Valor inválido. Tente novamente.");
            }
        }
    }

    private static void cabecalho() {
        System.out.println("\n" + "═".repeat(64));
        System.out.println("   SISTEMA DE DIMENSIONAMENTO – CALHA PARSHALL");
        System.out.println("   Normas: ABNT NBR 13399 | ISO 9826 | USBR Water Meas. Manual");
        System.out.println("═".repeat(64));
    }

    private static void menu() {
        System.out.println("\n  ── Menu Principal ──");
        System.out.println("  [1]  Calcular Ha a partir de Q e W  (dimensionamento)");
        System.out.println("  [2]  Calcular Q a partir de Ha medido  (operação)");
        System.out.println("  [3]  Seleção automática de tamanho por Q");
        System.out.println("  [4]  Listar todos os tamanhos normalizados");
        System.out.println("  [0]  Sair\n");
    }
}
