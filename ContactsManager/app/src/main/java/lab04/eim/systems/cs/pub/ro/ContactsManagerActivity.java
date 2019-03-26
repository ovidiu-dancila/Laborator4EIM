package lab04.eim.systems.cs.pub.ro;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ContactsManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);

        EditText phoneEditText = findViewById(R.id.txt_phone_nr);
        Intent startingIntent = getIntent();
        if (startingIntent != null) {
            String phoneNr = startingIntent.getStringExtra("lab04.eim.systems.cs.pub.ro.intent.action.ContactsManagerActivity.PHONE_NUMBER_KEY");
            if (phoneNr != null) {
                phoneEditText.setText(phoneNr);
            } else {
                Toast.makeText(this, "No phone number", Toast.LENGTH_LONG).show();
            }
        }

        final Button additionalFieldsBtn = findViewById(R.id.additional_fields_btn);
        additionalFieldsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout additionalFieldsLayout = findViewById(R.id.lin_layout_additional);
                if (additionalFieldsBtn.getText().equals(Constants.SHOW_ADDITIONAL_FIELDS)) {
                    additionalFieldsLayout.setVisibility(View.VISIBLE);
                    additionalFieldsBtn.setText(Constants.HIDE_ADDITIONAL_FIELDS);
                } else if (additionalFieldsBtn.getText().equals(Constants.HIDE_ADDITIONAL_FIELDS)) {
                    additionalFieldsLayout.setVisibility(View.GONE);
                    additionalFieldsBtn.setText(Constants.SHOW_ADDITIONAL_FIELDS);
                }
            }
        });

        Button cancelBtn = findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED, new Intent());
                finish();
            }
        });

        Button saveBtn = findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText) findViewById(R.id.txt_name)).getText().toString();
                String phone = ((EditText) findViewById(R.id.txt_phone_nr)).getText().toString();
                String email = ((EditText) findViewById(R.id.txt_email)).getText().toString();
                String address = ((EditText) findViewById(R.id.txt_address)).getText().toString();
                //Toast.makeText(getApplicationContext(), name + email + address, Toast.LENGTH_SHORT).show();
                String jobTitle = ((EditText) findViewById(R.id.txt_job_title)).getText().toString();
                String company = ((EditText) findViewById(R.id.txt_company)).getText().toString();
                String website = ((EditText) findViewById(R.id.txt_website)).getText().toString();
                String im = ((EditText) findViewById(R.id.txt_im)).getText().toString();

                Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                intent.setType((ContactsContract.RawContacts.CONTENT_TYPE));

                if (name != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, name);
                }
                if (phone != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone);
                }
                if (email != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
                }
                if (address != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address);
                }
                if (jobTitle != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, jobTitle);
                }
                if (company != null) {
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company);
                }

                ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
                if (website != null) {
                    ContentValues websiteRow = new ContentValues();
                    websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
                    websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website);
                    contactData.add(websiteRow);
                }
                if (im != null) {
                    ContentValues imRow = new ContentValues();
                    imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
                    imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im);
                    contactData.add(imRow);
                }

                intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
//                startActivity(intent);
                startActivityForResult(intent, Constants.CONTACTS_MANAGER_REQUEST_CODE);
            }
        });


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case Constants.CONTACTS_MANAGER_REQUEST_CODE:
                setResult(resultCode, new Intent());
                Toast.makeText(this, "Activiy return with result" + resultCode, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
