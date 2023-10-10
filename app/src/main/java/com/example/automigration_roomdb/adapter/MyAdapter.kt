package com.example.automigration_roomdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.automigration_roomdb.databinding.ItemsViewBinding
import com.example.automigration_roomdb.model.User

class MyAdapter() : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    var list: ArrayList<User> = ArrayList<User>()
    val newList: ArrayList<User> = list as ArrayList<User>

    class MyViewHolder(private val binding: ItemsViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun getAllViewData(user: User) {
            binding.lblName.text = user.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemsViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            val data = list[position]
            getAllViewData(data)
        }
    }

    fun setNewListData(list: User) {
        newList.add(list)
        notifyDataSetChanged()
    }
}