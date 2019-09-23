package com.godwinvc.utils;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Implementation of {@link RecyclerView.ItemAnimator} that disables all default animations.
 */
/*package*/ class NotAnimatedItemAnimator extends RecyclerView.ItemAnimator {

  @Override
  public boolean animateDisappearance(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @Nullable ItemHolderInfo postLayoutInfo) {
    dispatchAnimationStarted(viewHolder);
    dispatchAnimationFinished(viewHolder);
    return true;
  }

  @Override
  public boolean animateAppearance(@NonNull RecyclerView.ViewHolder viewHolder, @Nullable ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
    dispatchAnimationStarted(viewHolder);
    dispatchAnimationFinished(viewHolder);
    return true;
  }

  @Override
  public boolean animatePersistence(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
    dispatchAnimationStarted(viewHolder);
    dispatchAnimationFinished(viewHolder);
    return true;
  }

  @Override
  public boolean animateChange(@NonNull RecyclerView.ViewHolder oldHolder, @NonNull RecyclerView.ViewHolder newHolder, @NonNull ItemHolderInfo preLayoutInfo, @NonNull ItemHolderInfo postLayoutInfo) {
    if(newHolder != null){
      dispatchAnimationStarted(newHolder);
      dispatchAnimationFinished(newHolder);
    }else{
      dispatchAnimationStarted(oldHolder);
      dispatchAnimationFinished(oldHolder);
    }
    return true;
  }

  @Override
  public void runPendingAnimations() {
    // nothing
  }

  @Override
  public void endAnimation(RecyclerView.ViewHolder item) {
  }

  @Override
  public void endAnimations() {
  }

  @Override
  public boolean isRunning() {
    return false;
  }
}
