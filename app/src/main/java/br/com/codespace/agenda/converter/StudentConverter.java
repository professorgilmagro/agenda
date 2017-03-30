package br.com.codespace.agenda.converter;

import android.widget.Toast;
import br.com.codespace.agenda.model.Student;
import org.json.JSONException;
import org.json.JSONStringer;

import java.util.List;

/**
 * Created by gilmar on 29/03/17.
 */

public class StudentConverter {
    private final List<Student> students;

    public StudentConverter(List<Student> students) {
        this.students = students;
    }

    public String toJSON() {
        JSONStringer json = new JSONStringer();
        try {
            json.object().key("list").array().object().key("aluno").array();
            for (Student student : students) {
                json.object();
                json.key("nome").value(student.getFirstName());
                json.key("sobrenome").value(student.getLastName());
                json.key("logradouro").value(student.getStreet());
                json.key("numero").value(student.getStreetNumber());
                json.key("email").value(student.getEmail());
                json.key("telefone").value(student.getPhoneNumber());
                json.endObject();
            }
            json.endArray().endObject().endArray().endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }
}
