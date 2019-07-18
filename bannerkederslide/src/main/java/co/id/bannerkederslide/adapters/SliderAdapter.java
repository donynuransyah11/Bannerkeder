package co.id.bannerkederslide.adapters;

import co.id.bannerkederslide.SlideType;
import co.id.bannerkederslide.viewholder.ImageSlideViewHolder;


public abstract class SliderAdapter {
    public abstract int getItemCount();

    public final SlideType getSlideType(int position) {
        return SlideType.IMAGE;
    }

    public abstract void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder);
}
