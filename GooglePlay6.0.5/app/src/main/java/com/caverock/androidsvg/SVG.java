package com.caverock.androidsvg;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xml.sax.SAXException;

public final class SVG
{
  private static SVGCache mCache = new SVGCache();
  CSSParser.Ruleset cssRules = new CSSParser.Ruleset();
  String desc = "";
  SVGExternalFileResolver fileResolver = null;
  Map<String, SvgElementBase> idToElementMap = new HashMap();
  float overallScale = 1.0F;
  private float renderDPI = 96.0F;
  Svg rootElement = null;
  String title = "";
  
  private static float getDensityScale(Resources paramResources)
  {
    return paramResources.getDisplayMetrics().densityDpi / 160.0F;
  }
  
  private Box getDocumentDimensions(float paramFloat)
  {
    Length localLength1 = this.rootElement.width;
    Length localLength2 = this.rootElement.height;
    if ((localLength1 == null) || (localLength1.isZero()) || (localLength1.unit == Unit.percent) || (localLength1.unit == Unit.em) || (localLength1.unit == Unit.ex)) {
      return new Box(-1.0F, -1.0F, -1.0F, -1.0F);
    }
    float f1 = localLength1.floatValue(paramFloat);
    float f2;
    if (localLength2 != null)
    {
      if ((localLength2.isZero()) || (localLength2.unit == Unit.percent) || (localLength2.unit == Unit.em) || (localLength2.unit == Unit.ex)) {
        return new Box(-1.0F, -1.0F, -1.0F, -1.0F);
      }
      f2 = localLength2.floatValue(paramFloat);
    }
    for (;;)
    {
      return new Box(0.0F, 0.0F, f1, f2);
      if (this.rootElement.viewBox != null) {
        f2 = f1 * this.rootElement.viewBox.height / this.rootElement.viewBox.width;
      } else {
        f2 = f1;
      }
    }
  }
  
  public static Drawable getDrawableFromResource(Resources paramResources, int paramInt1, int paramInt2)
  {
    Colour localColour = new Colour(paramResources.getColor(paramInt2));
    SVGCache localSVGCache1 = mCache;
    float f1 = getDensityScale(paramResources);
    String str1 = SVGCache.getCacheKey("res" + paramInt1, f1);
    List localList = (List)localSVGCache1.mLruCache.get(str1);
    if ((localList == null) || (localList.isEmpty())) {}
    for (SVG localSVG1 = null; localSVG1 != null; localSVG1 = (SVG)localList.get(0)) {
      return new SVGDrawable(localSVG1, localColour);
    }
    for (;;)
    {
      SVG localSVG2;
      float f2;
      float f3;
      float f4;
      try
      {
        localSVG2 = getFromResource(paramResources, paramInt1);
        f2 = getDensityScale(paramResources);
        f3 = localSVG2.getDocumentHeight();
        f4 = localSVG2.getDocumentWidth();
        if ((f3 <= 0.0F) || (f4 <= 0.0F))
        {
          SVGCache localSVGCache2 = mCache;
          String str2 = "res" + paramInt1;
          if (localSVG2 != null)
          {
            ArrayList localArrayList = new ArrayList(1);
            localArrayList.add(localSVG2);
            String str3 = SVGCache.getCacheKey(str2, localSVG2.overallScale);
            localSVGCache2.mLruCache.put(str3, localArrayList);
          }
          SVGDrawable localSVGDrawable = new SVGDrawable(localSVG2, localColour);
          return localSVGDrawable;
        }
      }
      catch (SVGParseException localSVGParseException)
      {
        localSVGParseException.printStackTrace();
        return null;
      }
      float f5 = f3 * f2;
      if (localSVG2.rootElement == null) {
        throw new IllegalArgumentException("SVG document is empty");
      }
      localSVG2.rootElement.height = new Length(f5);
      float f6 = f4 * f2;
      if (localSVG2.rootElement == null) {
        throw new IllegalArgumentException("SVG document is empty");
      }
      localSVG2.rootElement.width = new Length(f6);
      localSVG2.overallScale = (f2 * localSVG2.overallScale);
    }
  }
  
  private SvgElementBase getElementById(SvgContainer paramSvgContainer, String paramString)
  {
    SvgElementBase localSvgElementBase1 = (SvgElementBase)paramSvgContainer;
    if (paramString.equals(localSvgElementBase1.id)) {
      return localSvgElementBase1;
    }
    Iterator localIterator = paramSvgContainer.getChildren().iterator();
    while (localIterator.hasNext())
    {
      SvgObject localSvgObject = (SvgObject)localIterator.next();
      if ((localSvgObject instanceof SvgElementBase))
      {
        SvgElementBase localSvgElementBase2 = (SvgElementBase)localSvgObject;
        if (paramString.equals(localSvgElementBase2.id)) {
          return localSvgElementBase2;
        }
        if ((localSvgObject instanceof SvgContainer))
        {
          SvgElementBase localSvgElementBase3 = getElementById((SvgContainer)localSvgObject, paramString);
          if (localSvgElementBase3 != null) {
            return localSvgElementBase3;
          }
        }
      }
    }
    return null;
  }
  
  public static SVG getFromResource(Context paramContext, int paramInt)
    throws SVGParseException
  {
    return getFromResource(paramContext.getResources(), paramInt);
  }
  
  /* Error */
  private static SVG getFromResource(Resources paramResources, int paramInt)
    throws SVGParseException
  {
    // Byte code:
    //   0: new 257	com/caverock/androidsvg/SVGParser
    //   3: dup
    //   4: invokespecial 258	com/caverock/androidsvg/SVGParser:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: iload_1
    //   10: invokevirtual 262	android/content/res/Resources:openRawResource	(I)Ljava/io/InputStream;
    //   13: astore_3
    //   14: aload_2
    //   15: aload_3
    //   16: invokevirtual 266	com/caverock/androidsvg/SVGParser:parse	(Ljava/io/InputStream;)Lcom/caverock/androidsvg/SVG;
    //   19: astore 6
    //   21: aload_3
    //   22: invokevirtual 271	java/io/InputStream:close	()V
    //   25: aload 6
    //   27: areturn
    //   28: astore 4
    //   30: aload_3
    //   31: invokevirtual 271	java/io/InputStream:close	()V
    //   34: aload 4
    //   36: athrow
    //   37: astore 7
    //   39: aload 6
    //   41: areturn
    //   42: astore 5
    //   44: goto -10 -> 34
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	47	0	paramResources	Resources
    //   0	47	1	paramInt	int
    //   7	8	2	localSVGParser	SVGParser
    //   13	18	3	localInputStream	java.io.InputStream
    //   28	7	4	localObject	Object
    //   42	1	5	localIOException1	java.io.IOException
    //   19	21	6	localSVG	SVG
    //   37	1	7	localIOException2	java.io.IOException
    // Exception table:
    //   from	to	target	type
    //   14	21	28	finally
    //   21	25	37	java/io/IOException
    //   30	34	42	java/io/IOException
  }
  
  private void renderToCanvas$71a225af(Canvas paramCanvas, Colour paramColour1, Colour paramColour2)
  {
    SVGAndroidRenderer localSVGAndroidRenderer = new SVGAndroidRenderer(paramCanvas, new Box(0.0F, 0.0F, paramCanvas.getWidth(), paramCanvas.getHeight()), this.renderDPI);
    localSVGAndroidRenderer.fill = paramColour1;
    localSVGAndroidRenderer.stroke = paramColour2;
    localSVGAndroidRenderer.renderDocument$673e8f3d(this);
  }
  
  public final float getDocumentHeight()
  {
    if (this.rootElement == null) {
      throw new IllegalArgumentException("SVG document is empty");
    }
    return getDocumentDimensions(this.renderDPI).height;
  }
  
  public final float getDocumentWidth()
  {
    if (this.rootElement == null) {
      throw new IllegalArgumentException("SVG document is empty");
    }
    return getDocumentDimensions(this.renderDPI).width;
  }
  
  public final void renderToCanvas(Canvas paramCanvas)
  {
    renderToCanvas$71a225af(paramCanvas, null, null);
  }
  
  public final void renderToCanvas(Canvas paramCanvas, Colour paramColour1, Colour paramColour2)
  {
    renderToCanvas$71a225af(paramCanvas, paramColour1, paramColour2);
  }
  
  protected final SvgObject resolveIRI(String paramString)
  {
    if (paramString == null) {}
    String str;
    do
    {
      do
      {
        return null;
      } while ((paramString.length() <= 1) || (!paramString.startsWith("#")));
      str = paramString.substring(1);
    } while ((str == null) || (str.length() == 0));
    if (str.equals(this.rootElement.id)) {
      return this.rootElement;
    }
    if (this.idToElementMap.containsKey(str)) {
      return (SvgObject)this.idToElementMap.get(str);
    }
    SvgElementBase localSvgElementBase = getElementById(this.rootElement, str);
    this.idToElementMap.put(str, localSvgElementBase);
    return localSvgElementBase;
  }
  
  protected static final class Box
    implements Cloneable
  {
    public float height;
    public float minX;
    public float minY;
    public float width;
    
    public Box(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.minX = paramFloat1;
      this.minY = paramFloat2;
      this.width = paramFloat3;
      this.height = paramFloat4;
    }
    
    public static Box fromLimits(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      return new Box(paramFloat1, paramFloat2, paramFloat3 - paramFloat1, paramFloat4 - paramFloat2);
    }
    
    public final float maxX()
    {
      return this.minX + this.width;
    }
    
    public final float maxY()
    {
      return this.minY + this.height;
    }
    
    public final String toString()
    {
      return "[" + this.minX + " " + this.minY + " " + this.width + " " + this.height + "]";
    }
  }
  
  protected static final class CSSClipRect
  {
    public SVG.Length bottom;
    public SVG.Length left;
    public SVG.Length right;
    public SVG.Length top;
    
    public CSSClipRect(SVG.Length paramLength1, SVG.Length paramLength2, SVG.Length paramLength3, SVG.Length paramLength4)
    {
      this.top = paramLength1;
      this.right = paramLength2;
      this.bottom = paramLength3;
      this.left = paramLength4;
    }
  }
  
  protected static final class Circle
    extends SVG.GraphicsElement
  {
    public SVG.Length cx;
    public SVG.Length cy;
    public SVG.Length r;
  }
  
  protected static final class ClipPath
    extends SVG.Group
    implements SVG.NotDirectlyRendered
  {
    public Boolean clipPathUnitsAreUser;
  }
  
  protected static final class Colour
    extends SVG.SvgPaint
  {
    public static final Colour BLACK = new Colour(0);
    public int colour;
    
    public Colour(int paramInt)
    {
      this.colour = paramInt;
    }
    
    public final String toString()
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.colour);
      return String.format("#%06x", arrayOfObject);
    }
  }
  
  protected static final class CurrentColor
    extends SVG.SvgPaint
  {
    private static CurrentColor instance = new CurrentColor();
    
    public static CurrentColor getInstance()
    {
      return instance;
    }
  }
  
  protected static final class Defs
    extends SVG.Group
    implements SVG.NotDirectlyRendered
  {}
  
  protected static final class Ellipse
    extends SVG.GraphicsElement
  {
    public SVG.Length cx;
    public SVG.Length cy;
    public SVG.Length rx;
    public SVG.Length ry;
  }
  
  protected static class GradientElement
    extends SVG.SvgElementBase
    implements SVG.SvgContainer
  {
    public List<SVG.SvgObject> children = new ArrayList();
    public Matrix gradientTransform;
    public Boolean gradientUnitsAreUser;
    public String href;
    public SVG.GradientSpread spreadMethod;
    
    public final void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {
      if ((paramSvgObject instanceof SVG.Stop))
      {
        this.children.add(paramSvgObject);
        return;
      }
      throw new SAXException("Gradient elements cannot contain " + paramSvgObject + " elements.");
    }
    
    public final List<SVG.SvgObject> getChildren()
    {
      return this.children;
    }
  }
  
  protected static enum GradientSpread
  {
    static
    {
      GradientSpread[] arrayOfGradientSpread = new GradientSpread[3];
      arrayOfGradientSpread[0] = pad;
      arrayOfGradientSpread[1] = reflect;
      arrayOfGradientSpread[2] = repeat;
      $VALUES = arrayOfGradientSpread;
    }
    
    private GradientSpread() {}
  }
  
  protected static abstract class GraphicsElement
    extends SVG.SvgConditionalElement
    implements SVG.HasTransform
  {
    public Matrix transform;
    
    public final void setTransform(Matrix paramMatrix)
    {
      this.transform = paramMatrix;
    }
  }
  
  protected static class Group
    extends SVG.SvgConditionalContainer
    implements SVG.HasTransform
  {
    public Matrix transform;
    
    public final void setTransform(Matrix paramMatrix)
    {
      this.transform = paramMatrix;
    }
  }
  
  protected static abstract interface HasTransform
  {
    public abstract void setTransform(Matrix paramMatrix);
  }
  
  protected static final class Image
    extends SVG.SvgPreserveAspectRatioContainer
    implements SVG.HasTransform
  {
    public SVG.Length height;
    public String href;
    public Matrix transform;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
    
    public final void setTransform(Matrix paramMatrix)
    {
      this.transform = paramMatrix;
    }
  }
  
  protected static final class Length
    implements Cloneable
  {
    SVG.Unit unit = SVG.Unit.px;
    float value = 0.0F;
    
    public Length(float paramFloat)
    {
      this.value = paramFloat;
      this.unit = SVG.Unit.px;
    }
    
    public Length(float paramFloat, SVG.Unit paramUnit)
    {
      this.value = paramFloat;
      this.unit = paramUnit;
    }
    
    public final float floatValue(float paramFloat)
    {
      switch (SVG.1.$SwitchMap$com$caverock$androidsvg$SVG$Unit[this.unit.ordinal()])
      {
      case 2: 
      case 3: 
      default: 
        return this.value;
      case 1: 
        return this.value;
      case 4: 
        return paramFloat * this.value;
      case 5: 
        return paramFloat * this.value / 2.54F;
      case 6: 
        return paramFloat * this.value / 25.4F;
      case 7: 
        return paramFloat * this.value / 72.0F;
      }
      return paramFloat * this.value / 6.0F;
    }
    
    public final float floatValue(SVGAndroidRenderer paramSVGAndroidRenderer)
    {
      if (this.unit == SVG.Unit.percent)
      {
        SVG.Box localBox = paramSVGAndroidRenderer.getCurrentViewPortInUserUnits();
        if (localBox == null) {
          return this.value;
        }
        float f1 = localBox.width;
        float f2 = localBox.height;
        if (f1 == f2) {
          return f1 * this.value / 100.0F;
        }
        return (float)(Math.sqrt(f1 * f1 + f2 * f2) / 1.414213562373095D) * this.value / 100.0F;
      }
      return floatValueX(paramSVGAndroidRenderer);
    }
    
    public final float floatValue(SVGAndroidRenderer paramSVGAndroidRenderer, float paramFloat)
    {
      if (this.unit == SVG.Unit.percent) {
        return paramFloat * this.value / 100.0F;
      }
      return floatValueX(paramSVGAndroidRenderer);
    }
    
    public final float floatValueX(SVGAndroidRenderer paramSVGAndroidRenderer)
    {
      switch (SVG.1.$SwitchMap$com$caverock$androidsvg$SVG$Unit[this.unit.ordinal()])
      {
      default: 
        return this.value;
      case 1: 
        return this.value;
      case 2: 
        return this.value * paramSVGAndroidRenderer.getCurrentFontSize();
      case 3: 
        return this.value * (paramSVGAndroidRenderer.state.fillPaint.getTextSize() / 2.0F);
      case 4: 
        return this.value * paramSVGAndroidRenderer.dpi;
      case 5: 
        return this.value * paramSVGAndroidRenderer.dpi / 2.54F;
      case 6: 
        return this.value * paramSVGAndroidRenderer.dpi / 25.4F;
      case 7: 
        return this.value * paramSVGAndroidRenderer.dpi / 72.0F;
      case 8: 
        return this.value * paramSVGAndroidRenderer.dpi / 6.0F;
      }
      SVG.Box localBox = paramSVGAndroidRenderer.getCurrentViewPortInUserUnits();
      if (localBox == null) {
        return this.value;
      }
      return this.value * localBox.width / 100.0F;
    }
    
    public final float floatValueY(SVGAndroidRenderer paramSVGAndroidRenderer)
    {
      if (this.unit == SVG.Unit.percent)
      {
        SVG.Box localBox = paramSVGAndroidRenderer.getCurrentViewPortInUserUnits();
        if (localBox == null) {
          return this.value;
        }
        return this.value * localBox.height / 100.0F;
      }
      return floatValueX(paramSVGAndroidRenderer);
    }
    
    public final boolean isNegative()
    {
      return this.value < 0.0F;
    }
    
    public final boolean isZero()
    {
      return this.value == 0.0F;
    }
    
    public final String toString()
    {
      return String.valueOf(this.value) + this.unit;
    }
  }
  
  protected static final class Line
    extends SVG.GraphicsElement
  {
    public SVG.Length x1;
    public SVG.Length x2;
    public SVG.Length y1;
    public SVG.Length y2;
  }
  
  protected static final class Marker
    extends SVG.SvgViewBoxContainer
    implements SVG.NotDirectlyRendered
  {
    public SVG.Length markerHeight;
    public boolean markerUnitsAreUser;
    public SVG.Length markerWidth;
    public Float orient;
    public SVG.Length refX;
    public SVG.Length refY;
  }
  
  protected static final class Mask
    extends SVG.SvgConditionalContainer
    implements SVG.NotDirectlyRendered
  {
    public SVG.Length height;
    public Boolean maskContentUnitsAreUser;
    public Boolean maskUnitsAreUser;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }
  
  protected static abstract interface NotDirectlyRendered {}
  
  protected static final class PaintReference
    extends SVG.SvgPaint
  {
    public SVG.SvgPaint fallback;
    public String href;
    
    public PaintReference(String paramString, SVG.SvgPaint paramSvgPaint)
    {
      this.href = paramString;
      this.fallback = paramSvgPaint;
    }
    
    public final String toString()
    {
      return this.href + " " + this.fallback;
    }
  }
  
  protected static final class Path
    extends SVG.GraphicsElement
  {
    public SVG.PathDefinition d;
    public Float pathLength;
  }
  
  protected static final class PathDefinition
    implements SVG.PathInterface
  {
    private byte[] commands = null;
    int commandsLength = 0;
    private float[] coords = null;
    private int coordsLength = 0;
    
    private void coordsEnsure(int paramInt)
    {
      if (this.coords.length < paramInt + this.coordsLength)
      {
        float[] arrayOfFloat = new float[2 * this.coords.length];
        System.arraycopy(this.coords, 0, arrayOfFloat, 0, this.coords.length);
        this.coords = arrayOfFloat;
      }
    }
    
    final void addCommand(byte paramByte)
    {
      if (this.commandsLength == this.commands.length)
      {
        byte[] arrayOfByte2 = new byte[2 * this.commands.length];
        System.arraycopy(this.commands, 0, arrayOfByte2, 0, this.commands.length);
        this.commands = arrayOfByte2;
      }
      byte[] arrayOfByte1 = this.commands;
      int i = this.commandsLength;
      this.commandsLength = (i + 1);
      arrayOfByte1[i] = paramByte;
    }
    
    public final void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5)
    {
      if (paramBoolean1) {}
      for (int i = 2;; i = 0)
      {
        int j = i | 0x4;
        int k = 0;
        if (paramBoolean2) {
          k = 1;
        }
        addCommand((byte)(j | k));
        coordsEnsure(5);
        float[] arrayOfFloat1 = this.coords;
        int m = this.coordsLength;
        this.coordsLength = (m + 1);
        arrayOfFloat1[m] = paramFloat1;
        float[] arrayOfFloat2 = this.coords;
        int n = this.coordsLength;
        this.coordsLength = (n + 1);
        arrayOfFloat2[n] = paramFloat2;
        float[] arrayOfFloat3 = this.coords;
        int i1 = this.coordsLength;
        this.coordsLength = (i1 + 1);
        arrayOfFloat3[i1] = paramFloat3;
        float[] arrayOfFloat4 = this.coords;
        int i2 = this.coordsLength;
        this.coordsLength = (i2 + 1);
        arrayOfFloat4[i2] = paramFloat4;
        float[] arrayOfFloat5 = this.coords;
        int i3 = this.coordsLength;
        this.coordsLength = (i3 + 1);
        arrayOfFloat5[i3] = paramFloat5;
        return;
      }
    }
    
    public final void close()
    {
      addCommand((byte)8);
    }
    
    public final void cubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
    {
      addCommand((byte)2);
      coordsEnsure(6);
      float[] arrayOfFloat1 = this.coords;
      int i = this.coordsLength;
      this.coordsLength = (i + 1);
      arrayOfFloat1[i] = paramFloat1;
      float[] arrayOfFloat2 = this.coords;
      int j = this.coordsLength;
      this.coordsLength = (j + 1);
      arrayOfFloat2[j] = paramFloat2;
      float[] arrayOfFloat3 = this.coords;
      int k = this.coordsLength;
      this.coordsLength = (k + 1);
      arrayOfFloat3[k] = paramFloat3;
      float[] arrayOfFloat4 = this.coords;
      int m = this.coordsLength;
      this.coordsLength = (m + 1);
      arrayOfFloat4[m] = paramFloat4;
      float[] arrayOfFloat5 = this.coords;
      int n = this.coordsLength;
      this.coordsLength = (n + 1);
      arrayOfFloat5[n] = paramFloat5;
      float[] arrayOfFloat6 = this.coords;
      int i1 = this.coordsLength;
      this.coordsLength = (i1 + 1);
      arrayOfFloat6[i1] = paramFloat6;
    }
    
    public final void enumeratePath(SVG.PathInterface paramPathInterface)
    {
      int i = 0;
      int j = 0;
      if (j < this.commandsLength)
      {
        int k = this.commands[j];
        boolean bool1;
        switch (k)
        {
        case 4: 
        case 5: 
        case 6: 
        case 7: 
        default: 
          if ((k & 0x2) != 0)
          {
            bool1 = true;
            label82:
            if ((k & 0x1) == 0) {
              break label527;
            }
          }
          break;
        }
        label527:
        for (boolean bool2 = true;; bool2 = false)
        {
          float[] arrayOfFloat15 = this.coords;
          int i9 = i + 1;
          float f11 = arrayOfFloat15[i];
          float[] arrayOfFloat16 = this.coords;
          int i10 = i9 + 1;
          float f12 = arrayOfFloat16[i9];
          float[] arrayOfFloat17 = this.coords;
          int i11 = i10 + 1;
          float f13 = arrayOfFloat17[i10];
          float[] arrayOfFloat18 = this.coords;
          int i12 = i11 + 1;
          float f14 = arrayOfFloat18[i11];
          float[] arrayOfFloat19 = this.coords;
          int i13 = i12 + 1;
          paramPathInterface.arcTo(f11, f12, f13, bool1, bool2, f14, arrayOfFloat19[i12]);
          i = i13;
          for (;;)
          {
            j++;
            break;
            float[] arrayOfFloat13 = this.coords;
            int i8 = i + 1;
            float f10 = arrayOfFloat13[i];
            float[] arrayOfFloat14 = this.coords;
            i = i8 + 1;
            paramPathInterface.moveTo(f10, arrayOfFloat14[i8]);
            continue;
            float[] arrayOfFloat11 = this.coords;
            int i7 = i + 1;
            float f9 = arrayOfFloat11[i];
            float[] arrayOfFloat12 = this.coords;
            i = i7 + 1;
            paramPathInterface.lineTo(f9, arrayOfFloat12[i7]);
            continue;
            float[] arrayOfFloat5 = this.coords;
            int i2 = i + 1;
            float f4 = arrayOfFloat5[i];
            float[] arrayOfFloat6 = this.coords;
            int i3 = i2 + 1;
            float f5 = arrayOfFloat6[i2];
            float[] arrayOfFloat7 = this.coords;
            int i4 = i3 + 1;
            float f6 = arrayOfFloat7[i3];
            float[] arrayOfFloat8 = this.coords;
            int i5 = i4 + 1;
            float f7 = arrayOfFloat8[i4];
            float[] arrayOfFloat9 = this.coords;
            int i6 = i5 + 1;
            float f8 = arrayOfFloat9[i5];
            float[] arrayOfFloat10 = this.coords;
            i = i6 + 1;
            paramPathInterface.cubicTo(f4, f5, f6, f7, f8, arrayOfFloat10[i6]);
            continue;
            float[] arrayOfFloat1 = this.coords;
            int m = i + 1;
            float f1 = arrayOfFloat1[i];
            float[] arrayOfFloat2 = this.coords;
            int n = m + 1;
            float f2 = arrayOfFloat2[m];
            float[] arrayOfFloat3 = this.coords;
            int i1 = n + 1;
            float f3 = arrayOfFloat3[n];
            float[] arrayOfFloat4 = this.coords;
            i = i1 + 1;
            paramPathInterface.quadTo(f1, f2, f3, arrayOfFloat4[i1]);
            continue;
            paramPathInterface.close();
          }
          bool1 = false;
          break label82;
        }
      }
    }
    
    public final void lineTo(float paramFloat1, float paramFloat2)
    {
      addCommand((byte)1);
      coordsEnsure(2);
      float[] arrayOfFloat1 = this.coords;
      int i = this.coordsLength;
      this.coordsLength = (i + 1);
      arrayOfFloat1[i] = paramFloat1;
      float[] arrayOfFloat2 = this.coords;
      int j = this.coordsLength;
      this.coordsLength = (j + 1);
      arrayOfFloat2[j] = paramFloat2;
    }
    
    public final void moveTo(float paramFloat1, float paramFloat2)
    {
      addCommand((byte)0);
      coordsEnsure(2);
      float[] arrayOfFloat1 = this.coords;
      int i = this.coordsLength;
      this.coordsLength = (i + 1);
      arrayOfFloat1[i] = paramFloat1;
      float[] arrayOfFloat2 = this.coords;
      int j = this.coordsLength;
      this.coordsLength = (j + 1);
      arrayOfFloat2[j] = paramFloat2;
    }
    
    public final void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      addCommand((byte)3);
      coordsEnsure(4);
      float[] arrayOfFloat1 = this.coords;
      int i = this.coordsLength;
      this.coordsLength = (i + 1);
      arrayOfFloat1[i] = paramFloat1;
      float[] arrayOfFloat2 = this.coords;
      int j = this.coordsLength;
      this.coordsLength = (j + 1);
      arrayOfFloat2[j] = paramFloat2;
      float[] arrayOfFloat3 = this.coords;
      int k = this.coordsLength;
      this.coordsLength = (k + 1);
      arrayOfFloat3[k] = paramFloat3;
      float[] arrayOfFloat4 = this.coords;
      int m = this.coordsLength;
      this.coordsLength = (m + 1);
      arrayOfFloat4[m] = paramFloat4;
    }
  }
  
  protected static abstract interface PathInterface
  {
    public abstract void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5);
    
    public abstract void close();
    
    public abstract void cubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6);
    
    public abstract void lineTo(float paramFloat1, float paramFloat2);
    
    public abstract void moveTo(float paramFloat1, float paramFloat2);
    
    public abstract void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  }
  
  protected static final class Pattern
    extends SVG.SvgViewBoxContainer
    implements SVG.NotDirectlyRendered
  {
    public SVG.Length height;
    public String href;
    public Boolean patternContentUnitsAreUser;
    public Matrix patternTransform;
    public Boolean patternUnitsAreUser;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }
  
  protected static class PolyLine
    extends SVG.GraphicsElement
  {
    public float[] points;
  }
  
  protected static final class Polygon
    extends SVG.PolyLine
  {}
  
  protected static final class Rect
    extends SVG.GraphicsElement
  {
    public SVG.Length height;
    public SVG.Length rx;
    public SVG.Length ry;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }
  
  protected static final class SolidColor
    extends SVG.SvgElementBase
    implements SVG.SvgContainer
  {
    public final void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {}
    
    public final List<SVG.SvgObject> getChildren()
    {
      return Collections.emptyList();
    }
  }
  
  protected static final class Stop
    extends SVG.SvgElementBase
    implements SVG.SvgContainer
  {
    public Float offset;
    
    public final void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {}
    
    public final List<SVG.SvgObject> getChildren()
    {
      return Collections.emptyList();
    }
  }
  
  protected static final class Style
    implements Cloneable
  {
    public SVG.CSSClipRect clip;
    public String clipPath;
    public FillRule clipRule;
    public SVG.Colour color;
    public TextDirection direction;
    public Boolean display;
    public SVG.SvgPaint fill;
    public Float fillOpacity;
    public FillRule fillRule;
    public List<String> fontFamily;
    public SVG.Length fontSize;
    public FontStyle fontStyle;
    public Integer fontWeight;
    public String markerEnd;
    public String markerMid;
    public String markerStart;
    public String mask;
    public Float opacity;
    public Boolean overflow;
    public SVG.SvgPaint solidColor;
    public Float solidOpacity;
    public long specifiedFlags = 0L;
    public SVG.SvgPaint stopColor;
    public Float stopOpacity;
    public SVG.SvgPaint stroke;
    public SVG.Length[] strokeDashArray;
    public SVG.Length strokeDashOffset;
    public LineCaps strokeLineCap;
    public LineJoin strokeLineJoin;
    public Float strokeMiterLimit;
    public Float strokeOpacity;
    public SVG.Length strokeWidth;
    public TextAnchor textAnchor;
    public TextDecoration textDecoration;
    public VectorEffect vectorEffect;
    public SVG.SvgPaint viewportFill;
    public Float viewportFillOpacity;
    public Boolean visibility;
    
    public static Style getDefaultStyle()
    {
      Style localStyle = new Style();
      localStyle.specifiedFlags = -1L;
      localStyle.fill = SVG.Colour.BLACK;
      localStyle.fillRule = FillRule.NonZero;
      localStyle.fillOpacity = Float.valueOf(1.0F);
      localStyle.stroke = null;
      localStyle.strokeOpacity = Float.valueOf(1.0F);
      localStyle.strokeWidth = new SVG.Length(1.0F);
      localStyle.strokeLineCap = LineCaps.Butt;
      localStyle.strokeLineJoin = LineJoin.Miter;
      localStyle.strokeMiterLimit = Float.valueOf(4.0F);
      localStyle.strokeDashArray = null;
      localStyle.strokeDashOffset = new SVG.Length(0.0F);
      localStyle.opacity = Float.valueOf(1.0F);
      localStyle.color = SVG.Colour.BLACK;
      localStyle.fontFamily = null;
      localStyle.fontSize = new SVG.Length(12.0F, SVG.Unit.pt);
      localStyle.fontWeight = Integer.valueOf(400);
      localStyle.fontStyle = FontStyle.Normal;
      localStyle.textDecoration = TextDecoration.None;
      localStyle.direction = TextDirection.LTR;
      localStyle.textAnchor = TextAnchor.Start;
      localStyle.overflow = Boolean.valueOf(true);
      localStyle.clip = null;
      localStyle.markerStart = null;
      localStyle.markerMid = null;
      localStyle.markerEnd = null;
      localStyle.display = Boolean.TRUE;
      localStyle.visibility = Boolean.TRUE;
      localStyle.stopColor = SVG.Colour.BLACK;
      localStyle.stopOpacity = Float.valueOf(1.0F);
      localStyle.clipPath = null;
      localStyle.clipRule = FillRule.NonZero;
      localStyle.mask = null;
      localStyle.solidColor = null;
      localStyle.solidOpacity = Float.valueOf(1.0F);
      localStyle.viewportFill = null;
      localStyle.viewportFillOpacity = Float.valueOf(1.0F);
      localStyle.vectorEffect = VectorEffect.None;
      return localStyle;
    }
    
    protected final Object clone()
    {
      try
      {
        Style localStyle = (Style)super.clone();
        if (this.strokeDashArray != null) {
          localStyle.strokeDashArray = ((SVG.Length[])this.strokeDashArray.clone());
        }
        return localStyle;
      }
      catch (CloneNotSupportedException localCloneNotSupportedException)
      {
        throw new InternalError(localCloneNotSupportedException.toString());
      }
    }
    
    public static enum FillRule
    {
      static
      {
        EvenOdd = new FillRule("EvenOdd", 1);
        FillRule[] arrayOfFillRule = new FillRule[2];
        arrayOfFillRule[0] = NonZero;
        arrayOfFillRule[1] = EvenOdd;
        $VALUES = arrayOfFillRule;
      }
      
      private FillRule() {}
    }
    
    public static enum FontStyle
    {
      static
      {
        Italic = new FontStyle("Italic", 1);
        Oblique = new FontStyle("Oblique", 2);
        FontStyle[] arrayOfFontStyle = new FontStyle[3];
        arrayOfFontStyle[0] = Normal;
        arrayOfFontStyle[1] = Italic;
        arrayOfFontStyle[2] = Oblique;
        $VALUES = arrayOfFontStyle;
      }
      
      private FontStyle() {}
    }
    
    public static enum LineCaps
    {
      static
      {
        LineCaps[] arrayOfLineCaps = new LineCaps[3];
        arrayOfLineCaps[0] = Butt;
        arrayOfLineCaps[1] = Round;
        arrayOfLineCaps[2] = Square;
        $VALUES = arrayOfLineCaps;
      }
      
      private LineCaps() {}
    }
    
    public static enum LineJoin
    {
      static
      {
        Bevel = new LineJoin("Bevel", 2);
        LineJoin[] arrayOfLineJoin = new LineJoin[3];
        arrayOfLineJoin[0] = Miter;
        arrayOfLineJoin[1] = Round;
        arrayOfLineJoin[2] = Bevel;
        $VALUES = arrayOfLineJoin;
      }
      
      private LineJoin() {}
    }
    
    public static enum TextAnchor
    {
      static
      {
        Middle = new TextAnchor("Middle", 1);
        End = new TextAnchor("End", 2);
        TextAnchor[] arrayOfTextAnchor = new TextAnchor[3];
        arrayOfTextAnchor[0] = Start;
        arrayOfTextAnchor[1] = Middle;
        arrayOfTextAnchor[2] = End;
        $VALUES = arrayOfTextAnchor;
      }
      
      private TextAnchor() {}
    }
    
    public static enum TextDecoration
    {
      static
      {
        Overline = new TextDecoration("Overline", 2);
        LineThrough = new TextDecoration("LineThrough", 3);
        Blink = new TextDecoration("Blink", 4);
        TextDecoration[] arrayOfTextDecoration = new TextDecoration[5];
        arrayOfTextDecoration[0] = None;
        arrayOfTextDecoration[1] = Underline;
        arrayOfTextDecoration[2] = Overline;
        arrayOfTextDecoration[3] = LineThrough;
        arrayOfTextDecoration[4] = Blink;
        $VALUES = arrayOfTextDecoration;
      }
      
      private TextDecoration() {}
    }
    
    public static enum TextDirection
    {
      static
      {
        TextDirection[] arrayOfTextDirection = new TextDirection[2];
        arrayOfTextDirection[0] = LTR;
        arrayOfTextDirection[1] = RTL;
        $VALUES = arrayOfTextDirection;
      }
      
      private TextDirection() {}
    }
    
    public static enum VectorEffect
    {
      static
      {
        NonScalingStroke = new VectorEffect("NonScalingStroke", 1);
        VectorEffect[] arrayOfVectorEffect = new VectorEffect[2];
        arrayOfVectorEffect[0] = None;
        arrayOfVectorEffect[1] = NonScalingStroke;
        $VALUES = arrayOfVectorEffect;
      }
      
      private VectorEffect() {}
    }
  }
  
  protected static final class Svg
    extends SVG.SvgViewBoxContainer
  {
    public SVG.Length height;
    public String version;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }
  
  protected static abstract interface SvgConditional
  {
    public abstract String getRequiredExtensions();
    
    public abstract Set<String> getRequiredFeatures();
    
    public abstract Set<String> getRequiredFonts();
    
    public abstract Set<String> getRequiredFormats();
    
    public abstract Set<String> getSystemLanguage();
    
    public abstract void setRequiredExtensions(String paramString);
    
    public abstract void setRequiredFeatures(Set<String> paramSet);
    
    public abstract void setRequiredFonts(Set<String> paramSet);
    
    public abstract void setRequiredFormats(Set<String> paramSet);
    
    public abstract void setSystemLanguage(Set<String> paramSet);
  }
  
  protected static class SvgConditionalContainer
    extends SVG.SvgElement
    implements SVG.SvgConditional, SVG.SvgContainer
  {
    public List<SVG.SvgObject> children = new ArrayList();
    public String requiredExtensions = null;
    public Set<String> requiredFeatures = null;
    public Set<String> requiredFonts = null;
    public Set<String> requiredFormats = null;
    public Set<String> systemLanguage = null;
    
    public void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {
      this.children.add(paramSvgObject);
    }
    
    public final List<SVG.SvgObject> getChildren()
    {
      return this.children;
    }
    
    public final String getRequiredExtensions()
    {
      return this.requiredExtensions;
    }
    
    public final Set<String> getRequiredFeatures()
    {
      return this.requiredFeatures;
    }
    
    public final Set<String> getRequiredFonts()
    {
      return this.requiredFonts;
    }
    
    public final Set<String> getRequiredFormats()
    {
      return this.requiredFormats;
    }
    
    public final Set<String> getSystemLanguage()
    {
      return null;
    }
    
    public final void setRequiredExtensions(String paramString)
    {
      this.requiredExtensions = paramString;
    }
    
    public final void setRequiredFeatures(Set<String> paramSet)
    {
      this.requiredFeatures = paramSet;
    }
    
    public final void setRequiredFonts(Set<String> paramSet)
    {
      this.requiredFonts = paramSet;
    }
    
    public final void setRequiredFormats(Set<String> paramSet)
    {
      this.requiredFormats = paramSet;
    }
    
    public final void setSystemLanguage(Set<String> paramSet)
    {
      this.systemLanguage = paramSet;
    }
  }
  
  protected static class SvgConditionalElement
    extends SVG.SvgElement
    implements SVG.SvgConditional
  {
    public String requiredExtensions = null;
    public Set<String> requiredFeatures = null;
    public Set<String> requiredFonts = null;
    public Set<String> requiredFormats = null;
    public Set<String> systemLanguage = null;
    
    public final String getRequiredExtensions()
    {
      return this.requiredExtensions;
    }
    
    public final Set<String> getRequiredFeatures()
    {
      return this.requiredFeatures;
    }
    
    public final Set<String> getRequiredFonts()
    {
      return this.requiredFonts;
    }
    
    public final Set<String> getRequiredFormats()
    {
      return this.requiredFormats;
    }
    
    public final Set<String> getSystemLanguage()
    {
      return this.systemLanguage;
    }
    
    public final void setRequiredExtensions(String paramString)
    {
      this.requiredExtensions = paramString;
    }
    
    public final void setRequiredFeatures(Set<String> paramSet)
    {
      this.requiredFeatures = paramSet;
    }
    
    public final void setRequiredFonts(Set<String> paramSet)
    {
      this.requiredFonts = paramSet;
    }
    
    public final void setRequiredFormats(Set<String> paramSet)
    {
      this.requiredFormats = paramSet;
    }
    
    public final void setSystemLanguage(Set<String> paramSet)
    {
      this.systemLanguage = paramSet;
    }
  }
  
  protected static abstract interface SvgContainer
  {
    public abstract void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException;
    
    public abstract List<SVG.SvgObject> getChildren();
  }
  
  protected static class SvgElement
    extends SVG.SvgElementBase
  {
    public SVG.Box boundingBox = null;
  }
  
  protected static class SvgElementBase
    extends SVG.SvgObject
  {
    public SVG.Style baseStyle = null;
    public List<String> classNames = null;
    public String id = null;
    public Boolean spacePreserve = null;
    public SVG.Style style = null;
  }
  
  protected static final class SvgLinearGradient
    extends SVG.GradientElement
  {
    public SVG.Length x1;
    public SVG.Length x2;
    public SVG.Length y1;
    public SVG.Length y2;
  }
  
  protected static class SvgObject
  {
    public SVG document;
    public SVG.SvgContainer parent;
    
    public String toString()
    {
      return getClass().getSimpleName();
    }
  }
  
  protected static abstract class SvgPaint
    implements Cloneable
  {}
  
  protected static class SvgPreserveAspectRatioContainer
    extends SVG.SvgConditionalContainer
  {
    public PreserveAspectRatio preserveAspectRatio = null;
  }
  
  protected static final class SvgRadialGradient
    extends SVG.GradientElement
  {
    public SVG.Length cx;
    public SVG.Length cy;
    public SVG.Length fx;
    public SVG.Length fy;
    public SVG.Length r;
  }
  
  protected static class SvgViewBoxContainer
    extends SVG.SvgPreserveAspectRatioContainer
  {
    public SVG.Box viewBox;
  }
  
  protected static final class Switch
    extends SVG.Group
  {}
  
  protected static final class Symbol
    extends SVG.SvgViewBoxContainer
    implements SVG.NotDirectlyRendered
  {}
  
  protected static final class TRef
    extends SVG.TextContainer
    implements SVG.TextChild
  {
    public String href;
    SVG.TextRoot textRoot;
    
    public final SVG.TextRoot getTextRoot()
    {
      return this.textRoot;
    }
  }
  
  protected static final class TSpan
    extends SVG.TextPositionedContainer
    implements SVG.TextChild
  {
    SVG.TextRoot textRoot;
    
    public final SVG.TextRoot getTextRoot()
    {
      return this.textRoot;
    }
  }
  
  protected static final class Text
    extends SVG.TextPositionedContainer
    implements SVG.HasTransform, SVG.TextRoot
  {
    public Matrix transform;
    
    public final void setTransform(Matrix paramMatrix)
    {
      this.transform = paramMatrix;
    }
  }
  
  protected static abstract interface TextChild
  {
    public abstract SVG.TextRoot getTextRoot();
  }
  
  protected static class TextContainer
    extends SVG.SvgConditionalContainer
  {
    public final void addChild(SVG.SvgObject paramSvgObject)
      throws SAXException
    {
      if ((paramSvgObject instanceof SVG.TextChild))
      {
        this.children.add(paramSvgObject);
        return;
      }
      throw new SAXException("Text content elements cannot contain " + paramSvgObject + " elements.");
    }
  }
  
  protected static final class TextPath
    extends SVG.TextContainer
    implements SVG.TextChild
  {
    public String href;
    public SVG.Length startOffset;
    SVG.TextRoot textRoot;
    
    public final SVG.TextRoot getTextRoot()
    {
      return this.textRoot;
    }
  }
  
  protected static class TextPositionedContainer
    extends SVG.TextContainer
  {
    public List<SVG.Length> dx;
    public List<SVG.Length> dy;
    public List<SVG.Length> x;
    public List<SVG.Length> y;
  }
  
  protected static abstract interface TextRoot {}
  
  protected static final class TextSequence
    extends SVG.SvgObject
    implements SVG.TextChild
  {
    public String text;
    private SVG.TextRoot textRoot;
    
    public TextSequence(String paramString)
    {
      this.text = paramString;
    }
    
    public final SVG.TextRoot getTextRoot()
    {
      return this.textRoot;
    }
    
    public final String toString()
    {
      return getClass().getSimpleName() + " '" + this.text + "'";
    }
  }
  
  protected static enum Unit
  {
    static
    {
      em = new Unit("em", 1);
      ex = new Unit("ex", 2);
      in = new Unit("in", 3);
      cm = new Unit("cm", 4);
      mm = new Unit("mm", 5);
      pt = new Unit("pt", 6);
      pc = new Unit("pc", 7);
      percent = new Unit("percent", 8);
      Unit[] arrayOfUnit = new Unit[9];
      arrayOfUnit[0] = px;
      arrayOfUnit[1] = em;
      arrayOfUnit[2] = ex;
      arrayOfUnit[3] = in;
      arrayOfUnit[4] = cm;
      arrayOfUnit[5] = mm;
      arrayOfUnit[6] = pt;
      arrayOfUnit[7] = pc;
      arrayOfUnit[8] = percent;
      $VALUES = arrayOfUnit;
    }
    
    private Unit() {}
  }
  
  protected static final class Use
    extends SVG.Group
  {
    public SVG.Length height;
    public String href;
    public SVG.Length width;
    public SVG.Length x;
    public SVG.Length y;
  }
  
  protected static final class View
    extends SVG.SvgViewBoxContainer
    implements SVG.NotDirectlyRendered
  {}
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SVG
 * JD-Core Version:    0.7.0.1
 */