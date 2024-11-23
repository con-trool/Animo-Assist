package com.mobdeve.s13.grp50.mc02_mobdeve

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        // Add the space as a margin below each item
        outRect.bottom = spaceHeight

        // Optionally, you can add margin for the first item as well, for uniformity
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = spaceHeight
        }
    }
}
