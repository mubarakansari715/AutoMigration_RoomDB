package com.example.automigration_roomdb

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.automigration_roomdb.BaseApp.Companion.appDatabase
import com.example.automigration_roomdb.adapter.MyAdapter
import com.example.automigration_roomdb.databinding.ActivityMainBinding
import com.example.automigration_roomdb.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), ItemClick {

    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.btnSubmit.setOnClickListener {
            val edtText = binding.edtText.text.toString()
            if (edtText.isNotEmpty()) {
                GlobalScope.launch {
                    appDatabase.getUserDao().insertUser(User(name = edtText))

                    runOnUiThread {
                        myAdapter.setNewListData(User(name = edtText))
                        //getAllUserData()
                        binding.edtText.text?.clear()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter something", Toast.LENGTH_SHORT).show()
            }
        }

        getAllUserData()
    }

    private fun getAllUserData() {
        binding.apply {
            GlobalScope.launch {
                val response = appDatabase.getUserDao().getAllUserDataFromRoomDatabase()
                Log.e("TAG", "getAllUserData: $response")

                runOnUiThread {
                    myAdapter = MyAdapter(response)
                    // myAdapter.setList(response as ArrayList<User>)
                    myAdapter.setupOnClickItem(this@MainActivity)
                    recyclerView.adapter = myAdapter
                }
            }
        }
    }

    override fun itemClickListener(user: User) {
        binding.progressCircular.visibility = View.VISIBLE
        GlobalScope.launch {

            val userId = if (user.id == 0) {
                val response = appDatabase.getUserDao().getUserId(name = user.name.toString())
                response.id
            } else {
                user.id
            }

            appDatabase.getUserDao().deleteUserById(userId)

            runOnUiThread {
                myAdapter.setNewDeleteListData(user)
                binding.progressCircular.visibility = View.GONE
                Toast.makeText(this@MainActivity, user.name.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun itemClickDeleteUser(user: User, position: Int) {
        GlobalScope.launch {
            val response = appDatabase.getUserDao().getUserId(name = user.name.toString())

            runOnUiThread {

                showEditTextDialog(
                    context = this@MainActivity,
                    title = "Enter Text",
                    positiveButtonText = "OK",
                    negativeButtonText = "Cancel",
                    userName = user.name.toString()
                ) { enteredText ->
                    // Handle the entered text here
                    if (enteredText.isNotEmpty()) {
                        myAdapter.listOfUser[position].name = enteredText
                        binding.recyclerView.adapter?.notifyItemChanged(position)

                        GlobalScope.launch {
                            appDatabase.getUserDao()
                                .updateUser(User(id = response.id, name = enteredText))
                        }
                    }
                }
            }
        }
    }
}


fun showEditTextDialog(
    context: Context,
    title: String,
    positiveButtonText: String,
    negativeButtonText: String,
    userName: String,
    onPositiveButtonClick: (String) -> Unit,
) {
    val editText = EditText(context)
    editText.setText(userName)

    val dialog = AlertDialog.Builder(context)
        .setTitle(title)
        .setView(editText)
        .setPositiveButton(positiveButtonText) { dialog, which ->
            val enteredText = editText.text.toString()
            onPositiveButtonClick(enteredText)
            dialog.dismiss()
        }
        .setNegativeButton(negativeButtonText) { dialog, which ->
            dialog.cancel()
        }
        .create()

    dialog.show()
}