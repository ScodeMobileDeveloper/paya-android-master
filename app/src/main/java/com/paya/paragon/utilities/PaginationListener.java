package com.paya.paragon.utilities;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class PaginationListener extends RecyclerView.OnScrollListener {

  public static final int PAGE_START = 1;

  @NonNull
  private LinearLayoutManager layoutManager;

  /**
   * Set scrolling threshold here (for now i'm assuming 20 item in one page)
   */
  private static final int PAGE_SIZE = 20;

  /**
   * Supporting only LinearLayoutManager for now.
   */
  public PaginationListener(@NonNull LinearLayoutManager layoutManager) {
    this.layoutManager = layoutManager;
  }

  @Override
  public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    int visibleItemCount = layoutManager.getChildCount();
    int totalItemCount = layoutManager.getItemCount();
    int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

      if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
        loadMoreItems();
      }
    }

  protected abstract void loadMoreItems();


}