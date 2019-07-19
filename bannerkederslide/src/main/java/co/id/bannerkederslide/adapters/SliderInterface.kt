package co.id.bannerkederslide.adapters

import co.id.bannerkederslide.SlideType
import co.id.bannerkederslide.viewholder.ImageSlideViewHolder

abstract class SliderInterface {
    abstract fun getItemCount(): Int

    fun getSlideType(position: Int): SlideType {
        return SlideType.IMAGE
    }

    abstract fun onBindImageSlide(position: Int, imageSlideViewHolder: ImageSlideViewHolder)
}