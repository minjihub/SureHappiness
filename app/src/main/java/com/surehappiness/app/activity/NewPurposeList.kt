package com.surehappiness.app.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surehappiness.app.R
import com.surehappiness.app.activity.purposelist.Contract
import com.surehappiness.app.activity.purposelist.Presenter
import com.surehappiness.app.activity.purposelist.adapter.NewPurposeListAdapter
import com.surehappiness.app.databinding.NewLayoutPurposeListBinding
import com.surehappiness.app.utils.CommonFunction
import kotlinx.android.synthetic.main.toolbar.view.*


class NewPurposeList: Fragment(), Contract.View {

    private lateinit var binding: NewLayoutPurposeListBinding
    private lateinit var presenter: Presenter
    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.new_layout_purpose_list, container, false)

        val toolbar = binding.root.toolbar
        val appCompatActivity = activity as AppCompatActivity
        appCompatActivity.setSupportActionBar(toolbar)
        appCompatActivity.supportActionBar!!.setDisplayShowTitleEnabled(false)

        presenter = Presenter(this)
        presenter.getUserInfoPreference()

        binding.recyclerVeiw.layoutManager = GridLayoutManager(context, 2) as RecyclerView.LayoutManager

        binding.floatingBtn.setOnClickListener {
            val intent = Intent(activity, CreatePurpose::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()

        searchView?.let {
            it.setQuery("", false)
            it.clearFocus()

            if(!it.isIconified)
                it.isIconified = true
        }

        presenter.makePurposeList()
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_toolbar, menu)

        val searchItem = menu.findItem(R.id.search)
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        searchItem?.let {
            searchView = it.actionView as SearchView
            searchView?.setOnCloseListener {
                presenter.makePurposeList()
                false
            }
        }

        searchView?.let {
            it.queryHint = "목표명 검색"
            it.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))

            val queryTextListener = object: SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {

                    presenter.makeSearchList(query)

                    val infutMethodManager = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    infutMethodManager.hideSoftInputFromWindow(it.windowToken,0)

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            }
            it.setOnQueryTextListener(queryTextListener)
        }
    }

    override fun setUserInfo(name: String) {
        binding.userName.text = "$name 님,"
    }

    override fun setAdapter(adapter: NewPurposeListAdapter) {
        binding.recyclerVeiw.adapter = adapter
    }

    override fun showDialog(message: Int) {
        CommonFunction.showBasicDialog(activity!!, message)
    }

    override fun getContext(): Context? {
        return activity
    }


}