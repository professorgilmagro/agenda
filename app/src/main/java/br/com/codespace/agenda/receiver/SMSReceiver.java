package br.com.codespace.agenda.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;
import android.widget.Toast;
import br.com.codespace.agenda.R;
import br.com.codespace.agenda.dao.StudentDAO;
import br.com.codespace.agenda.model.Student;

/**
 * Created by gilmar on 29/03/17.
 */

public class SMSReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Object[] pdus = (Object[]) intent.getSerializableExtra("pdus");
        byte[] pdu = (byte[]) pdus[0];
        String formato = (String) intent.getSerializableExtra("format");
        SmsMessage sms = SmsMessage.createFromPdu(pdu, formato);
        String phone = sms.getDisplayOriginatingAddress();
        StudentDAO dao = new StudentDAO(context);
        Student student = dao.getByPhone(phone);
        if (student.exists()) {
            MediaPlayer mp = MediaPlayer.create(context, R.raw.msg);
            mp.start();
            Toast.makeText(context, "SMS recebido de " + student.getFullName(), Toast.LENGTH_LONG).show();
        }
    }
}
