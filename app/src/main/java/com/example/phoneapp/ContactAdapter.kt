package com.example.phoneapp

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.phoneapp.RoomDB.Entity.Contact
import com.example.phoneapp.databinding.ContactListBinding

class ContactAdapter(var contactList: List<Contact>, var context: Context) :
    RecyclerView.Adapter<ContactAdapter.MyViewHolder>() {

    inner class MyViewHolder(var binding: ContactListBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.MyViewHolder {
        val binding = ContactListBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ContactAdapter.MyViewHolder, position: Int) {
        val contact = contactList[position]
        if (contact.userProfileImg != null) {
            var imageByte = contact.userProfileImg
            if (imageByte != null) {
                var image = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.size)
                holder.binding.userImg.setImageBitmap(image)
                holder.binding.userImg.visibility = View.VISIBLE
                holder.binding.userSign.visibility = View.GONE
            } else {
                val splitName = contact.userName?.split(" ")
                var sign: String = ""
                splitName?.forEach {
                    if (it.isNotEmpty()) {
                        sign += it[0]
                    }
                }
                holder.binding.userSign.text = sign
                holder.binding.userImg.visibility = View.GONE
                holder.binding.userSign.visibility = View.VISIBLE

            }
        } else {
            val splitName = contact.userName?.split(" ")
            var sign: String = ""
            splitName?.forEach {
                if (it.isNotEmpty()) {
                    sign += it[0]
                }
            }
            holder.binding.userSign.text = sign
            holder.binding.userImg.visibility = View.GONE
            holder.binding.userSign.visibility = View.VISIBLE

        }
        holder.binding.userName.text = contact.userName
        holder.binding.userPhoneNo.text = contact.userPhoneNo
        holder.binding.userEmail.text = contact.userEmail

        //single click slide view
        holder.itemView.setOnClickListener {
            if (holder.binding.hiddenView.visibility == View.VISIBLE) {
                // The transition of the hiddenView is carried out by the TransitionManager class.
                // Here we use an object of the AutoTransition Class to create a default transition
                TransitionManager.beginDelayedTransition(
                    holder.binding.contactCard,
                    AutoTransition()
                )
                holder.binding.hiddenView.visibility = View.GONE
//                holder.binding.openHiddenLayout.setImageResource(R.drawable.baseline_arrow_circle_down_24)
            } else {
                TransitionManager.beginDelayedTransition(
                    holder.binding.contactCard,
                    AutoTransition()
                )

                holder.binding.hiddenView.visibility = View.VISIBLE
//                holder.binding.openHiddenLayout.setImageResource(R.drawable.baseline_arrow_circle_up_24)


                ItemTouchHelper(object : ItemTouchHelper.Callback() {
                    override fun getMovementFlags(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder
                    ): Int {
                        TODO("Not yet implemented")
                    }

                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                    }

                })
            }

        }

        //for update contact
        holder.itemView.setOnLongClickListener {
            Toast.makeText(context, "Now Update Contact", Toast.LENGTH_SHORT).show()
            context.startActivity(
                Intent(context, AddEditActivity::class.java).putExtra("FLAG", 1)
                    .putExtra("DATA", contact)
            )
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }
}