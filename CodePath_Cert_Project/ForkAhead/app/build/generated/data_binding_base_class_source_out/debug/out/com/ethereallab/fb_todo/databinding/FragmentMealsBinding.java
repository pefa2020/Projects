// Generated by view binder compiler. Do not edit!
package com.ethereallab.fb_todo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ethereallab.fb_todo.R;
import com.google.android.material.button.MaterialButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMealsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final MaterialButton allIngredientsButton;

  @NonNull
  public final TextView emptyMessage;

  @NonNull
  public final RecyclerView plansRecyclerView;

  private FragmentMealsBinding(@NonNull ConstraintLayout rootView,
      @NonNull MaterialButton allIngredientsButton, @NonNull TextView emptyMessage,
      @NonNull RecyclerView plansRecyclerView) {
    this.rootView = rootView;
    this.allIngredientsButton = allIngredientsButton;
    this.emptyMessage = emptyMessage;
    this.plansRecyclerView = plansRecyclerView;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMealsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMealsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_meals, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMealsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.allIngredientsButton;
      MaterialButton allIngredientsButton = ViewBindings.findChildViewById(rootView, id);
      if (allIngredientsButton == null) {
        break missingId;
      }

      id = R.id.emptyMessage;
      TextView emptyMessage = ViewBindings.findChildViewById(rootView, id);
      if (emptyMessage == null) {
        break missingId;
      }

      id = R.id.plansRecyclerView;
      RecyclerView plansRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (plansRecyclerView == null) {
        break missingId;
      }

      return new FragmentMealsBinding((ConstraintLayout) rootView, allIngredientsButton,
          emptyMessage, plansRecyclerView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
