package com.ethereallab.fb_todo.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.ethereallab.fb_todo.models.Todo;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TodoDao_Impl implements TodoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Todo> __insertionAdapterOfTodo;

  private final EntityDeletionOrUpdateAdapter<Todo> __updateAdapterOfTodo;

  public TodoDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTodo = new EntityInsertionAdapter<Todo>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `Todo` (`localId`,`firestoreId`,`content`,`isDone`,`completedDate`,`userId`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Todo entity) {
        statement.bindLong(1, entity.getLocalId());
        if (entity.getFirestoreId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFirestoreId());
        }
        if (entity.getContent() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContent());
        }
        final int _tmp = entity.isDone() ? 1 : 0;
        statement.bindLong(4, _tmp);
        if (entity.getCompletedDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getCompletedDate());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getUserId());
        }
      }
    };
    this.__updateAdapterOfTodo = new EntityDeletionOrUpdateAdapter<Todo>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `Todo` SET `localId` = ?,`firestoreId` = ?,`content` = ?,`isDone` = ?,`completedDate` = ?,`userId` = ? WHERE `localId` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Todo entity) {
        statement.bindLong(1, entity.getLocalId());
        if (entity.getFirestoreId() == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, entity.getFirestoreId());
        }
        if (entity.getContent() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContent());
        }
        final int _tmp = entity.isDone() ? 1 : 0;
        statement.bindLong(4, _tmp);
        if (entity.getCompletedDate() == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, entity.getCompletedDate());
        }
        if (entity.getUserId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getUserId());
        }
        statement.bindLong(7, entity.getLocalId());
      }
    };
  }

  @Override
  public Object insert(final Todo todo, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTodo.insertAndReturnId(todo);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertAll(final List<Todo> todos, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTodo.insert(todos);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object update(final Todo todo, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTodo.handle(todo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Todo>> getPendingTodos(final String userId) {
    final String _sql = "SELECT * FROM Todo WHERE isDone = 0 AND userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"Todo"}, new Callable<List<Todo>>() {
      @Override
      @NonNull
      public List<Todo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLocalId = CursorUtil.getColumnIndexOrThrow(_cursor, "localId");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfIsDone = CursorUtil.getColumnIndexOrThrow(_cursor, "isDone");
          final int _cursorIndexOfCompletedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "completedDate");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final List<Todo> _result = new ArrayList<Todo>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Todo _item;
            final int _tmpLocalId;
            _tmpLocalId = _cursor.getInt(_cursorIndexOfLocalId);
            final String _tmpFirestoreId;
            if (_cursor.isNull(_cursorIndexOfFirestoreId)) {
              _tmpFirestoreId = null;
            } else {
              _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final boolean _tmpIsDone;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDone);
            _tmpIsDone = _tmp != 0;
            final Long _tmpCompletedDate;
            if (_cursor.isNull(_cursorIndexOfCompletedDate)) {
              _tmpCompletedDate = null;
            } else {
              _tmpCompletedDate = _cursor.getLong(_cursorIndexOfCompletedDate);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _item = new Todo(_tmpLocalId,_tmpFirestoreId,_tmpContent,_tmpIsDone,_tmpCompletedDate,_tmpUserId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Todo>> getCompletedTodos(final String userId) {
    final String _sql = "SELECT * FROM Todo WHERE isDone = 1 AND userId = ? ORDER BY completedDate DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (userId == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, userId);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"Todo"}, new Callable<List<Todo>>() {
      @Override
      @NonNull
      public List<Todo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfLocalId = CursorUtil.getColumnIndexOrThrow(_cursor, "localId");
          final int _cursorIndexOfFirestoreId = CursorUtil.getColumnIndexOrThrow(_cursor, "firestoreId");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfIsDone = CursorUtil.getColumnIndexOrThrow(_cursor, "isDone");
          final int _cursorIndexOfCompletedDate = CursorUtil.getColumnIndexOrThrow(_cursor, "completedDate");
          final int _cursorIndexOfUserId = CursorUtil.getColumnIndexOrThrow(_cursor, "userId");
          final List<Todo> _result = new ArrayList<Todo>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Todo _item;
            final int _tmpLocalId;
            _tmpLocalId = _cursor.getInt(_cursorIndexOfLocalId);
            final String _tmpFirestoreId;
            if (_cursor.isNull(_cursorIndexOfFirestoreId)) {
              _tmpFirestoreId = null;
            } else {
              _tmpFirestoreId = _cursor.getString(_cursorIndexOfFirestoreId);
            }
            final String _tmpContent;
            if (_cursor.isNull(_cursorIndexOfContent)) {
              _tmpContent = null;
            } else {
              _tmpContent = _cursor.getString(_cursorIndexOfContent);
            }
            final boolean _tmpIsDone;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDone);
            _tmpIsDone = _tmp != 0;
            final Long _tmpCompletedDate;
            if (_cursor.isNull(_cursorIndexOfCompletedDate)) {
              _tmpCompletedDate = null;
            } else {
              _tmpCompletedDate = _cursor.getLong(_cursorIndexOfCompletedDate);
            }
            final String _tmpUserId;
            if (_cursor.isNull(_cursorIndexOfUserId)) {
              _tmpUserId = null;
            } else {
              _tmpUserId = _cursor.getString(_cursorIndexOfUserId);
            }
            _item = new Todo(_tmpLocalId,_tmpFirestoreId,_tmpContent,_tmpIsDone,_tmpCompletedDate,_tmpUserId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
