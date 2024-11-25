package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class EditStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_student)

        // Get data from the intent
        val studentName = intent.getStringExtra("student_name")
        val studentId = intent.getStringExtra("student_id")
        val position = intent.getIntExtra("position", -1)

        // Ensure position is valid
        if (position == -1) {
            setResult(Activity.RESULT_CANCELED)
            finish()
            return
        }

        // References to UI elements
        val editTextStudentName: EditText = findViewById(R.id.edit_text_student_name)
        val editTextStudentId: EditText = findViewById(R.id.edit_text_student_id)
        val btnUpdate: Button = findViewById(R.id.btn_update_student)

        // Populate the EditTexts with current data
        editTextStudentName.setText(studentName)
        editTextStudentId.setText(studentId)

        // Handle update button click
        btnUpdate.setOnClickListener {
            val updatedName = editTextStudentName.text.toString()
            val updatedId = editTextStudentId.text.toString()

            // Return updated data to MainActivity
            val resultIntent = intent.apply {
                putExtra("student_name", updatedName)
                putExtra("student_id", updatedId)
                putExtra("position", position)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
