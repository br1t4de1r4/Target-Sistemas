package org.testes.farturamentoJson.faturamento;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class FaturamentoMensalDistribuidora {

    public static void main(String[] args) throws Exception {

        // Ler o vetor de faturamento diário a partir do arquivo JSON externo
        InputStream inputStream = FaturamentoMensalDistribuidora.class.getClassLoader().getResourceAsStream("faturamento.json");

        if (inputStream == null) {
            throw new FileNotFoundException("Arquivo não encontrado: faturamento.json");
        }

        JSONArray faturamentoJson = (JSONArray) new JSONParser().parse(new InputStreamReader(inputStream));
        List<Double> faturamento = new ArrayList<>();
        int numDiasUteis = 0;
        double totalFaturamentoUteis = 0;

        for (Object objJson : faturamentoJson) {
            JSONObject obj = (JSONObject) objJson;
            String dataStr = (String) obj.get("data");
            double valor = ((Number) obj.get("valor")).doubleValue();
            LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            if (data.getDayOfWeek() != DayOfWeek.SATURDAY && data.getDayOfWeek() != DayOfWeek.SUNDAY
                    && !isFeriado(data)) {
                faturamento.add(valor);
                numDiasUteis++;
                totalFaturamentoUteis += valor;
            }
        }

        // Calcular o menor e o maior valor de faturamento diário do mês
        double menor = faturamento.get(0);
        double maior = faturamento.get(0);
        for (double valor : faturamento) {
            if (valor < menor) {
                menor = valor;
            }
            if (valor > maior) {
                maior = valor;
            }
        }
        System.out.println("Menor valor de faturamento diário do mês: " + menor);
        System.out.println("Maior valor de faturamento diário do mês: " + maior);

        // Calcular a média mensal de faturamento diário (considerando apenas dias úteis)
        double media = totalFaturamentoUteis / numDiasUteis;

        // Contar quantos dias tiveram um valor de faturamento diário superior à média
        int acimaMedia = 0;
        for (double valor : faturamento) {
            if (valor > media) {
                acimaMedia++;
            }
        }
        System.out.println("Número de dias no mês em que o valor de faturamento diário foi superior à média mensal levando em consideração os finais de semana e os feriados é de: "
                + acimaMedia);
    }

    // Exemplo simples do método isFeriado
    private static boolean isFeriado(LocalDate data) {
        // Implementar a lógica para determinar se a data é um feriado
        // Exemplo simples: 25 de Dezembro
        return data.equals(LocalDate.of(data.getYear(), 12, 25));
    }
}
