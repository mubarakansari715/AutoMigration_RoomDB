package com.example.automigration_roomdb

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.automigration_roomdb.BaseApp.Companion.appDatabase
import com.example.automigration_roomdb.adapter.MyAdapter
import com.example.automigration_roomdb.databinding.ActivityMainBinding
import com.example.automigration_roomdb.model.User
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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
                }
                runOnUiThread {
                    myAdapter.setNewListData(User(name = edtText))
                    binding.edtText.text?.clear()
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
                    recyclerView.adapter = myAdapter
                }
            }
        }
    }
}