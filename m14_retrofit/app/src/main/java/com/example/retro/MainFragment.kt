package com.example.retro

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch


class MainFragment : Fragment(R.layout.fragment_main) {
    private lateinit var nameView: TextView
    private lateinit var sourNameView: TextView
    private lateinit var ageView: TextView
    private lateinit var infoView: TextView
    private lateinit var imageView: ImageView
    private lateinit var restartButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nameView = requireActivity().findViewById<TextView>(R.id.textView1)
        sourNameView = requireActivity().findViewById<TextView>(R.id.textView2)
        ageView = requireActivity().findViewById<TextView>(R.id.textView3)
        infoView = requireActivity().findViewById<TextView>(R.id.textView4)
        imageView = requireActivity().findViewById<ImageView>(R.id.imageView)
        restartButton = requireActivity().findViewById<Button>(R.id.button)

        getInfo()
        restartButton.setOnClickListener {
            getInfo()
        }

    }

    @SuppressLint("SetTextI18n")
    fun getInfo() {
        lifecycleScope.launch {
            val user = RetrofitServices.searchUsersApi.getUsersInfoList()
            nameView.text =
                "Name: ${user.results.first().name.title}. ${user.results.first().name.first}"
            sourNameView.text = "Sourname: ${user.results.first().name.last}"
            ageView.text = "Age: ${user.results.first().dob.age}"
            infoView.text =
                "Email: ${user.results.first().email} \nId: ${user.results.first().id.value} \n" +
                        "Birthday: ${user.results.first().dob.date} " +
                        "\nPhone number: ${user.results.first().phone} " +
                        "\nRegistered ${user.results.first().registered.age} years ago"
            val pic = user.results.first().picture.medium
            Glide.with(this@MainFragment)
                .load(pic)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imageView)
        }
    }


}