package vn.edu.hust.studentman

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
  private val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )
  private lateinit var studentAdapter: StudentAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // Setup RecyclerView
    studentAdapter = StudentAdapter(students) { action, position ->
      when (action) {
        "edit" -> showEditStudentDialog(position)
        "delete" -> showDeleteConfirmationDialog(position)
      }
    }

    findViewById<RecyclerView>(R.id.recycler_view_students).apply {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    // Add New Button Listener
    findViewById<Button>(R.id.btn_add_new).setOnClickListener {
      showAddNewStudentDialog()
    }
  }

  // Add New Student Dialog
  private fun showAddNewStudentDialog() {
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_student, null)
    val dialog = AlertDialog.Builder(this)
      .setTitle("Add New Student")
      .setView(dialogView)
      .setPositiveButton("Add") { _, _ ->
        val name = dialogView.findViewById<EditText>(R.id.edit_text_student_name).text.toString()
        val id = dialogView.findViewById<EditText>(R.id.edit_text_student_id).text.toString()
        if (name.isNotBlank() && id.isNotBlank()) {
          students.add(StudentModel(name, id))
          studentAdapter.notifyItemInserted(students.size - 1)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()

    dialog.show()
  }

  // Edit Student Dialog
  private fun showEditStudentDialog(position: Int) {
    val student = students[position]
    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_edit_student, null)
    val editName = dialogView.findViewById<EditText>(R.id.edit_text_student_name)
    val editId = dialogView.findViewById<EditText>(R.id.edit_text_student_id)

    editName.setText(student.studentName)
    editId.setText(student.studentId)

    val dialog = AlertDialog.Builder(this)
      .setTitle("Edit Student")
      .setView(dialogView)
      .setPositiveButton("Update") { _, _ ->
        val newName = editName.text.toString()
        val newId = editId.text.toString()
        if (newName.isNotBlank() && newId.isNotBlank()) {
          students[position] = StudentModel(newName, newId)
          studentAdapter.notifyItemChanged(position)
        }
      }
      .setNegativeButton("Cancel", null)
      .create()

    dialog.show()
  }

  // Delete Confirmation Dialog
  private fun showDeleteConfirmationDialog(position: Int) {
    val student = students[position]

    AlertDialog.Builder(this)
      .setTitle("Delete Student")
      .setMessage("Are you sure you want to delete ${student.studentName}?")
      .setPositiveButton("Delete") { _, _ ->
        val deletedStudent = students.removeAt(position)
        studentAdapter.notifyItemRemoved(position)
        showUndoSnackbar(deletedStudent, position)
      }
      .setNegativeButton("Cancel", null)
      .create()
      .show()
  }

  // Show Snackbar with Undo Option
  private fun showUndoSnackbar(deletedStudent: StudentModel, position: Int) {
    val mainLayout = findViewById<ConstraintLayout>(R.id.main)

    Snackbar.make(mainLayout, "${deletedStudent.studentName} has been deleted", Snackbar.LENGTH_LONG)
      .setAction("Undo") {
        students.add(position, deletedStudent)
        studentAdapter.notifyItemInserted(position)
      }
      .show()
  }
}
