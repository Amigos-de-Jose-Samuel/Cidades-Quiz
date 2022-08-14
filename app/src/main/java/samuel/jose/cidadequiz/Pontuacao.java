package samuel.jose.cidadequiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Pontuacao extends AppCompatActivity {

    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pontuacao);

        output = findViewById(R.id.pontuacao);

        Intent it =  getIntent();
        if(it != null) {
            Bundle params = it.getExtras();
            if(params != null) {
                output.setText(params.getString("pontuacao"));
            }
        }
    }
}