package com.example.androidapp.messageList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.androidapp.R
import com.example.androidapp.domain.Message
import com.example.androidapp.viewMessage.ViewMessageFragment
import kotlinx.android.synthetic.main.view_message.view.*

class MessageListAdapter(
    private val fragment: Fragment
) : RecyclerView.Adapter<MessageListAdapter.ViewHolder>() {

    var items = emptyList<Message>()
        set(value) {
            field = value
            notifyDataSetChanged();
        }

    private var onItemClick: View.OnClickListener;

    init {
        onItemClick = View.OnClickListener { view ->
            val item = view.tag as Message
            fragment.findNavController().navigate(R.id.ViewMessageFragment, Bundle().apply {
                putInt(ViewMessageFragment.MESSAGE_ID, item.id)
                putString(ViewMessageFragment.MESSAGE_FROM_NAME, item.fromName)
                putString(ViewMessageFragment.MESSAGE_SUBJECT, item.subject)
                putString(ViewMessageFragment.MESSAGE_CONTENT, item.content)
                putBoolean(ViewMessageFragment.MESSAGE_READ, item.read)
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
        holder.textViewName.text = message.fromName
        holder.textViewSubject.text = message.subject
        if (message.read) {
            holder.readCircle.visibility = View.INVISIBLE
        } else {
            holder.readCircle.visibility = View.VISIBLE
        }
        holder.itemView.setOnClickListener(onItemClick)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.fromName
        val textViewSubject: TextView = view.subject
        val readCircle: FrameLayout = view.readCircle
    }
}