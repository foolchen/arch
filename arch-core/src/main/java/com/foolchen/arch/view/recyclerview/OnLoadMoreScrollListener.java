package com.foolchen.arch.view.recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by aspsine on 16/3/13.
 */
public abstract class OnLoadMoreScrollListener extends RecyclerView.OnScrollListener {

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
  }

  @Override public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

    if (recyclerView instanceof IRecyclerView
        && ((IRecyclerView) recyclerView).getLoadMoreFooterView() instanceof ILoadMoreFooterView
        && !((ILoadMoreFooterView) ((IRecyclerView) recyclerView).getLoadMoreFooterView()).canLoadMore()) {
      return;
    }

    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    int visibleItemCount = layoutManager.getChildCount();

    boolean triggerCondition =
        visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE && canTriggerLoadMore(
            recyclerView);

    if (triggerCondition) {
      onLoadMore(recyclerView);
    }
  }

  public boolean canTriggerLoadMore(RecyclerView recyclerView) {
    View lastChild = recyclerView.getChildAt(recyclerView.getChildCount() - 1);
    int position = recyclerView.getChildLayoutPosition(lastChild);
    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
    int totalItemCount = layoutManager.getItemCount();
    return totalItemCount - 1 == position;
  }

  public abstract void onLoadMore(RecyclerView recyclerView);
}
