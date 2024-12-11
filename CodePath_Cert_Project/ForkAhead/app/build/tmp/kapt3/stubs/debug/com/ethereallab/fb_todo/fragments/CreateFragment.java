package com.ethereallab.fb_todo.fragments;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u001a\u0010\u001d\u001a\u000e\u0012\u0004\u0012\u00020\u001f\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010 \u001a\u00020\u001fJ$\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\b\u0010%\u001a\u0004\u0018\u00010&2\b\u0010\'\u001a\u0004\u0018\u00010(H\u0017J\b\u0010)\u001a\u00020*H\u0016J\u001a\u0010+\u001a\u00020*2\u0006\u0010,\u001a\u00020\"2\b\u0010\'\u001a\u0004\u0018\u00010(H\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001b\u0010\u0005\u001a\u00020\u00068BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00048BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/ethereallab/fb_todo/fragments/CreateFragment;", "Landroidx/fragment/app/Fragment;", "()V", "_binding", "Lcom/ethereallab/fb_todo/databinding/FragmentCreateBinding;", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "getAuth", "()Lcom/google/firebase/auth/FirebaseAuth;", "auth$delegate", "Lkotlin/Lazy;", "binding", "getBinding", "()Lcom/ethereallab/fb_todo/databinding/FragmentCreateBinding;", "cart", "", "Lcom/ethereallab/fb_todo/models/Recipe;", "cartSizeFlow", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "getCartSizeFlow", "()Lkotlinx/coroutines/flow/MutableStateFlow;", "database", "Lcom/google/firebase/database/FirebaseDatabase;", "recipes", "recipesRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "repository", "Lcom/ethereallab/fb_todo/repository/ToDoRepository;", "createMealPlan", "Lkotlin/Pair;", "Ljava/time/LocalDate;", "inputDate", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onDestroyView", "", "onViewCreated", "view", "app_debug"})
public final class CreateFragment extends androidx.fragment.app.Fragment {
    @org.jetbrains.annotations.Nullable
    private com.ethereallab.fb_todo.databinding.FragmentCreateBinding _binding;
    private androidx.recyclerview.widget.RecyclerView recipesRecyclerView;
    private com.ethereallab.fb_todo.repository.ToDoRepository repository;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.database.FirebaseDatabase database = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.ethereallab.fb_todo.models.Recipe> cart = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.ethereallab.fb_todo.models.Recipe> recipes = null;
    @org.jetbrains.annotations.NotNull
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> cartSizeFlow = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.Lazy auth$delegate = null;
    
    public CreateFragment() {
        super();
    }
    
    private final com.ethereallab.fb_todo.databinding.FragmentCreateBinding getBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Integer> getCartSizeFlow() {
        return null;
    }
    
    private final com.google.firebase.auth.FirebaseAuth getAuth() {
        return null;
    }
    
    @java.lang.Override
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.TIRAMISU)
    @org.jetbrains.annotations.NotNull
    public android.view.View onCreateView(@org.jetbrains.annotations.NotNull
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable
    android.view.ViewGroup container, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlin.Pair<java.time.LocalDate, java.time.LocalDate> createMealPlan(@org.jetbrains.annotations.NotNull
    java.time.LocalDate inputDate) {
        return null;
    }
    
    @java.lang.Override
    public void onViewCreated(@org.jetbrains.annotations.NotNull
    android.view.View view, @org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    public void onDestroyView() {
    }
}