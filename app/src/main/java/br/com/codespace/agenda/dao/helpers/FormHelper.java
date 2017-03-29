package br.com.codespace.agenda.dao.helpers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.format.DateUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import android.widget.Toast;
import br.com.codespace.agenda.FormularioActivity;
import br.com.codespace.agenda.R;
import br.com.codespace.agenda.model.Student;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by gilmar on 22/03/17.
 */

public class FormHelper {
    private final FormularioActivity activity;
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
    private TextView txtBirthDate;
    private TextView txtHomeNumber;
    private ImageView imgPhoto;
    private RatingBar rtbScore;

    public FormHelper(FormularioActivity activity)
    {
        this.activity = activity;
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
        txtBirthDate = (TextView) activity.findViewById(R.id.txtBirthDate);
        imgPhoto = (ImageView) activity.findViewById(R.id.imgProfile);
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

        if (imgPhoto.getTag() != null) {
            student.setPhotoPath(imgPhoto.getTag().toString());
        }

        if (txtBirthDate.getText() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                student.setBirthDate(formatter.parse(txtBirthDate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
        txtPhone.setText(student.getPhoneFormattedNumber());
        rtbScore.setProgress(student.getScore().intValue());

        if (student.getBirthDate() != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            txtBirthDate.setText(formatter.format(student.getBirthDate()));
        }

        if (student.getPhotoPath() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(student.getPhotoPath());
            Bitmap bitmapReduced = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            imgPhoto.setImageBitmap(bitmapReduced);
            imgPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            imgPhoto.setTag(student.getPhotoPath());
        }
    }
}
