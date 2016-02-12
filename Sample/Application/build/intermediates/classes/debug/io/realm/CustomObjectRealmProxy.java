package io.realm;


import android.util.JsonReader;
import android.util.JsonToken;
import com.example.android.demoapp.volley.CustomObject;
import io.realm.RealmFieldType;
import io.realm.exceptions.RealmMigrationNeededException;
import io.realm.internal.ColumnInfo;
import io.realm.internal.ImplicitTransaction;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Table;
import io.realm.internal.TableOrView;
import io.realm.internal.android.JsonUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CustomObjectRealmProxy extends CustomObject
    implements RealmObjectProxy {

    static final class CustomObjectColumnInfo extends ColumnInfo {

        public final long IDIndex;
        public final long uriIndex;
        public final long titleIndex;

        CustomObjectColumnInfo(String path, Table table) {
            final Map<String, Long> indicesMap = new HashMap<String, Long>(3);
            this.IDIndex = getValidColumnIndex(path, table, "CustomObject", "ID");
            indicesMap.put("ID", this.IDIndex);

            this.uriIndex = getValidColumnIndex(path, table, "CustomObject", "uri");
            indicesMap.put("uri", this.uriIndex);

            this.titleIndex = getValidColumnIndex(path, table, "CustomObject", "title");
            indicesMap.put("title", this.titleIndex);

            setIndicesMap(indicesMap);
        }
    }

    private final CustomObjectColumnInfo columnInfo;
    private static final List<String> FIELD_NAMES;
    static {
        List<String> fieldNames = new ArrayList<String>();
        fieldNames.add("ID");
        fieldNames.add("uri");
        fieldNames.add("title");
        FIELD_NAMES = Collections.unmodifiableList(fieldNames);
    }

    CustomObjectRealmProxy(ColumnInfo columnInfo) {
        this.columnInfo = (CustomObjectColumnInfo) columnInfo;
    }

    @Override
    @SuppressWarnings("cast")
    public long getID() {
        realm.checkIfValid();
        return (long) row.getLong(columnInfo.IDIndex);
    }

    @Override
    public void setID(long value) {
        realm.checkIfValid();
        row.setLong(columnInfo.IDIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getUri() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.uriIndex);
    }

    @Override
    public void setUri(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.uriIndex);
            return;
        }
        row.setString(columnInfo.uriIndex, value);
    }

    @Override
    @SuppressWarnings("cast")
    public String getTitle() {
        realm.checkIfValid();
        return (java.lang.String) row.getString(columnInfo.titleIndex);
    }

    @Override
    public void setTitle(String value) {
        realm.checkIfValid();
        if (value == null) {
            row.setNull(columnInfo.titleIndex);
            return;
        }
        row.setString(columnInfo.titleIndex, value);
    }

    public static Table initTable(ImplicitTransaction transaction) {
        if (!transaction.hasTable("class_CustomObject")) {
            Table table = transaction.getTable("class_CustomObject");
            table.addColumn(RealmFieldType.INTEGER, "ID", Table.NOT_NULLABLE);
            table.addColumn(RealmFieldType.STRING, "uri", Table.NULLABLE);
            table.addColumn(RealmFieldType.STRING, "title", Table.NULLABLE);
            table.setPrimaryKey("");
            return table;
        }
        return transaction.getTable("class_CustomObject");
    }

    public static CustomObjectColumnInfo validateTable(ImplicitTransaction transaction) {
        if (transaction.hasTable("class_CustomObject")) {
            Table table = transaction.getTable("class_CustomObject");
            if (table.getColumnCount() != 3) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field count does not match - expected 3 but was " + table.getColumnCount());
            }
            Map<String, RealmFieldType> columnTypes = new HashMap<String, RealmFieldType>();
            for (long i = 0; i < 3; i++) {
                columnTypes.put(table.getColumnName(i), table.getColumnType(i));
            }

            final CustomObjectColumnInfo columnInfo = new CustomObjectColumnInfo(transaction.getPath(), table);

            if (!columnTypes.containsKey("ID")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'ID' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("ID") != RealmFieldType.INTEGER) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'long' for field 'ID' in existing Realm file.");
            }
            if (table.isColumnNullable(columnInfo.IDIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'ID' does support null values in the existing Realm file. Use corresponding boxed type for field 'ID' or migrate using io.realm.internal.Table.convertColumnToNotNullable().");
            }
            if (!columnTypes.containsKey("uri")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'uri' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("uri") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'uri' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.uriIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'uri' is required. Either set @Required to field 'uri' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            if (!columnTypes.containsKey("title")) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Missing field 'title' in existing Realm file. Either remove field or migrate using io.realm.internal.Table.addColumn().");
            }
            if (columnTypes.get("title") != RealmFieldType.STRING) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Invalid type 'String' for field 'title' in existing Realm file.");
            }
            if (!table.isColumnNullable(columnInfo.titleIndex)) {
                throw new RealmMigrationNeededException(transaction.getPath(), "Field 'title' is required. Either set @Required to field 'title' or migrate using io.realm.internal.Table.convertColumnToNullable().");
            }
            return columnInfo;
        } else {
            throw new RealmMigrationNeededException(transaction.getPath(), "The CustomObject class is missing from the schema for this Realm.");
        }
    }

    public static String getTableName() {
        return "class_CustomObject";
    }

    public static List<String> getFieldNames() {
        return FIELD_NAMES;
    }

    @SuppressWarnings("cast")
    public static CustomObject createOrUpdateUsingJsonObject(Realm realm, JSONObject json, boolean update)
        throws JSONException {
        CustomObject obj = realm.createObject(CustomObject.class);
        if (json.has("ID")) {
            if (json.isNull("ID")) {
                throw new IllegalArgumentException("Trying to set non-nullable field ID to null.");
            } else {
                obj.setID((long) json.getLong("ID"));
            }
        }
        if (json.has("uri")) {
            if (json.isNull("uri")) {
                obj.setUri(null);
            } else {
                obj.setUri((String) json.getString("uri"));
            }
        }
        if (json.has("title")) {
            if (json.isNull("title")) {
                obj.setTitle(null);
            } else {
                obj.setTitle((String) json.getString("title"));
            }
        }
        return obj;
    }

    @SuppressWarnings("cast")
    public static CustomObject createUsingJsonStream(Realm realm, JsonReader reader)
        throws IOException {
        CustomObject obj = realm.createObject(CustomObject.class);
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("ID")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    throw new IllegalArgumentException("Trying to set non-nullable field ID to null.");
                } else {
                    obj.setID((long) reader.nextLong());
                }
            } else if (name.equals("uri")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setUri(null);
                } else {
                    obj.setUri((String) reader.nextString());
                }
            } else if (name.equals("title")) {
                if (reader.peek() == JsonToken.NULL) {
                    reader.skipValue();
                    obj.setTitle(null);
                } else {
                    obj.setTitle((String) reader.nextString());
                }
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return obj;
    }

    public static CustomObject copyOrUpdate(Realm realm, CustomObject object, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        if (object.realm != null && object.realm.getPath().equals(realm.getPath())) {
            return object;
        }
        return copy(realm, object, update, cache);
    }

    public static CustomObject copy(Realm realm, CustomObject newObject, boolean update, Map<RealmObject,RealmObjectProxy> cache) {
        CustomObject realmObject = realm.createObject(CustomObject.class);
        cache.put(newObject, (RealmObjectProxy) realmObject);
        realmObject.setID(newObject.getID());
        realmObject.setUri(newObject.getUri());
        realmObject.setTitle(newObject.getTitle());
        return realmObject;
    }

    public static CustomObject createDetachedCopy(CustomObject realmObject, int currentDepth, int maxDepth, Map<RealmObject, CacheData<RealmObject>> cache) {
        if (currentDepth > maxDepth || realmObject == null) {
            return null;
        }
        CacheData<CustomObject> cachedObject = (CacheData) cache.get(realmObject);
        CustomObject standaloneObject;
        if (cachedObject != null) {
            // Reuse cached object or recreate it because it was encountered at a lower depth.
            if (currentDepth >= cachedObject.minDepth) {
                return cachedObject.object;
            } else {
                standaloneObject = cachedObject.object;
                cachedObject.minDepth = currentDepth;
            }
        } else {
            standaloneObject = new CustomObject();
            cache.put(realmObject, new RealmObjectProxy.CacheData<RealmObject>(currentDepth, standaloneObject));
        }
        standaloneObject.setID(realmObject.getID());
        standaloneObject.setUri(realmObject.getUri());
        standaloneObject.setTitle(realmObject.getTitle());
        return standaloneObject;
    }

    @Override
    public String toString() {
        if (!isValid()) {
            return "Invalid object";
        }
        StringBuilder stringBuilder = new StringBuilder("CustomObject = [");
        stringBuilder.append("{ID:");
        stringBuilder.append(getID());
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{uri:");
        stringBuilder.append(getUri() != null ? getUri() : "null");
        stringBuilder.append("}");
        stringBuilder.append(",");
        stringBuilder.append("{title:");
        stringBuilder.append(getTitle() != null ? getTitle() : "null");
        stringBuilder.append("}");
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        String realmName = realm.getPath();
        String tableName = row.getTable().getName();
        long rowIndex = row.getIndex();

        int result = 17;
        result = 31 * result + ((realmName != null) ? realmName.hashCode() : 0);
        result = 31 * result + ((tableName != null) ? tableName.hashCode() : 0);
        result = 31 * result + (int) (rowIndex ^ (rowIndex >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomObjectRealmProxy aCustomObject = (CustomObjectRealmProxy)o;

        String path = realm.getPath();
        String otherPath = aCustomObject.realm.getPath();
        if (path != null ? !path.equals(otherPath) : otherPath != null) return false;;

        String tableName = row.getTable().getName();
        String otherTableName = aCustomObject.row.getTable().getName();
        if (tableName != null ? !tableName.equals(otherTableName) : otherTableName != null) return false;

        if (row.getIndex() != aCustomObject.row.getIndex()) return false;

        return true;
    }

}
