package br.com.codespace.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.codespace.agenda.model.Exam;

public class DetalheProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_prova);
        Exam exam = (Exam) getIntent().getSerializableExtra("exam");
        TextView materia = (TextView) findViewById(R.id.txt_materia);
        TextView data = (TextView) findViewById(R.id.txt_data);
        ListView lista = (ListView) findViewById(R.id.lista_topicos);

        materia.setText(exam.getMatter());
        data.setText(exam.getDate());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, exam.getTopics());
        lista.setAdapter(adapter);
    }
}
