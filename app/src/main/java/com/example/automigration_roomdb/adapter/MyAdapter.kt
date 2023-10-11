package com.example.automigration_roomdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.automigration_roomdb.databinding.ItemsViewBinding
import com.example.automigration_roomdb.model.User
import com.example.automigration_roomdb.utils.ItemClick
import java.util.Collections

class MyAdapter(var listOfUser: List<User>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    private val newList: ArrayList<User> = listOfUser as ArrayList<User>

    private lateinit var _itemClick: ItemClick

    private var positions: Int = 0
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

            positions = position
            val data = listOfUser[positions]
            getAllViewData(data)

            binding.imgDeleteIcon.setOnClickListener {
                _itemClick.clickUserDeleteData(data)
            }

            binding.imgEditIcon.setOnClickListener {
                _itemClick.clickUserEditData(data, positions)
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

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        // Swap the items in your data list
        Collections.swap(listOfUser, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        positions = toPosition
    }

}