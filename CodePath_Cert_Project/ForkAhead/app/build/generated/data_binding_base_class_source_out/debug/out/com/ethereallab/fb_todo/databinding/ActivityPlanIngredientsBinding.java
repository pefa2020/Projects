// Generated by view binder compiler. Do not edit!
package com.ethereallab.fb_todo.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ethereallab.fb_todo.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityPlanIngredientsBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final TextView allIngredients;

  @NonNull
  public final Button btnSendEmail;

  @NonNull
  public final EditText etRecipientEmail;

  @NonNull
  public final EditText etSubject;

  @NonNull
  public final ScrollView ingredientsScrollView;

  @NonNull
  public final TextView recipeTitle;

  private ActivityPlanIngredientsBinding(@NonNull ConstraintLayout rootView,
      @NonNull TextView allIngredients, @NonNull Button btnSendEmail,
      @NonNull EditText etRecipientEmail, @NonNull EditText etSubject,
      @NonNull ScrollView ingredientsScrollView, @NonNull TextView recipeTitle) {
    this.rootView = rootView;
    this.allIngredients = allIngredients;
    this.btnSendEmail = btnSendEmail;
    this.etRecipientEmail = etRecipientEmail;
    this.etSubject = etSubject;
    this.ingredientsScrollView = ingredientsScrollView;
    this.recipeTitle = recipeTitle;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityPlanIngredientsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityPlanIngredientsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_plan_ingredients, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityPlanIngredientsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.allIngredients;
      TextView allIngredients = ViewBindings.findChildViewById(rootView, id);
      if (allIngredients == null) {
        break missingId;
      }

      id = R.id.btnSendEmail;
      Button btnSendEmail = ViewBindings.findChildViewById(rootView, id);
      if (btnSendEmail == null) {
        break missingId;
      }

      id = R.id.etRecipientEmail;
      EditText etRecipientEmail = ViewBindings.findChildViewById(rootView, id);
      if (etRecipientEmail == null) {
        break missingId;
      }

      id = R.id.etSubject;
      EditText etSubject = ViewBindings.findChildViewById(rootView, id);
      if (etSubject == null) {
        break missingId;
      }

      id = R.id.ingredientsScrollView;
      ScrollView ingredientsScrollView = ViewBindings.findChildViewById(rootView, id);
      if (ingredientsScrollView == null) {
        break missingId;
      }

      id = R.id.recipeTitle;
      TextView recipeTitle = ViewBindings.findChildViewById(rootView, id);
      if (recipeTitle == null) {
        break missingId;
      }

      return new ActivityPlanIngredientsBinding((ConstraintLayout) rootView, allIngredients,
          btnSendEmail, etRecipientEmail, etSubject, ingredientsScrollView, recipeTitle);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
