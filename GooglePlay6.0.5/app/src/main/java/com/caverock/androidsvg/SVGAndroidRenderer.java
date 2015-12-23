package com.caverock.androidsvg;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.FillType;
import android.graphics.PathMeasure;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.util.Base64;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Stack;

public final class SVGAndroidRenderer
{
  private static HashSet<String> supportedFeatures = null;
  private Stack<Bitmap> bitmapStack;
  private Canvas canvas;
  private Stack<Canvas> canvasStack;
  private SVG.Box canvasViewPort;
  private boolean directRenderingMode;
  private SVG document;
  float dpi;
  SVG.Colour fill;
  private Stack<Matrix> matrixStack;
  private Stack<SVG.SvgContainer> parentStack;
  RendererState state;
  private Stack<RendererState> stateStack;
  SVG.Colour stroke;
  
  protected SVGAndroidRenderer(Canvas paramCanvas, SVG.Box paramBox, float paramFloat)
  {
    this.canvas = paramCanvas;
    this.dpi = paramFloat;
    this.canvasViewPort = paramBox;
  }
  
  private void addObjectToClip(SVG.SvgObject paramSvgObject, boolean paramBoolean, Path paramPath, Matrix paramMatrix)
  {
    if (!display()) {
      return;
    }
    this.canvas.save(1);
    this.stateStack.push(this.state);
    this.state = ((RendererState)this.state.clone());
    SVG.Use localUse;
    SVG.SvgObject localSvgObject;
    if ((paramSvgObject instanceof SVG.Use)) {
      if (paramBoolean)
      {
        localUse = (SVG.Use)paramSvgObject;
        updateStyleForElement(this.state, localUse);
        if ((display()) && (visible()))
        {
          if (localUse.transform != null) {
            paramMatrix.preConcat(localUse.transform);
          }
          localSvgObject = localUse.document.resolveIRI(localUse.href);
          if (localSvgObject != null) {
            break label167;
          }
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localUse.href;
          error("Use reference '%s' not found", arrayOfObject2);
        }
      }
    }
    for (;;)
    {
      this.canvas.restore();
      this.state = ((RendererState)this.stateStack.pop());
      return;
      label167:
      checkForClipPath(localUse);
      addObjectToClip(localSvgObject, false, paramPath, paramMatrix);
      continue;
      error("<use> elements inside a <clipPath> cannot reference another <use>", new Object[0]);
      continue;
      if ((paramSvgObject instanceof SVG.Path))
      {
        SVG.Path localPath = (SVG.Path)paramSvgObject;
        updateStyleForElement(this.state, localPath);
        if ((display()) && (visible()))
        {
          if (localPath.transform != null) {
            paramMatrix.preConcat(localPath.transform);
          }
          Path localPath3 = new PathConverter(localPath.d).path;
          if (localPath.boundingBox == null) {
            localPath.boundingBox = calculatePathBounds(localPath3);
          }
          checkForClipPath(localPath);
          paramPath.setFillType(getClipRuleFromState());
          paramPath.addPath(localPath3, paramMatrix);
        }
      }
      else if ((paramSvgObject instanceof SVG.Text))
      {
        SVG.Text localText = (SVG.Text)paramSvgObject;
        updateStyleForElement(this.state, localText);
        if (display())
        {
          if (localText.transform != null) {
            paramMatrix.preConcat(localText.transform);
          }
          float f1;
          label388:
          float f2;
          label412:
          float f3;
          label436:
          float f4;
          label471:
          float f5;
          if ((localText.x == null) || (localText.x.size() == 0))
          {
            f1 = 0.0F;
            if ((localText.y != null) && (localText.y.size() != 0)) {
              break label677;
            }
            f2 = 0.0F;
            if ((localText.dx != null) && (localText.dx.size() != 0)) {
              break label700;
            }
            f3 = 0.0F;
            List localList = localText.dy;
            f4 = 0.0F;
            if (localList != null)
            {
              int i = localText.dy.size();
              f4 = 0.0F;
              if (i != 0) {
                break label723;
              }
            }
            if (this.state.style.textAnchor != SVG.Style.TextAnchor.Start)
            {
              f5 = calculateTextWidth(localText);
              if (this.state.style.textAnchor != SVG.Style.TextAnchor.Middle) {
                break label746;
              }
            }
          }
          label677:
          label700:
          label723:
          label746:
          for (f1 -= f5 / 2.0F;; f1 -= f5)
          {
            if (localText.boundingBox == null)
            {
              TextBoundsCalculator localTextBoundsCalculator = new TextBoundsCalculator(f1, f2);
              enumerateTextSpans(localText, localTextBoundsCalculator);
              localText.boundingBox = new SVG.Box(localTextBoundsCalculator.bbox.left, localTextBoundsCalculator.bbox.top, localTextBoundsCalculator.bbox.width(), localTextBoundsCalculator.bbox.height());
            }
            checkForClipPath(localText);
            Path localPath2 = new Path();
            enumerateTextSpans(localText, new PlainTextToPath(f1 + f3, f4 + f2, localPath2));
            paramPath.setFillType(getClipRuleFromState());
            paramPath.addPath(localPath2, paramMatrix);
            break;
            f1 = ((SVG.Length)localText.x.get(0)).floatValueX(this);
            break label388;
            f2 = ((SVG.Length)localText.y.get(0)).floatValueY(this);
            break label412;
            f3 = ((SVG.Length)localText.dx.get(0)).floatValueX(this);
            break label436;
            f4 = ((SVG.Length)localText.dy.get(0)).floatValueY(this);
            break label471;
          }
        }
      }
      else if ((paramSvgObject instanceof SVG.GraphicsElement))
      {
        SVG.GraphicsElement localGraphicsElement = (SVG.GraphicsElement)paramSvgObject;
        updateStyleForElement(this.state, localGraphicsElement);
        if ((display()) && (visible()))
        {
          if (localGraphicsElement.transform != null) {
            paramMatrix.preConcat(localGraphicsElement.transform);
          }
          Path localPath1;
          if ((localGraphicsElement instanceof SVG.Rect)) {
            localPath1 = makePathAndBoundingBox((SVG.Rect)localGraphicsElement);
          }
          for (;;)
          {
            checkForClipPath(localGraphicsElement);
            paramPath.setFillType(localPath1.getFillType());
            paramPath.addPath(localPath1, paramMatrix);
            break;
            if ((localGraphicsElement instanceof SVG.Circle))
            {
              localPath1 = makePathAndBoundingBox((SVG.Circle)localGraphicsElement);
            }
            else if ((localGraphicsElement instanceof SVG.Ellipse))
            {
              localPath1 = makePathAndBoundingBox((SVG.Ellipse)localGraphicsElement);
            }
            else
            {
              if (!(localGraphicsElement instanceof SVG.PolyLine)) {
                break;
              }
              localPath1 = makePathAndBoundingBox((SVG.PolyLine)localGraphicsElement);
            }
          }
        }
      }
      else
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = paramSvgObject.getClass().getSimpleName();
        error("Invalid %s element found in clipPath definition", arrayOfObject1);
      }
    }
  }
  
  private List<MarkerVector> calculateMarkerPositions(SVG.PolyLine paramPolyLine)
  {
    int i = paramPolyLine.points.length;
    Object localObject1;
    if (i < 2) {
      localObject1 = null;
    }
    float f1;
    float f2;
    Object localObject2;
    do
    {
      return localObject1;
      localObject1 = new ArrayList();
      MarkerVector localMarkerVector1 = new MarkerVector(paramPolyLine.points[0], paramPolyLine.points[1], 0.0F, 0.0F);
      f1 = 0.0F;
      f2 = 0.0F;
      int j = 2;
      MarkerVector localMarkerVector3;
      for (localObject2 = localMarkerVector1; j < i; localObject2 = localMarkerVector3)
      {
        f1 = paramPolyLine.points[j];
        f2 = paramPolyLine.points[(j + 1)];
        ((MarkerVector)localObject2).add(f1, f2);
        ((List)localObject1).add(localObject2);
        localMarkerVector3 = new MarkerVector(f1, f2, f1 - ((MarkerVector)localObject2).x, f2 - ((MarkerVector)localObject2).y);
        j += 2;
      }
      if (!(paramPolyLine instanceof SVG.Polygon)) {
        break;
      }
    } while ((f1 == paramPolyLine.points[0]) || (f2 == paramPolyLine.points[1]));
    float f3 = paramPolyLine.points[0];
    float f4 = paramPolyLine.points[1];
    ((MarkerVector)localObject2).add(f3, f4);
    ((List)localObject1).add(localObject2);
    MarkerVector localMarkerVector2 = new MarkerVector(f3, f4, f3 - ((MarkerVector)localObject2).x, f4 - ((MarkerVector)localObject2).y);
    localMarkerVector2.add((MarkerVector)((List)localObject1).get(0));
    ((List)localObject1).add(localMarkerVector2);
    ((List)localObject1).set(0, localMarkerVector2);
    return localObject1;
    ((List)localObject1).add(localObject2);
    return localObject1;
  }
  
  private static SVG.Box calculatePathBounds(Path paramPath)
  {
    RectF localRectF = new RectF();
    paramPath.computeBounds(localRectF, true);
    return new SVG.Box(localRectF.left, localRectF.top, localRectF.width(), localRectF.height());
  }
  
  private float calculateTextWidth(SVG.TextContainer paramTextContainer)
  {
    TextWidthCalculator localTextWidthCalculator = new TextWidthCalculator((byte)0);
    enumerateTextSpans(paramTextContainer, localTextWidthCalculator);
    return localTextWidthCalculator.x;
  }
  
  private static Matrix calculateViewBoxTransform(SVG.Box paramBox1, SVG.Box paramBox2, PreserveAspectRatio paramPreserveAspectRatio)
  {
    Matrix localMatrix = new Matrix();
    if ((paramPreserveAspectRatio == null) || (paramPreserveAspectRatio.alignment == null)) {
      return localMatrix;
    }
    float f1 = paramBox1.width / paramBox2.width;
    float f2 = paramBox1.height / paramBox2.height;
    float f3 = -paramBox2.minX;
    float f4 = -paramBox2.minY;
    if (paramPreserveAspectRatio.equals(PreserveAspectRatio.STRETCH))
    {
      localMatrix.preTranslate(paramBox1.minX, paramBox1.minY);
      localMatrix.preScale(f1, f2);
      localMatrix.preTranslate(f3, f4);
      return localMatrix;
    }
    float f5;
    float f6;
    float f7;
    if (paramPreserveAspectRatio.scale == PreserveAspectRatio.Scale.Slice)
    {
      f5 = Math.max(f1, f2);
      f6 = paramBox1.width / f5;
      f7 = paramBox1.height / f5;
      switch (1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[paramPreserveAspectRatio.alignment.ordinal()])
      {
      default: 
        label188:
        switch (1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[paramPreserveAspectRatio.alignment.ordinal()])
        {
        }
        break;
      }
    }
    for (;;)
    {
      localMatrix.preTranslate(paramBox1.minX, paramBox1.minY);
      localMatrix.preScale(f5, f5);
      localMatrix.preTranslate(f3, f4);
      return localMatrix;
      f5 = Math.min(f1, f2);
      break;
      f3 -= (paramBox2.width - f6) / 2.0F;
      break label188;
      f3 -= paramBox2.width - f6;
      break label188;
      f4 -= (paramBox2.height - f7) / 2.0F;
      continue;
      f4 -= paramBox2.height - f7;
    }
  }
  
  private void checkForClipPath(SVG.SvgElement paramSvgElement)
  {
    checkForClipPath(paramSvgElement, paramSvgElement.boundingBox);
  }
  
  private void checkForClipPath(SVG.SvgElement paramSvgElement, SVG.Box paramBox)
  {
    if (this.state.style.clipPath == null) {
      return;
    }
    SVG.SvgObject localSvgObject = paramSvgElement.document.resolveIRI(this.state.style.clipPath);
    if (localSvgObject == null)
    {
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.state.style.clipPath;
      error("ClipPath reference '%s' not found", arrayOfObject2);
      return;
    }
    SVG.ClipPath localClipPath = (SVG.ClipPath)localSvgObject;
    if (localClipPath.children.isEmpty())
    {
      this.canvas.clipRect(0, 0, 0, 0);
      return;
    }
    if ((localClipPath.clipPathUnitsAreUser == null) || (localClipPath.clipPathUnitsAreUser.booleanValue())) {}
    for (int i = 1; ((paramSvgElement instanceof SVG.Group)) && (i == 0); i = 0)
    {
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = paramSvgElement.getClass().getSimpleName();
      warn("<clipPath clipPathUnits=\"objectBoundingBox\"> is not supported when referenced from container elements (like %s)", arrayOfObject1);
      return;
    }
    this.stateStack.push(this.state);
    this.state = ((RendererState)this.state.clone());
    Matrix localMatrix1 = new Matrix();
    if (i == 0)
    {
      Matrix localMatrix2 = new Matrix();
      localMatrix2.preTranslate(paramBox.minX, paramBox.minY);
      localMatrix2.preScale(paramBox.width, paramBox.height);
      this.canvas.concat(localMatrix2);
      Matrix localMatrix3 = new Matrix();
      if (localMatrix2.invert(localMatrix3)) {
        localMatrix1.postConcat(localMatrix3);
      }
    }
    if (localClipPath.transform != null)
    {
      Matrix localMatrix4 = localClipPath.transform;
      this.canvas.concat(localMatrix4);
      Matrix localMatrix5 = new Matrix();
      if (localMatrix4.invert(localMatrix5)) {
        localMatrix1.postConcat(localMatrix5);
      }
    }
    this.state = findInheritFromAncestorState(localClipPath);
    checkForClipPath(localClipPath);
    Path localPath = new Path();
    Iterator localIterator = localClipPath.children.iterator();
    while (localIterator.hasNext()) {
      addObjectToClip((SVG.SvgObject)localIterator.next(), true, localPath, new Matrix());
    }
    this.canvas.clipPath(localPath);
    this.state = ((RendererState)this.stateStack.pop());
    this.canvas.concat(localMatrix1);
  }
  
  private void checkForGradiantsAndPatterns(SVG.SvgElement paramSvgElement)
  {
    if ((this.state.style.fill instanceof SVG.PaintReference)) {
      decodePaintReference(true, paramSvgElement.boundingBox, (SVG.PaintReference)this.state.style.fill);
    }
    if ((this.state.style.stroke instanceof SVG.PaintReference)) {
      decodePaintReference(false, paramSvgElement.boundingBox, (SVG.PaintReference)this.state.style.stroke);
    }
  }
  
  private static Typeface checkGenericFont(String paramString, Integer paramInteger, SVG.Style.FontStyle paramFontStyle)
  {
    int i;
    int j;
    label26:
    Typeface localTypeface;
    if (paramFontStyle == SVG.Style.FontStyle.Italic)
    {
      i = 1;
      if (paramInteger.intValue() <= 500) {
        break label60;
      }
      if (i == 0) {
        break label54;
      }
      j = 3;
      if (!paramString.equals("serif")) {
        break label76;
      }
      localTypeface = Typeface.create(Typeface.SERIF, j);
    }
    label54:
    label60:
    label76:
    boolean bool;
    do
    {
      return localTypeface;
      i = 0;
      break;
      j = 1;
      break label26;
      if (i != 0)
      {
        j = 2;
        break label26;
      }
      j = 0;
      break label26;
      if (paramString.equals("sans-serif")) {
        return Typeface.create(Typeface.SANS_SERIF, j);
      }
      if (paramString.equals("monospace")) {
        return Typeface.create(Typeface.MONOSPACE, j);
      }
      if (paramString.equals("cursive")) {
        return Typeface.create(Typeface.SANS_SERIF, j);
      }
      bool = paramString.equals("fantasy");
      localTypeface = null;
    } while (!bool);
    return Typeface.create(Typeface.SANS_SERIF, j);
  }
  
  private void checkXMLSpaceAttribute(SVG.SvgObject paramSvgObject)
  {
    if (!(paramSvgObject instanceof SVG.SvgElementBase)) {}
    SVG.SvgElementBase localSvgElementBase;
    do
    {
      return;
      localSvgElementBase = (SVG.SvgElementBase)paramSvgObject;
    } while (localSvgElementBase.spacePreserve == null);
    this.state.spacePreserve = localSvgElementBase.spacePreserve.booleanValue();
  }
  
  private static int clamp255(float paramFloat)
  {
    int i = (int)(256.0F * paramFloat);
    if (i < 0) {
      i = 0;
    }
    while (i <= 255) {
      return i;
    }
    return 255;
  }
  
  private void decodePaintReference(boolean paramBoolean, SVG.Box paramBox, SVG.PaintReference paramPaintReference)
  {
    SVG.SvgObject localSvgObject = this.document.resolveIRI(paramPaintReference.href);
    String str;
    if (localSvgObject == null)
    {
      Object[] arrayOfObject = new Object[2];
      if (paramBoolean)
      {
        str = "Fill";
        arrayOfObject[0] = str;
        arrayOfObject[1] = paramPaintReference.href;
        error("%s reference '%s' not found", arrayOfObject);
        if (paramPaintReference.fallback == null) {
          break label83;
        }
        setPaintColour(this.state, paramBoolean, paramPaintReference.fallback);
      }
    }
    label83:
    SVG.SvgLinearGradient localSvgLinearGradient;
    int i;
    label161:
    Paint localPaint;
    label174:
    SVG.Box localBox;
    float f1;
    label204:
    float f2;
    label223:
    float f3;
    label242:
    float f4;
    label261:
    Matrix localMatrix;
    int j;
    label369:
    do
    {
      return;
      str = "Stroke";
      break;
      if (paramBoolean)
      {
        this.state.hasFill = false;
        return;
      }
      this.state.hasStroke = false;
      return;
      if ((localSvgObject instanceof SVG.SvgLinearGradient))
      {
        localSvgLinearGradient = (SVG.SvgLinearGradient)localSvgObject;
        if (localSvgLinearGradient.href != null) {
          fillInChainedGradientFields(localSvgLinearGradient, localSvgLinearGradient.href);
        }
        if ((localSvgLinearGradient.gradientUnitsAreUser == null) || (!localSvgLinearGradient.gradientUnitsAreUser.booleanValue())) {
          break label531;
        }
        i = 1;
        if (!paramBoolean) {
          break label537;
        }
        localPaint = this.state.fillPaint;
        if (i == 0) {
          break label577;
        }
        localBox = getCurrentViewPortInUserUnits();
        if (localSvgLinearGradient.x1 == null) {
          break label549;
        }
        f1 = localSvgLinearGradient.x1.floatValueX(this);
        if (localSvgLinearGradient.y1 == null) {
          break label555;
        }
        f2 = localSvgLinearGradient.y1.floatValueY(this);
        if (localSvgLinearGradient.x2 == null) {
          break label561;
        }
        f3 = localSvgLinearGradient.x2.floatValueX(this);
        if (localSvgLinearGradient.y2 == null) {
          break label571;
        }
        f4 = localSvgLinearGradient.y2.floatValueY(this);
        statePush();
        this.state = findInheritFromAncestorState(localSvgLinearGradient);
        localMatrix = new Matrix();
        if (i == 0)
        {
          localMatrix.preTranslate(paramBox.minX, paramBox.minY);
          localMatrix.preScale(paramBox.width, paramBox.height);
        }
        if (localSvgLinearGradient.gradientTransform != null) {
          localMatrix.preConcat(localSvgLinearGradient.gradientTransform);
        }
        j = localSvgLinearGradient.children.size();
        if (j != 0) {
          break label695;
        }
        statePop();
        if (!paramBoolean) {
          break label684;
        }
        this.state.hasFill = false;
      }
      if ((localSvgObject instanceof SVG.SvgRadialGradient)) {
        makeRadialGradiant(paramBoolean, paramBox, (SVG.SvgRadialGradient)localSvgObject);
      }
    } while (!(localSvgObject instanceof SVG.SolidColor));
    SVG.SolidColor localSolidColor = (SVG.SolidColor)localSvgObject;
    if (paramBoolean)
    {
      RendererState localRendererState2;
      if (isSpecified(localSolidColor.baseStyle, 2147483648L))
      {
        this.state.style.fill = localSolidColor.baseStyle.solidColor;
        localRendererState2 = this.state;
        if (localSolidColor.baseStyle.solidColor == null) {
          break label1024;
        }
      }
      label1024:
      for (boolean bool2 = true;; bool2 = false)
      {
        localRendererState2.hasFill = bool2;
        if (isSpecified(localSolidColor.baseStyle, 4294967296L)) {
          this.state.style.fillOpacity = localSolidColor.baseStyle.solidOpacity;
        }
        if (!isSpecified(localSolidColor.baseStyle, 6442450944L)) {
          break;
        }
        setPaintColour(this.state, paramBoolean, this.state.style.fill);
        return;
        label531:
        i = 0;
        break label161;
        label537:
        localPaint = this.state.strokePaint;
        break label174;
        label549:
        f1 = 0.0F;
        break label204;
        label555:
        f2 = 0.0F;
        break label223;
        label561:
        f3 = localBox.width;
        break label242;
        label571:
        f4 = 0.0F;
        break label261;
        label577:
        if (localSvgLinearGradient.x1 != null)
        {
          f1 = localSvgLinearGradient.x1.floatValue(this, 1.0F);
          label597:
          if (localSvgLinearGradient.y1 == null) {
            break label666;
          }
          f2 = localSvgLinearGradient.y1.floatValue(this, 1.0F);
          label617:
          if (localSvgLinearGradient.x2 == null) {
            break label672;
          }
        }
        label666:
        label672:
        for (f3 = localSvgLinearGradient.x2.floatValue(this, 1.0F);; f3 = 1.0F)
        {
          if (localSvgLinearGradient.y2 == null) {
            break label678;
          }
          f4 = localSvgLinearGradient.y2.floatValue(this, 1.0F);
          break;
          f1 = 0.0F;
          break label597;
          f2 = 0.0F;
          break label617;
        }
        label678:
        f4 = 0.0F;
        break label261;
        label684:
        this.state.hasStroke = false;
        break label369;
        label695:
        int[] arrayOfInt = new int[j];
        float[] arrayOfFloat = new float[j];
        Iterator localIterator = localSvgLinearGradient.children.iterator();
        int k = 0;
        float f5 = -1.0F;
        if (localIterator.hasNext())
        {
          SVG.Stop localStop = (SVG.Stop)localIterator.next();
          if ((k == 0) || (localStop.offset.floatValue() >= f5))
          {
            arrayOfFloat[k] = localStop.offset.floatValue();
            f5 = localStop.offset.floatValue();
          }
          for (;;)
          {
            statePush();
            updateStyleForElement(this.state, localStop);
            SVG.Colour localColour = (SVG.Colour)this.state.style.stopColor;
            if (localColour == null) {
              localColour = SVG.Colour.BLACK;
            }
            arrayOfInt[k] = (clamp255(this.state.style.stopOpacity.floatValue()) << 24 | localColour.colour);
            int m = k + 1;
            statePop();
            k = m;
            break;
            arrayOfFloat[k] = f5;
          }
        }
        if (((f1 == f3) && (f2 == f4)) || (j == 1))
        {
          statePop();
          localPaint.setColor(arrayOfInt[(j - 1)]);
          break label369;
        }
        Shader.TileMode localTileMode = Shader.TileMode.CLAMP;
        if (localSvgLinearGradient.spreadMethod != null)
        {
          if (localSvgLinearGradient.spreadMethod != SVG.GradientSpread.reflect) {
            break label1005;
          }
          localTileMode = Shader.TileMode.MIRROR;
        }
        for (;;)
        {
          statePop();
          LinearGradient localLinearGradient = new LinearGradient(f1, f2, f3, f4, arrayOfInt, arrayOfFloat, localTileMode);
          localLinearGradient.setLocalMatrix(localMatrix);
          localPaint.setShader(localLinearGradient);
          break;
          label1005:
          if (localSvgLinearGradient.spreadMethod == SVG.GradientSpread.repeat) {
            localTileMode = Shader.TileMode.REPEAT;
          }
        }
      }
    }
    RendererState localRendererState1;
    if (isSpecified(localSolidColor.baseStyle, 2147483648L))
    {
      this.state.style.stroke = localSolidColor.baseStyle.solidColor;
      localRendererState1 = this.state;
      if (localSolidColor.baseStyle.solidColor == null) {
        break label1154;
      }
    }
    label1154:
    for (boolean bool1 = true;; bool1 = false)
    {
      localRendererState1.hasStroke = bool1;
      if (isSpecified(localSolidColor.baseStyle, 4294967296L)) {
        this.state.style.strokeOpacity = localSolidColor.baseStyle.solidOpacity;
      }
      if (!isSpecified(localSolidColor.baseStyle, 6442450944L)) {
        break;
      }
      setPaintColour(this.state, paramBoolean, this.state.style.stroke);
      return;
    }
  }
  
  private boolean display()
  {
    if (this.state.style.display != null) {
      return this.state.style.display.booleanValue();
    }
    return true;
  }
  
  private void doFilledPath(SVG.SvgElement paramSvgElement, Path paramPath)
  {
    if ((this.state.style.fill instanceof SVG.PaintReference))
    {
      SVG.SvgObject localSvgObject1 = this.document.resolveIRI(((SVG.PaintReference)this.state.style.fill).href);
      if ((localSvgObject1 instanceof SVG.Pattern))
      {
        SVG.Pattern localPattern1 = (SVG.Pattern)localSvgObject1;
        int i;
        String str;
        label90:
        SVG.SvgObject localSvgObject2;
        label122:
        float f18;
        label146:
        float f19;
        label165:
        float f20;
        if ((localPattern1.patternUnitsAreUser != null) && (localPattern1.patternUnitsAreUser.booleanValue()))
        {
          i = 1;
          if (localPattern1.href != null)
          {
            str = localPattern1.href;
            localSvgObject2 = localPattern1.document.resolveIRI(str);
            if (localSvgObject2 != null) {
              break label240;
            }
            warn("Pattern reference '%s' not found", new Object[] { str });
          }
          if (i == 0) {
            break label520;
          }
          if (localPattern1.x == null) {
            break label496;
          }
          f18 = localPattern1.x.floatValueX(this);
          if (localPattern1.y == null) {
            break label502;
          }
          f19 = localPattern1.y.floatValueY(this);
          if (localPattern1.width == null) {
            break label508;
          }
          f20 = localPattern1.width.floatValueX(this);
          label184:
          if (localPattern1.height == null) {
            break label514;
          }
        }
        float f8;
        float f9;
        float f10;
        float f11;
        label514:
        for (float f21 = localPattern1.height.floatValueY(this);; f21 = 0.0F)
        {
          f8 = f21;
          f9 = f20;
          f10 = f19;
          f11 = f18;
          if ((f9 != 0.0F) && (f8 != 0.0F)) {
            break label703;
          }
          return;
          i = 0;
          break;
          label240:
          if (!(localSvgObject2 instanceof SVG.Pattern))
          {
            error("Pattern href attributes must point to other pattern elements", new Object[0]);
            break label122;
          }
          if (localSvgObject2 == localPattern1)
          {
            error("Circular reference in pattern href attribute '%s'", new Object[] { str });
            break label122;
          }
          SVG.Pattern localPattern2 = (SVG.Pattern)localSvgObject2;
          if (localPattern1.patternUnitsAreUser == null) {
            localPattern1.patternUnitsAreUser = localPattern2.patternUnitsAreUser;
          }
          if (localPattern1.patternContentUnitsAreUser == null) {
            localPattern1.patternContentUnitsAreUser = localPattern2.patternContentUnitsAreUser;
          }
          if (localPattern1.patternTransform == null) {
            localPattern1.patternTransform = localPattern2.patternTransform;
          }
          if (localPattern1.x == null) {
            localPattern1.x = localPattern2.x;
          }
          if (localPattern1.y == null) {
            localPattern1.y = localPattern2.y;
          }
          if (localPattern1.width == null) {
            localPattern1.width = localPattern2.width;
          }
          if (localPattern1.height == null) {
            localPattern1.height = localPattern2.height;
          }
          if (localPattern1.children.isEmpty()) {
            localPattern1.children = localPattern2.children;
          }
          if (localPattern1.viewBox == null) {
            localPattern1.viewBox = localPattern2.viewBox;
          }
          if (localPattern1.preserveAspectRatio == null) {
            localPattern1.preserveAspectRatio = localPattern2.preserveAspectRatio;
          }
          if (localPattern2.href == null) {
            break label122;
          }
          str = localPattern2.href;
          break label90;
          label496:
          f18 = 0.0F;
          break label146;
          label502:
          f19 = 0.0F;
          break label165;
          label508:
          f20 = 0.0F;
          break label184;
        }
        label520:
        float f1;
        label540:
        float f2;
        label560:
        float f3;
        if (localPattern1.x != null)
        {
          f1 = localPattern1.x.floatValue(this, 1.0F);
          if (localPattern1.y == null) {
            break label685;
          }
          f2 = localPattern1.y.floatValue(this, 1.0F);
          if (localPattern1.width == null) {
            break label691;
          }
          f3 = localPattern1.width.floatValue(this, 1.0F);
          label580:
          if (localPattern1.height == null) {
            break label697;
          }
        }
        label685:
        label691:
        label697:
        for (float f4 = localPattern1.height.floatValue(this, 1.0F);; f4 = 0.0F)
        {
          float f5 = paramSvgElement.boundingBox.minX + f1 * paramSvgElement.boundingBox.width;
          float f6 = paramSvgElement.boundingBox.minY + f2 * paramSvgElement.boundingBox.height;
          float f7 = f3 * paramSvgElement.boundingBox.width;
          f8 = f4 * paramSvgElement.boundingBox.height;
          f9 = f7;
          f10 = f6;
          f11 = f5;
          break;
          f1 = 0.0F;
          break label540;
          f2 = 0.0F;
          break label560;
          f3 = 0.0F;
          break label580;
        }
        label703:
        if (localPattern1.preserveAspectRatio != null) {}
        RectF localRectF;
        for (PreserveAspectRatio localPreserveAspectRatio = localPattern1.preserveAspectRatio;; localPreserveAspectRatio = PreserveAspectRatio.LETTERBOX)
        {
          statePush();
          this.canvas.clipPath(paramPath);
          RendererState localRendererState = new RendererState();
          updateStyle(localRendererState, SVG.Style.getDefaultStyle());
          localRendererState.style.overflow = Boolean.valueOf(false);
          this.state = findInheritFromAncestorState(localPattern1, localRendererState);
          localBox1 = paramSvgElement.boundingBox;
          if (localPattern1.patternTransform == null) {
            break label1119;
          }
          this.canvas.concat(localPattern1.patternTransform);
          Matrix localMatrix = new Matrix();
          if (!localPattern1.patternTransform.invert(localMatrix)) {
            break label1119;
          }
          float[] arrayOfFloat = new float[8];
          arrayOfFloat[0] = paramSvgElement.boundingBox.minX;
          arrayOfFloat[1] = paramSvgElement.boundingBox.minY;
          arrayOfFloat[2] = paramSvgElement.boundingBox.maxX();
          arrayOfFloat[3] = paramSvgElement.boundingBox.minY;
          arrayOfFloat[4] = paramSvgElement.boundingBox.maxX();
          arrayOfFloat[5] = paramSvgElement.boundingBox.maxY();
          arrayOfFloat[6] = paramSvgElement.boundingBox.minX;
          arrayOfFloat[7] = paramSvgElement.boundingBox.maxY();
          localMatrix.mapPoints(arrayOfFloat);
          localRectF = new RectF(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[0], arrayOfFloat[1]);
          for (int k = 2; k <= 6; k += 2)
          {
            if (arrayOfFloat[k] < localRectF.left) {
              localRectF.left = arrayOfFloat[k];
            }
            if (arrayOfFloat[k] > localRectF.right) {
              localRectF.right = arrayOfFloat[k];
            }
            if (arrayOfFloat[(k + 1)] < localRectF.top) {
              localRectF.top = arrayOfFloat[(k + 1)];
            }
            if (arrayOfFloat[(k + 1)] > localRectF.bottom) {
              localRectF.bottom = arrayOfFloat[(k + 1)];
            }
          }
        }
        SVG.Box localBox1 = new SVG.Box(localRectF.left, localRectF.top, localRectF.right - localRectF.left, localRectF.bottom - localRectF.top);
        label1119:
        float f12 = f11 + f9 * (float)Math.floor((localBox1.minX - f11) / f9);
        float f13 = f10 + f8 * (float)Math.floor((localBox1.minY - f10) / f8);
        float f14 = localBox1.maxX();
        float f15 = localBox1.maxY();
        SVG.Box localBox2 = new SVG.Box(0.0F, 0.0F, f9, f8);
        for (float f16 = f13; f16 < f15; f16 += f8) {
          for (float f17 = f12; f17 < f14; f17 += f9)
          {
            localBox2.minX = f17;
            localBox2.minY = f16;
            statePush();
            if (!this.state.style.overflow.booleanValue()) {
              setClipRect(localBox2.minX, localBox2.minY, localBox2.width, localBox2.height);
            }
            boolean bool;
            if (localPattern1.viewBox != null)
            {
              this.canvas.concat(calculateViewBoxTransform(localBox2, localPattern1.viewBox, localPreserveAspectRatio));
              bool = pushLayer();
              Iterator localIterator = localPattern1.children.iterator();
              while (localIterator.hasNext()) {
                render((SVG.SvgObject)localIterator.next());
              }
            }
            if ((localPattern1.patternContentUnitsAreUser == null) || (localPattern1.patternContentUnitsAreUser.booleanValue())) {}
            for (int j = 1;; j = 0)
            {
              this.canvas.translate(f17, f16);
              if (j != 0) {
                break;
              }
              this.canvas.scale(paramSvgElement.boundingBox.width, paramSvgElement.boundingBox.height);
              break;
            }
            if (bool) {
              popLayer(localPattern1);
            }
            statePop();
          }
        }
        statePop();
        return;
      }
    }
    this.canvas.drawPath(paramPath, this.state.fillPaint);
  }
  
  private void doStroke(Path paramPath)
  {
    if (this.state.style.vectorEffect == SVG.Style.VectorEffect.NonScalingStroke)
    {
      Matrix localMatrix1 = this.canvas.getMatrix();
      Path localPath = new Path();
      paramPath.transform(localMatrix1, localPath);
      this.canvas.setMatrix(new Matrix());
      Shader localShader = this.state.strokePaint.getShader();
      Matrix localMatrix2 = new Matrix();
      if (localShader != null)
      {
        localShader.getLocalMatrix(localMatrix2);
        Matrix localMatrix3 = new Matrix(localMatrix2);
        localMatrix3.postConcat(localMatrix1);
        localShader.setLocalMatrix(localMatrix3);
      }
      this.canvas.drawPath(localPath, this.state.strokePaint);
      this.canvas.setMatrix(localMatrix1);
      if (localShader != null) {
        localShader.setLocalMatrix(localMatrix2);
      }
      return;
    }
    this.canvas.drawPath(paramPath, this.state.strokePaint);
  }
  
  private void duplicateCanvas()
  {
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(this.canvas.getWidth(), this.canvas.getHeight(), Bitmap.Config.ARGB_8888);
      this.bitmapStack.push(localBitmap);
      Canvas localCanvas = new Canvas(localBitmap);
      localCanvas.setMatrix(this.canvas.getMatrix());
      this.canvas = localCanvas;
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      error("Not enough memory to create temporary bitmaps for mask processing", new Object[0]);
      throw localOutOfMemoryError;
    }
  }
  
  private void enumerateTextSpans(SVG.TextContainer paramTextContainer, TextProcessor paramTextProcessor)
  {
    if (!display()) {
      return;
    }
    Iterator localIterator = paramTextContainer.children.iterator();
    boolean bool1 = true;
    label21:
    SVG.SvgObject localSvgObject1;
    boolean bool4;
    if (localIterator.hasNext())
    {
      localSvgObject1 = (SVG.SvgObject)localIterator.next();
      if (!(localSvgObject1 instanceof SVG.TextSequence)) {
        break label97;
      }
      String str = ((SVG.TextSequence)localSvgObject1).text;
      if (localIterator.hasNext()) {
        break label91;
      }
      bool4 = true;
      label71:
      paramTextProcessor.processText(textXMLSpaceTransform(str, bool1, bool4));
    }
    label91:
    label97:
    SVG.TextPath localTextPath;
    SVG.SvgObject localSvgObject3;
    for (;;)
    {
      bool1 = false;
      break label21;
      break;
      bool4 = false;
      break label71;
      if (paramTextProcessor.doTextContainer((SVG.TextContainer)localSvgObject1))
      {
        if (!(localSvgObject1 instanceof SVG.TextPath)) {
          break label386;
        }
        statePush();
        localTextPath = (SVG.TextPath)localSvgObject1;
        updateStyleForElement(this.state, localTextPath);
        if ((display()) && (visible()))
        {
          localSvgObject3 = localTextPath.document.resolveIRI(localTextPath.href);
          if (localSvgObject3 != null) {
            break label202;
          }
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localTextPath.href;
          error("TextPath reference '%s' not found", arrayOfObject2);
        }
        label195:
        statePop();
      }
    }
    label202:
    SVG.Path localPath = (SVG.Path)localSvgObject3;
    Path localPath1 = new PathConverter(localPath.d).path;
    if (localPath.transform != null) {
      localPath1.transform(localPath.transform);
    }
    PathMeasure localPathMeasure = new PathMeasure(localPath1, false);
    float f7;
    label281:
    float f9;
    float f8;
    if (localTextPath.startOffset != null)
    {
      f7 = localTextPath.startOffset.floatValue(this, localPathMeasure.getLength());
      SVG.Style.TextAnchor localTextAnchor = getAnchorPosition();
      if (localTextAnchor == SVG.Style.TextAnchor.Start) {
        break label872;
      }
      f9 = calculateTextWidth(localTextPath);
      if (localTextAnchor != SVG.Style.TextAnchor.Middle) {
        break label376;
      }
      f8 = f7 - f9 / 2.0F;
    }
    for (;;)
    {
      checkForGradiantsAndPatterns((SVG.SvgElement)localTextPath.textRoot);
      boolean bool3 = pushLayer();
      enumerateTextSpans(localTextPath, new PathTextDrawer(localPath1, f8));
      if (!bool3) {
        break label195;
      }
      popLayer(localTextPath);
      break label195;
      f7 = 0.0F;
      break label281;
      label376:
      f8 = f7 - f9;
      continue;
      label386:
      SVG.TSpan localTSpan;
      float f5;
      label459:
      float f2;
      label489:
      float f3;
      label513:
      float f6;
      label537:
      float f4;
      float f1;
      if ((localSvgObject1 instanceof SVG.TSpan))
      {
        statePush();
        localTSpan = (SVG.TSpan)localSvgObject1;
        updateStyleForElement(this.state, localTSpan);
        if (display())
        {
          if (!(paramTextProcessor instanceof PlainTextDrawer)) {
            break label857;
          }
          if ((localTSpan.x != null) && (localTSpan.x.size() != 0)) {
            break label619;
          }
          f5 = ((PlainTextDrawer)paramTextProcessor).x;
          if ((localTSpan.y != null) && (localTSpan.y.size() != 0)) {
            break label642;
          }
          f2 = ((PlainTextDrawer)paramTextProcessor).y;
          if ((localTSpan.dx != null) && (localTSpan.dx.size() != 0)) {
            break label665;
          }
          f3 = 0.0F;
          if ((localTSpan.dy != null) && (localTSpan.dy.size() != 0)) {
            break label688;
          }
          f6 = 0.0F;
          f4 = f5;
          f1 = f6;
        }
      }
      for (;;)
      {
        checkForGradiantsAndPatterns((SVG.SvgElement)localTSpan.textRoot);
        if ((paramTextProcessor instanceof PlainTextDrawer))
        {
          ((PlainTextDrawer)paramTextProcessor).x = (f3 + f4);
          ((PlainTextDrawer)paramTextProcessor).y = (f1 + f2);
        }
        boolean bool2 = pushLayer();
        enumerateTextSpans(localTSpan, paramTextProcessor);
        if (bool2) {
          popLayer(localTSpan);
        }
        statePop();
        break;
        label619:
        f5 = ((SVG.Length)localTSpan.x.get(0)).floatValueX(this);
        break label459;
        label642:
        f2 = ((SVG.Length)localTSpan.y.get(0)).floatValueY(this);
        break label489;
        label665:
        f3 = ((SVG.Length)localTSpan.dx.get(0)).floatValueX(this);
        break label513;
        label688:
        f6 = ((SVG.Length)localTSpan.dy.get(0)).floatValueY(this);
        break label537;
        if (!(localSvgObject1 instanceof SVG.TRef)) {
          break;
        }
        statePush();
        SVG.TRef localTRef = (SVG.TRef)localSvgObject1;
        updateStyleForElement(this.state, localTRef);
        if (display())
        {
          checkForGradiantsAndPatterns((SVG.SvgElement)localTRef.textRoot);
          SVG.SvgObject localSvgObject2 = localSvgObject1.document.resolveIRI(localTRef.href);
          if ((localSvgObject2 == null) || (!(localSvgObject2 instanceof SVG.TextContainer))) {
            break label831;
          }
          StringBuilder localStringBuilder = new StringBuilder();
          extractRawText((SVG.TextContainer)localSvgObject2, localStringBuilder);
          if (localStringBuilder.length() > 0) {
            paramTextProcessor.processText(localStringBuilder.toString());
          }
        }
        for (;;)
        {
          statePop();
          break;
          label831:
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = localTRef.href;
          error("Tref reference '%s' not found", arrayOfObject1);
        }
        label857:
        f1 = 0.0F;
        f2 = 0.0F;
        f3 = 0.0F;
        f4 = 0.0F;
      }
      label872:
      f8 = f7;
    }
  }
  
  private static void error(String paramString, Object... paramVarArgs)
  {
    Log.e("SVGAndroidRenderer", String.format(paramString, paramVarArgs));
  }
  
  private void extractRawText(SVG.TextContainer paramTextContainer, StringBuilder paramStringBuilder)
  {
    Iterator localIterator = paramTextContainer.children.iterator();
    boolean bool1 = true;
    if (localIterator.hasNext())
    {
      SVG.SvgObject localSvgObject = (SVG.SvgObject)localIterator.next();
      if ((localSvgObject instanceof SVG.TextContainer)) {
        extractRawText((SVG.TextContainer)localSvgObject, paramStringBuilder);
      }
      while (!(localSvgObject instanceof SVG.TextSequence))
      {
        bool1 = false;
        break;
      }
      String str = ((SVG.TextSequence)localSvgObject).text;
      if (!localIterator.hasNext()) {}
      for (boolean bool2 = true;; bool2 = false)
      {
        paramStringBuilder.append(textXMLSpaceTransform(str, bool1, bool2));
        break;
      }
    }
  }
  
  private void fillInChainedGradientFields(SVG.GradientElement paramGradientElement, String paramString)
  {
    SVG.SvgObject localSvgObject = paramGradientElement.document.resolveIRI(paramString);
    if (localSvgObject == null)
    {
      warn("Gradient reference '%s' not found", new Object[] { paramString });
      label27:
      return;
    }
    if (!(localSvgObject instanceof SVG.GradientElement))
    {
      error("Gradient href attributes must point to other gradient elements", new Object[0]);
      return;
    }
    if (localSvgObject == paramGradientElement)
    {
      error("Circular reference in gradient href attribute '%s'", new Object[] { paramString });
      return;
    }
    SVG.GradientElement localGradientElement = (SVG.GradientElement)localSvgObject;
    if (paramGradientElement.gradientUnitsAreUser == null) {
      paramGradientElement.gradientUnitsAreUser = localGradientElement.gradientUnitsAreUser;
    }
    if (paramGradientElement.gradientTransform == null) {
      paramGradientElement.gradientTransform = localGradientElement.gradientTransform;
    }
    if (paramGradientElement.spreadMethod == null) {
      paramGradientElement.spreadMethod = localGradientElement.spreadMethod;
    }
    if (paramGradientElement.children.isEmpty()) {
      paramGradientElement.children = localGradientElement.children;
    }
    for (;;)
    {
      try
      {
        if (!(paramGradientElement instanceof SVG.SvgLinearGradient)) {
          continue;
        }
        SVG.SvgLinearGradient localSvgLinearGradient1 = (SVG.SvgLinearGradient)paramGradientElement;
        SVG.SvgLinearGradient localSvgLinearGradient2 = (SVG.SvgLinearGradient)localSvgObject;
        if (localSvgLinearGradient1.x1 == null) {
          localSvgLinearGradient1.x1 = localSvgLinearGradient2.x1;
        }
        if (localSvgLinearGradient1.y1 == null) {
          localSvgLinearGradient1.y1 = localSvgLinearGradient2.y1;
        }
        if (localSvgLinearGradient1.x2 == null) {
          localSvgLinearGradient1.x2 = localSvgLinearGradient2.x2;
        }
        if (localSvgLinearGradient1.y2 == null) {
          localSvgLinearGradient1.y2 = localSvgLinearGradient2.y2;
        }
      }
      catch (ClassCastException localClassCastException)
      {
        SVG.SvgRadialGradient localSvgRadialGradient1;
        SVG.SvgRadialGradient localSvgRadialGradient2;
        continue;
      }
      if (localGradientElement.href == null) {
        break label27;
      }
      paramString = localGradientElement.href;
      break;
      localSvgRadialGradient1 = (SVG.SvgRadialGradient)paramGradientElement;
      localSvgRadialGradient2 = (SVG.SvgRadialGradient)localSvgObject;
      if (localSvgRadialGradient1.cx == null) {
        localSvgRadialGradient1.cx = localSvgRadialGradient2.cx;
      }
      if (localSvgRadialGradient1.cy == null) {
        localSvgRadialGradient1.cy = localSvgRadialGradient2.cy;
      }
      if (localSvgRadialGradient1.r == null) {
        localSvgRadialGradient1.r = localSvgRadialGradient2.r;
      }
      if (localSvgRadialGradient1.fx == null) {
        localSvgRadialGradient1.fx = localSvgRadialGradient2.fx;
      }
      if (localSvgRadialGradient1.fy == null) {
        localSvgRadialGradient1.fy = localSvgRadialGradient2.fy;
      }
    }
  }
  
  private RendererState findInheritFromAncestorState(SVG.SvgObject paramSvgObject)
  {
    RendererState localRendererState = new RendererState();
    updateStyle(localRendererState, SVG.Style.getDefaultStyle());
    return findInheritFromAncestorState(paramSvgObject, localRendererState);
  }
  
  private RendererState findInheritFromAncestorState(SVG.SvgObject paramSvgObject, RendererState paramRendererState)
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      if ((paramSvgObject instanceof SVG.SvgElementBase)) {
        localArrayList.add(0, (SVG.SvgElementBase)paramSvgObject);
      }
      if (paramSvgObject.parent == null) {
        break;
      }
      paramSvgObject = (SVG.SvgObject)paramSvgObject.parent;
    }
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext()) {
      updateStyleForElement(paramRendererState, (SVG.SvgElementBase)localIterator.next());
    }
    paramRendererState.viewBox = this.document.rootElement.viewBox;
    if (paramRendererState.viewBox == null) {
      paramRendererState.viewBox = this.canvasViewPort;
    }
    paramRendererState.viewPort = this.canvasViewPort;
    paramRendererState.directRendering = this.state.directRendering;
    return paramRendererState;
  }
  
  private SVG.Style.TextAnchor getAnchorPosition()
  {
    if ((this.state.style.direction == SVG.Style.TextDirection.LTR) || (this.state.style.textAnchor == SVG.Style.TextAnchor.Middle)) {
      return this.state.style.textAnchor;
    }
    if (this.state.style.textAnchor == SVG.Style.TextAnchor.Start) {
      return SVG.Style.TextAnchor.End;
    }
    return SVG.Style.TextAnchor.Start;
  }
  
  private Path.FillType getClipRuleFromState()
  {
    if (this.state.style.clipRule == null) {
      return Path.FillType.WINDING;
    }
    switch (1.$SwitchMap$com$caverock$androidsvg$SVG$Style$FillRule[this.state.style.clipRule.ordinal()])
    {
    default: 
      return Path.FillType.WINDING;
    }
    return Path.FillType.EVEN_ODD;
  }
  
  private static void initialiseSupportedFeaturesMap()
  {
    try
    {
      HashSet localHashSet = new HashSet();
      supportedFeatures = localHashSet;
      localHashSet.add("Structure");
      supportedFeatures.add("BasicStructure");
      supportedFeatures.add("ConditionalProcessing");
      supportedFeatures.add("Image");
      supportedFeatures.add("Style");
      supportedFeatures.add("ViewportAttribute");
      supportedFeatures.add("Shape");
      supportedFeatures.add("BasicText");
      supportedFeatures.add("PaintAttribute");
      supportedFeatures.add("BasicPaintAttribute");
      supportedFeatures.add("OpacityAttribute");
      supportedFeatures.add("BasicGraphicsAttribute");
      supportedFeatures.add("Marker");
      supportedFeatures.add("Gradient");
      supportedFeatures.add("Pattern");
      supportedFeatures.add("Clip");
      supportedFeatures.add("BasicClip");
      supportedFeatures.add("Mask");
      supportedFeatures.add("View");
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private static boolean isSpecified(SVG.Style paramStyle, long paramLong)
  {
    return (paramLong & paramStyle.specifiedFlags) != 0L;
  }
  
  private Path makePathAndBoundingBox(SVG.Circle paramCircle)
  {
    float f1;
    if (paramCircle.cx != null)
    {
      f1 = paramCircle.cx.floatValueX(this);
      if (paramCircle.cy == null) {
        break label214;
      }
    }
    label214:
    for (float f2 = paramCircle.cy.floatValueY(this);; f2 = 0.0F)
    {
      float f3 = paramCircle.r.floatValue(this);
      float f4 = f1 - f3;
      float f5 = f2 - f3;
      float f6 = f1 + f3;
      float f7 = f2 + f3;
      if (paramCircle.boundingBox == null) {
        paramCircle.boundingBox = new SVG.Box(f4, f5, 2.0F * f3, 2.0F * f3);
      }
      float f8 = f3 * 0.5522848F;
      Path localPath = new Path();
      localPath.moveTo(f1, f5);
      localPath.cubicTo(f1 + f8, f5, f6, f2 - f8, f6, f2);
      localPath.cubicTo(f6, f2 + f8, f1 + f8, f7, f1, f7);
      localPath.cubicTo(f1 - f8, f7, f4, f2 + f8, f4, f2);
      localPath.cubicTo(f4, f2 - f8, f1 - f8, f5, f1, f5);
      localPath.close();
      return localPath;
      f1 = 0.0F;
      break;
    }
  }
  
  private Path makePathAndBoundingBox(SVG.Ellipse paramEllipse)
  {
    float f1;
    if (paramEllipse.cx != null)
    {
      f1 = paramEllipse.cx.floatValueX(this);
      if (paramEllipse.cy == null) {
        break label232;
      }
    }
    label232:
    for (float f2 = paramEllipse.cy.floatValueY(this);; f2 = 0.0F)
    {
      float f3 = paramEllipse.rx.floatValueX(this);
      float f4 = paramEllipse.ry.floatValueY(this);
      float f5 = f1 - f3;
      float f6 = f2 - f4;
      float f7 = f1 + f3;
      float f8 = f2 + f4;
      if (paramEllipse.boundingBox == null) {
        paramEllipse.boundingBox = new SVG.Box(f5, f6, 2.0F * f3, 2.0F * f4);
      }
      float f9 = f3 * 0.5522848F;
      float f10 = f4 * 0.5522848F;
      Path localPath = new Path();
      localPath.moveTo(f1, f6);
      localPath.cubicTo(f1 + f9, f6, f7, f2 - f10, f7, f2);
      localPath.cubicTo(f7, f2 + f10, f1 + f9, f8, f1, f8);
      localPath.cubicTo(f1 - f9, f8, f5, f2 + f10, f5, f2);
      localPath.cubicTo(f5, f2 - f10, f1 - f9, f6, f1, f6);
      localPath.close();
      return localPath;
      f1 = 0.0F;
      break;
    }
  }
  
  private Path makePathAndBoundingBox(SVG.PolyLine paramPolyLine)
  {
    Path localPath = new Path();
    localPath.moveTo(paramPolyLine.points[0], paramPolyLine.points[1]);
    for (int i = 2; i < paramPolyLine.points.length; i += 2) {
      localPath.lineTo(paramPolyLine.points[i], paramPolyLine.points[(i + 1)]);
    }
    if ((paramPolyLine instanceof SVG.Polygon)) {
      localPath.close();
    }
    if (paramPolyLine.boundingBox == null) {
      paramPolyLine.boundingBox = calculatePathBounds(localPath);
    }
    localPath.setFillType(getClipRuleFromState());
    return localPath;
  }
  
  private Path makePathAndBoundingBox(SVG.Rect paramRect)
  {
    float f1;
    float f2;
    float f4;
    float f6;
    float f7;
    label75:
    float f8;
    label92:
    float f11;
    float f12;
    Path localPath;
    if ((paramRect.rx == null) && (paramRect.ry == null))
    {
      f1 = 0.0F;
      f2 = 0.0F;
      float f3 = paramRect.width.floatValueX(this) / 2.0F;
      f4 = Math.min(f1, f3);
      float f5 = paramRect.height.floatValueY(this) / 2.0F;
      f6 = Math.min(f2, f5);
      if (paramRect.x == null) {
        break label291;
      }
      f7 = paramRect.x.floatValueX(this);
      if (paramRect.y == null) {
        break label297;
      }
      f8 = paramRect.y.floatValueY(this);
      float f9 = paramRect.width.floatValueX(this);
      float f10 = paramRect.height.floatValueY(this);
      if (paramRect.boundingBox == null) {
        paramRect.boundingBox = new SVG.Box(f7, f8, f9, f10);
      }
      f11 = f7 + f9;
      f12 = f8 + f10;
      localPath = new Path();
      if ((f4 != 0.0F) && (f6 != 0.0F)) {
        break label303;
      }
      localPath.moveTo(f7, f8);
      localPath.lineTo(f11, f8);
      localPath.lineTo(f11, f12);
      localPath.lineTo(f7, f12);
      localPath.lineTo(f7, f8);
    }
    for (;;)
    {
      localPath.close();
      return localPath;
      if (paramRect.rx == null)
      {
        f2 = paramRect.ry.floatValueY(this);
        f1 = f2;
        break;
      }
      if (paramRect.ry == null)
      {
        f2 = paramRect.rx.floatValueX(this);
        f1 = f2;
        break;
      }
      f1 = paramRect.rx.floatValueX(this);
      f2 = paramRect.ry.floatValueY(this);
      break;
      label291:
      f7 = 0.0F;
      break label75;
      label297:
      f8 = 0.0F;
      break label92;
      label303:
      float f13 = f4 * 0.5522848F;
      float f14 = f6 * 0.5522848F;
      localPath.moveTo(f7, f8 + f6);
      localPath.cubicTo(f7, f8 + f6 - f14, f7 + f4 - f13, f8, f7 + f4, f8);
      localPath.lineTo(f11 - f4, f8);
      localPath.cubicTo(f13 + (f11 - f4), f8, f11, f8 + f6 - f14, f11, f8 + f6);
      localPath.lineTo(f11, f12 - f6);
      localPath.cubicTo(f11, f14 + (f12 - f6), f13 + (f11 - f4), f12, f11 - f4, f12);
      localPath.lineTo(f7 + f4, f12);
      float f15 = f7 + f4 - f13;
      float f16 = f14 + (f12 - f6);
      float f17 = f12 - f6;
      localPath.cubicTo(f15, f12, f7, f16, f7, f17);
      localPath.lineTo(f7, f8 + f6);
    }
  }
  
  private void makeRadialGradiant(boolean paramBoolean, SVG.Box paramBox, SVG.SvgRadialGradient paramSvgRadialGradient)
  {
    if (paramSvgRadialGradient.href != null) {
      fillInChainedGradientFields(paramSvgRadialGradient, paramSvgRadialGradient.href);
    }
    int i;
    Paint localPaint;
    label49:
    SVG.Length localLength;
    float f1;
    label86:
    float f2;
    if ((paramSvgRadialGradient.gradientUnitsAreUser != null) && (paramSvgRadialGradient.gradientUnitsAreUser.booleanValue()))
    {
      i = 1;
      if (!paramBoolean) {
        break label231;
      }
      localPaint = this.state.fillPaint;
      if (i == 0) {
        break label276;
      }
      localLength = new SVG.Length(50.0F, SVG.Unit.percent);
      if (paramSvgRadialGradient.cx == null) {
        break label243;
      }
      f1 = paramSvgRadialGradient.cx.floatValueX(this);
      if (paramSvgRadialGradient.cy == null) {
        break label254;
      }
      f2 = paramSvgRadialGradient.cy.floatValueY(this);
      label103:
      if (paramSvgRadialGradient.r == null) {
        break label265;
      }
    }
    Matrix localMatrix;
    int j;
    label265:
    for (float f3 = paramSvgRadialGradient.r.floatValue(this);; f3 = localLength.floatValue(this))
    {
      statePush();
      this.state = findInheritFromAncestorState(paramSvgRadialGradient);
      localMatrix = new Matrix();
      if (i == 0)
      {
        localMatrix.preTranslate(paramBox.minX, paramBox.minY);
        localMatrix.preScale(paramBox.width, paramBox.height);
      }
      if (paramSvgRadialGradient.gradientTransform != null) {
        localMatrix.preConcat(paramSvgRadialGradient.gradientTransform);
      }
      j = paramSvgRadialGradient.children.size();
      if (j != 0) {
        break label366;
      }
      statePop();
      if (!paramBoolean) {
        break label357;
      }
      this.state.hasFill = false;
      return;
      i = 0;
      break;
      label231:
      localPaint = this.state.strokePaint;
      break label49;
      label243:
      f1 = localLength.floatValueX(this);
      break label86;
      label254:
      f2 = localLength.floatValueY(this);
      break label103;
    }
    label276:
    if (paramSvgRadialGradient.cx != null)
    {
      f1 = paramSvgRadialGradient.cx.floatValue(this, 1.0F);
      label294:
      if (paramSvgRadialGradient.cy == null) {
        break label341;
      }
      f2 = paramSvgRadialGradient.cy.floatValue(this, 1.0F);
      label312:
      if (paramSvgRadialGradient.r == null) {
        break label349;
      }
    }
    label341:
    label349:
    for (f3 = paramSvgRadialGradient.r.floatValue(this, 1.0F);; f3 = 0.5F)
    {
      break;
      f1 = 0.5F;
      break label294;
      f2 = 0.5F;
      break label312;
    }
    label357:
    this.state.hasStroke = false;
    return;
    label366:
    int[] arrayOfInt = new int[j];
    float[] arrayOfFloat = new float[j];
    int k = 0;
    float f4 = -1.0F;
    Iterator localIterator = paramSvgRadialGradient.children.iterator();
    if (localIterator.hasNext())
    {
      SVG.Stop localStop = (SVG.Stop)localIterator.next();
      if ((k == 0) || (localStop.offset.floatValue() >= f4))
      {
        arrayOfFloat[k] = localStop.offset.floatValue();
        f4 = localStop.offset.floatValue();
      }
      for (;;)
      {
        statePush();
        updateStyleForElement(this.state, localStop);
        SVG.Colour localColour = (SVG.Colour)this.state.style.stopColor;
        if (localColour == null) {
          localColour = SVG.Colour.BLACK;
        }
        arrayOfInt[k] = (clamp255(this.state.style.stopOpacity.floatValue()) << 24 | localColour.colour);
        k++;
        statePop();
        break;
        arrayOfFloat[k] = f4;
      }
    }
    if ((f3 == 0.0F) || (j == 1))
    {
      statePop();
      int m = arrayOfInt[(j - 1)];
      localPaint.setColor(m);
      return;
    }
    Shader.TileMode localTileMode = Shader.TileMode.CLAMP;
    if (paramSvgRadialGradient.spreadMethod != null)
    {
      if (paramSvgRadialGradient.spreadMethod != SVG.GradientSpread.reflect) {
        break label655;
      }
      localTileMode = Shader.TileMode.MIRROR;
    }
    for (;;)
    {
      statePop();
      RadialGradient localRadialGradient = new RadialGradient(f1, f2, f3, arrayOfInt, arrayOfFloat, localTileMode);
      localRadialGradient.setLocalMatrix(localMatrix);
      localPaint.setShader(localRadialGradient);
      return;
      label655:
      if (paramSvgRadialGradient.spreadMethod == SVG.GradientSpread.repeat) {
        localTileMode = Shader.TileMode.REPEAT;
      }
    }
  }
  
  private void parentPop()
  {
    this.parentStack.pop();
    this.matrixStack.pop();
  }
  
  private void parentPush(SVG.SvgContainer paramSvgContainer)
  {
    this.parentStack.push(paramSvgContainer);
    this.matrixStack.push(this.canvas.getMatrix());
  }
  
  private void popLayer(SVG.SvgElement paramSvgElement)
  {
    if ((this.state.style.mask != null) && (this.state.directRendering))
    {
      SVG.SvgObject localSvgObject = this.document.resolveIRI(this.state.style.mask);
      duplicateCanvas();
      SVG.Mask localMask = (SVG.Mask)localSvgObject;
      int i;
      float f3;
      label92:
      float f4;
      label109:
      Bitmap localBitmap1;
      Bitmap localBitmap2;
      int j;
      int k;
      int[] arrayOfInt1;
      int[] arrayOfInt2;
      if ((localMask.maskUnitsAreUser != null) && (localMask.maskUnitsAreUser.booleanValue()))
      {
        i = 1;
        if (i == 0) {
          break label352;
        }
        if (localMask.width == null) {
          break label328;
        }
        f3 = localMask.width.floatValueX(this);
        if (localMask.height == null) {
          break label340;
        }
        f4 = localMask.height.floatValueY(this);
        if (localMask.x != null) {
          localMask.x.floatValueX(this);
        }
        if (localMask.y != null) {
          localMask.y.floatValueY(this);
        }
        if ((f3 != 0.0F) && (f4 != 0.0F)) {
          break label465;
        }
        localBitmap1 = (Bitmap)this.bitmapStack.pop();
        localBitmap2 = (Bitmap)this.bitmapStack.pop();
        j = localBitmap1.getWidth();
        k = localBitmap1.getHeight();
        arrayOfInt1 = new int[j];
        arrayOfInt2 = new int[j];
      }
      label328:
      label457:
      label465:
      for (int m = 0;; m++)
      {
        if (m >= k) {
          break label674;
        }
        localBitmap1.getPixels(arrayOfInt1, 0, j, 0, m, j, 1);
        localBitmap2.getPixels(arrayOfInt2, 0, j, 0, m, j, 1);
        int n = 0;
        label250:
        if (n < j)
        {
          int i1 = arrayOfInt1[n];
          int i2 = i1 & 0xFF;
          int i3 = 0xFF & i1 >> 8;
          int i4 = 0xFF & i1 >> 16;
          int i5 = 0xFF & i1 >> 24;
          if (i5 == 0) {
            arrayOfInt2[n] = 0;
          }
          for (;;)
          {
            n++;
            break label250;
            i = 0;
            break;
            f3 = paramSvgElement.boundingBox.width;
            break label92;
            f4 = paramSvgElement.boundingBox.height;
            break label109;
            if (localMask.x != null) {
              localMask.x.floatValue(this, 1.0F);
            }
            if (localMask.y != null) {
              localMask.y.floatValue(this, 1.0F);
            }
            float f1;
            if (localMask.width != null)
            {
              f1 = localMask.width.floatValue(this, 1.0F);
              if (localMask.height == null) {
                break label457;
              }
            }
            for (float f2 = localMask.height.floatValue(this, 1.0F);; f2 = 1.2F)
            {
              f3 = f1 * paramSvgElement.boundingBox.width;
              f4 = f2 * paramSvgElement.boundingBox.height;
              break;
              f1 = 1.2F;
              break label404;
            }
            statePush();
            this.state = findInheritFromAncestorState(localMask);
            this.state.style.opacity = Float.valueOf(1.0F);
            if ((localMask.maskContentUnitsAreUser == null) || (localMask.maskContentUnitsAreUser.booleanValue())) {}
            for (int i9 = 1;; i9 = 0)
            {
              if (i9 == 0)
              {
                this.canvas.translate(paramSvgElement.boundingBox.minX, paramSvgElement.boundingBox.minY);
                this.canvas.scale(paramSvgElement.boundingBox.width, paramSvgElement.boundingBox.height);
              }
              renderChildren(localMask, false);
              statePop();
              break;
            }
            int i6 = i5 * (i4 * 6963 + i3 * 23442 + i2 * 2362) / 8355840;
            int i7 = arrayOfInt2[n];
            int i8 = i6 * (0xFF & i7 >> 24) / 255;
            arrayOfInt2[n] = (i7 & 0xFFFFFF | i8 << 24);
          }
        }
        label340:
        label352:
        localBitmap2.setPixels(arrayOfInt2, 0, j, 0, m, j, 1);
      }
      label404:
      label674:
      localBitmap1.recycle();
      this.canvas = ((Canvas)this.canvasStack.pop());
      this.canvas.save();
      this.canvas.setMatrix(new Matrix());
      this.canvas.drawBitmap(localBitmap2, 0.0F, 0.0F, this.state.fillPaint);
      localBitmap2.recycle();
      this.canvas.restore();
    }
    statePop();
  }
  
  private boolean pushLayer()
  {
    if ((this.state.style.mask != null) && (!this.state.directRendering)) {
      warn("Masks are not supported when using getPicture()", new Object[0]);
    }
    if ((this.state.style.opacity.floatValue() < 1.0F) || ((this.state.style.mask != null) && (this.state.directRendering))) {}
    for (int i = 1; i == 0; i = 0) {
      return false;
    }
    this.canvas.saveLayerAlpha(null, clamp255(this.state.style.opacity.floatValue()), 4);
    this.stateStack.push(this.state);
    this.state = ((RendererState)this.state.clone());
    if ((this.state.style.mask != null) && (this.state.directRendering))
    {
      SVG.SvgObject localSvgObject = this.document.resolveIRI(this.state.style.mask);
      if ((localSvgObject == null) || (!(localSvgObject instanceof SVG.Mask)))
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = this.state.style.mask;
        error("Mask reference '%s' not found", arrayOfObject);
        this.state.style.mask = null;
        return true;
      }
      this.canvasStack.push(this.canvas);
      duplicateCanvas();
    }
    return true;
  }
  
  private void render(SVG.Svg paramSvg, SVG.Length paramLength1, SVG.Length paramLength2)
  {
    render(paramSvg, paramLength1, paramLength2, paramSvg.viewBox, paramSvg.preserveAspectRatio);
  }
  
  private void render(SVG.Svg paramSvg, SVG.Length paramLength1, SVG.Length paramLength2, SVG.Box paramBox, PreserveAspectRatio paramPreserveAspectRatio)
  {
    if (((paramLength1 != null) && (paramLength1.isZero())) || ((paramLength2 != null) && (paramLength2.isZero()))) {}
    do
    {
      return;
      if (paramPreserveAspectRatio == null)
      {
        if (paramSvg.preserveAspectRatio == null) {
          break;
        }
        paramPreserveAspectRatio = paramSvg.preserveAspectRatio;
      }
      updateStyleForElement(this.state, paramSvg);
    } while (!display());
    SVG.SvgContainer localSvgContainer = paramSvg.parent;
    float f1 = 0.0F;
    float f2 = 0.0F;
    label91:
    label108:
    SVG.Box localBox;
    float f3;
    label125:
    float f4;
    if (localSvgContainer != null)
    {
      if (paramSvg.x != null)
      {
        f1 = paramSvg.x.floatValueX(this);
        if (paramSvg.y == null) {
          break label313;
        }
        f2 = paramSvg.y.floatValueY(this);
      }
    }
    else
    {
      localBox = getCurrentViewPortInUserUnits();
      if (paramLength1 == null) {
        break label319;
      }
      f3 = paramLength1.floatValueX(this);
      if (paramLength2 == null) {
        break label329;
      }
      f4 = paramLength2.floatValueY(this);
      label136:
      this.state.viewPort = new SVG.Box(f1, f2, f3, f4);
      if (!this.state.style.overflow.booleanValue()) {
        setClipRect(this.state.viewPort.minX, this.state.viewPort.minY, this.state.viewPort.width, this.state.viewPort.height);
      }
      checkForClipPath(paramSvg, this.state.viewPort);
      if (paramBox == null) {
        break label339;
      }
      this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, paramBox, paramPreserveAspectRatio));
      this.state.viewBox = paramSvg.viewBox;
    }
    for (;;)
    {
      boolean bool = pushLayer();
      viewportFill();
      renderChildren(paramSvg, true);
      if (bool) {
        popLayer(paramSvg);
      }
      updateParentBoundingBox(paramSvg);
      return;
      paramPreserveAspectRatio = PreserveAspectRatio.LETTERBOX;
      break;
      f1 = 0.0F;
      break label91;
      label313:
      f2 = 0.0F;
      break label108;
      label319:
      f3 = localBox.width;
      break label125;
      label329:
      f4 = localBox.height;
      break label136;
      label339:
      this.canvas.translate(f1, f2);
    }
  }
  
  private void render(SVG.SvgObject paramSvgObject)
  {
    if ((paramSvgObject instanceof SVG.NotDirectlyRendered)) {
      return;
    }
    statePush();
    checkXMLSpaceAttribute(paramSvgObject);
    if ((paramSvgObject instanceof SVG.Svg))
    {
      SVG.Svg localSvg2 = (SVG.Svg)paramSvgObject;
      render(localSvg2, localSvg2.width, localSvg2.height);
    }
    label212:
    label231:
    label361:
    label367:
    label377:
    label505:
    SVG.Image localImage;
    label302:
    label317:
    label487:
    PreserveAspectRatio localPreserveAspectRatio1;
    label387:
    label410:
    label543:
    label721:
    label736:
    label1011:
    String str;
    label520:
    Bitmap localBitmap1;
    label1032:
    do
    {
      do
      {
        for (;;)
        {
          statePop();
          return;
          if ((paramSvgObject instanceof SVG.Use))
          {
            SVG.Use localUse = (SVG.Use)paramSvgObject;
            if (((localUse.width == null) || (!localUse.width.isZero())) && ((localUse.height == null) || (!localUse.height.isZero())))
            {
              updateStyleForElement(this.state, localUse);
              if (display())
              {
                SVG.SvgObject localSvgObject = localUse.document.resolveIRI(localUse.href);
                if (localSvgObject == null)
                {
                  Object[] arrayOfObject2 = new Object[1];
                  arrayOfObject2[0] = localUse.href;
                  error("Use reference '%s' not found", arrayOfObject2);
                }
                else
                {
                  if (localUse.transform != null) {
                    this.canvas.concat(localUse.transform);
                  }
                  Matrix localMatrix = new Matrix();
                  float f14;
                  float f15;
                  boolean bool12;
                  SVG.Svg localSvg1;
                  SVG.Length localLength4;
                  SVG.Length localLength5;
                  if (localUse.x != null)
                  {
                    f14 = localUse.x.floatValueX(this);
                    if (localUse.y == null) {
                      break label361;
                    }
                    f15 = localUse.y.floatValueY(this);
                    localMatrix.preTranslate(f14, f15);
                    this.canvas.concat(localMatrix);
                    checkForClipPath(localUse);
                    bool12 = pushLayer();
                    parentPush(localUse);
                    if (!(localSvgObject instanceof SVG.Svg)) {
                      break label387;
                    }
                    statePush();
                    localSvg1 = (SVG.Svg)localSvgObject;
                    if (localUse.width == null) {
                      break label367;
                    }
                    localLength4 = localUse.width;
                    if (localUse.height == null) {
                      break label377;
                    }
                    localLength5 = localUse.height;
                    render(localSvg1, localLength4, localLength5);
                    statePop();
                  }
                  for (;;)
                  {
                    parentPop();
                    if (bool12) {
                      popLayer(localUse);
                    }
                    updateParentBoundingBox(localUse);
                    break;
                    f14 = 0.0F;
                    break label212;
                    f15 = 0.0F;
                    break label231;
                    localLength4 = localSvg1.width;
                    break label302;
                    localLength5 = localSvg1.height;
                    break label317;
                    if ((localSvgObject instanceof SVG.Symbol))
                    {
                      SVG.Length localLength2;
                      if (localUse.width != null)
                      {
                        localLength2 = localUse.width;
                        if (localUse.height == null) {
                          break label487;
                        }
                      }
                      SVG.Symbol localSymbol;
                      for (SVG.Length localLength3 = localUse.height;; localLength3 = new SVG.Length(100.0F, SVG.Unit.percent))
                      {
                        statePush();
                        localSymbol = (SVG.Symbol)localSvgObject;
                        if (((localLength2 == null) || (!localLength2.isZero())) && ((localLength3 == null) || (!localLength3.isZero()))) {
                          break label505;
                        }
                        statePop();
                        break;
                        localLength2 = new SVG.Length(100.0F, SVG.Unit.percent);
                        break label410;
                      }
                      PreserveAspectRatio localPreserveAspectRatio2;
                      float f16;
                      if (localSymbol.preserveAspectRatio != null)
                      {
                        localPreserveAspectRatio2 = localSymbol.preserveAspectRatio;
                        updateStyleForElement(this.state, localSymbol);
                        if (localLength2 == null) {
                          break label721;
                        }
                        f16 = localLength2.floatValueX(this);
                        if (localLength3 == null) {
                          break label736;
                        }
                      }
                      for (float f17 = localLength3.floatValueX(this);; f17 = this.state.viewPort.height)
                      {
                        this.state.viewPort = new SVG.Box(0.0F, 0.0F, f16, f17);
                        if (!this.state.style.overflow.booleanValue()) {
                          setClipRect(this.state.viewPort.minX, this.state.viewPort.minY, this.state.viewPort.width, this.state.viewPort.height);
                        }
                        if (localSymbol.viewBox != null)
                        {
                          this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, localSymbol.viewBox, localPreserveAspectRatio2));
                          this.state.viewBox = localSymbol.viewBox;
                        }
                        boolean bool13 = pushLayer();
                        renderChildren(localSymbol, true);
                        if (bool13) {
                          popLayer(localSymbol);
                        }
                        updateParentBoundingBox(localSymbol);
                        break;
                        localPreserveAspectRatio2 = PreserveAspectRatio.LETTERBOX;
                        break label520;
                        f16 = this.state.viewPort.width;
                        break label543;
                      }
                    }
                    render(localSvgObject);
                  }
                }
              }
            }
          }
          else if ((paramSvgObject instanceof SVG.Switch))
          {
            SVG.Switch localSwitch = (SVG.Switch)paramSvgObject;
            updateStyleForElement(this.state, localSwitch);
            if (display())
            {
              if (localSwitch.transform != null) {
                this.canvas.concat(localSwitch.transform);
              }
              checkForClipPath(localSwitch);
              boolean bool11 = pushLayer();
              renderSwitchChild(localSwitch);
              if (bool11) {
                popLayer(localSwitch);
              }
              updateParentBoundingBox(localSwitch);
            }
          }
          else
          {
            if (!(paramSvgObject instanceof SVG.Group)) {
              break;
            }
            SVG.Group localGroup = (SVG.Group)paramSvgObject;
            updateStyleForElement(this.state, localGroup);
            if (display())
            {
              if (localGroup.transform != null) {
                this.canvas.concat(localGroup.transform);
              }
              checkForClipPath(localGroup);
              boolean bool10 = pushLayer();
              renderChildren(localGroup, true);
              if (bool10) {
                popLayer(localGroup);
              }
              updateParentBoundingBox(localGroup);
            }
          }
        }
        if (!(paramSvgObject instanceof SVG.Image)) {
          break;
        }
        localImage = (SVG.Image)paramSvgObject;
      } while ((localImage.width == null) || (localImage.width.isZero()) || (localImage.height == null) || (localImage.height.isZero()) || (localImage.href == null));
      if (localImage.preserveAspectRatio == null) {
        break;
      }
      localPreserveAspectRatio1 = localImage.preserveAspectRatio;
      str = localImage.href;
      if (str.startsWith("data:")) {
        break label1089;
      }
      localBitmap1 = null;
      if (localBitmap1 != null) {
        break label3311;
      }
    } while (this.document.fileResolver == null);
    label1089:
    label1252:
    label2536:
    label3054:
    label3311:
    for (Bitmap localBitmap2 = null;; localBitmap2 = localBitmap1)
    {
      if (localBitmap2 == null)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = localImage.href;
        error("Could not locate image '%s'", arrayOfObject1);
        break;
        localPreserveAspectRatio1 = PreserveAspectRatio.LETTERBOX;
        break label1011;
        if (str.length() < 14)
        {
          localBitmap1 = null;
          break label1032;
        }
        int j = str.indexOf(',');
        if ((j == -1) || (j < 12))
        {
          localBitmap1 = null;
          break label1032;
        }
        if (!";base64".equals(str.substring(j - 7, j)))
        {
          localBitmap1 = null;
          break label1032;
        }
        byte[] arrayOfByte = Base64.decode(str.substring(j + 1), 0);
        localBitmap1 = BitmapFactory.decodeByteArray(arrayOfByte, 0, arrayOfByte.length);
        break label1032;
      }
      updateStyleForElement(this.state, localImage);
      if ((!display()) || (!visible())) {
        break;
      }
      if (localImage.transform != null) {
        this.canvas.concat(localImage.transform);
      }
      float f10;
      if (localImage.x != null)
      {
        f10 = localImage.x.floatValueX(this);
        if (localImage.y == null) {
          break label1485;
        }
      }
      for (float f11 = localImage.y.floatValueY(this);; f11 = 0.0F)
      {
        float f12 = localImage.width.floatValueX(this);
        float f13 = localImage.height.floatValueX(this);
        this.state.viewPort = new SVG.Box(f10, f11, f12, f13);
        if (!this.state.style.overflow.booleanValue()) {
          setClipRect(this.state.viewPort.minX, this.state.viewPort.minY, this.state.viewPort.width, this.state.viewPort.height);
        }
        localImage.boundingBox = new SVG.Box(0.0F, 0.0F, localBitmap2.getWidth(), localBitmap2.getHeight());
        this.canvas.concat(calculateViewBoxTransform(this.state.viewPort, localImage.boundingBox, localPreserveAspectRatio1));
        updateParentBoundingBox(localImage);
        checkForClipPath(localImage);
        boolean bool9 = pushLayer();
        viewportFill();
        this.canvas.drawBitmap(localBitmap2, 0.0F, 0.0F, new Paint());
        if (!bool9) {
          break;
        }
        popLayer(localImage);
        break;
        f10 = 0.0F;
        break label1252;
      }
      if ((paramSvgObject instanceof SVG.Path))
      {
        SVG.Path localPath = (SVG.Path)paramSvgObject;
        if (localPath.d == null) {
          break;
        }
        updateStyleForElement(this.state, localPath);
        if ((!display()) || (!visible()) || ((!this.state.hasStroke) && (!this.state.hasFill))) {
          break;
        }
        if (localPath.transform != null) {
          this.canvas.concat(localPath.transform);
        }
        Path localPath7 = new PathConverter(localPath.d).path;
        if (localPath.boundingBox == null) {
          localPath.boundingBox = calculatePathBounds(localPath7);
        }
        updateParentBoundingBox(localPath);
        checkForGradiantsAndPatterns(localPath);
        checkForClipPath(localPath);
        boolean bool8 = pushLayer();
        if (this.state.hasFill) {
          if (this.state.style.fillRule != null) {
            switch (1.$SwitchMap$com$caverock$androidsvg$SVG$Style$FillRule[this.state.style.fillRule.ordinal()])
            {
            }
          }
        }
        for (Path.FillType localFillType = Path.FillType.WINDING;; localFillType = Path.FillType.EVEN_ODD)
        {
          localPath7.setFillType(localFillType);
          doFilledPath(localPath, localPath7);
          if (this.state.hasStroke) {
            doStroke(localPath7);
          }
          renderMarkers(localPath);
          if (!bool8) {
            break;
          }
          popLayer(localPath);
          break;
        }
      }
      if ((paramSvgObject instanceof SVG.Rect))
      {
        SVG.Rect localRect = (SVG.Rect)paramSvgObject;
        if ((localRect.width == null) || (localRect.height == null) || (localRect.width.isZero()) || (localRect.height.isZero())) {
          break;
        }
        updateStyleForElement(this.state, localRect);
        if ((!display()) || (!visible())) {
          break;
        }
        if (localRect.transform != null) {
          this.canvas.concat(localRect.transform);
        }
        Path localPath6 = makePathAndBoundingBox(localRect);
        updateParentBoundingBox(localRect);
        checkForGradiantsAndPatterns(localRect);
        checkForClipPath(localRect);
        boolean bool7 = pushLayer();
        if (this.state.hasFill) {
          doFilledPath(localRect, localPath6);
        }
        if (this.state.hasStroke) {
          doStroke(localPath6);
        }
        if (!bool7) {
          break;
        }
        popLayer(localRect);
        break;
      }
      if ((paramSvgObject instanceof SVG.Circle))
      {
        SVG.Circle localCircle = (SVG.Circle)paramSvgObject;
        if ((localCircle.r == null) || (localCircle.r.isZero())) {
          break;
        }
        updateStyleForElement(this.state, localCircle);
        if ((!display()) || (!visible())) {
          break;
        }
        if (localCircle.transform != null) {
          this.canvas.concat(localCircle.transform);
        }
        Path localPath5 = makePathAndBoundingBox(localCircle);
        updateParentBoundingBox(localCircle);
        checkForGradiantsAndPatterns(localCircle);
        checkForClipPath(localCircle);
        boolean bool6 = pushLayer();
        if (this.state.hasFill) {
          doFilledPath(localCircle, localPath5);
        }
        if (this.state.hasStroke) {
          doStroke(localPath5);
        }
        if (!bool6) {
          break;
        }
        popLayer(localCircle);
        break;
      }
      if ((paramSvgObject instanceof SVG.Ellipse))
      {
        SVG.Ellipse localEllipse = (SVG.Ellipse)paramSvgObject;
        if ((localEllipse.rx == null) || (localEllipse.ry == null) || (localEllipse.rx.isZero()) || (localEllipse.ry.isZero())) {
          break;
        }
        updateStyleForElement(this.state, localEllipse);
        if ((!display()) || (!visible())) {
          break;
        }
        if (localEllipse.transform != null) {
          this.canvas.concat(localEllipse.transform);
        }
        Path localPath4 = makePathAndBoundingBox(localEllipse);
        updateParentBoundingBox(localEllipse);
        checkForGradiantsAndPatterns(localEllipse);
        checkForClipPath(localEllipse);
        boolean bool5 = pushLayer();
        if (this.state.hasFill) {
          doFilledPath(localEllipse, localPath4);
        }
        if (this.state.hasStroke) {
          doStroke(localPath4);
        }
        if (!bool5) {
          break;
        }
        popLayer(localEllipse);
        break;
      }
      if ((paramSvgObject instanceof SVG.Line))
      {
        SVG.Line localLine = (SVG.Line)paramSvgObject;
        updateStyleForElement(this.state, localLine);
        if ((!display()) || (!visible()) || (!this.state.hasStroke)) {
          break;
        }
        if (localLine.transform != null) {
          this.canvas.concat(localLine.transform);
        }
        float f6;
        float f7;
        float f8;
        float f9;
        if (localLine.x1 == null)
        {
          f6 = 0.0F;
          if (localLine.y1 != null) {
            break label2522;
          }
          f7 = 0.0F;
          if (localLine.x2 != null) {
            break label2536;
          }
          f8 = 0.0F;
          SVG.Length localLength1 = localLine.y2;
          f9 = 0.0F;
          if (localLength1 != null) {
            break label2550;
          }
        }
        for (;;)
        {
          if (localLine.boundingBox == null) {
            localLine.boundingBox = new SVG.Box(Math.min(f6, f7), Math.min(f7, f9), Math.abs(f8 - f6), Math.abs(f9 - f7));
          }
          Path localPath3 = new Path();
          localPath3.moveTo(f6, f7);
          localPath3.lineTo(f8, f9);
          updateParentBoundingBox(localLine);
          checkForGradiantsAndPatterns(localLine);
          checkForClipPath(localLine);
          boolean bool4 = pushLayer();
          doStroke(localPath3);
          renderMarkers(localLine);
          if (!bool4) {
            break;
          }
          popLayer(localLine);
          break;
          f6 = localLine.x1.floatValueX(this);
          break label2344;
          f7 = localLine.y1.floatValueY(this);
          break label2355;
          f8 = localLine.x2.floatValueX(this);
          break label2366;
          label2550:
          f9 = localLine.y2.floatValueY(this);
        }
      }
      if ((paramSvgObject instanceof SVG.Polygon))
      {
        SVG.Polygon localPolygon = (SVG.Polygon)paramSvgObject;
        updateStyleForElement(this.state, localPolygon);
        if ((!display()) || (!visible()) || ((!this.state.hasStroke) && (!this.state.hasFill))) {
          break;
        }
        if (localPolygon.transform != null) {
          this.canvas.concat(localPolygon.transform);
        }
        if (localPolygon.points.length < 2) {
          break;
        }
        Path localPath2 = makePathAndBoundingBox(localPolygon);
        updateParentBoundingBox(localPolygon);
        checkForGradiantsAndPatterns(localPolygon);
        checkForClipPath(localPolygon);
        boolean bool3 = pushLayer();
        if (this.state.hasFill) {
          doFilledPath(localPolygon, localPath2);
        }
        if (this.state.hasStroke) {
          doStroke(localPath2);
        }
        renderMarkers(localPolygon);
        if (!bool3) {
          break;
        }
        popLayer(localPolygon);
        break;
      }
      if ((paramSvgObject instanceof SVG.PolyLine))
      {
        SVG.PolyLine localPolyLine = (SVG.PolyLine)paramSvgObject;
        updateStyleForElement(this.state, localPolyLine);
        if ((!display()) || (!visible()) || ((!this.state.hasStroke) && (!this.state.hasFill))) {
          break;
        }
        if (localPolyLine.transform != null) {
          this.canvas.concat(localPolyLine.transform);
        }
        if (localPolyLine.points.length < 2) {
          break;
        }
        Path localPath1 = makePathAndBoundingBox(localPolyLine);
        updateParentBoundingBox(localPolyLine);
        checkForGradiantsAndPatterns(localPolyLine);
        checkForClipPath(localPolyLine);
        boolean bool2 = pushLayer();
        if (this.state.hasFill) {
          doFilledPath(localPolyLine, localPath1);
        }
        if (this.state.hasStroke) {
          doStroke(localPath1);
        }
        renderMarkers(localPolyLine);
        if (!bool2) {
          break;
        }
        popLayer(localPolyLine);
        break;
      }
      if (!(paramSvgObject instanceof SVG.Text)) {
        break;
      }
      SVG.Text localText = (SVG.Text)paramSvgObject;
      updateStyleForElement(this.state, localText);
      if (!display()) {
        break;
      }
      if (localText.transform != null) {
        this.canvas.concat(localText.transform);
      }
      float f1;
      float f2;
      float f3;
      float f4;
      float f5;
      if ((localText.x == null) || (localText.x.size() == 0))
      {
        f1 = 0.0F;
        if ((localText.y != null) && (localText.y.size() != 0)) {
          break label3237;
        }
        f2 = 0.0F;
        if ((localText.dx != null) && (localText.dx.size() != 0)) {
          break label3259;
        }
        f3 = 0.0F;
        List localList = localText.dy;
        f4 = 0.0F;
        if (localList != null)
        {
          int i = localText.dy.size();
          f4 = 0.0F;
          if (i != 0) {
            break label3281;
          }
        }
        SVG.Style.TextAnchor localTextAnchor = getAnchorPosition();
        if (localTextAnchor != SVG.Style.TextAnchor.Start)
        {
          f5 = calculateTextWidth(localText);
          if (localTextAnchor != SVG.Style.TextAnchor.Middle) {
            break label3303;
          }
        }
      }
      for (f1 -= f5 / 2.0F;; f1 -= f5)
      {
        if (localText.boundingBox == null)
        {
          TextBoundsCalculator localTextBoundsCalculator = new TextBoundsCalculator(f1, f2);
          enumerateTextSpans(localText, localTextBoundsCalculator);
          localText.boundingBox = new SVG.Box(localTextBoundsCalculator.bbox.left, localTextBoundsCalculator.bbox.top, localTextBoundsCalculator.bbox.width(), localTextBoundsCalculator.bbox.height());
        }
        updateParentBoundingBox(localText);
        checkForGradiantsAndPatterns(localText);
        checkForClipPath(localText);
        boolean bool1 = pushLayer();
        enumerateTextSpans(localText, new PlainTextDrawer(f1 + f3, f4 + f2));
        if (!bool1) {
          break;
        }
        popLayer(localText);
        break;
        f1 = ((SVG.Length)localText.x.get(0)).floatValueX(this);
        break label2977;
        f2 = ((SVG.Length)localText.y.get(0)).floatValueY(this);
        break label2999;
        f3 = ((SVG.Length)localText.dx.get(0)).floatValueX(this);
        break label3021;
        f4 = ((SVG.Length)localText.dy.get(0)).floatValueY(this);
        break label3054;
      }
    }
  }
  
  private void renderChildren(SVG.SvgContainer paramSvgContainer, boolean paramBoolean)
  {
    if (paramBoolean) {
      parentPush(paramSvgContainer);
    }
    Iterator localIterator = paramSvgContainer.getChildren().iterator();
    while (localIterator.hasNext()) {
      render((SVG.SvgObject)localIterator.next());
    }
    if (paramBoolean) {
      parentPop();
    }
  }
  
  private void renderMarker(SVG.Marker paramMarker, MarkerVector paramMarkerVector)
  {
    statePush();
    Float localFloat = paramMarker.orient;
    float f1 = 0.0F;
    if (localFloat != null)
    {
      if (!Float.isNaN(paramMarker.orient.floatValue())) {
        break label527;
      }
      if (paramMarkerVector.dx == 0.0F)
      {
        boolean bool2 = paramMarkerVector.dy < 0.0F;
        f1 = 0.0F;
        if (!bool2) {}
      }
      else
      {
        f1 = (float)Math.toDegrees(Math.atan2(paramMarkerVector.dy, paramMarkerVector.dx));
      }
    }
    float f2;
    label83:
    Matrix localMatrix;
    float f3;
    label150:
    float f4;
    label167:
    float f5;
    label184:
    float f6;
    label201:
    float f7;
    float f8;
    PreserveAspectRatio localPreserveAspectRatio;
    label245:
    float f9;
    label276:
    float f10;
    float f11;
    label388:
    float f12;
    if (paramMarker.markerUnitsAreUser)
    {
      f2 = 1.0F;
      this.state = findInheritFromAncestorState(paramMarker);
      localMatrix = new Matrix();
      localMatrix.preTranslate(paramMarkerVector.x, paramMarkerVector.y);
      localMatrix.preRotate(f1);
      localMatrix.preScale(f2, f2);
      if (paramMarker.refX == null) {
        break label561;
      }
      f3 = paramMarker.refX.floatValueX(this);
      if (paramMarker.refY == null) {
        break label567;
      }
      f4 = paramMarker.refY.floatValueY(this);
      if (paramMarker.markerWidth == null) {
        break label573;
      }
      f5 = paramMarker.markerWidth.floatValueX(this);
      if (paramMarker.markerHeight == null) {
        break label581;
      }
      f6 = paramMarker.markerHeight.floatValueY(this);
      if (paramMarker.viewBox == null) {
        break label661;
      }
      f7 = f5 / paramMarker.viewBox.width;
      f8 = f6 / paramMarker.viewBox.height;
      if (paramMarker.preserveAspectRatio == null) {
        break label589;
      }
      localPreserveAspectRatio = paramMarker.preserveAspectRatio;
      if (!localPreserveAspectRatio.equals(PreserveAspectRatio.STRETCH))
      {
        if (localPreserveAspectRatio.scale != PreserveAspectRatio.Scale.Slice) {
          break label597;
        }
        f8 = Math.max(f7, f8);
        f7 = f8;
      }
      localMatrix.preTranslate(f7 * -f3, f8 * -f4);
      this.canvas.concat(localMatrix);
      f9 = f7 * paramMarker.viewBox.width;
      f10 = f8 * paramMarker.viewBox.height;
      int i = 1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[localPreserveAspectRatio.alignment.ordinal()];
      f11 = 0.0F;
      switch (i)
      {
      default: 
        int j = 1.$SwitchMap$com$caverock$androidsvg$PreserveAspectRatio$Alignment[localPreserveAspectRatio.alignment.ordinal()];
        f12 = 0.0F;
        switch (j)
        {
        case 4: 
        default: 
          label448:
          if (!this.state.style.overflow.booleanValue()) {
            setClipRect(f11, f12, f5, f6);
          }
          localMatrix.reset();
          localMatrix.preScale(f7, f8);
          this.canvas.concat(localMatrix);
        }
        break;
      }
    }
    for (;;)
    {
      boolean bool1 = pushLayer();
      renderChildren(paramMarker, false);
      if (bool1) {
        popLayer(paramMarker);
      }
      statePop();
      return;
      label527:
      f1 = paramMarker.orient.floatValue();
      break;
      f2 = this.state.style.strokeWidth.floatValue(this.dpi);
      break label83;
      label561:
      f3 = 0.0F;
      break label150;
      label567:
      f4 = 0.0F;
      break label167;
      label573:
      f5 = 3.0F;
      break label184;
      label581:
      f6 = 3.0F;
      break label201;
      label589:
      localPreserveAspectRatio = PreserveAspectRatio.LETTERBOX;
      break label245;
      label597:
      f8 = Math.min(f7, f8);
      break label276;
      f11 = 0.0F - (f5 - f9) / 2.0F;
      break label388;
      f11 = 0.0F - (f5 - f9);
      break label388;
      f12 = 0.0F - (f6 - f10) / 2.0F;
      break label448;
      f12 = 0.0F - (f6 - f10);
      break label448;
      label661:
      localMatrix.preTranslate(-f3, -f4);
      this.canvas.concat(localMatrix);
      if (!this.state.style.overflow.booleanValue()) {
        setClipRect(0.0F, 0.0F, f5, f6);
      }
    }
  }
  
  private void renderMarkers(SVG.GraphicsElement paramGraphicsElement)
  {
    if ((this.state.style.markerStart == null) && (this.state.style.markerMid == null) && (this.state.style.markerEnd == null)) {}
    label87:
    SVG.Marker localMarker3;
    label138:
    label189:
    Object localObject;
    int i;
    label370:
    label508:
    do
    {
      for (;;)
      {
        return;
        String str1 = this.state.style.markerStart;
        SVG.Marker localMarker1 = null;
        SVG.Marker localMarker2;
        if (str1 != null)
        {
          SVG.SvgObject localSvgObject3 = paramGraphicsElement.document.resolveIRI(this.state.style.markerStart);
          if (localSvgObject3 != null) {
            localMarker1 = (SVG.Marker)localSvgObject3;
          }
        }
        else
        {
          String str2 = this.state.style.markerMid;
          localMarker2 = null;
          if (str2 != null)
          {
            SVG.SvgObject localSvgObject2 = paramGraphicsElement.document.resolveIRI(this.state.style.markerMid);
            if (localSvgObject2 == null) {
              break label370;
            }
            localMarker2 = (SVG.Marker)localSvgObject2;
          }
          String str3 = this.state.style.markerEnd;
          localMarker3 = null;
          if (str3 != null)
          {
            SVG.SvgObject localSvgObject1 = paramGraphicsElement.document.resolveIRI(this.state.style.markerEnd);
            if (localSvgObject1 == null) {
              break label404;
            }
            localMarker3 = (SVG.Marker)localSvgObject1;
          }
          if (!(paramGraphicsElement instanceof SVG.Path)) {
            break label438;
          }
          localObject = new MarkerPositionCalculator(((SVG.Path)paramGraphicsElement).d).markers;
        }
        for (;;)
        {
          if (localObject == null) {
            break label643;
          }
          i = ((List)localObject).size();
          if (i == 0) {
            break;
          }
          SVG.Style localStyle1 = this.state.style;
          SVG.Style localStyle2 = this.state.style;
          this.state.style.markerEnd = null;
          localStyle2.markerMid = null;
          localStyle1.markerStart = null;
          if (localMarker1 != null) {
            renderMarker(localMarker1, (MarkerVector)((List)localObject).get(0));
          }
          if (localMarker2 == null) {
            break label645;
          }
          for (int j = 1; j < i - 1; j++) {
            renderMarker(localMarker2, (MarkerVector)((List)localObject).get(j));
          }
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = this.state.style.markerStart;
          error("Marker reference '%s' not found", arrayOfObject3);
          localMarker1 = null;
          break label87;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = this.state.style.markerMid;
          error("Marker reference '%s' not found", arrayOfObject2);
          localMarker2 = null;
          break label138;
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = this.state.style.markerEnd;
          error("Marker reference '%s' not found", arrayOfObject1);
          localMarker3 = null;
          break label189;
          if ((paramGraphicsElement instanceof SVG.Line))
          {
            SVG.Line localLine = (SVG.Line)paramGraphicsElement;
            float f1;
            float f2;
            float f3;
            if (localLine.x1 != null)
            {
              f1 = localLine.x1.floatValueX(this);
              if (localLine.y1 == null) {
                break label614;
              }
              f2 = localLine.y1.floatValueY(this);
              if (localLine.x2 == null) {
                break label620;
              }
              f3 = localLine.x2.floatValueX(this);
              if (localLine.y2 == null) {
                break label626;
              }
            }
            for (float f4 = localLine.y2.floatValueY(this);; f4 = 0.0F)
            {
              localObject = new ArrayList(2);
              ((List)localObject).add(new MarkerVector(f1, f2, f3 - f1, f4 - f2));
              float f5 = f3 - f1;
              float f6 = f4 - f2;
              ((List)localObject).add(new MarkerVector(f3, f4, f5, f6));
              break;
              f1 = 0.0F;
              break label470;
              f2 = 0.0F;
              break label489;
              f3 = 0.0F;
              break label508;
            }
          }
          localObject = calculateMarkerPositions((SVG.PolyLine)paramGraphicsElement);
        }
      }
    } while (localMarker3 == null);
    label404:
    label438:
    label470:
    label489:
    label620:
    label626:
    renderMarker(localMarker3, (MarkerVector)((List)localObject).get(i - 1));
    label614:
  }
  
  private void renderSwitchChild(SVG.Switch paramSwitch)
  {
    String str = Locale.getDefault().getLanguage();
    SVGExternalFileResolver localSVGExternalFileResolver = this.document.fileResolver;
    Iterator localIterator1 = paramSwitch.children.iterator();
    while (localIterator1.hasNext())
    {
      SVG.SvgObject localSvgObject = (SVG.SvgObject)localIterator1.next();
      if ((localSvgObject instanceof SVG.SvgConditional))
      {
        SVG.SvgConditional localSvgConditional = (SVG.SvgConditional)localSvgObject;
        if (localSvgConditional.getRequiredExtensions() == null)
        {
          Set localSet1 = localSvgConditional.getSystemLanguage();
          if ((localSet1 == null) || ((!localSet1.isEmpty()) && (localSet1.contains(str))))
          {
            Set localSet2 = localSvgConditional.getRequiredFeatures();
            if (localSet2 != null)
            {
              if (supportedFeatures == null) {
                initialiseSupportedFeaturesMap();
              }
              if ((localSet2.isEmpty()) || (!supportedFeatures.containsAll(localSet2))) {
                break;
              }
            }
            else
            {
              Set localSet3 = localSvgConditional.getRequiredFormats();
              if (localSet3 != null)
              {
                if ((localSet3.isEmpty()) || (localSVGExternalFileResolver == null)) {
                  continue;
                }
                Iterator localIterator3 = localSet3.iterator();
                if (localIterator3.hasNext())
                {
                  localIterator3.next();
                  continue;
                }
              }
              Set localSet4 = localSvgConditional.getRequiredFonts();
              if (localSet4 != null)
              {
                if ((localSet4.isEmpty()) || (localSVGExternalFileResolver == null)) {
                  continue;
                }
                Iterator localIterator2 = localSet4.iterator();
                if (localIterator2.hasNext())
                {
                  localIterator2.next();
                  this.state.style.fontWeight.intValue();
                  String.valueOf(this.state.style.fontStyle);
                  continue;
                }
              }
              render(localSvgObject);
            }
          }
        }
      }
    }
  }
  
  private void setClipRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    float f1 = paramFloat1;
    float f2 = paramFloat2;
    float f3 = paramFloat1 + paramFloat3;
    float f4 = paramFloat2 + paramFloat4;
    if (this.state.style.clip != null)
    {
      f1 += this.state.style.clip.left.floatValueX(this);
      f2 += this.state.style.clip.top.floatValueY(this);
      f3 -= this.state.style.clip.right.floatValueX(this);
      f4 -= this.state.style.clip.bottom.floatValueY(this);
    }
    this.canvas.clipRect(f1, f2, f3, f4);
  }
  
  private static void setPaintColour(RendererState paramRendererState, boolean paramBoolean, SVG.SvgPaint paramSvgPaint)
  {
    Float localFloat;
    float f;
    if (paramBoolean)
    {
      localFloat = paramRendererState.style.fillOpacity;
      f = localFloat.floatValue();
      if (!(paramSvgPaint instanceof SVG.Colour)) {
        break label72;
      }
    }
    int j;
    for (int i = ((SVG.Colour)paramSvgPaint).colour;; i = paramRendererState.style.color.colour)
    {
      j = i | clamp255(f) << 24;
      if (!paramBoolean) {
        break label94;
      }
      paramRendererState.fillPaint.setColor(j);
      label72:
      do
      {
        return;
        localFloat = paramRendererState.style.strokeOpacity;
        break;
      } while (!(paramSvgPaint instanceof SVG.CurrentColor));
    }
    label94:
    paramRendererState.strokePaint.setColor(j);
  }
  
  private void statePop()
  {
    this.canvas.restore();
    this.state = ((RendererState)this.stateStack.pop());
  }
  
  private void statePush()
  {
    this.canvas.save();
    this.stateStack.push(this.state);
    this.state = ((RendererState)this.state.clone());
  }
  
  private String textXMLSpaceTransform(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.state.spacePreserve) {
      return paramString.replaceAll("[\\n\\t]", " ");
    }
    String str = paramString.replaceAll("\\n", "").replaceAll("\\t", " ");
    if (paramBoolean1) {
      str = str.replaceAll("^\\s+", "");
    }
    if (paramBoolean2) {
      str = str.replaceAll("\\s+$", "");
    }
    return str.replaceAll("\\s{2,}", " ");
  }
  
  private void updateParentBoundingBox(SVG.SvgElement paramSvgElement)
  {
    if (paramSvgElement.parent == null) {}
    SVG.Box localBox1;
    SVG.Box localBox2;
    do
    {
      Matrix localMatrix;
      do
      {
        do
        {
          return;
        } while (paramSvgElement.boundingBox == null);
        localMatrix = new Matrix();
      } while (!((Matrix)this.matrixStack.peek()).invert(localMatrix));
      float[] arrayOfFloat = new float[8];
      arrayOfFloat[0] = paramSvgElement.boundingBox.minX;
      arrayOfFloat[1] = paramSvgElement.boundingBox.minY;
      arrayOfFloat[2] = paramSvgElement.boundingBox.maxX();
      arrayOfFloat[3] = paramSvgElement.boundingBox.minY;
      arrayOfFloat[4] = paramSvgElement.boundingBox.maxX();
      arrayOfFloat[5] = paramSvgElement.boundingBox.maxY();
      arrayOfFloat[6] = paramSvgElement.boundingBox.minX;
      arrayOfFloat[7] = paramSvgElement.boundingBox.maxY();
      localMatrix.preConcat(this.canvas.getMatrix());
      localMatrix.mapPoints(arrayOfFloat);
      RectF localRectF = new RectF(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[0], arrayOfFloat[1]);
      for (int i = 2; i <= 6; i += 2)
      {
        if (arrayOfFloat[i] < localRectF.left) {
          localRectF.left = arrayOfFloat[i];
        }
        if (arrayOfFloat[i] > localRectF.right) {
          localRectF.right = arrayOfFloat[i];
        }
        if (arrayOfFloat[(i + 1)] < localRectF.top) {
          localRectF.top = arrayOfFloat[(i + 1)];
        }
        if (arrayOfFloat[(i + 1)] > localRectF.bottom) {
          localRectF.bottom = arrayOfFloat[(i + 1)];
        }
      }
      SVG.SvgElement localSvgElement = (SVG.SvgElement)this.parentStack.peek();
      if (localSvgElement.boundingBox == null)
      {
        localSvgElement.boundingBox = SVG.Box.fromLimits(localRectF.left, localRectF.top, localRectF.right, localRectF.bottom);
        return;
      }
      localBox1 = localSvgElement.boundingBox;
      localBox2 = SVG.Box.fromLimits(localRectF.left, localRectF.top, localRectF.right, localRectF.bottom);
      if (localBox2.minX < localBox1.minX) {
        localBox1.minX = localBox2.minX;
      }
      if (localBox2.minY < localBox1.minY) {
        localBox1.minY = localBox2.minY;
      }
      if (localBox2.maxX() > localBox1.maxX()) {
        localBox1.width = (localBox2.maxX() - localBox1.minX);
      }
    } while (localBox2.maxY() <= localBox1.maxY());
    localBox1.height = (localBox2.maxY() - localBox1.minY);
  }
  
  private void updateStyle(RendererState paramRendererState, SVG.Style paramStyle)
  {
    if (isSpecified(paramStyle, 4096L)) {
      paramRendererState.style.color = paramStyle.color;
    }
    if (isSpecified(paramStyle, 2048L)) {
      paramRendererState.style.opacity = paramStyle.opacity;
    }
    boolean bool6;
    boolean bool5;
    label172:
    label340:
    label506:
    boolean bool1;
    label400:
    boolean bool2;
    label649:
    label924:
    boolean bool3;
    label899:
    label958:
    Paint localPaint4;
    if (isSpecified(paramStyle, 1L))
    {
      paramRendererState.style.fill = paramStyle.fill;
      if (paramStyle.fill != null)
      {
        bool6 = true;
        paramRendererState.hasFill = bool6;
      }
    }
    else
    {
      if (isSpecified(paramStyle, 4L)) {
        paramRendererState.style.fillOpacity = paramStyle.fillOpacity;
      }
      if (isSpecified(paramStyle, 6149L)) {
        setPaintColour(paramRendererState, true, paramRendererState.style.fill);
      }
      if (isSpecified(paramStyle, 2L)) {
        paramRendererState.style.fillRule = paramStyle.fillRule;
      }
      if (isSpecified(paramStyle, 8L))
      {
        paramRendererState.style.stroke = paramStyle.stroke;
        if (paramStyle.stroke == null) {
          break label1416;
        }
        bool5 = true;
        paramRendererState.hasStroke = bool5;
      }
      if (isSpecified(paramStyle, 16L)) {
        paramRendererState.style.strokeOpacity = paramStyle.strokeOpacity;
      }
      if (isSpecified(paramStyle, 6168L)) {
        setPaintColour(paramRendererState, false, paramRendererState.style.stroke);
      }
      if (isSpecified(paramStyle, 34359738368L)) {
        paramRendererState.style.vectorEffect = paramStyle.vectorEffect;
      }
      if (isSpecified(paramStyle, 32L))
      {
        paramRendererState.style.strokeWidth = paramStyle.strokeWidth;
        paramRendererState.strokePaint.setStrokeWidth(paramRendererState.style.strokeWidth.floatValue(this));
      }
      if (isSpecified(paramStyle, 64L)) {
        paramRendererState.style.strokeLineCap = paramStyle.strokeLineCap;
      }
      switch (1.$SwitchMap$com$caverock$androidsvg$SVG$Style$LineCaps[paramStyle.strokeLineCap.ordinal()])
      {
      default: 
        if (isSpecified(paramStyle, 128L)) {
          paramRendererState.style.strokeLineJoin = paramStyle.strokeLineJoin;
        }
        switch (1.$SwitchMap$com$caverock$androidsvg$SVG$Style$LineJoin[paramStyle.strokeLineJoin.ordinal()])
        {
        default: 
          if (isSpecified(paramStyle, 256L))
          {
            paramRendererState.style.strokeMiterLimit = paramStyle.strokeMiterLimit;
            paramRendererState.strokePaint.setStrokeMiter(paramStyle.strokeMiterLimit.floatValue());
          }
          if (isSpecified(paramStyle, 512L)) {
            paramRendererState.style.strokeDashArray = paramStyle.strokeDashArray;
          }
          if (isSpecified(paramStyle, 1024L)) {
            paramRendererState.style.strokeDashOffset = paramStyle.strokeDashOffset;
          }
          if (isSpecified(paramStyle, 1536L))
          {
            if (paramRendererState.style.strokeDashArray == null) {
              paramRendererState.strokePaint.setPathEffect(null);
            }
          }
          else
          {
            if (isSpecified(paramStyle, 16384L))
            {
              float f1 = getCurrentFontSize();
              paramRendererState.style.fontSize = paramStyle.fontSize;
              paramRendererState.fillPaint.setTextSize(paramStyle.fontSize.floatValue(this, f1));
              paramRendererState.strokePaint.setTextSize(paramStyle.fontSize.floatValue(this, f1));
            }
            if (isSpecified(paramStyle, 8192L)) {
              paramRendererState.style.fontFamily = paramStyle.fontFamily;
            }
            if (isSpecified(paramStyle, 32768L))
            {
              if ((paramStyle.fontWeight.intValue() != -1) || (paramRendererState.style.fontWeight.intValue() <= 100)) {
                break label1658;
              }
              SVG.Style localStyle2 = paramRendererState.style;
              localStyle2.fontWeight = Integer.valueOf(-100 + localStyle2.fontWeight.intValue());
            }
            if (isSpecified(paramStyle, 65536L)) {
              paramRendererState.style.fontStyle = paramStyle.fontStyle;
            }
            if (isSpecified(paramStyle, 106496L))
            {
              List localList = paramRendererState.style.fontFamily;
              Typeface localTypeface = null;
              if (localList != null)
              {
                SVG localSVG = this.document;
                localTypeface = null;
                if (localSVG != null)
                {
                  SVGExternalFileResolver localSVGExternalFileResolver = this.document.fileResolver;
                  Iterator localIterator = paramRendererState.style.fontFamily.iterator();
                  do
                  {
                    if (!localIterator.hasNext()) {
                      break;
                    }
                    localTypeface = checkGenericFont((String)localIterator.next(), paramRendererState.style.fontWeight, paramRendererState.style.fontStyle);
                    if ((localTypeface == null) && (localSVGExternalFileResolver != null))
                    {
                      paramRendererState.style.fontWeight.intValue();
                      String.valueOf(paramRendererState.style.fontStyle);
                      localTypeface = null;
                    }
                  } while (localTypeface == null);
                }
              }
              if (localTypeface == null) {
                localTypeface = checkGenericFont("sans-serif", paramRendererState.style.fontWeight, paramRendererState.style.fontStyle);
              }
              paramRendererState.fillPaint.setTypeface(localTypeface);
              paramRendererState.strokePaint.setTypeface(localTypeface);
            }
            if (isSpecified(paramStyle, 131072L))
            {
              paramRendererState.style.textDecoration = paramStyle.textDecoration;
              Paint localPaint1 = paramRendererState.fillPaint;
              if (paramStyle.textDecoration != SVG.Style.TextDecoration.LineThrough) {
                break label1727;
              }
              bool1 = true;
              localPaint1.setStrikeThruText(bool1);
              Paint localPaint2 = paramRendererState.fillPaint;
              if (paramStyle.textDecoration != SVG.Style.TextDecoration.Underline) {
                break label1733;
              }
              bool2 = true;
              localPaint2.setUnderlineText(bool2);
              if (Build.VERSION.SDK_INT >= 17)
              {
                Paint localPaint3 = paramRendererState.strokePaint;
                if (paramStyle.textDecoration != SVG.Style.TextDecoration.LineThrough) {
                  break label1739;
                }
                bool3 = true;
                localPaint3.setStrikeThruText(bool3);
                localPaint4 = paramRendererState.strokePaint;
                if (paramStyle.textDecoration != SVG.Style.TextDecoration.Underline) {
                  break label1745;
                }
              }
            }
          }
          break;
        }
        break;
      }
    }
    label1416:
    label1727:
    label1733:
    label1739:
    label1745:
    for (boolean bool4 = true;; bool4 = false)
    {
      localPaint4.setUnderlineText(bool4);
      if (isSpecified(paramStyle, 68719476736L)) {
        paramRendererState.style.direction = paramStyle.direction;
      }
      if (isSpecified(paramStyle, 262144L)) {
        paramRendererState.style.textAnchor = paramStyle.textAnchor;
      }
      if (isSpecified(paramStyle, 524288L)) {
        paramRendererState.style.overflow = paramStyle.overflow;
      }
      if (isSpecified(paramStyle, 2097152L)) {
        paramRendererState.style.markerStart = paramStyle.markerStart;
      }
      if (isSpecified(paramStyle, 4194304L)) {
        paramRendererState.style.markerMid = paramStyle.markerMid;
      }
      if (isSpecified(paramStyle, 8388608L)) {
        paramRendererState.style.markerEnd = paramStyle.markerEnd;
      }
      if (isSpecified(paramStyle, 16777216L)) {
        paramRendererState.style.display = paramStyle.display;
      }
      if (isSpecified(paramStyle, 33554432L)) {
        paramRendererState.style.visibility = paramStyle.visibility;
      }
      if (isSpecified(paramStyle, 1048576L)) {
        paramRendererState.style.clip = paramStyle.clip;
      }
      if (isSpecified(paramStyle, 268435456L)) {
        paramRendererState.style.clipPath = paramStyle.clipPath;
      }
      if (isSpecified(paramStyle, 536870912L)) {
        paramRendererState.style.clipRule = paramStyle.clipRule;
      }
      if (isSpecified(paramStyle, 1073741824L)) {
        paramRendererState.style.mask = paramStyle.mask;
      }
      if (isSpecified(paramStyle, 67108864L)) {
        paramRendererState.style.stopColor = paramStyle.stopColor;
      }
      if (isSpecified(paramStyle, 134217728L)) {
        paramRendererState.style.stopOpacity = paramStyle.stopOpacity;
      }
      if (isSpecified(paramStyle, 8589934592L)) {
        paramRendererState.style.viewportFill = paramStyle.viewportFill;
      }
      if (isSpecified(paramStyle, 17179869184L)) {
        paramRendererState.style.viewportFillOpacity = paramStyle.viewportFillOpacity;
      }
      if (this.fill != null)
      {
        paramRendererState.style.fillOpacity = Float.valueOf(Color.alpha(this.fill.colour) / 255.0F);
        setPaintColour(paramRendererState, true, this.fill);
      }
      if (this.stroke != null)
      {
        paramRendererState.style.strokeOpacity = Float.valueOf(Color.alpha(this.stroke.colour) / 255.0F);
        setPaintColour(paramRendererState, false, this.stroke);
      }
      return;
      bool6 = false;
      break;
      bool5 = false;
      break label172;
      paramRendererState.strokePaint.setStrokeCap(Paint.Cap.BUTT);
      break label340;
      paramRendererState.strokePaint.setStrokeCap(Paint.Cap.ROUND);
      break label340;
      paramRendererState.strokePaint.setStrokeCap(Paint.Cap.SQUARE);
      break label340;
      paramRendererState.strokePaint.setStrokeJoin(Paint.Join.MITER);
      break label400;
      paramRendererState.strokePaint.setStrokeJoin(Paint.Join.ROUND);
      break label400;
      paramRendererState.strokePaint.setStrokeJoin(Paint.Join.BEVEL);
      break label400;
      float f2 = 0.0F;
      int i = paramRendererState.style.strokeDashArray.length;
      if (i % 2 == 0) {}
      float[] arrayOfFloat;
      for (int j = i;; j = i * 2)
      {
        arrayOfFloat = new float[j];
        for (int k = 0; k < j; k++)
        {
          arrayOfFloat[k] = paramRendererState.style.strokeDashArray[(k % i)].floatValue(this);
          f2 += arrayOfFloat[k];
        }
      }
      if (f2 == 0.0F)
      {
        paramRendererState.strokePaint.setPathEffect(null);
        break label506;
      }
      float f3 = paramRendererState.style.strokeDashOffset.floatValue(this);
      if (f3 < 0.0F) {
        f3 = f2 + f3 % f2;
      }
      paramRendererState.strokePaint.setPathEffect(new DashPathEffect(arrayOfFloat, f3));
      break label506;
      label1658:
      if ((paramStyle.fontWeight.intValue() == 1) && (paramRendererState.style.fontWeight.intValue() < 900))
      {
        SVG.Style localStyle1 = paramRendererState.style;
        localStyle1.fontWeight = Integer.valueOf(100 + localStyle1.fontWeight.intValue());
        break label649;
      }
      paramRendererState.style.fontWeight = paramStyle.fontWeight;
      break label649;
      bool1 = false;
      break label899;
      bool2 = false;
      break label924;
      bool3 = false;
      break label958;
    }
  }
  
  private void updateStyleForElement(RendererState paramRendererState, SVG.SvgElementBase paramSvgElementBase)
  {
    int i;
    Boolean localBoolean;
    label32:
    int j;
    label170:
    int k;
    label178:
    Iterator localIterator;
    if (paramSvgElementBase.parent == null)
    {
      i = 1;
      SVG.Style localStyle = paramRendererState.style;
      localStyle.display = Boolean.TRUE;
      if (i == 0) {
        break label277;
      }
      localBoolean = Boolean.TRUE;
      localStyle.overflow = localBoolean;
      localStyle.clip = null;
      localStyle.clipPath = null;
      localStyle.opacity = Float.valueOf(1.0F);
      localStyle.stopColor = SVG.Colour.BLACK;
      localStyle.stopOpacity = Float.valueOf(1.0F);
      localStyle.mask = null;
      localStyle.solidColor = null;
      localStyle.solidOpacity = Float.valueOf(1.0F);
      localStyle.viewportFill = null;
      localStyle.viewportFillOpacity = Float.valueOf(1.0F);
      localStyle.vectorEffect = SVG.Style.VectorEffect.None;
      if (paramSvgElementBase.baseStyle != null) {
        updateStyle(paramRendererState, paramSvgElementBase.baseStyle);
      }
      CSSParser.Ruleset localRuleset = this.document.cssRules;
      if ((localRuleset.rules != null) && (!localRuleset.rules.isEmpty())) {
        break label285;
      }
      j = 1;
      if (j != 0) {
        break label291;
      }
      k = 1;
      if (k == 0) {
        break label373;
      }
      localIterator = this.document.cssRules.rules.iterator();
    }
    label277:
    label285:
    label291:
    label371:
    for (;;)
    {
      if (!localIterator.hasNext()) {
        break label373;
      }
      CSSParser.Rule localRule = (CSSParser.Rule)localIterator.next();
      CSSParser.Selector localSelector = localRule.selector;
      ArrayList localArrayList = new ArrayList();
      SVG.SvgContainer localSvgContainer = paramSvgElementBase.parent;
      for (;;)
      {
        if (localSvgContainer != null)
        {
          localArrayList.add(0, localSvgContainer);
          localSvgContainer = ((SVG.SvgObject)localSvgContainer).parent;
          continue;
          i = 0;
          break;
          localBoolean = Boolean.FALSE;
          break label32;
          j = 0;
          break label170;
          k = 0;
          break label178;
        }
      }
      int m = -1 + localArrayList.size();
      if (localSelector.size() == 1) {}
      for (boolean bool = CSSParser.selectorMatch(localSelector.get(0), localArrayList, m, paramSvgElementBase);; bool = CSSParser.ruleMatch(localSelector, -1 + localSelector.size(), localArrayList, m, paramSvgElementBase))
      {
        if (!bool) {
          break label371;
        }
        updateStyle(paramRendererState, localRule.style);
        break;
      }
    }
    label373:
    if (paramSvgElementBase.style != null) {
      updateStyle(paramRendererState, paramSvgElementBase.style);
    }
  }
  
  private void viewportFill()
  {
    if ((this.state.style.viewportFill instanceof SVG.Colour)) {}
    for (int i = ((SVG.Colour)this.state.style.viewportFill).colour;; i = this.state.style.color.colour)
    {
      if (this.state.style.viewportFillOpacity != null) {
        i |= clamp255(this.state.style.viewportFillOpacity.floatValue()) << 24;
      }
      this.canvas.drawColor(i);
      do
      {
        return;
      } while (!(this.state.style.viewportFill instanceof SVG.CurrentColor));
    }
  }
  
  private boolean visible()
  {
    if (this.state.style.visibility != null) {
      return this.state.style.visibility.booleanValue();
    }
    return true;
  }
  
  private static void warn(String paramString, Object... paramVarArgs)
  {
    Log.w("SVGAndroidRenderer", String.format(paramString, paramVarArgs));
  }
  
  protected final float getCurrentFontSize()
  {
    return this.state.fillPaint.getTextSize();
  }
  
  protected final SVG.Box getCurrentViewPortInUserUnits()
  {
    if (this.state.viewBox != null) {
      return this.state.viewBox;
    }
    return this.state.viewPort;
  }
  
  protected final void renderDocument$673e8f3d(SVG paramSVG)
  {
    this.document = paramSVG;
    this.directRenderingMode = true;
    SVG.Svg localSvg = paramSVG.rootElement;
    if (localSvg == null)
    {
      warn("Nothing to render. Document is empty.", new Object[0]);
      return;
    }
    this.state = new RendererState();
    this.stateStack = new Stack();
    updateStyle(this.state, SVG.Style.getDefaultStyle());
    this.state.viewPort = this.canvasViewPort;
    this.state.spacePreserve = false;
    this.state.directRendering = this.directRenderingMode;
    this.stateStack.push((RendererState)this.state.clone());
    this.canvasStack = new Stack();
    this.bitmapStack = new Stack();
    this.matrixStack = new Stack();
    this.parentStack = new Stack();
    checkXMLSpaceAttribute(localSvg);
    render(localSvg, localSvg.width, localSvg.height, localSvg.viewBox, localSvg.preserveAspectRatio);
  }
  
  private final class MarkerPositionCalculator
    implements SVG.PathInterface
  {
    private boolean closepathReAdjustPending;
    private SVGAndroidRenderer.MarkerVector lastPos = null;
    List<SVGAndroidRenderer.MarkerVector> markers = new ArrayList();
    private boolean normalCubic = true;
    private boolean startArc = false;
    private float startX;
    private float startY;
    private int subpathStartIndex = -1;
    
    public MarkerPositionCalculator(SVG.PathDefinition paramPathDefinition)
    {
      if (paramPathDefinition == null) {}
      do
      {
        return;
        paramPathDefinition.enumeratePath(this);
        if (this.closepathReAdjustPending)
        {
          this.lastPos.add((SVGAndroidRenderer.MarkerVector)this.markers.get(this.subpathStartIndex));
          this.markers.set(this.subpathStartIndex, this.lastPos);
          this.closepathReAdjustPending = false;
        }
      } while (this.lastPos == null);
      this.markers.add(this.lastPos);
    }
    
    public final void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5)
    {
      this.startArc = true;
      this.normalCubic = false;
      SVGAndroidRenderer.access$700(this.lastPos.x, this.lastPos.y, paramFloat1, paramFloat2, paramFloat3, paramBoolean1, paramBoolean2, paramFloat4, paramFloat5, this);
      this.normalCubic = true;
      this.closepathReAdjustPending = false;
    }
    
    public final void close()
    {
      this.markers.add(this.lastPos);
      lineTo(this.startX, this.startY);
      this.closepathReAdjustPending = true;
    }
    
    public final void cubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
    {
      if ((this.normalCubic) || (this.startArc))
      {
        this.lastPos.add(paramFloat1, paramFloat2);
        this.markers.add(this.lastPos);
        this.startArc = false;
      }
      this.lastPos = new SVGAndroidRenderer.MarkerVector(SVGAndroidRenderer.this, paramFloat5, paramFloat6, paramFloat5 - paramFloat3, paramFloat6 - paramFloat4);
      this.closepathReAdjustPending = false;
    }
    
    public final void lineTo(float paramFloat1, float paramFloat2)
    {
      this.lastPos.add(paramFloat1, paramFloat2);
      this.markers.add(this.lastPos);
      this.lastPos = new SVGAndroidRenderer.MarkerVector(SVGAndroidRenderer.this, paramFloat1, paramFloat2, paramFloat1 - this.lastPos.x, paramFloat2 - this.lastPos.y);
      this.closepathReAdjustPending = false;
    }
    
    public final void moveTo(float paramFloat1, float paramFloat2)
    {
      if (this.closepathReAdjustPending)
      {
        this.lastPos.add((SVGAndroidRenderer.MarkerVector)this.markers.get(this.subpathStartIndex));
        this.markers.set(this.subpathStartIndex, this.lastPos);
        this.closepathReAdjustPending = false;
      }
      if (this.lastPos != null) {
        this.markers.add(this.lastPos);
      }
      this.startX = paramFloat1;
      this.startY = paramFloat2;
      this.lastPos = new SVGAndroidRenderer.MarkerVector(SVGAndroidRenderer.this, paramFloat1, paramFloat2, 0.0F, 0.0F);
      this.subpathStartIndex = this.markers.size();
    }
    
    public final void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.lastPos.add(paramFloat1, paramFloat2);
      this.markers.add(this.lastPos);
      this.lastPos = new SVGAndroidRenderer.MarkerVector(SVGAndroidRenderer.this, paramFloat3, paramFloat4, paramFloat3 - paramFloat1, paramFloat4 - paramFloat2);
      this.closepathReAdjustPending = false;
    }
  }
  
  private final class MarkerVector
  {
    public float dx = 0.0F;
    public float dy = 0.0F;
    public float x;
    public float y;
    
    public MarkerVector(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.x = paramFloat1;
      this.y = paramFloat2;
      double d = Math.sqrt(paramFloat3 * paramFloat3 + paramFloat4 * paramFloat4);
      if (d != 0.0D)
      {
        this.dx = ((float)(paramFloat3 / d));
        this.dy = ((float)(paramFloat4 / d));
      }
    }
    
    public final void add(float paramFloat1, float paramFloat2)
    {
      float f1 = paramFloat1 - this.x;
      float f2 = paramFloat2 - this.y;
      double d = Math.sqrt(f1 * f1 + f2 * f2);
      if (d != 0.0D)
      {
        this.dx += (float)(f1 / d);
        this.dy += (float)(f2 / d);
      }
    }
    
    public final void add(MarkerVector paramMarkerVector)
    {
      this.dx += paramMarkerVector.dx;
      this.dy += paramMarkerVector.dy;
    }
    
    public final String toString()
    {
      return "(" + this.x + "," + this.y + " " + this.dx + "," + this.dy + ")";
    }
  }
  
  private final class PathConverter
    implements SVG.PathInterface
  {
    float lastX;
    float lastY;
    Path path = new Path();
    
    public PathConverter(SVG.PathDefinition paramPathDefinition)
    {
      if (paramPathDefinition == null) {
        return;
      }
      paramPathDefinition.enumeratePath(this);
    }
    
    public final void arcTo(float paramFloat1, float paramFloat2, float paramFloat3, boolean paramBoolean1, boolean paramBoolean2, float paramFloat4, float paramFloat5)
    {
      SVGAndroidRenderer.access$700(this.lastX, this.lastY, paramFloat1, paramFloat2, paramFloat3, paramBoolean1, paramBoolean2, paramFloat4, paramFloat5, this);
      this.lastX = paramFloat4;
      this.lastY = paramFloat5;
    }
    
    public final void close()
    {
      this.path.close();
    }
    
    public final void cubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
    {
      this.path.cubicTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
      this.lastX = paramFloat5;
      this.lastY = paramFloat6;
    }
    
    public final void lineTo(float paramFloat1, float paramFloat2)
    {
      this.path.lineTo(paramFloat1, paramFloat2);
      this.lastX = paramFloat1;
      this.lastY = paramFloat2;
    }
    
    public final void moveTo(float paramFloat1, float paramFloat2)
    {
      this.path.moveTo(paramFloat1, paramFloat2);
      this.lastX = paramFloat1;
      this.lastY = paramFloat2;
    }
    
    public final void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
    {
      this.path.quadTo(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
      this.lastX = paramFloat3;
      this.lastY = paramFloat4;
    }
  }
  
  private final class PathTextDrawer
    extends SVGAndroidRenderer.PlainTextDrawer
  {
    private Path path;
    
    public PathTextDrawer(Path paramPath, float paramFloat)
    {
      super(paramFloat, 0.0F);
      this.path = paramPath;
    }
    
    public final void processText(String paramString)
    {
      if (SVGAndroidRenderer.this.visible())
      {
        if (SVGAndroidRenderer.this.state.hasFill) {
          SVGAndroidRenderer.this.canvas.drawTextOnPath(paramString, this.path, this.x, this.y, SVGAndroidRenderer.this.state.fillPaint);
        }
        if (SVGAndroidRenderer.this.state.hasStroke) {
          SVGAndroidRenderer.this.canvas.drawTextOnPath(paramString, this.path, this.x, this.y, SVGAndroidRenderer.this.state.strokePaint);
        }
      }
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }
  
  private class PlainTextDrawer
    extends SVGAndroidRenderer.TextProcessor
  {
    public float x;
    public float y;
    
    public PlainTextDrawer(float paramFloat1, float paramFloat2)
    {
      super((byte)0);
      this.x = paramFloat1;
      this.y = paramFloat2;
    }
    
    public void processText(String paramString)
    {
      if (SVGAndroidRenderer.this.visible())
      {
        if (SVGAndroidRenderer.this.state.hasFill) {
          SVGAndroidRenderer.this.canvas.drawText(paramString, this.x, this.y, SVGAndroidRenderer.this.state.fillPaint);
        }
        if (SVGAndroidRenderer.this.state.hasStroke) {
          SVGAndroidRenderer.this.canvas.drawText(paramString, this.x, this.y, SVGAndroidRenderer.this.state.strokePaint);
        }
      }
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }
  
  private final class PlainTextToPath
    extends SVGAndroidRenderer.TextProcessor
  {
    public Path textAsPath;
    public float x;
    public float y;
    
    public PlainTextToPath(float paramFloat1, float paramFloat2, Path paramPath)
    {
      super((byte)0);
      this.x = paramFloat1;
      this.y = paramFloat2;
      this.textAsPath = paramPath;
    }
    
    public final boolean doTextContainer(SVG.TextContainer paramTextContainer)
    {
      if ((paramTextContainer instanceof SVG.TextPath))
      {
        SVGAndroidRenderer.warn("Using <textPath> elements in a clip path is not supported.", new Object[0]);
        return false;
      }
      return true;
    }
    
    public final void processText(String paramString)
    {
      if (SVGAndroidRenderer.this.visible())
      {
        Path localPath = new Path();
        SVGAndroidRenderer.this.state.fillPaint.getTextPath(paramString, 0, paramString.length(), this.x, this.y, localPath);
        this.textAsPath.addPath(localPath);
      }
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }
  
  private final class RendererState
    implements Cloneable
  {
    public boolean directRendering;
    public Paint fillPaint = new Paint();
    public boolean hasFill;
    public boolean hasStroke;
    public boolean spacePreserve;
    public Paint strokePaint;
    public SVG.Style style;
    public SVG.Box viewBox;
    public SVG.Box viewPort;
    
    public RendererState()
    {
      this.fillPaint.setFlags(385);
      this.fillPaint.setStyle(Paint.Style.FILL);
      this.fillPaint.setTypeface(Typeface.DEFAULT);
      this.strokePaint = new Paint();
      this.strokePaint.setFlags(385);
      this.strokePaint.setStyle(Paint.Style.STROKE);
      this.strokePaint.setTypeface(Typeface.DEFAULT);
      this.style = SVG.Style.getDefaultStyle();
    }
    
    protected final Object clone()
    {
      try
      {
        RendererState localRendererState = (RendererState)super.clone();
        localRendererState.style = ((SVG.Style)this.style.clone());
        localRendererState.fillPaint = new Paint(this.fillPaint);
        localRendererState.strokePaint = new Paint(this.strokePaint);
        return localRendererState;
      }
      catch (CloneNotSupportedException localCloneNotSupportedException)
      {
        throw new InternalError(localCloneNotSupportedException.toString());
      }
    }
  }
  
  private final class TextBoundsCalculator
    extends SVGAndroidRenderer.TextProcessor
  {
    RectF bbox = new RectF();
    float x;
    float y;
    
    public TextBoundsCalculator(float paramFloat1, float paramFloat2)
    {
      super((byte)0);
      this.x = paramFloat1;
      this.y = paramFloat2;
    }
    
    public final boolean doTextContainer(SVG.TextContainer paramTextContainer)
    {
      if ((paramTextContainer instanceof SVG.TextPath))
      {
        SVG.TextPath localTextPath = (SVG.TextPath)paramTextContainer;
        SVG.SvgObject localSvgObject = paramTextContainer.document.resolveIRI(localTextPath.href);
        if (localSvgObject == null)
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = localTextPath.href;
          SVGAndroidRenderer.error("TextPath path reference '%s' not found", arrayOfObject);
          return false;
        }
        SVG.Path localPath = (SVG.Path)localSvgObject;
        Path localPath1 = new SVGAndroidRenderer.PathConverter(SVGAndroidRenderer.this, localPath.d).path;
        if (localPath.transform != null) {
          localPath1.transform(localPath.transform);
        }
        RectF localRectF = new RectF();
        localPath1.computeBounds(localRectF, true);
        this.bbox.union(localRectF);
        return false;
      }
      return true;
    }
    
    public final void processText(String paramString)
    {
      if (SVGAndroidRenderer.this.visible())
      {
        Rect localRect = new Rect();
        SVGAndroidRenderer.this.state.fillPaint.getTextBounds(paramString, 0, paramString.length(), localRect);
        RectF localRectF = new RectF(localRect);
        localRectF.offset(this.x, this.y);
        this.bbox.union(localRectF);
      }
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }
  
  private abstract class TextProcessor
  {
    private TextProcessor() {}
    
    public boolean doTextContainer(SVG.TextContainer paramTextContainer)
    {
      return true;
    }
    
    public abstract void processText(String paramString);
  }
  
  private final class TextWidthCalculator
    extends SVGAndroidRenderer.TextProcessor
  {
    public float x = 0.0F;
    
    private TextWidthCalculator()
    {
      super((byte)0);
    }
    
    public final void processText(String paramString)
    {
      this.x += SVGAndroidRenderer.this.state.fillPaint.measureText(paramString);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SVGAndroidRenderer
 * JD-Core Version:    0.7.0.1
 */