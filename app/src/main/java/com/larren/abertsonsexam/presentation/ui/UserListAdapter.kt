package com.larren.abertsonsexam.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.larren.abertsonsexam.data.models.RandomUserResponse

class UserListAdapter(private var list: List<RandomUserResponse>) :
    RecyclerView.Adapter<UserListAdapter.ResultViewHolder>() {

//    class ResultViewHolder(private val view: RoomUserItemListBinding) : ViewHolder(view.root) {
//        fun bind(user: User, viewModel: UsersViewModel) {
//            view.apply {
//                tvId.text = user.uid.toString()
//                tvFirstName.text = user.firstName
//                tvLastName.text = user.lastName
//
//                btDelete.setOnClickListener {
//                    showConfirmationDialogPrompt(user, viewModel)
//                }
//            }
//        }
//
//        private fun showConfirmationDialogPrompt(user: User, viewModel: UsersViewModel) {
//            AlertDialog.Builder(itemView.context).apply {
//                setTitle(R.string.delete_confirmation_title_dialog)
//                setMessage(R.string.delete_confirmation_message_dialog)
//                setPositiveButton(R.string.room__delete_confirmation_proceed_button_dialog) { _, _ ->
//                    viewModel.deleteUser(user)
//                }
//                setNegativeButton(R.string.delete_confirmation_cancel_button_dialog, null)
//                    .create()
//                    .show()
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
//        val binding =
//            RoomUserItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return ResultViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
//        holder.bind(list[position], viewModel)
//    }
//
//    override fun getItemCount() = list.size
//    fun updateData(it: List<User>) {
//        list = it
//        notifyDataSetChanged()
//    }
}