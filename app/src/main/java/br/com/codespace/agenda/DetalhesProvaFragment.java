package br.com.codespace.agenda;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.codespace.agenda.model.Exam;


public class DetalhesProvaFragment extends Fragment {
    private TextView campoMateria;
    private TextView campoData;
    private ListView listTopicos;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);
        campoMateria = (TextView) view.findViewById(R.id.txt_materia);
        campoData = (TextView) view.findViewById(R.id.txt_data);
        listTopicos = (ListView) view.findViewById(R.id.lista_topicos);

        Bundle bundle = getArguments();
        if (bundle != null){
            Exam exam = (Exam) bundle.getSerializable("exam");
            fillFields(exam);
        }

        return view;
    }

    public void fillFields(Exam exam) {
        campoData.setText(exam.getDate());
        campoMateria.setText(exam.getMatter());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                exam.getTopics()
        );

        listTopicos.setAdapter(adapter);
    }
}
