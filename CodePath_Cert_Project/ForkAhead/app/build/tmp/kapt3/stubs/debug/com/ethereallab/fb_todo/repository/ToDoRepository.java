package com.ethereallab.fb_todo.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011J\u0012\u0010\u0012\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00140\u0013J\u0012\u0010\u0015\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00140\u0013J\u0019\u0010\u0016\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0011R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082D\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0017"}, d2 = {"Lcom/ethereallab/fb_todo/repository/ToDoRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "auth", "Lcom/google/firebase/auth/FirebaseAuth;", "db", "Lcom/ethereallab/fb_todo/database/AppDatabase;", "firestore", "Lcom/google/firebase/firestore/FirebaseFirestore;", "tag", "", "addTodo", "", "todo", "Lcom/ethereallab/fb_todo/models/Todo;", "(Lcom/ethereallab/fb_todo/models/Todo;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getCompletedTodos", "Lkotlinx/coroutines/flow/Flow;", "", "getPendingTodos", "updateTodoStatus", "app_debug"})
public final class ToDoRepository {
    @org.jetbrains.annotations.NotNull
    private final com.ethereallab.fb_todo.database.AppDatabase db = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.firestore.FirebaseFirestore firestore = null;
    @org.jetbrains.annotations.NotNull
    private final com.google.firebase.auth.FirebaseAuth auth = null;
    @org.jetbrains.annotations.NotNull
    private final java.lang.String tag = "TodoRepository";
    
    public ToDoRepository(@org.jetbrains.annotations.NotNull
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.ethereallab.fb_todo.models.Todo>> getPendingTodos() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final kotlinx.coroutines.flow.Flow<java.util.List<com.ethereallab.fb_todo.models.Todo>> getCompletedTodos() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object addTodo(@org.jetbrains.annotations.NotNull
    com.ethereallab.fb_todo.models.Todo todo, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object updateTodoStatus(@org.jetbrains.annotations.NotNull
    com.ethereallab.fb_todo.models.Todo todo, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion) {
        return null;
    }
}