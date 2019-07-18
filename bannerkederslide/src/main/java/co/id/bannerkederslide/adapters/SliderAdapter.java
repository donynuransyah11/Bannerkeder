package co.id.bannerkederslide.adapters;

import co.id.bannerkederslide.SlideType;
import co.id.bannerkederslide.viewholder.ImageSlideViewHolder;

/**
 * @author S.Shahini
 * @since 3/4/18
 */

public abstract class SliderAdapter {
    public abstract int getItemCount();

    public final SlideType getSlideType(int position) {
        return SlideType.IMAGE;
    }

    public abstract void onBindImageSlide(int position, ImageSlideViewHolder imageSlideViewHolder);
}
