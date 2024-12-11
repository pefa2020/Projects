package com.ethereallab.fb_todo.fragments;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001d2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0017J\b\u0010 \u001a\u00020!H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\b\u001a\u00020\t8BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u000e\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00120\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00120\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\""}, d2 = {"Lcom/ethereallab/fb_todo/fragments/MealsFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/ethereallab/fb_todo/databinding/FragmentMealsBinding;", "all_ingredients", "", "", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "getAuth", "()Lcom/google/firebase/auth/FirebaseAuth;", "auth$delegate", "Lkotlin/Lazy;", "binding", "getBinding", "()Lcom/ethereallab/fb_todo/databinding/FragmentMealsBinding;", "cart", "Lcom/ethereallab/fb_todo/models/Recipe;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "planRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "plan_recipes", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "app_debug"})
public final class MealsFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.ethereallab.fb_todo.databinding.FragmentMealsBinding _binding;
    private androidx.recyclerview.widget.RecyclerView planRecyclerView;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.database.FirebaseDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.ethereallab.fb_todo.models.Recipe> cart = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.ethereallab.fb_todo.models.Recipe> plan_recipes = null;
    @org.jetbrains.annotations.NotNull
    private java.util.List<java.lang.String> all_ingredients;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy auth$delegate = null;
    
    public MealsFragment() {
        super();
    }
    
    private final com.ethereallab.fb_todo.databinding.FragmentMealsBinding getBinding() {
        return null;
    }
    
    private final com.google.firebase.auth.FirebaseAuth getAuth() {
        return null;
    }
    
    @java.lang.Override
    @android.annotation.SuppressLint(value = {"SuspiciousIndentation"})
    @org.jetbrains.annotations.NotNull
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}