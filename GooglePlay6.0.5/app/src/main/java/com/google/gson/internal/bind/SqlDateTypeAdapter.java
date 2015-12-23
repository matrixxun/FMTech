package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class SqlDateTypeAdapter
  extends TypeAdapter<Date>
{
  public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory()
  {
    public final <T> TypeAdapter<T> create(Gson paramAnonymousGson, TypeToken<T> paramAnonymousTypeToken)
    {
      if (paramAnonymousTypeToken.rawType == Date.class) {
        return new SqlDateTypeAdapter();
      }
      return null;
    }
  };
  private final DateFormat format = new SimpleDateFormat("MMM d, yyyy");
  
  /* Error */
  private Date read(com.google.gson.stream.JsonReader paramJsonReader)
    throws IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_1
    //   3: invokevirtual 40	com/google/gson/stream/JsonReader:peek	()Lcom/google/gson/stream/JsonToken;
    //   6: getstatic 46	com/google/gson/stream/JsonToken:NULL	Lcom/google/gson/stream/JsonToken;
    //   9: if_acmpne +13 -> 22
    //   12: aload_1
    //   13: invokevirtual 49	com/google/gson/stream/JsonReader:nextNull	()V
    //   16: aconst_null
    //   17: astore_3
    //   18: aload_0
    //   19: monitorexit
    //   20: aload_3
    //   21: areturn
    //   22: new 51	java/sql/Date
    //   25: dup
    //   26: aload_0
    //   27: getfield 28	com/google/gson/internal/bind/SqlDateTypeAdapter:format	Ljava/text/DateFormat;
    //   30: aload_1
    //   31: invokevirtual 55	com/google/gson/stream/JsonReader:nextString	()Ljava/lang/String;
    //   34: invokevirtual 61	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   37: invokevirtual 67	java/util/Date:getTime	()J
    //   40: invokespecial 70	java/sql/Date:<init>	(J)V
    //   43: astore_3
    //   44: goto -26 -> 18
    //   47: astore 4
    //   49: new 72	com/google/gson/JsonSyntaxException
    //   52: dup
    //   53: aload 4
    //   55: invokespecial 75	com/google/gson/JsonSyntaxException:<init>	(Ljava/lang/Throwable;)V
    //   58: athrow
    //   59: astore_2
    //   60: aload_0
    //   61: monitorexit
    //   62: aload_2
    //   63: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	64	0	this	SqlDateTypeAdapter
    //   0	64	1	paramJsonReader	com.google.gson.stream.JsonReader
    //   59	4	2	localObject	Object
    //   17	27	3	localDate	Date
    //   47	7	4	localParseException	java.text.ParseException
    // Exception table:
    //   from	to	target	type
    //   22	44	47	java/text/ParseException
    //   2	16	59	finally
    //   22	44	59	finally
    //   49	59	59	finally
  }
  
  private void write(JsonWriter paramJsonWriter, Date paramDate)
    throws IOException
  {
    if (paramDate == null) {}
    String str;
    for (Object localObject2 = null;; localObject2 = str)
    {
      try
      {
        paramJsonWriter.value((String)localObject2);
        return;
      }
      finally {}
      str = this.format.format(paramDate);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.SqlDateTypeAdapter
 * JD-Core Version:    0.7.0.1
 */