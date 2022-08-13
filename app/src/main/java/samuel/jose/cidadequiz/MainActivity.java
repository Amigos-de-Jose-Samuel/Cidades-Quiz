package samuel.jose.cidadequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    String[] states = {"Barcelona", "Brasilia", "Curitiba", "Las Vegas", "Montreal", "Paris", "Rio de Janeiro", "Salvador", "São Paulo", "Tóquio"};
    String[] urls = {
        "http://200.236.3.202/Cidades/01_barcelona.jpg",
        "http://200.236.3.202/Cidades/02_brasilia.jpg",
        "http://200.236.3.202/Cidades/03_curitiba.jpg",
        "http://200.236.3.202/Cidades/04_lasvegas.jpg",
        "http://200.236.3.202/Cidades/05_montreal.jpg",
        "http://200.236.3.202/Cidades/06_paris.jpg",
        "http://200.236.3.202/Cidades/07_riodejaneiro.jpg",
        "http://200.236.3.202/Cidades/08_salvador.jpg",
        "http://200.236.3.202/Cidades/09_saopaulo.jpg",
        "http://200.236.3.202/Cidades/10_toquio.jpg"
    };
    ImageView imageView;
    List<String> sorteadas = new ArrayList<String>();
    TextView state, output;
    EditText input;
    Button guessBtn, nextBtn;
    Random random = new Random();
    int actual, count, scoreInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        state = findViewById(R.id.state);
        output = findViewById(R.id.output);
        input = findViewById(R.id.input);
        guessBtn = findViewById(R.id.guessBtn);
        nextBtn = findViewById(R.id.nextBtn);
        imageView = findViewById(R.id.imageView);

        draw(); //sorteia um estado inicial

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
                .matcher(Normalizer.normalize(states[actual].toLowerCase(Locale.ROOT), Normalizer.Form.NFD))
                .replaceAll(""); //Retira todos os acentos
        String capital = capitalSemAcento.toLowerCase(Locale.ROOT);
        if(res.equals(capital)) {
            output.setText("Correto!");
            scoreInt += 25; // aumento o score
        } else
            output.setText("Erro, resposta correta: " + states[actual]);

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
            actual = random.nextInt(10);
        } while (sorteadas.contains(states[actual]));
        DownloadTask task = new DownloadTask(this, imageView);
        task.execute(urls[actual]);
        sorteadas.add(states[actual]);
    }
}