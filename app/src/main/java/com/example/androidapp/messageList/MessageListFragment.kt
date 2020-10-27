package com.example.androidapp.messageList

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.androidapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MessageListFragment : Fragment() {
    private lateinit var itemListAdapter: MessageListAdapter
    private lateinit var itemsModel: MessageListViewModel
    private var m_Text = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupItemList()
    }

    private fun setupItemList() {
        itemListAdapter = MessageListAdapter(this)
        message_list.adapter = itemListAdapter
        itemsModel = ViewModelProvider(this).get(MessageListViewModel::class.java)
        itemsModel.items.observe(viewLifecycleOwner) { items ->
            itemListAdapter.items = items.reversed()
        }
        itemsModel.loadItems()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}