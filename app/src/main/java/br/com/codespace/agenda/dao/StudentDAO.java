package br.com.codespace.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.codespace.agenda.model.Student;

/**
 * Created by gilmar on 23/03/17.
 */
public class StudentDAO extends SQLiteOpenHelper {
    static final private String TABLE_NAME = "students";
    static final private Integer TABLE_VERSION = 2;
    static final private String DB_NAME = "Agenda";

    public StudentDAO(Context context) {
        super(context, DB_NAME, null, TABLE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = String.format(
                "CREATE TABLE %s(" +
                    "id             INTEGER     PRIMARY KEY, " +
                    "first_name     TEXT        NOT NULL," +
                    "last_name      TEXT," +
                    "email          TEXT        UNIQUE," +
                    "zipcode        TEXT," +
                    "street         TEXT," +
                    "neighborhood   TEXT," +
                    "home_number    INTEGER," +
                    "complement     TEXT," +
                    "city           TEXT," +
                    "state          TEXT," +
                    "birthDate      TEXT," +
                    "website        TEXT," +
                    "phone          TEXT," +
                    "score          REAL," +
                    "photo_path     TEXT," +
                    "gender         TEXT" +
                ")",
                TABLE_NAME
        );

        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        switch (oldVersion) {
            case 1:
                String sql = String.format("ALTER TABLE %s ADD COLUMN photo_path TEXT", TABLE_NAME);
                sqLiteDatabase.execSQL(sql);
                break;
        }
    }

    /**
     * Permite gravar os dados do aluno com base na instância do mesmo
     * Este método decide se deve atualizar ou inserir de maneira transparente
     * @param student instancia do aluno
     */
    public void save(Student student) {
        if (student.getId() != null) {
            this.update(student);
        }
        else {
            this.insert(student);
        }
    }

    /**
     * Insere um novo aluno
     * @param student   instância do aluno a ser gravada
     */
    public void insert(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME, null, this.getContentValues(student));
    }

    /**
     * Atualiza o aluno
     * @param student   Instância do aluno
     */
    public void update(Student student) {
        String[] params = {student.getId().toString()};
        SQLiteDatabase db = getWritableDatabase();
        db.update(TABLE_NAME, this.getContentValues(student), "id = ?", params);
    }

    /**
     * Retorna o conteúdo com base na instância do aluno
     *
     * @param student   instância
     * @return ContentValues
     */
    private ContentValues getContentValues(Student student) {
        ContentValues data = new ContentValues();
        if (student.getId() != null) {
            data.put("id", student.getId());
        }

        data.put("first_name", student.getFirstName());
        data.put("last_name", student.getLastName());
        data.put("email", student.getEmail());
        data.put("zipcode", student.getZipcode());
        data.put("street", student.getStreet());
        data.put("neighborhood", student.getNeighborhood());
        data.put("home_number", student.getStreetNumber());
        data.put("complement", student.getComplement());
        data.put("city", student.getCity());
        data.put("state", student.getState());
        data.put("website", student.getWebsite());
        data.put("phone", student.getPhoneNumber());
        data.put("score", student.getScore());
        data.put("gender", student.getGender());
        data.put("photo_path", student.getPhotoPath());

        return data;
    }

    /**
     * Retorna todos os registros de alunos contidos na tabela
     * @return List<Student>
     */
    public List<Student> getAll() {
        String sql = String.format("SELECT * FROM %s", TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, null);
        List<Student> students = new ArrayList<>();
        while (c.moveToNext()) {
            students.add(this.fillByCursor(c));
        }

        c.close();
        return students;
    }

    /**
     * Retorna todos os registros de alunos contidos na tabela
     * @return Student
     */
    public Student getById(Long id) {
        String[] params = {id.toString()};
        String sql = String.format("SELECT * FROM %s WHERE id = ?", TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, params);
        Student student = this.fillByCursor(c);
        c.close();
        return student;
    }

    /**
     * Retorna todos os registros de alunos contidos na tabela
     * @return Student
     */
    public Student getByPhone(String phone) {
        String sql = String.format("SELECT * FROM %s WHERE phone = ?", TABLE_NAME);
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(sql, new String[] {phone});
        Student student = this.fillByCursor(c);
        c.close();
        return student;
    }

    /**
     * Cria uma instância de Student com base nos dados oriundos de um cursor do recordset
     * @param c Cursor
     * @return  Student
     */
    private Student fillByCursor(Cursor c) {
        Student student = new Student();
        if (c.getCount() > 0) {
            student.setId(c.getLong(c.getColumnIndex("id")));
            student.setFirstName(c.getString(c.getColumnIndex("first_name")));
            student.setLastName(c.getString(c.getColumnIndex("last_name")));
            student.setEmail(c.getString(c.getColumnIndex("email")));
            student.setWebsite(c.getString(c.getColumnIndex("website")));
            student.setPhoneNumber(c.getString(c.getColumnIndex("phone")));
            student.setZipcode(c.getString(c.getColumnIndex("zipcode")));
            student.setStreet(c.getString(c.getColumnIndex("street")));
            student.setNeighborhood(c.getString(c.getColumnIndex("neighborhood")));
            student.setStreetNumber(c.getInt(c.getColumnIndex("home_number")));
            student.setComplement(c.getString(c.getColumnIndex("complement")));
            student.setCity(c.getString(c.getColumnIndex("city")));
            student.setState(c.getString(c.getColumnIndex("state")));
            student.setGender(c.getString(c.getColumnIndex("gender")));
            student.setPhotoPath(c.getString(c.getColumnIndex("photo_path")));
            student.setScore(c.getDouble(c.getColumnIndex("score")));
        }

        return student;
    }

    /**
     * Permite excluir um aluno com base na instância do mesmo
     * @param student   instância do student
     */
    public void delete(Student student) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {student.getId().toString()};
        db.delete(TABLE_NAME, "id = ?", params);
    }
}
