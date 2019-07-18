package co.id.bannerkederslide;

import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

class SsSnapHelper extends PagerSnapHelper {
    private OnSelectedItemChange onSelectedItemChange;
    private int lastPosition;

    public SsSnapHelper(OnSelectedItemChange onSelectedItemChange) {
        this.onSelectedItemChange = onSelectedItemChange;
    }

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        int position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY);
        if (position != RecyclerView.NO_POSITION && lastPosition != position && position < layoutManager.getItemCount()) {
            onSelectedItemChange.onSelectedItemChange(position);
            lastPosition = position;
        }
        return position;
    }

    public interface OnSelectedItemChange {
        void onSelectedItemChange(int position);
    }
}
