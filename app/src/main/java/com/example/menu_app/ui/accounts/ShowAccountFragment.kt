package com.example.menu_app.ui.accountsl

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil

import androidx.recyclerview.widget.RecyclerView
import com.example.menu_app.Classes.Accounts


import com.example.menu_app.R
import com.example.menu_app.database.DBHandler
import com.example.menu_app.databinding.FragmentShowAccountBinding
import kotlinx.android.synthetic.main.fragment_home.*


/**
 * A simple [Fragment] subclass.
 */
class ShowAccountFragment : Fragment() {

    lateinit var dbHandler: DBHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentShowAccountBinding>(inflater, R.layout.fragment_show_account,container,false)


        return binding.root
    }



}
