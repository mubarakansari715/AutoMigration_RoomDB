package com.example.automigration_roomdb.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.automigration_roomdb.ItemClick
import com.example.automigration_roomdb.databinding.ItemsViewBinding
import com.example.automigration_roomdb.model.User

class MyAdapter(var listOfUser: List<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val newList: ArrayList<User> = listOfUser as ArrayList<User>

    private lateinit var _itemClick: ItemClick
    fun setupOnClickItem(itemClick: ItemClick) {
        _itemClick = itemClick
    }

    class MyViewHolder(val binding: ItemsViewBinding) :
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

    override fun getItemCount(): Int = listOfUser.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            val data = listOfUser[position]
            getAllViewData(data)

            binding.imgDeleteIcon.setOnClickListener {
                _itemClick.clickUserDeleteData(data)
            }

            binding.imgEditIcon.setOnClickListener {
                _itemClick.clickUserEditData(data, position)
            }
        }
    }

    fun setNewListData(user: User) {
        newList.add(user)
        notifyDataSetChanged()
    }

    fun setNewDeleteListData(user: User) {
        newList.remove(user)
        notifyDataSetChanged()
    }
}