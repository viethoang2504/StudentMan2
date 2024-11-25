package vn.edu.hust.studentman

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


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

  private lateinit var studentAdapter: ArrayAdapter<String>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val toolbar: Toolbar = findViewById(R.id.toolbar)
    setSupportActionBar(toolbar)

    val listView: ListView = findViewById(R.id.list_view_students)

    // Setup Adapter for ListView
    studentAdapter = ArrayAdapter(
      this,
      android.R.layout.simple_list_item_1,
      students.map { "${it.studentName} - ${it.studentId}" }.toMutableList()
    )
    listView.adapter = studentAdapter

    // Register context menu for ListView
    registerForContextMenu(listView)

    // Handle ListView item clicks
    listView.setOnItemClickListener { _, _, position, _ ->
      val student = students[position]
      Toast.makeText(this, "Sinh viên: ${student.studentName}", Toast.LENGTH_SHORT).show()
    }
  }

  // Create Option Menu
  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_add_new -> {
        // Open AddStudentActivity
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_ADD)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  // Create Context Menu
  override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
    super.onCreateContextMenu(menu, v, menuInfo)
    menuInflater.inflate(R.menu.menu_student_context, menu)
  }

  override fun onContextItemSelected(item: MenuItem): Boolean {
    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
    val position = info.position
    val student = students[position]

    return when (item.itemId) {
      R.id.menu_edit -> {
        // Open EditStudentActivity
        val intent = Intent(this, EditStudentActivity::class.java).apply {
          putExtra("student_name", student.studentName)
          putExtra("student_id", student.studentId)
          putExtra("position", position)
        }
        startActivityForResult(intent, REQUEST_CODE_EDIT)
        true
      }
      R.id.menu_remove -> {
        // Remove student
        students.removeAt(position)
        updateListView()
        Toast.makeText(this, "Đã xóa sinh viên ${student.studentName}", Toast.LENGTH_SHORT).show()
        true
      }
      else -> super.onContextItemSelected(item)
    }
  }

  // Update the ListView
  private fun updateListView() {
    studentAdapter.clear()
    studentAdapter.addAll(students.map { "${it.studentName} - ${it.studentId}" })
    studentAdapter.notifyDataSetChanged()
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == RESULT_OK && data != null) {
      when (requestCode) {
        REQUEST_CODE_ADD -> {
          val name = data.getStringExtra("student_name") ?: return
          val id = data.getStringExtra("student_id") ?: return
          students.add(StudentModel(name, id))
          updateListView()
        }
        REQUEST_CODE_EDIT -> {
          val name = data.getStringExtra("student_name") ?: return
          val id = data.getStringExtra("student_id") ?: return
          val position = data.getIntExtra("position", -1)
          if (position != -1) {
            students[position] = StudentModel(name, id)
            updateListView()
          }
        }
      }
    }
  }

  companion object {
    const val REQUEST_CODE_ADD = 1
    const val REQUEST_CODE_EDIT = 2
  }
}
