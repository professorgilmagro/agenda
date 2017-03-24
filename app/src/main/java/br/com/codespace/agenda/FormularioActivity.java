package br.com.codespace.agenda;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import br.com.codespace.agenda.dao.StudentDAO;
import br.com.codespace.agenda.dao.helpers.FormHelper;
import br.com.codespace.agenda.model.Student;

import java.util.Locale;

public class FormularioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String action = getIntent().getStringExtra("EXTRA_ACTION");
        String title = "Cadastrar aluno";
        if (action.equals(ListaAlunos.EXTRA_ACTION_EDIT)){
            Student student = (Student) getIntent().getParcelableExtra("EXTRA_ITEM");
            title = String.format("Editar aluno %s", student.getFullName());
            FormHelper helper = new FormHelper(this);
            helper.fillFields(student);
        }

        this.addSearchZipcodeEvent();
        this.setTitle(title);

        final TextView txtPhone = (TextView) findViewById(R.id.txtPhoneNumber);
        txtPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher(){});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ok:
                FormHelper helper = new FormHelper(this);
                Student student = helper.getStudent();
                StudentDAO dao = new StudentDAO(this);
                dao.save(student);
                dao.close();
                Toast.makeText(this, String.format("Estudante %s salvo com sucesso", student.getFullName()), Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Adiciona o envento ao botão para adicionar novos itens
     */
    private void addSearchZipcodeEvent()
    {
        final TextView txtZipcode = (TextView) findViewById(R.id.txtZipcode);
        txtZipcode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean gainFocus) {
               if (gainFocus) {
                   return;
               }

               CepService CepService = new CepService(FormularioActivity.this);
               CepService.execute(txtZipcode.getText().toString());
           }
       });
    }

    private void addCustomDateEvent()
    {

    }


}
