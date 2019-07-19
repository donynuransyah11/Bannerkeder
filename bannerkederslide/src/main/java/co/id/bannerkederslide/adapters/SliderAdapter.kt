package co.id.bannerkederslide.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ZoomButtonsController
import androidx.recyclerview.widget.RecyclerView
import co.id.bannerkederslide.SlideType
import co.id.bannerkederslide.event.OnSlideClickListener
import co.id.bannerkederslide.event.OnSlideZoomListener
import co.id.bannerkederslide.viewholder.ImageSlideViewHolder

class SliderAdapter constructor(
    iSliderAdapter: SliderInterface,
    private var loop: Boolean,
    private var zoom: Boolean,
    private val imageViewLayoutParams: ViewGroup.LayoutParams,
    private val itemOnTouchListener: View.OnTouchListener,
    private val positionController: Controller
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var onSlideClickListener: OnSlideClickListener? = null
    private var onZoomListener: OnSlideZoomListener? = null
    private val sliderAdapter: SliderInterface = iSliderAdapter


    fun setOnSlideClickListener(onSlideClickListener: OnSlideClickListener) {
        this.onSlideClickListener = onSlideClickListener
    }

    fun setOnSlideZoomListener(onZoomListener: OnSlideZoomListener) {
        this.onZoomListener = onZoomListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == SlideType.IMAGE.value) {
            val imageView = ImageView(parent.context)
            imageView.layoutParams = imageViewLayoutParams
            imageView.adjustViewBounds = true
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            return ImageSlideViewHolder(imageView)
        }
        return super.createViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (!loop) {
            sliderAdapter.onBindImageSlide(position, holder as ImageSlideViewHolder)
        } else {
            if (position == 0) {
                sliderAdapter.onBindImageSlide(
                    positionController.getLastUserSlidePosition(),
                    holder as ImageSlideViewHolder
                )
            } else if (position == itemCount - 1) {
                sliderAdapter.onBindImageSlide(
                    positionController.getFirstUserSlidePosition(),
                    holder as ImageSlideViewHolder
                )
            } else {
                sliderAdapter.onBindImageSlide(position - 1, holder as ImageSlideViewHolder)
            }
        }

        holder.itemView.setOnClickListener {
            if (zoom) {
                onZoomListener?.let {
                    it.zoom(holder.adapterPosition)
                }
            } else {
                onSlideClickListener?.let {
                    it.onSlideClick(
                        positionController.getUserSlidePosition(
                            holder.adapterPosition
                        )
                    )
                }
            }
        }

        holder.itemView.setOnTouchListener(itemOnTouchListener)
    }


    override fun getItemCount(): Int {
        return sliderAdapter.getItemCount() + if (loop) 2 else 0
    }

    fun setLoop(loop: Boolean) {
        this.loop = loop
    }
}