package com.example.plantdoc

import android.R
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    var LogInButton: Button? = null
    var RegisterButton: Button? = null
    var Email: EditText? = null
    var Password: EditText? = null
    var EmailHolder: String? = null
    var PasswordHolder: String? = null
    var EditTextEmptyHolder: Boolean? = null
    var sqLiteDatabaseObj: SQLiteDatabase? = null
    var sqLiteHelper: SQLiteHelper? = null
    var cursor: Cursor? = null
    var TempPassword = "NOT_FOUND"
    protected fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        LogInButton = findViewById(R.id.buttonLogin) as Button?
        RegisterButton = findViewById(R.id.buttonRegister) as Button?
        Email = findViewById(R.id.editEmail) as EditText?
        Password = findViewById(R.id.editPassword) as EditText?
        sqLiteHelper = SQLiteHelper(this)

        //Adding click listener to log in button.
        LogInButton!!.setOnClickListener { // Calling EditText is empty or no method.
            CheckEditTextStatus()

            // Calling login method.
            LoginFunction()
        }

        // Adding click listener to register button.
        RegisterButton!!.setOnClickListener { // Opening new user registration activity using intent on button click.
            val intent: Intent = Intent(
                this@MainActivity,
                RegisterActivity::class.java
            )
            ContextCompat.startActivity(intent)
        }
    }

    // Login function starts from here.
    fun LoginFunction() {
        if (EditTextEmptyHolder!!) {

            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper!!.writableDatabase

            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(
                SQLiteHelper.TABLE_NAME,
                null,
                " " + SQLiteHelper.Table_Column_2_Email + "=?",
                arrayOf(EmailHolder),
                null,
                null,
                null
            )
            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst()

                    // Storing Password associated with entered email.
                    TempPassword =
                        cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_Password))

                    // Closing cursor.
                    cursor.close()
                }
            }

            // Calling method to check final result ..
            CheckFinalResult()
        } else {

            //If any of login EditText empty then this block will be executed.
            Toast.makeText(
                this@MainActivity,
                "Please Enter UserName or Password.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Checking EditText is empty or not.
    fun CheckEditTextStatus() {

        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email!!.text.toString()
        PasswordHolder = Password!!.text.toString()

        // Checking EditText is empty or no using TextUtils.
        EditTextEmptyHolder =
            if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)) {
                false
            } else {
                true
            }
    }

    // Checking entered password from SQLite database email associated password.
    fun CheckFinalResult() {
        if (TempPassword.equals(PasswordHolder, ignoreCase = true)) {
            Toast.makeText(this@MainActivity, "Login Successful", Toast.LENGTH_LONG).show()

            // Going to Dashboard activity after login success message.
            val intent: Intent = Intent(
                this@MainActivity,
                DashboardActivity::class.java
            )

            // Sending Email to Dashboard Activity using intent.
            intent.putExtra(UserEmail, EmailHolder)
            ContextCompat.startActivity(intent)
        } else {
            Toast.makeText(
                this@MainActivity,
                "UserName or Password is Wrong, Please Try Again.",
                Toast.LENGTH_LONG
            ).show()
        }
        TempPassword = "NOT_FOUND"
    }

    companion object {
        const val UserEmail = ""
    }
}