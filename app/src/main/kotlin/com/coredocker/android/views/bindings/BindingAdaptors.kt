package com.coredocker.android.views.bindings

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.coredocker.android.views.components.user.IPassToBing


@BindingAdapter("onItemSwipeLeft","onItemSwipeRight",requireAll = false)
fun RecyclerView.setItemSwipeToRecyclerView(onItemSwipeLeft:OnItemSwipeListener?,onItemSwipeRight:OnItemSwipeListener?) {
    var allow : Int? = null
    val recyclerView = this
    if (onItemSwipeLeft != null) {
        allow = ItemTouchHelper.LEFT
    }
    if (onItemSwipeRight != null) {
        allow = if (allow == null)
            ItemTouchHelper.RIGHT
        else {
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        }
    }
    val swipeCallback: ItemTouchHelper.Callback = object : ItemTouchHelper.SimpleCallback(0, allow?:0) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (direction == ItemTouchHelper.LEFT) {
                if (viewHolder is IPassToBing) {
                    onItemSwipeLeft?.onItemSwiped(viewHolder.getBoundValue())
                }

            } else if (direction == ItemTouchHelper.RIGHT) {
                if (viewHolder is IPassToBing) {
                    onItemSwipeRight?.onItemSwiped(viewHolder.getBoundValue())
                }
            }
        }

    }

    val itemTouchHelper = ItemTouchHelper(swipeCallback)

    itemTouchHelper.attachToRecyclerView(recyclerView)
}

interface OnItemSwipeListener {
    fun onItemSwiped(position: Any)
}
