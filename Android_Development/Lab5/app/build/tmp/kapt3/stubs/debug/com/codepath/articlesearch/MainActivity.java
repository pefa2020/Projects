package com.codepath.articlesearch;

import java.lang.System;

@kotlin.Metadata(mv = {1, 7, 1}, k = 1, d1 = {"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0011\u0010\u001f\u001a\u00020 H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010!J\u0012\u0010\"\u001a\u00020 2\b\u0010#\u001a\u0004\u0018\u00010$H\u0014J\b\u0010%\u001a\u00020 H\u0014J\u001f\u0010&\u001a\u00020 2\f\u0010\'\u001a\b\u0012\u0004\u0012\u00020)0(H\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010*R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0016\u001a\u00020\u000fX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001d\u001a\u00020\u001eX\u0082.\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006+"}, d2 = {"Lcom/codepath/articlesearch/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "articleAdapter", "Lcom/codepath/articlesearch/ArticleAdapter;", "articles", "", "Lcom/codepath/articlesearch/DisplayArticle;", "articles2", "Lcom/codepath/articlesearch/Article;", "articlesRecyclerView", "Landroidx/recyclerview/widget/RecyclerView;", "binding", "Lcom/codepath/articlesearch/databinding/ActivityMainBinding;", "connectivityManager", "Landroid/net/ConnectivityManager;", "my_cache", "", "getMy_cache", "()Ljava/lang/String;", "setMy_cache", "(Ljava/lang/String;)V", "networkCallback", "networkStatusTextView", "Landroid/widget/TextView;", "sharedPref", "Landroid/content/SharedPreferences;", "swipeRefreshLayout", "Landroidx/swiperefreshlayout/widget/SwipeRefreshLayout;", "toggle", "Landroidx/appcompat/widget/SwitchCompat;", "fetchArticles", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "updateArticles", "databaseList", "", "Lcom/codepath/articlesearch/ArticleEntity;", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView articlesRecyclerView;
    private com.codepath.articlesearch.databinding.ActivityMainBinding binding;
    private final java.util.List<com.codepath.articlesearch.DisplayArticle> articles = null;
    private final java.util.List<com.codepath.articlesearch.Article> articles2 = null;
    private com.codepath.articlesearch.ArticleAdapter articleAdapter;
    private androidx.swiperefreshlayout.widget.SwipeRefreshLayout swipeRefreshLayout;
    private androidx.appcompat.widget.SwitchCompat toggle;
    private android.content.SharedPreferences sharedPref;
    private android.net.ConnectivityManager connectivityManager;
    private android.net.ConnectivityManager networkCallback;
    private android.widget.TextView networkStatusTextView;
    @org.jetbrains.annotations.NotNull
    private java.lang.String my_cache = "no";
    
    public MainActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMy_cache() {
        return null;
    }
    
    public final void setMy_cache(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    private final java.lang.Object fetchArticles(kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    private final java.lang.Object updateArticles(java.util.List<com.codepath.articlesearch.ArticleEntity> databaseList, kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}