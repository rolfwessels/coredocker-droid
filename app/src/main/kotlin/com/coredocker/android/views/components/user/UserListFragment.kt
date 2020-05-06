package com.coredocker.android.views.components.user

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coredocker.android.R
import com.coredocker.android.data.network.graphql.PagedFragment
import com.coredocker.android.databinding.UserListFragmentBinding
import com.coredocker.android.databinding.UserListItemPartialBinding
import com.coredocker.android.services.Navigate
import com.coredocker.android.util.extensions.hideIt
import com.coredocker.android.util.extensions.hideKeyboard
import com.coredocker.fragment.UserFragment
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class UserFragment : Fragment() {

    private val viewModel: UserListFragmentViewModel by inject()
    private val navigate: Navigate by inject()
    private lateinit var listOfUsers: RecyclerView
    private lateinit var listOfUsersLayout: RecyclerView.LayoutManager
    private lateinit var listOfUsersAdapter: RecyclerView.Adapter<*>
    private lateinit var shimmerLoader: ShimmerFrameLayout
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<UserListFragmentBinding>(
            inflater,
            R.layout.user_list_fragment,
            container,
            false
        )
        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.userViewModel = viewModel
        val root = binding.root
        shimmerLoader = root.findViewById(R.id.sfl_list_of_users)

        listOfUsersLayout = LinearLayoutManager(activity)
        val userListItemPartialAdapter = UserListItemPartialAdapter(null, activity!!, viewModel)
        listOfUsersAdapter = userListItemPartialAdapter

        lifecycleScope.launch {
            viewModel.allUsers.collect {
                Timber.i("setData ${it.items.size}")
                userListItemPartialAdapter.setData(it)
                shimmerLoader.stopShimmer()
                shimmerLoader.hideIt()
            }
        }

        listOfUsers = root.findViewById<RecyclerView>(R.id.rv_list_of_users).apply {
            setHasFixedSize(true)
            layoutManager = listOfUsersLayout
            adapter = listOfUsersAdapter
            itemAnimator = DefaultItemAnimator()
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        navigate.currentView.observe(this, Observer { n ->
            when (n.location) {
                Navigate.Location.UserCreateUpdateFragment -> {
                    navController.navigate(
                        R.id.action_userFragment_to_userCreateUpdateFragment,
                        n.bundle
                    )
                    view.context.hideKeyboard()
                }
                else -> throw ArrayIndexOutOfBoundsException("Unknown nav location ${n.location}")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        shimmerLoader.startShimmer()
    }

    override fun onPause() {
        shimmerLoader.stopShimmer()
        super.onPause()
    }
}

class UserListItemPartialAdapter(
    private var data: PagedFragment<UserFragment>? = null,
    private var context: Context,
    private val viewModel: UserListFragmentViewModel
) :
    RecyclerView.Adapter<UserListItemPartialAdapter.Holder>() {

    inner class Holder(
        private var binding: UserListItemPartialBinding
    ) : RecyclerView.ViewHolder(binding.root), IPassToBing {

        fun bind(user: UserFragment) {
            binding.user = user
            binding.viewModel = viewModel
            binding.executePendingBindings()
        }

        override fun getBoundValue(): Any {
            return this.binding.user as Any
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = UserListItemPartialBinding.inflate(layoutInflater, parent, false)
        return Holder(itemBinding)
    }

    fun setData(newData: PagedFragment<UserFragment>) {
        this.data = newData
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val stored = data
        if (stored != null) {
            holder.bind(stored.items[position])
        }
    }

    override fun getItemCount() = data?.items?.size ?: 0
}

interface IPassToBing {
    fun getBoundValue(): Any
}
