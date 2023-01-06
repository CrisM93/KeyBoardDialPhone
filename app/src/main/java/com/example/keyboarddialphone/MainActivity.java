package com.example.keyboarddialphone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Creates the activity and sets the view, then sets the listener to
     * detect if a key was pressed in the EditText view.
     *
     * @param savedInstanceState    Instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the editText_main view and assign it to editText.
        EditText editText = findViewById(R.id.editText_main);

        if (editText != null)
            // If view is found, set the listener for editText.
            editText.setOnEditorActionListener(
                    new TextView.OnEditorActionListener() {
                        /**
                         * Responds to the pressed key and calls a method to dial
                         * the phone number.
                         * @param textView  The view that was clicked.
                         * @param actionId  Identifier of the action.
                         * @param keyEvent  If triggered by an Enter key, this is the
                         *                  event.
                         * @return          True, the key was entered, or false.
                         */
                        @Override
                        public boolean onEditorAction(
                                TextView textView, int actionId, KeyEvent keyEvent) {
                            // Start with no event.
                            boolean handled = false;

                            // If the action for the keyboard is defined as
                            // IME_ACTION_SEND (android:imeOptions="actionSend" in the
                            // layout), call the dialNumber method and return true.
                            if (actionId == EditorInfo.IME_ACTION_SEND) {
                                dialNumber();
                                handled = true;
                            }
                            return handled;
                        }
                    });

    }

    /**
     * Creates a string (phoneNum) for the phone number to dial,
     * and performs an implicit intent to dial the number.
     */
    private void dialNumber() {
        // Find the editText_main view.
        EditText editText = findViewById(R.id.editText_main);
        String phoneNum = null;

        // If the editText field is not null,
        // concatenate "tel: " with the phone number string.
        if (editText != null) phoneNum = "tel:" + editText.getText().toString();

        // Log the concatenated phone number for dialing.
        Log.d(TAG, "dialNumber: " + phoneNum);

        // Specify the intent.
        Intent intent = new Intent(Intent.ACTION_DIAL);

        // Set the data for the intent as the phone number.
        intent.setData(Uri.parse(phoneNum));

        // If the intent resolves to a package (app),
        // start the activity with the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(TAG, "ImplicitIntents: Can't handle this!");
        }
    }
}