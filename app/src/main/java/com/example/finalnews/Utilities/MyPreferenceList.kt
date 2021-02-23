package com.example.finalnews.Utilities

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.preference.ListPreference
import android.util.AttributeSet

class MyPreferenceList(context: Context, attr: AttributeSet) : ListPreference(context, attr),DialogInterface.OnClickListener{

    var mClickedDialogEntryIndex:Int =0

    private fun getValueIndex():Int {
        return findIndexOfValue(this.getValue() + "")
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        this.value = this.entryValues[mClickedDialogEntryIndex].toString() + ""
    }

    override fun onPrepareDialogBuilder(builder: AlertDialog.Builder?) {
        super.onPrepareDialogBuilder(builder)
        mClickedDialogEntryIndex = getValueIndex()
        builder?.setSingleChoiceItems(
            this.entries, mClickedDialogEntryIndex
        ) { dialog, which -> mClickedDialogEntryIndex = which }
        builder?.setPositiveButton("OK", this)

    }
}