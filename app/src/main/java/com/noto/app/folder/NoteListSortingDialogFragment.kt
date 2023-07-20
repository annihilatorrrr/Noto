package com.noto.app.folder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.navigation.fragment.navArgs
import com.noto.app.R
import com.noto.app.components.BaseDialogFragment
import com.noto.app.components.BottomSheetDialog
import com.noto.app.components.SelectableDialogItem
import com.noto.app.domain.model.NoteListSortingType
import com.noto.app.toColor
import com.noto.app.util.toResource
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class NoteListSortingDialogFragment : BaseDialogFragment() {

    private val viewModel by viewModel<FolderViewModel> { parametersOf(args.folderId) }

    private val args by navArgs<NoteListSortingDialogFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? = context?.let { context ->
        ComposeView(context).apply {
            setContent {
                val folder by viewModel.folder.collectAsState()
                val types = remember { NoteListSortingType.values().toList() }

                BottomSheetDialog(title = stringResource(R.string.sorting), headerColor = folder.color.toColor()) {
                    types.forEach { type ->
                        SelectableDialogItem(
                            selected = folder.sortingType == type,
                            onClick = { viewModel.updateSortingType(type).invokeOnCompletion { dismiss() } },
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(text = stringResource(id = type.toResource()))
                        }
                    }
                }
            }
        }
    }
}