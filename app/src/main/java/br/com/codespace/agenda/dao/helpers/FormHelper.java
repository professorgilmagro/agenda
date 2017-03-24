package br.com.codespace.agenda.dao.helpers;

import android.widget.RatingBar;
import android.widget.TextView;

import br.com.codespace.agenda.FormularioActivity;
import br.com.codespace.agenda.R;
import br.com.codespace.agenda.model.Student;

/**
 * Created by gilmar on 22/03/17.
 */

public class FormHelper {
    private TextView txtId;
    private TextView txtFirstName;
    private TextView txtLastName;
    private TextView txtZipcode;
    private TextView txtStreet;
    private TextView txtNeighborhood;
    private TextView txtCity;
    private TextView txtState;
    private TextView txtEmail;
    private TextView txtSite;
    private TextView txtPhone;
    private TextView txtHomeNumber;
    private RatingBar rtbScore;

    public FormHelper(FormularioActivity activity)
    {
        txtId = (TextView) activity.findViewById(R.id.txtId);
        txtFirstName = (TextView) activity.findViewById(R.id.txtFirstName);
        txtLastName = (TextView) activity.findViewById(R.id.txtLastName);
        txtZipcode = (TextView) activity.findViewById(R.id.txtZipcode);
        txtHomeNumber = (TextView) activity.findViewById(R.id.txtAddressNumber);
        txtStreet = (TextView) activity.findViewById(R.id.txtStreet);
        txtNeighborhood = (TextView) activity.findViewById(R.id.txtNeighborhood);
        txtCity = (TextView) activity.findViewById(R.id.txtCity);
        txtState = (TextView) activity.findViewById(R.id.txtState);
        txtEmail = (TextView) activity.findViewById(R.id.txtEmail);
        txtSite = (TextView) activity.findViewById(R.id.txtWebsite);
        txtPhone = (TextView) activity.findViewById(R.id.txtPhoneNumber);
        rtbScore = (RatingBar) activity.findViewById(R.id.rtbRanking);
    }

    /**
     * Retorna a instância do estudante com os dados preenchidos no formulário
     * @return Student
     */
    public Student getStudent()
    {
        Student student = new Student();
        if (!txtId.getText().toString().isEmpty()) {
            student.setId(Long.parseLong(txtId.getText().toString()));
        }

        student.setFirstName(txtFirstName.getText().toString());
        student.setLastName(txtLastName.getText().toString());
        student.setZipcode(txtZipcode.getText().toString());
        student.setStreet(txtStreet.getText().toString());
        student.setHomeNumber(Integer.parseInt(txtHomeNumber.getText().toString()));
        student.setNeighborhood(txtNeighborhood.getText().toString());
        student.setCity(txtCity.getText().toString());
        student.setState(txtState.getText().toString());
        student.setEmail(txtEmail.getText().toString());
        student.setWebsite(txtSite.getText().toString());
        student.setPhoneNumber(txtPhone.getText().toString());
        student.setScore(Double.valueOf(rtbScore.getProgress()));
        return student;
    }

    /**
     * Preenche os campos da Activity com base numa instância do aluno
     *
     * @param student   Instância do aluno
     */
    public void fillFields(Student student)
    {
        txtId.setText(student.getId().toString());
        txtFirstName.setText(student.getFirstName());
        txtLastName.setText(student.getLastName());
        txtZipcode.setText(student.getZipcode());
        txtStreet.setText(student.getStreet());
        txtHomeNumber.setText(student.getHomeNumber().toString());
        txtNeighborhood.setText(student.getNeighborhood());
        txtState.setText(student.getState());
        txtCity.setText(student.getCity());
        txtEmail.setText(student.getEmail());
        txtSite.setText(student.getWebsite());
        txtPhone.setText(student.getPhoneNumber());
        rtbScore.setProgress(student.getScore().intValue());
    }
}
