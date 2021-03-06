/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.addedittask

import android.databinding.Observable
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.databinding.AddtaskFragBinding
import com.example.android.architecture.blueprints.todoapp.util.*

import com.google.common.base.Preconditions.checkNotNull

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
class AddEditTaskFragment : Fragment() {

    private var mViewModel: AddEditTaskViewModel? = null

    private lateinit var mViewDataBinding: AddtaskFragBinding

    private var mSnackbarCallback: Observable.OnPropertyChangedCallback? = null

    override fun onResume() {
        super.onResume()
        if (arguments != null) {
            mViewModel?.start(arguments.getString(ARGUMENT_EDIT_TASK_ID))
        } else {
            mViewModel?.start(null)
        }
    }

    fun setViewModel(viewModel: AddEditTaskViewModel) {
        mViewModel = viewModel
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupFab()

        setupSnackbar()

        setupActionBar()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val root = container?.inflate(R.layout.addtask_frag)

        mViewDataBinding = AddtaskFragBinding.bind(root)

        mViewDataBinding.viewmodel = mViewModel

        setHasOptionsMenu(true)
        retainInstance = false

        return mViewDataBinding.root
    }

    override fun onDestroy() {
        if (mSnackbarCallback != null) {
            mViewModel?.snackbarText?.removeOnPropertyChangedCallback(mSnackbarCallback)
        }
        super.onDestroy()
    }

    private fun setupSnackbar() {
        mSnackbarCallback = object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, i: Int) {
                view?.snack(mViewModel?.getSnackbarTextString()!!)
            }
        }
        mViewModel?.snackbarText?.addOnPropertyChangedCallback(mSnackbarCallback)
    }

    private fun setupFab() {
        val fab = activity.findViewById<View>(R.id.fab_edit_task_done) as FloatingActionButton
        fab.setImageResource(R.drawable.ic_done)
        fab.setOnClickListener { mViewModel?.saveTask() }
    }

    private fun setupActionBar() {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        if (arguments != null) {
            actionBar?.setTitle(R.string.edit_task)
        } else {
            actionBar?.setTitle(R.string.add_task)
        }
    }

    companion object {

        val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"

        fun newInstance(): AddEditTaskFragment {
            return AddEditTaskFragment()
        }
    }
}// Required empty public constructor
