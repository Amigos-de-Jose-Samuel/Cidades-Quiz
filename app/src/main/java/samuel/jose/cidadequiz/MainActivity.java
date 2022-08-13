package samuel.jose.cidadequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    String[] states = {"Acre", "Alagoas", "Amapá", "Amazonas", "Bahia", "Ceará", "Espírito Santo", "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul", "Paraíba", "Piauí", "Roraima", "Rondônia"};
    String[] cities = {"Rio Branco", "Maceió", "Macapá", "Manaus", "Salvador", "Fortaleza", "Vitória", "Goiânia", "São Luís", "Cuiabá", "Campo Grande", "João Pessoa", "Teresina", "Boa Vista", "Porto Velho"};
    List<String> sorteadas = new ArrayList<String>();
    TextView score, state, output;
    EditText input;
    Button guessBtn, nextBtn;
    Random random = new Random();
    int actual, count, scoreInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = findViewById(R.id.score);
        state = findViewById(R.id.state);
        output = findViewById(R.id.output);
        input = findViewById(R.id.capital);
        guessBtn = findViewById(R.id.guessBtn);
        nextBtn = findViewById(R.id.nextBtn);

        draw(); //sorteia um estado inicial
        score.setText("Pontuação: " + scoreInt + " pontos"); //seto o score inicial pra zero

        nextBtn.setEnabled(false);//desabilito o botao de proximo

        count = 1;//inicio o contador de perguntas pra primeira
    }

    public void guess(View view)
    {
        String res = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                .matcher(Normalizer.normalize((input.getText().toString()), Normalizer.Form.NFD))
                .replaceAll(""); //Retira todos os acentos
        res = res.toLowerCase(Locale.ROOT);
        String capitalSemAcento = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
                .matcher(Normalizer.normalize(cities[actual].toLowerCase(Locale.ROOT), Normalizer.Form.NFD))
                .replaceAll(""); //Retira todos os acentos
        String capital = capitalSemAcento.toLowerCase(Locale.ROOT);
        if(res.equals(capital)) {
            output.setText("Correto!");
            scoreInt += 10; // aumento o score
            score.setText("Pontuação: " + scoreInt + " pontos");
        } else
            output.setText("Erro, resposta correta: " + cities[actual]);

        nextBtn.setEnabled(count < 5); // se for a quinta resposta ele termina o jogo e nao deixa dar next
        guessBtn.setEnabled(false);
    }

    public void next(View view) {
        draw();
        output.setText("");
        input.setText("");
        nextBtn.setEnabled(false);
        guessBtn.setEnabled(true);
        count++;
    }

    private void draw() {
        do {
            actual = random.nextInt(14);
        } while (sorteadas.contains(states[actual]));
        state.setText(states[actual]);
        sorteadas.add(states[actual]);
    }
}