package com.noto.note.ui

import android.app.AlertDialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.noto.R
import com.noto.databinding.DialogNotebookBinding
import com.noto.databinding.FragmentNotebookListBinding
import com.noto.network.Repos
import com.noto.note.adapter.NavigateToNotebook
import com.noto.note.adapter.NotebookListRVAdapter
import com.noto.note.model.Notebook
import com.noto.note.model.NotebookColor
import com.noto.note.viewModel.NotebookListViewModel
import com.noto.note.viewModel.NotebookListViewModelFactory

/**
 * A simple [Fragment] subclass.
 */
class NotebookListFragment : Fragment(), NavigateToNotebook {

    // Binding
    private lateinit var binding: FragmentNotebookListBinding

    // Notebook List RV Adapter
    private lateinit var adapter: NotebookListRVAdapter

    private lateinit var exFabNewNotebook: ExtendedFloatingActionButton

    private lateinit var dialogBinding: DialogNotebookBinding

    private lateinit var dialog: AlertDialog

    private val viewModel by viewModels<NotebookListViewModel> {
        NotebookListViewModelFactory(Repos.notebookRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotebookListBinding.inflate(inflater, container, false)

        // Binding
        binding.let {

            it.lifecycleOwner = this

        }

        activity?.window?.statusBarColor = resources.getColor(R.color.bottom_nav_color, null)

        // Collapse Toolbar
        binding.ctb.let { ctb ->

            ctb.setCollapsedTitleTypeface(ResourcesCompat.getFont(context!!, R.font.roboto_bold))

            ctb.setExpandedTitleTypeface(ResourcesCompat.getFont(context!!, R.font.roboto_medium))

        }

        // RV
        binding.rv.let { rv ->

            // RV Adapter
            adapter = NotebookListRVAdapter(this)
            rv.adapter = adapter

            // RV Layout Manger
            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

            viewModel.notebooks.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                }
            })
        }

        exFabNewNotebook =
            activity?.findViewById(R.id.exFab_new_notebook) as ExtendedFloatingActionButton

        exFabNewNotebook.setOnClickListener {
            dialog()
        }


        return binding.root
    }

    override fun navigate(notebook: Notebook) {
        this.findNavController().navigate(
            NotebookListFragmentDirections.actionNotebookListFragmentToNotebookFragment(
                notebook.notebookId,
                notebook.notebookTitle,
                notebook.notebookColor
            )
        )
    }

    private fun dialog() {
        dialogBinding = DialogNotebookBinding.inflate(layoutInflater)

        dialog = AlertDialog.Builder(context).let {
            it.setView(dialogBinding.root)
            it.create()
            it.show()
        }.apply {
            this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.window?.setWindowAnimations(R.style.DialogAnimation)
        }

        dialogBinding.viewModel = viewModel

        viewModel.notebook.value = Notebook()

        dialogBinding.rbtnBlue.setOnClickListener {

            dialogBinding.root.background =
                resources.getDrawable(R.drawable.dialog_background_blue_drawable, null)

            dialogBinding.til.boxBackgroundColor =
                resources.getColor(R.color.blue_primary, null)

            viewModel.notebook.value?.notebookColor = NotebookColor.BLUE

            dialogBinding.createBtn.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.blue_primary_dark, null))
        }

        dialogBinding.rbtnPink.setOnClickListener {


            dialogBinding.root.background =
                resources.getDrawable(R.drawable.dialog_background_pink_drawable, null)

            dialogBinding.til.boxBackgroundColor =
                resources.getColor(R.color.pink_primary, null)

            viewModel.notebook.value?.notebookColor = NotebookColor.PINK

            dialogBinding.createBtn.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.pink_primary_dark, null))
        }

        dialogBinding.rbtnCyan.setOnClickListener {

            dialogBinding.root.background =
                resources.getDrawable(R.drawable.dialog_background_cyan_drawable, null)

            dialogBinding.til.boxBackgroundColor =
                resources.getColor(R.color.cyan_primary, null)

            viewModel.notebook.value?.notebookColor = NotebookColor.CYAN

            dialogBinding.createBtn.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.cyan_primary_dark, null))
        }

        dialogBinding.rbtnGray.setOnClickListener {

            dialogBinding.til.boxBackgroundColor =
                resources.getColor(R.color.gray_primary, null)

            dialogBinding.root.background =
                resources.getDrawable(R.drawable.dialog_background_gray_drawable, null)


            viewModel.notebook.value?.notebookColor = NotebookColor.GRAY

            dialogBinding.createBtn.backgroundTintList =
                ColorStateList.valueOf(resources.getColor(R.color.gray_primary_dark, null))
        }

        dialogBinding.createBtn.setOnClickListener {

            if (viewModel.notebook.value?.notebookTitle?.isBlank()!!) {
                dialogBinding.til.error = resources.getString(R.string.new_notebook_empty_error)

                dialogBinding.til.counterTextColor = ColorStateList.valueOf(Color.RED)

            } else {
                viewModel.saveNotebook()
                dialog.dismiss()
            }
        }

        dialogBinding.cancelBtn.setOnClickListener {
            dialog.dismiss()
        }
    }
}
