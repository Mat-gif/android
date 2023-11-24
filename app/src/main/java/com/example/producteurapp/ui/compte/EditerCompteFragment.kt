package com.example.producteurapp.ui.compte


import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.producteurapp.R


class EditerCompteFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout to use as a dialog or embedded fragment.
        return inflater.inflate(R.layout.fragment_editer_compte, container, false)
    }


}