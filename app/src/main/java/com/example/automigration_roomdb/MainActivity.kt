package com.example.automigration_roomdb

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.automigration_roomdb.adapter.MyAdapter
import com.example.automigration_roomdb.databinding.ActivityMainBinding
import com.example.automigration_roomdb.model.User

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var myAdapter: MyAdapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)


        binding.btnSubmit.setOnClickListener {
            val edtText = binding.edtText.text.toString()

            Toast.makeText(this, edtText, Toast.LENGTH_SHORT).show()
           // myAdapter.list

            myAdapter.setNewListData(User(name = edtText))
            binding.edtText.text?.clear()
        }

        getAllUserData()
    }

    private fun getAllUserData() {
        binding.apply {
            recyclerView.adapter = myAdapter
        }
    }
}