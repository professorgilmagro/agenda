package br.com.codespace.agenda;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import br.com.codespace.agenda.dao.StudentDAO;
import br.com.codespace.agenda.dao.helpers.FormHelper;
import br.com.codespace.agenda.model.Student;

import java.io.File;
import java.util.Locale;

public class FormularioActivity extends AppCompatActivity {
    final static int PHOTO_REQUEST_CODE = 666;
    public String photoPath;

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
        this.addPhotoEvent();

        final TextView txtPhone = (TextView) findViewById(R.id.txtPhoneNumber);
        txtPhone.addTextChangedListener(new PhoneNumberFormattingTextWatcher(){});
    }

    private void addPhotoEvent() {
        final Button btnPhoto = (Button) findViewById(R.id.btnPhoto);

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (ActivityCompat.checkSelfPermission(ListaAlunos.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(ListaAlunos.this,
//                            new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_CALL);
//                }

                Intent itCam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                FormularioActivity.this.photoPath = String.format("%s/%s.jpg", getExternalFilesDir(null), System.currentTimeMillis());
                File file = new File(photoPath);
                itCam.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(itCam, PHOTO_REQUEST_CODE);
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PHOTO_REQUEST_CODE:
                final ImageView imgProfile = (ImageView) findViewById(R.id.imgProfile);
                Bitmap bitmap = BitmapFactory.decodeFile(this.photoPath);
                imgProfile.setImageBitmap(bitmap);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
}
