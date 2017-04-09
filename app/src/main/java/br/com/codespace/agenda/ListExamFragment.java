package br.com.codespace.agenda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.codespace.agenda.adapter.ExamAdapter;
import br.com.codespace.agenda.model.Exam;

/**
 * Created by gilma on 01/04/2017.
 */

public class ListExamFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List portTopics = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        Exam port = new Exam("Portugês", "20/12/2017", portTopics);

        List MatTopics = Arrays.asList("Equação 2º grau", "Raiz quadrada", "Logaritmos", "Trigonometria");
        Exam mat = new Exam("Matemática", "20/12/2017", MatTopics);

        List exams = Arrays.asList(port, mat);
        ExamAdapter adapter = new ExamAdapter(getContext(), exams);
        ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setAdapter(adapter);

        this.addExamListEvent(view);

        return view;
    }

    private void addExamListEvent(View view)
    {
        final ListView lista = (ListView) view.findViewById(R.id.provas_lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exam exam = (Exam) parent.getItemAtPosition(position);

                // substitui somente se tiver no modo retrato
                if (!getResources().getBoolean(R.bool.landscape)) {
                    FragmentManager manager = getFragmentManager();
                    FragmentTransaction tx = manager.beginTransaction();
                    tx.replace(R.id.frame_principal, new DetalhesProvaFragment());
                    tx.commit();
                }

                ProvasActivity provaActivity = (ProvasActivity) getActivity();
                provaActivity.selectMatter(exam);
            }
        });
    }
}
