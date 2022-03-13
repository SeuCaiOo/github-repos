package br.com.seucaio.githubreposkotlin.presentation.repo

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import br.com.seucaio.githubreposkotlin.R
import br.com.seucaio.githubreposkotlin.databinding.FragmentReposBinding
import br.com.seucaio.githubreposkotlin.domain.entity.Repo
import br.com.seucaio.githubreposkotlin.presentation.repo.adapter.list.RepoListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoListFragment : Fragment() {

    @Parcelize
    data class Args(val repoId: Int) : Parcelable

    private var param1: String? = null
    private var param2: String? = null
    private var paramArgs: Args? = null

    private var _binding: FragmentReposBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<RepoListViewModel>()

    private val adapter by lazy { RepoListAdapter(clickRepository()) }

    private fun clickRepository(): (Repo) -> Unit = {
        Snackbar.make(binding.root, "#${it.id}", Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            paramArgs = it.getParcelable(ARG_REPOSITORY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentReposBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val ARG_PARAM1 = "param1"
        const val ARG_PARAM2 = "param2"
        const val ARG_REPOSITORY = "arg_repository"

        /*fun newInstance(repository: Args): ReposFragment = ReposFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_REPOSITORY, repository)
            }
        }*/

        @JvmStatic
        fun newInstance(repositoryId: Int) = RepoListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_REPOSITORY, Args(repositoryId))
            }
        }

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RepoListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}