package br.com.codespace.agenda;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.codespace.agenda.model.Exam;

public class ProvasActivity extends AppCompatActivity {

    public boolean isFragmentDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction tx = fragmentManager.beginTransaction();

        // inclui o fragmento da lista de provas
        tx.replace(R.id.frame_principal, new ListExamFragment());

        // se estiver em modo paisagem, inclui o fragmento de detalhes
        if (getResources().getBoolean(R.bool.landscape)) {
            tx.replace(R.id.frame_detalhes, new DetalhesProvaFragment());
        }

        isFragmentDetails = false;
        tx.commit();
    }

    public void selectMatter(Exam exam){
        FragmentManager manager = getSupportFragmentManager();
        isFragmentDetails = true;
        if (!getResources().getBoolean(R.bool.landscape)) {
            FragmentTransaction tx = manager.beginTransaction();

            DetalhesProvaFragment detalhesFragment = new DetalhesProvaFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("exam", exam);
            detalhesFragment.setArguments(bundle);
            tx.replace(R.id.frame_principal, detalhesFragment);
            tx.commit();
        } else {
            DetalhesProvaFragment detalhesFragment =
                    (DetalhesProvaFragment) manager.findFragmentById(R.id.frame_detalhes);
            detalhesFragment.fillFields(exam);
        }
    }

    /**
     * Sobrescrito para alterar o comportamento do botão voltar
     * se tiver no modo retrato, ao pressionar o botão voltar, o sistema deve apenas substituir
     * os detalhes pela lista novamente
     */
    @Override
    public void onBackPressed() {
        if (!getResources().getBoolean(R.bool.landscape) && this.isFragmentDetails) {
            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.frame_principal, new ListExamFragment()).commit();
            isFragmentDetails = false;
            return;
        }

        super.onBackPressed();
    }
}
