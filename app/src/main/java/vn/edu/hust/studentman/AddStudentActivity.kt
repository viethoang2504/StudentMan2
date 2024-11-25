package vn.edu.hust.studentman

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        val nameEditText = findViewById<EditText>(R.id.edit_text_student_name)
        val idEditText = findViewById<EditText>(R.id.edit_text_student_id)
        val addButton = findViewById<Button>(R.id.btn_add_student)

        addButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val id = idEditText.text.toString()

            if (name.isNotBlank() && id.isNotBlank()) {
                val resultIntent = Intent().apply {
                    putExtra("student_name", name)
                    putExtra("student_id", id)
                }
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
