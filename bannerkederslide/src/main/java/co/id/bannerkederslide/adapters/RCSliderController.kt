package co.id.bannerkederslide.adapters

import android.util.Log

class RCSliderController constructor(slider: RCSliderAdapter, loop: Boolean) {
    private val TAG = "PositionController"
    private var recyclerViewAdapter: RCSliderInterface? = null
    private val sliderAdapter: RCSliderAdapter = slider
    private var loop: Boolean = loop


    fun getUserSlidePosition(position: Int): Int {
        return if (loop) {
            if (position == 0) {
                recyclerViewAdapter!!.itemCount - 3
            } else if (position == recyclerViewAdapter!!.itemCount - 1) {
                0
            } else {
                position - 1
            }
        } else {
            position
        }
    }

    fun getRealSlidePosition(position: Int): Int {
        if (!loop) {
            return position
        } else {
            if (position >= 0 && position < sliderAdapter.getItemCount()) {
                return position + 1
            } else {
                Log.e(TAG, "setSelectedSlide: Invalid Item Position")
                return 1
            }
        }
    }

    fun getLastUserSlidePosition(): Int {
        return sliderAdapter.getItemCount() - 1
    }

    fun getFirstUserSlidePosition(): Int {
        return 0
    }

    fun setRecyclerViewAdapter(recyclerViewAdapter: RCSliderInterface) {
        this.recyclerViewAdapter = recyclerViewAdapter
    }

    fun getNextSlide(currentPosition: Int): Int {
        return if (currentPosition < recyclerViewAdapter!!.itemCount - 1) {
            currentPosition + 1
        } else {
            if (loop) 1 else 0
        }
    }

    fun setLoop(loop: Boolean) {
        this.loop = loop
    }
}