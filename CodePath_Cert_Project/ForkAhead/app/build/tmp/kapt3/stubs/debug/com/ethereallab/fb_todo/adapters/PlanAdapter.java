package com.ethereallab.fb_todo.adapters;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\f\u0012\b\u0012\u00060\u0002R\u00020\u00000\u0001:\u0001\u0015B#\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u00a2\u0006\u0002\u0010\nJ\b\u0010\u000b\u001a\u00020\fH\u0016J\u001c\u0010\r\u001a\u00020\u000e2\n\u0010\u000f\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0010\u001a\u00020\fH\u0016J\u001c\u0010\u0011\u001a\u00060\u0002R\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\fH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/ethereallab/fb_todo/adapters/PlanAdapter;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/ethereallab/fb_todo/adapters/PlanAdapter$PlanRecipesViewHolder;", "context", "Landroid/content/Context;", "mealsFragment_context", "Lcom/ethereallab/fb_todo/fragments/MealsFragment;", "plan_recipes", "", "Lcom/ethereallab/fb_todo/models/Recipe;", "(Landroid/content/Context;Lcom/ethereallab/fb_todo/fragments/MealsFragment;Ljava/util/List;)V", "getItemCount", "", "onBindViewHolder", "", "holder", "position", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "viewType", "PlanRecipesViewHolder", "app_debug"})
public final class PlanAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<com.ethereallab.fb_todo.adapters.PlanAdapter.PlanRecipesViewHolder> {
    @org.jetbrains.annotations.NotNull
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull
    private final com.ethereallab.fb_todo.fragments.MealsFragment mealsFragment_context = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.List<com.ethereallab.fb_todo.models.Recipe> plan_recipes = null;
    
    public PlanAdapter(@org.jetbrains.annotations.NotNull
    android.content.Context context, @org.jetbrains.annotations.NotNull
    com.ethereallab.fb_todo.fragments.MealsFragment mealsFragment_context, @org.jetbrains.annotations.NotNull
    java.util.List<com.ethereallab.fb_todo.models.Recipe> plan_recipes) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.NotNull
    public com.ethereallab.fb_todo.adapters.PlanAdapter.PlanRecipesViewHolder onCreateViewHolder(@org.jetbrains.annotations.NotNull
    android.view.ViewGroup parent, int viewType) {
        return null;
    }
    
    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    @java.lang.Override
    public void onBindViewHolder(@org.jetbrains.annotations.NotNull
    com.ethereallab.fb_todo.adapters.PlanAdapter.PlanRecipesViewHolder holder, int position) {
    }
    
    /**
     * Remember: RecyclerView adapters require a getItemCount() method.
     */
    @java.lang.Override
    public int getItemCount() {
        return 0;
    }
    
    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0086\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\u0012\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0004H\u0016R\u001c\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0017"}, d2 = {"Lcom/ethereallab/fb_todo/adapters/PlanAdapter$PlanRecipesViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroid/view/View$OnClickListener;", "mView", "Landroid/view/View;", "(Lcom/ethereallab/fb_todo/adapters/PlanAdapter;Landroid/view/View;)V", "mItem", "Lcom/ethereallab/fb_todo/models/Recipe;", "getMItem", "()Lcom/ethereallab/fb_todo/models/Recipe;", "setMItem", "(Lcom/ethereallab/fb_todo/models/Recipe;)V", "mMealImage", "Landroid/widget/ImageView;", "getMMealImage", "()Landroid/widget/ImageView;", "mMealTitle", "Landroid/widget/TextView;", "getMMealTitle", "()Landroid/widget/TextView;", "onClick", "", "v", "app_debug"})
    public final class PlanRecipesViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnClickListener {
        @org.jetbrains.annotations.Nullable
        private com.ethereallab.fb_todo.models.Recipe mItem;
        @org.jetbrains.annotations.NotNull
        private final android.widget.TextView mMealTitle = null;
        @org.jetbrains.annotations.NotNull
        private final android.widget.ImageView mMealImage = null;
        
        public PlanRecipesViewHolder(@org.jetbrains.annotations.NotNull
        android.view.View mView) {
            super(null);
        }
        
        @org.jetbrains.annotations.Nullable
        public final com.ethereallab.fb_todo.models.Recipe getMItem() {
            return null;
        }
        
        public final void setMItem(@org.jetbrains.annotations.Nullable
        com.ethereallab.fb_todo.models.Recipe p0) {
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.TextView getMMealTitle() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull
        public final android.widget.ImageView getMMealImage() {
            return null;
        }
        
        @java.lang.Override
        public void onClick(@org.jetbrains.annotations.Nullable
        android.view.View v) {
        }
    }
}