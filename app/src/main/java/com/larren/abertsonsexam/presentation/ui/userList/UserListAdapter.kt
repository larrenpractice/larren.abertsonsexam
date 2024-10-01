package com.larren.abertsonsexam.presentation.ui.userList

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.larren.abertsonsexam.data.models.User
import com.larren.abertsonsexam.databinding.RandomUserItemListBinding

class UserListAdapter(private var list: List<User>, private val onSelectUser: (User) -> Unit) :
    RecyclerView.Adapter<UserListAdapter.ResultViewHolder>() {

    class ResultViewHolder(
        private val binding: RandomUserItemListBinding,
        private val onSelectUser: (User) -> Unit
    ) :
        ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                this.user = user
                this.root.setOnClickListener { onSelectUser.invoke(user) }
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding =
            RandomUserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding, onSelectUser)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(it: List<User>) {
        list = it
        notifyDataSetChanged()
    }
}