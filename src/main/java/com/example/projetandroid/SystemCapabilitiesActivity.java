package com.example.projetandroid;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SystemCapabilitiesActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_capabilities);

        // Passer un appel téléphonique
        Button callButton = findViewById(R.id.call_button);
        EditText phoneNumber = findViewById(R.id.phone_number);
        callButton.setOnClickListener(v -> {
            String number = phoneNumber.getText().toString();
            if (!number.isEmpty()) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
                } else {
                    startActivity(callIntent);
                }
            }
        });

        // Envoyer un SMS
        Button smsButton = findViewById(R.id.sms_button);
        EditText smsNumber = findViewById(R.id.sms_number);
        EditText smsMessage = findViewById(R.id.sms_message);
        smsButton.setOnClickListener(v -> {
            String number = smsNumber.getText().toString();
            String message = smsMessage.getText().toString();
            if (!number.isEmpty() && !message.isEmpty()) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + number));
                smsIntent.putExtra("sms_body", message);
                startActivity(smsIntent);
            }
        });

        // Envoyer un email
        Button emailButton = findViewById(R.id.email_button);
        emailButton.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"example@example.com"});
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Sujet");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Contenu du message");
            startActivity(Intent.createChooser(emailIntent, "Choisissez une application"));
        });

        // Partager du contenu
        Button shareButton = findViewById(R.id.share_button);
        EditText shareText = findViewById(R.id.share_text);
        shareButton.setOnClickListener(v -> {
            String text = shareText.getText().toString();
            if (!text.isEmpty()) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, text);
                startActivity(Intent.createChooser(shareIntent, "Partager avec"));
            }
        });

        // Accéder à la localisation
        Button mapsButton = findViewById(R.id.maps_button);
        EditText locationAddress = findViewById(R.id.location_address);
        mapsButton.setOnClickListener(v -> {
            String address = locationAddress.getText().toString();
            if (!address.isEmpty()) {
                Intent mapsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
                startActivity(mapsIntent);
            }
        });

        // Ouvrir un navigateur web
        Button browserButton = findViewById(R.id.browser_button);
        EditText urlField = findViewById(R.id.url_field);
        browserButton.setOnClickListener(v -> {
            String url = urlField.getText().toString();
            if (!url.isEmpty()) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CALL_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission accordée. Réessayez l'appel.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission refusée.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
