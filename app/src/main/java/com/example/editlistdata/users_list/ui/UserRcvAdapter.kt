package com.example.editlistdata.users_list.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.editlistdata.R
import com.example.editlistdata.users_list.domain.modules.User
import com.google.android.material.card.MaterialCardView

class UserRcvAdapter(
    private val users: List<User>,
    private val onItemClick : (Int) -> Unit
) : RecyclerView.Adapter<UserRcvAdapter.UserRcvViewHolder>() {

        private lateinit var context : Context

    inner class UserRcvViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var rcvLayout = view.findViewById<MaterialCardView>(R.id.rcvLayout)
        var userPic = view.findViewById<ImageView>(R.id.rcvUserImage)
        var userWorkProfile = view.findViewById<TextView>(R.id.rcvUserWorkProfile)
        var userName = view.findViewById<TextView>(R.id.rcvUserName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRcvViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcv_item, parent, false)
        return UserRcvViewHolder(view)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: UserRcvViewHolder, position: Int) {
        val currentUser = users[position]

        Glide.with(context).load(currentUser.userProfilePhoto).into(holder.userPic)
        holder.userWorkProfile.text = currentUser.userWorkProfile
        holder.userName.text = currentUser.userName
        holder.rcvLayout.setOnClickListener {
            onItemClick.invoke(currentUser.userId)
        }

    }
}