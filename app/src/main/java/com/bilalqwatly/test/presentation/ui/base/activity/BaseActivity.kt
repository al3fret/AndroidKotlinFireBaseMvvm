package com.bilalqwatly.test.presentation.ui.base.activity

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bilalqwatly.test.R
import com.bilalqwatly.test.presentation.ui.base.IBaseView
import com.bilalqwatly.test.presentation.ui.base.adapter.BaseListAdapter


abstract class BaseActivity : AppCompatActivity(), IBaseView {

    var adapter: BaseListAdapter<*, *>? = null
    protected lateinit var context: Context
    protected abstract val layoutId: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this

        setView()
    }


    override fun onBackPressed() {
        super.onBackPressed()
    }

    protected open fun setView() {
        setContentView(layoutId)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.statusBarColor =
                ContextCompat.getColor(this, R.color.color_070014)
        }
    }


    fun setLayoutManger(recyclerView: RecyclerView) {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
    }

    // base methods
    override fun showMessage(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(stringId: Int) {
        showMessage(getString(stringId))
    }

    override fun hideKeyboard() {
        var currentFocus = currentFocus
        if (currentFocus == null) currentFocus = View(this)
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
    }

    fun showAlertDialog(
        title: String?,
        message: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, onClickListener)
            .setNegativeButton(android.R.string.cancel) { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            .create().show()
    }
}