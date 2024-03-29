/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.talkback.labeling;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.fmtech.accessibilityservicedemo.BuildConfig;
import com.android.utils.LogUtils;
import com.android.utils.labeling.LabelsTable;

/**
 * A content provider for accessing TalkBack custom label data.
 * <p>
 * The following operations are supported at each URI:
 * <ul>
 * <li>{@code AUTHORITY/labels}: query and insert.
 * <li>{@code AUTHORITY/labels/#}: query, update, and delete.
 * </ul>
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class LabelProvider extends ContentProvider {
    public static final String AUTHORITY = BuildConfig.APPLICATION_ID + ".providers.LabelProvider";
    static final String LABELS_PATH = "labels";
    static final Uri LABELS_CONTENT_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .path(LABELS_PATH)
            .build();
    private static final Uri LABELS_ID_CONTENT_URI = Uri.withAppendedPath(LABELS_CONTENT_URI, "#");
    private static final String PACKAGE_SUMMARY_PATH = "packageSummary";
    private static final Uri PACKAGE_SUMMARY_URI = new Uri.Builder()
            .scheme("content")
            .authority(AUTHORITY)
            .path(PACKAGE_SUMMARY_PATH)
            .build();

    /* Codes for URI matching */
    static final int LABELS = 1;
    static final int LABELS_ID = 2;
    private static final int PACKAGE_SUMMARY = 3;

    private static final String UNKNOWN_URI_FORMAT_STRING = "Unknown URI: %s";
    private static final String NULL_URI_FORMAT_STRING = "URI is null";

    static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUriMatcher.addURI(AUTHORITY, LABELS_CONTENT_URI.getPath(), LABELS);
        sUriMatcher.addURI(AUTHORITY, LABELS_ID_CONTENT_URI.getPath(), LABELS_ID);
        sUriMatcher.addURI(AUTHORITY, PACKAGE_SUMMARY_URI.getPath(), PACKAGE_SUMMARY);
    }

    private SQLiteDatabase mDatabase;

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    /**
     * Inserts a label in the labels database.
     *
     * @param uri The content URI for labels.
     * @param values The values to insert for the new label.
     * @return The URI of the newly inserted label,
     *         or {@code null} if the insert failed.
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        if (uri == null) {
            LogUtils.log(this, Log.WARN, NULL_URI_FORMAT_STRING);
            return null;
        }

        switch (sUriMatcher.match(uri)) {
            case LABELS:
                initializeDatabaseIfNull();

                if (values == null) {
                    return null;
                }

                if (values.containsKey(LabelsTable.KEY_ID)) {
                    LogUtils.log(this, Log.WARN, "Label ID must be assigned by the database.");
                    return null;
                }

                long rowId = mDatabase.insert(LabelsTable.TABLE_NAME, null, values);

                if (rowId < 0) {
                    LogUtils.log(this, Log.WARN, "Failed to insert label.");
                    return null;
                } else {
                    return ContentUris.withAppendedId(LABELS_CONTENT_URI, rowId);
                }
            default:
                LogUtils.log(this, Log.WARN, UNKNOWN_URI_FORMAT_STRING, uri);
                return null;
        }
    }

    /**
     * Queries for a label or multiple labels in the labels database.
     *
     * @param uri The URI representing the type of query to perform:
     *            {@code LABELS_CONTENT_URI} for a subset of all labels,
     *            {@code LABELS_ID_CONTENT_URI} for a specific label, or
     *            {@code PACKAGE_SUMMARY} for a label count per package.
     * @param projection The columns to return.
     * @param selection The WHERE clause for the query.
     * @param selectionArgs The arguments for the WHERE clause of the query.
     * @param sortOrder the ORDER BY clause for the query.
     * @return A cursor representing the data resulting from the query, or]
     *         {@code null} if the query failed to execute.
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
            String sortOrder) {
        if (uri == null) {
            LogUtils.log(this, Log.WARN, NULL_URI_FORMAT_STRING);
            return null;
        }

        final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(LabelsTable.TABLE_NAME);

        String groupBy = null;

        switch (sUriMatcher.match(uri)) {
            case LABELS:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = LabelsTable.KEY_ID;
                }
                break;
            case LABELS_ID:
                final String labelIdString = uri.getLastPathSegment();
                final int labelId;
                try {
                    labelId = Integer.parseInt(labelIdString);
                } catch (NumberFormatException e) {
                    LogUtils.log(this, Log.WARN, UNKNOWN_URI_FORMAT_STRING, uri);
                    return null;
                }

                final String where = String.format("%s = %d", LabelsTable.KEY_ID, labelId);
                queryBuilder.appendWhere(where);
                break;
            case PACKAGE_SUMMARY:
                projection = new String[] { LabelsTable.KEY_PACKAGE_NAME, "COUNT(*)" };
                groupBy = LabelsTable.KEY_PACKAGE_NAME;
                sortOrder = LabelsTable.KEY_PACKAGE_NAME;
                break;
            default:
                LogUtils.log(this, Log.WARN, UNKNOWN_URI_FORMAT_STRING, uri);
                return null;
        }

        initializeDatabaseIfNull();

        return queryBuilder.query(mDatabase, projection, selection, selectionArgs, groupBy,
                null /* having */, sortOrder);
    }

    /**
     * Updates a label in the labels database.
     *
     * @param uri The URI matching {code LABELS_ID_CONTENT_URI} that represents
     *            the specific label to update.
     * @param values The values to use to update the label.
     * @param selection The WHERE clause for the query.
     * @param selectionArgs The arguments for the WHERE clause of the query.
     * @return The number of rows affected.
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        if (uri == null) {
            LogUtils.log(this, Log.WARN, NULL_URI_FORMAT_STRING);
            return 0;
        }

        switch (sUriMatcher.match(uri)) {
            case LABELS_ID:
                initializeDatabaseIfNull();

                final String labelIdString = uri.getLastPathSegment();
                final int labelId;
                try {
                    labelId = Integer.parseInt(labelIdString);
                } catch (NumberFormatException e) {
                    LogUtils.log(this, Log.WARN, UNKNOWN_URI_FORMAT_STRING, uri);
                    return 0;
                }

                final String where = String.format("%s = %d", LabelsTable.KEY_ID, labelId);
                final int result = mDatabase.update(LabelsTable.TABLE_NAME, values,
                        combineSelectionAndWhere(selection, where), selectionArgs);

                getContext().getContentResolver().notifyChange(uri, null /* observer */);

                return result;
            default:
                LogUtils.log(this, Log.WARN, UNKNOWN_URI_FORMAT_STRING, uri);
                return 0;
        }
    }

    /**
     * Deletes a label in the labels database.
     *
     * @param uri The URI matching {code LABELS_ID_CONTENT_URI} that represents
     *            the specific label to delete.
     * @param selection The WHERE clause for the query.
     * @param selectionArgs The arguments for the WHERE clause of the query.
     * @return The number of rows affected.
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        if (uri == null) {
            LogUtils.log(this, Log.WARN, NULL_URI_FORMAT_STRING);
            return 0;
        }

        switch (sUriMatcher.match(uri)) {
            case LABELS_ID:
                initializeDatabaseIfNull();

                final String labelIdString = uri.getLastPathSegment();
                final int labelId;
                try {
                    labelId = Integer.parseInt(labelIdString);
                } catch (NumberFormatException e) {
                    LogUtils.log(this, Log.WARN, UNKNOWN_URI_FORMAT_STRING, uri);
                    return 0;
                }

                final String where = String.format("%s = %d", LabelsTable.KEY_ID, labelId);
                final int result = mDatabase.delete(LabelsTable.TABLE_NAME,
                        combineSelectionAndWhere(selection, where), selectionArgs);

                getContext().getContentResolver().notifyChange(uri, null /* observer */);

                return result;
            default:
                LogUtils.log(this, Log.WARN, UNKNOWN_URI_FORMAT_STRING, uri);
                return 0;
        }
    }

    @Override
    public void shutdown() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    /**
     * Joins a selection clause with a where clause to form a larger selection
     * clause that represents the AND of the two clauses.
     *
     * @param selection The selection clause.
     * @param where The where clause.
     * @return The joined clause.
     */
    private String combineSelectionAndWhere(String selection, final String where) {
        if (TextUtils.isEmpty(where)) {
            return selection;
        } else if (TextUtils.isEmpty(selection)) {
            return where;
        }

        return String.format("(%s) AND (%s)", where, selection);
    }

    /**
     * Initializes the database (if not already initialized) when used.
     * <p>
     * Note: the database is automatically cleaned up by the kernel when the
     * process terminates.
     */
    private void initializeDatabaseIfNull() {
        if (mDatabase == null) {
            mDatabase = new LabelsDatabaseOpenHelper(getContext()).getWritableDatabase();
        }
    }

    /**
     * A helper for managing a SQLite database that stores label data.
     */
    private final class LabelsDatabaseOpenHelper extends SQLiteOpenHelper {
        private static final String DATABASE_NAME = "labelsDatabase.db";

        /*
         * If the database structure is modified and this value is changed, be
         * sure to implement the onUpgrade method for the database and each
         * relevant table that it includes.
         */
        private static final int DATABASE_VERSION = 2;

        public LabelsDatabaseOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            LabelsTable.onCreate(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            LabelsTable.onUpgrade(db, oldVersion, newVersion);
        }
    }
}
