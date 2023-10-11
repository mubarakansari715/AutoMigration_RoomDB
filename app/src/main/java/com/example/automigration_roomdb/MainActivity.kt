package com.example.automigration_roomdb

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.automigration_roomdb.BaseApp.Companion.appDatabase
import com.example.automigration_roomdb.adapter.MyAdapter
import com.example.automigration_roomdb.databinding.ActivityMainBinding
import com.example.automigration_roomdb.model.User
import com.example.automigration_roomdb.utils.ItemClick
import com.example.automigration_roomdb.utils.ItemTouchHelperCallback
import com.example.automigration_roomdb.utils.Utils.hideKeyboard
import com.example.automigration_roomdb.utils.Utils.showEditTextDialog
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
                    val userId = System.currentTimeMillis().toString()
                    appDatabase.getUserDao().insertUser(User(name = edtText, userId = userId))

                    runOnUiThread {
                        myAdapter.setNewListData(User(name = edtText, userId = userId))
                        binding.edtText.text?.clear()
                        hideKeyboard(this@MainActivity)
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
                    myAdapter.setupOnClickItem(this@MainActivity)
                    moveItems(myAdapter)
                    recyclerView.adapter = myAdapter
                }
            }
        }
    }

    override fun clickUserDeleteData(user: User) {
        GlobalScope.launch {

            appDatabase.getUserDao().deleteUserById(user.userId.toString())

            runOnUiThread {
                myAdapter.setNewDeleteListData(user)
                Toast.makeText(this@MainActivity, user.name.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun clickUserEditData(user: User, position: Int) {
        showEditTextDialog(
            context = this@MainActivity,
            title = getString(R.string.app_name),
            positiveButtonText = "OK",
            negativeButtonText = "Cancel",
            userName = user.name.toString()
        ) { enteredText ->
            // Handle the entered text here
            if (enteredText.isNotEmpty()) {
                myAdapter.listOfUser[position].name = enteredText
                binding.recyclerView.adapter?.notifyItemChanged(position)

                GlobalScope.launch {
                    appDatabase.getUserDao().updateUser(
                        enteredText,
                        user.userId.toString()
                    )
                }
            }
        }
    }

    private fun moveItems(myAdapter: MyAdapter) {
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(myAdapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

    }
}