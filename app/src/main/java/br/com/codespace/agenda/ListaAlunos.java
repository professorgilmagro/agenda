package br.com.codespace.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.codespace.agenda.dao.StudentDAO;
import br.com.codespace.agenda.model.Student;

public class ListaAlunos extends AppCompatActivity {
    final static String EXTRA_ACTION_NEW = "new";
    final static String EXTRA_ACTION_EDIT = "edit";
    final static int REQUEST_CODE_CALL = 555;
    private ListView listaAlunos;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listaAlunos = (ListView) findViewById(R.id.lista_alunos);

        this.addCreateEvent();
        this.addEditEvent();

        registerForContextMenu(listaAlunos);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.fillListView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_alunos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Preenche a lista de alunos
     */
    private void fillListView() {
        StudentDAO dao = new StudentDAO(this);
        List<Student> students = dao.getAll();
        ArrayAdapter adapter = new ArrayAdapter<Student>(this, android.R.layout.simple_list_item_1, students);
        listaAlunos.setAdapter(adapter);
        dao.close();
    }

    /**
     * Adiciona o envento ao botão para adicionar novos itens
     */
    private void addCreateEvent() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ListaAlunos.this, FormularioActivity.class);
                it.putExtra("EXTRA_ACTION", EXTRA_ACTION_NEW);
                startActivity(it);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Student student = (Student) listaAlunos.getItemAtPosition(info.position);


        String website = student.getWebsite();
        if (!website.isEmpty()) {
            MenuItem menuSite = menu.add("Visitar site");
            Intent itSite = new Intent(Intent.ACTION_VIEW);
            if (!website.startsWith("http")) {
                website = "http://" + website;
            }
            itSite.setData(Uri.parse(website));
            menuSite.setIntent(itSite);
        }

        if (!(student.getPhoneNumber().isEmpty())) {
            MenuItem menuSMS = menu.add("Enviar SMS");
            Intent itSMS = new Intent(Intent.ACTION_VIEW);
            itSMS.setData(Uri.parse(String.format("sms::%s", student.getPhoneNumber())));
            menuSMS.setIntent(itSMS);

            this.phone = student.getPhoneNumber();
            MenuItem menuLigar = menu.add("Ligar");
            menuLigar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    if (ActivityCompat.checkSelfPermission(ListaAlunos.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(ListaAlunos.this,
                                new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL);
                    } else {
                      Intent itCall = getIntentCall(student.getPhoneNumber());
                        startActivity(itCall);
                    }
                    return false;
                }
            });
        }

        MenuItem menuMapa = menu.add("Ver no mapa");
        Intent itMap = new Intent(Intent.ACTION_VIEW);
        itMap.setData(Uri.parse(String.format("geo:0,0?q=%s", student.getAddress())));
        menuMapa.setIntent(itMap);

        MenuItem menuDel = menu.add("Excluir");
        menuDel.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                StudentDAO dao = new StudentDAO(ListaAlunos.this);
                dao.delete(student);
                dao.close();
                fillListView();
                Toast.makeText(ListaAlunos.this, "Aluno removido com sucesso", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    /**
     * Retorna um intent de ligação
     * @param phoneNumber   Número do telefone
     * @return Intent
     */
    private Intent getIntentCall(String phoneNumber)
    {
        Intent itCall = new Intent(Intent.ACTION_CALL);
        itCall.setData(Uri.parse(String.format("tell:%s", phoneNumber)));
        return itCall;
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_CALL:
                Intent itCall = getIntentCall(this.phone);
                startActivity(itCall);
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Adiciona o evento para permitir a edição do aluno
     */
    private void addEditEvent()
    {
        listaAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View item, int position, long id) {
                Intent it = new Intent(ListaAlunos.this, FormularioActivity.class);
                it.putExtra("EXTRA_ACTION", EXTRA_ACTION_EDIT);
                Student student = (Student) listView.getItemAtPosition(position);
                it.putExtra("EXTRA_ITEM", student);
                startActivity(it);
            }
        });
    }
}
