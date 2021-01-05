package com.example.androidapp.messageList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp.R
import com.example.androidapp.domain.Event
import com.example.androidapp.viewMessage.ViewMessageFragment
import kotlinx.android.synthetic.main.view_message.view.*

class MessageListAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {

    var items = emptyList<Event>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }

    private var onItemClick: View.OnClickListener;

    init {
        onItemClick = View.OnClickListener { view ->
            val item = view.tag as Event
            fragment.findNavController().navigate(R.id.ViewMessageFragment, Bundle().apply {
                putInt(ViewMessageFragment.MESSAGE_ID, item.id)
                putString(ViewMessageFragment.MESSAGE_TITLE, item.title)
                putString(ViewMessageFragment.MESSAGE_DESCRIPTION, item.description)
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_message, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val message = items[position]
        holder.itemView.tag = message
        holder.textViewTitle.text = message.title
        holder.itemView.setOnClickListener(onItemClick)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTitle: TextView = view.title
    }
}