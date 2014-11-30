package Send_sms;


        import android.app.Activity;
        import android.os.Bundle;
        import android.telephony.SmsManager;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;

        import com.example.michal.smssync.R;

public class SMSAppActivity extends Activity implements OnClickListener {

    // Wiget GUI
    EditText txtNumber, txtMessage;
    Button btnSend;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_sms);

        // Init GUI
        txtNumber = (EditText) findViewById(R.id.txtNumber);
        txtMessage = (EditText) findViewById(R.id.txtMesssage);
        btnSend = (Button) findViewById(R.id.btnSMS);

        // Attached Click Listener
        btnSend.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btnSend) {

            // Initialize SmsManager Object
            SmsManager smsManager = SmsManager.getDefault();

            // Send Message using method of SmsManager object
            smsManager.sendTextMessage(txtNumber.getText().toString(), null,
                    txtMessage.getText().toString(), null, null);

            Toast.makeText(this, "Message sent successfully", Toast.LENGTH_LONG)
                    .show();

        }

    }
}