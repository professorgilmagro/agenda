package br.com.codespace.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.codespace.agenda.adapter.ExamAdapter;
import br.com.codespace.agenda.model.Exam;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        List portTopics = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        Exam port = new Exam("Portugês", "20/12/2017", portTopics);

        List MatTopics = Arrays.asList("Equação 2º grau", "Raiz quadrada", "Logaritmos", "Trigonometria");
        Exam mat = new Exam("Matemática", "20/12/2017", MatTopics);

        List exams = Arrays.asList(port, mat);
        ExamAdapter adapter = new ExamAdapter(this, exams);
        ListView lista = (ListView) findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        this.addExamListEvent();
    }

    private void addExamListEvent()
    {
        final ListView lista = (ListView) findViewById(R.id.provas_lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exam exam = (Exam) parent.getItemAtPosition(position);
                Intent it = new Intent(ProvasActivity.this, DetalheProvaActivity.class);
                it.putExtra("exam", exam);
                startActivity(it);
            }
        });
    }
}
