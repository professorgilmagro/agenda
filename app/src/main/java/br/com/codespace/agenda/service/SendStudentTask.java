package br.com.codespace.agenda.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import br.com.codespace.agenda.WebClient;
import br.com.codespace.agenda.converter.StudentConverter;
import br.com.codespace.agenda.dao.StudentDAO;

/**
 * Created by gilma on 01/04/2017.
 */

public class SendStudentTask extends AsyncTask <Void, Void, String> {
    private Context context;
    private ProgressDialog dialog;

    public SendStudentTask(Context context) {
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {
        WebClient client = new WebClient();
        StudentDAO dao = new StudentDAO(context);
        StudentConverter converter = new StudentConverter(dao.getAll());
        dao.close();
        String json = converter.toJSON();
        return client.post(json);
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("Enviando dados. Aguarde...");
        dialog.setTitle("Aguarde");
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    protected void onPostExecute(String response) {
        dialog.dismiss();
        Toast.makeText(context, response, Toast.LENGTH_LONG).show();
    }
}
