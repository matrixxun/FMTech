package com.google.android.finsky.placesapi;

import android.content.Context;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public final class AdrMicroformatParser
{
  private static final HashMap<String, PartType> ADR_TYPE_MAP = new HashMap();
  Context mContext;
  
  static
  {
    for (PartType localPartType : PartType.values()) {
      if (localPartType.adrClass != null) {
        ADR_TYPE_MAP.put(localPartType.adrClass, localPartType);
      }
    }
  }
  
  public AdrMicroformatParser(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  static String getFirstAndRemove(LinkedList<AnnotatedPart> paramLinkedList, PartType paramPartType)
  {
    Iterator localIterator = paramLinkedList.iterator();
    while (localIterator.hasNext())
    {
      AnnotatedPart localAnnotatedPart = (AnnotatedPart)localIterator.next();
      if (localAnnotatedPart.type == paramPartType)
      {
        localIterator.remove();
        if ((localIterator.hasNext()) && (((AnnotatedPart)localIterator.next()).type == PartType.SEPARATOR)) {
          localIterator.remove();
        }
        return localAnnotatedPart.text;
      }
    }
    return null;
  }
  
  static LinkedList<AnnotatedPart> split(String paramString)
    throws AdrMicroformatParserException
  {
    SAXParserFactory localSAXParserFactory = SAXParserFactory.newInstance();
    try
    {
      SAXParser localSAXParser = localSAXParserFactory.newSAXParser();
      MicroformatAdrHandler localMicroformatAdrHandler = new MicroformatAdrHandler((byte)0);
      localSAXParser.parse(new InputSource(new StringReader("<?xml version=\"1.0\"?><root>" + paramString + "</root>")), localMicroformatAdrHandler);
      LinkedList localLinkedList = localMicroformatAdrHandler.parts;
      return localLinkedList;
    }
    catch (ParserConfigurationException localParserConfigurationException)
    {
      throw new AdrMicroformatParserException(localParserConfigurationException);
    }
    catch (SAXException localSAXException)
    {
      throw new AdrMicroformatParserException(localSAXException);
    }
    catch (IOException localIOException)
    {
      throw new AdrMicroformatParserException(localIOException);
    }
  }
  
  static final class AnnotatedPart
  {
    String text;
    AdrMicroformatParser.PartType type;
    
    AnnotatedPart(String paramString, AdrMicroformatParser.PartType paramPartType)
    {
      this.text = paramString;
      this.type = paramPartType;
    }
    
    public final String toString()
    {
      return this.text;
    }
  }
  
  private static final class MicroformatAdrHandler
    extends DefaultHandler
  {
    private StringBuilder mAggregatedText = new StringBuilder();
    private AdrMicroformatParser.PartType mCurrentType = AdrMicroformatParser.PartType.SEPARATOR;
    LinkedList<AdrMicroformatParser.AnnotatedPart> parts = new LinkedList();
    
    private void finishPart()
    {
      if (this.mAggregatedText.length() > 0)
      {
        this.parts.add(new AdrMicroformatParser.AnnotatedPart(this.mAggregatedText.toString(), this.mCurrentType));
        this.mAggregatedText = new StringBuilder();
      }
    }
    
    public final void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.mAggregatedText.append(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    public final void endDocument()
    {
      finishPart();
    }
    
    public final void endElement(String paramString1, String paramString2, String paramString3)
    {
      if (this.mCurrentType != AdrMicroformatParser.PartType.SEPARATOR)
      {
        finishPart();
        this.mCurrentType = AdrMicroformatParser.PartType.SEPARATOR;
      }
    }
    
    public final void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
      throws SAXException
    {
      if (this.mCurrentType != AdrMicroformatParser.PartType.SEPARATOR) {
        throw new SAXException("Invalid nested element.");
      }
      String str = paramAttributes.getValue("class");
      if (str != null)
      {
        AdrMicroformatParser.PartType localPartType = (AdrMicroformatParser.PartType)AdrMicroformatParser.ADR_TYPE_MAP.get(str);
        if (localPartType == null) {
          localPartType = AdrMicroformatParser.PartType.UNKNOWN;
        }
        finishPart();
        this.mCurrentType = localPartType;
      }
    }
  }
  
  static enum PartType
  {
    final String adrClass;
    
    static
    {
      ADR_EXTENDED_ADDRESS = new PartType("ADR_EXTENDED_ADDRESS", 1, "extended-address");
      ADR_LOCALITY = new PartType("ADR_LOCALITY", 2, "locality");
      ADR_REGION = new PartType("ADR_REGION", 3, "region");
      ADR_POSTAL_CODE = new PartType("ADR_POSTAL_CODE", 4, "postal-code");
      UNKNOWN = new PartType("UNKNOWN", 5, null);
      SEPARATOR = new PartType("SEPARATOR", 6, null);
      PartType[] arrayOfPartType = new PartType[7];
      arrayOfPartType[0] = ADR_STREET_ADDRESS;
      arrayOfPartType[1] = ADR_EXTENDED_ADDRESS;
      arrayOfPartType[2] = ADR_LOCALITY;
      arrayOfPartType[3] = ADR_REGION;
      arrayOfPartType[4] = ADR_POSTAL_CODE;
      arrayOfPartType[5] = UNKNOWN;
      arrayOfPartType[6] = SEPARATOR;
      $VALUES = arrayOfPartType;
    }
    
    private PartType(String paramString)
    {
      this.adrClass = paramString;
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.android.finsky.placesapi.AdrMicroformatParser
 * JD-Core Version:    0.7.0.1
 */