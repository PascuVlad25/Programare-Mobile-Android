package com.example.androidapp.viewMessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.example.androidapp.R
import com.example.androidapp.domain.Message
import kotlinx.android.synthetic.main.fragment_view_message.*

class ViewMessageFragment: Fragment() {
    companion object {
        const val MESSAGE_ID = "MESSAGE_ID"
        const val MESSAGE_FROM_NAME = "MESSAGE_FROM_NAME"
        const val MESSAGE_SUBJECT = "MESSAGE_SUBJECT"
        const val MESSAGE_CONTENT = "MESSAGE_CONTENT"
        const val MESSAGE_READ = "MESSAGE_READ"
    }

    private lateinit var viewModel : ViewMessageViewModel
    private var messageId: Int? = null
    private var messageFromName: String? = null
    private var messageSubject: String? = null
    private var messageContent: String? = null
    private var messageRead: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(MESSAGE_ID)) {
                messageId = it.getInt(MESSAGE_ID)
                messageFromName = it.getString(MESSAGE_FROM_NAME)
                messageSubject = it.getString(MESSAGE_SUBJECT)
                messageContent = it.getString(MESSAGE_CONTENT)
                messageRead = it.getBoolean(MESSAGE_READ)
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_view_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fromName.text = "From: $messageFromName"
        subject.text = messageSubject
        content.text = messageContent
//        if(!messageRead!!) {
//
//            messageId?.let { messageFromName?.let { it1 ->
//                messageSubject?.let { it2 ->
//                    messageContent?.let { it3 ->
//                        Message(it,
//                            it1, it2, it3, true)
//                    }
//                }
//            } }?.let {
//                viewModel.updateItem(
//                    it
//                )
//            }
//        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViewModel()
        if(!messageRead!!) {
            viewModel.updateItem(Message(messageId!!, messageFromName!!, messageSubject!!, messageContent!!, true))
        }


    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(ViewMessageViewModel::class.java)
        viewModel.item.observe(viewLifecycleOwner) { item ->
            fromName.text = item.fromName
        }
        viewModel.fetchingError.observe(viewLifecycleOwner) { exception ->
            if (exception != null) {
                val message = "Fetching exception ${exception.message}"
                val parentActivity = activity?.parent
                if (parentActivity != null) {
                    Toast.makeText(parentActivity, message, Toast.LENGTH_SHORT).show()
                }
            }
        }
        viewModel.completed.observe(viewLifecycleOwner, Observer { completed ->
            if (completed) {
                findNavController().navigateUp()
            }
        })
        val id = messageId
        if (id != null) {
            viewModel.loadItem(id)
        }
    }
}