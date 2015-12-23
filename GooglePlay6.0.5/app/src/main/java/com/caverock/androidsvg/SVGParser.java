package com.caverock.androidsvg;

import android.graphics.Matrix;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

public final class SVGParser
  extends DefaultHandler2
{
  private SVG.SvgContainer currentElement = null;
  private int ignoreDepth;
  private boolean ignoring = false;
  private boolean inMetadataElement = false;
  private boolean inStyleElement = false;
  private StringBuilder metadataElementContents = null;
  private SVGElem metadataTag = null;
  private StringBuilder styleElementContents = null;
  private Set<String> supportedFormats = null;
  private SVG svgDocument = null;
  
  private static int clamp255(float paramFloat)
  {
    if (paramFloat < 0.0F) {
      return 0;
    }
    if (paramFloat > 255.0F) {
      return 255;
    }
    return Math.round(paramFloat);
  }
  
  private static SVG.Style.FontStyle fontStyleKeyword(String paramString)
  {
    if ("italic".equals(paramString)) {
      return SVG.Style.FontStyle.Italic;
    }
    if ("normal".equals(paramString)) {
      return SVG.Style.FontStyle.Normal;
    }
    if ("oblique".equals(paramString)) {
      return SVG.Style.FontStyle.Oblique;
    }
    return null;
  }
  
  private static void parseAttributesCircle(SVG.Circle paramCircle, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      do
      {
        for (;;)
        {
          i++;
          break;
          paramCircle.cx = parseLength(str);
          continue;
          paramCircle.cy = parseLength(str);
        }
        paramCircle.r = parseLength(str);
      } while (!paramCircle.r.isNegative());
      throw new SAXException("Invalid <circle> element. r cannot be negative");
    }
  }
  
  private static void parseAttributesClipPath(SVG.ClipPath paramClipPath, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      for (;;)
      {
        i++;
        break;
        if ("objectBoundingBox".equals(str))
        {
          paramClipPath.clipPathUnitsAreUser = Boolean.valueOf(false);
        }
        else
        {
          if (!"userSpaceOnUse".equals(str)) {
            break label106;
          }
          paramClipPath.clipPathUnitsAreUser = Boolean.valueOf(true);
        }
      }
      label106:
      throw new SAXException("Invalid value for attribute clipPathUnits");
    }
  }
  
  private static void parseAttributesConditional(SVG.SvgConditional paramSvgConditional, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str1 = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default: 
      case 21: 
      case 22: 
      case 23: 
      case 24: 
        for (;;)
        {
          i++;
          break;
          TextScanner localTextScanner = new TextScanner(str1);
          HashSet localHashSet2 = new HashSet();
          if (!localTextScanner.empty())
          {
            String str2 = localTextScanner.nextToken();
            if (str2.startsWith("http://www.w3.org/TR/SVG11/feature#")) {
              localHashSet2.add(str2.substring(35));
            }
            for (;;)
            {
              localTextScanner.skipWhitespace();
              break;
              localHashSet2.add("UNSUPPORTED");
            }
          }
          paramSvgConditional.setRequiredFeatures(localHashSet2);
          continue;
          paramSvgConditional.setRequiredExtensions(str1);
          continue;
          paramSvgConditional.setSystemLanguage(parseSystemLanguage(str1));
          continue;
          paramSvgConditional.setRequiredFormats(parseRequiredFormats(str1));
        }
      }
      List localList = parseFontFamily(str1);
      if (localList != null) {}
      for (HashSet localHashSet1 = new HashSet(localList);; localHashSet1 = new HashSet(0))
      {
        paramSvgConditional.setRequiredFonts(localHashSet1);
        break;
      }
    }
  }
  
  private static void parseAttributesCore(SVG.SvgElementBase paramSvgElementBase, Attributes paramAttributes)
    throws SAXException
  {
    for (int i = 0;; i++)
    {
      String str1;
      if (i < paramAttributes.getLength())
      {
        str1 = paramAttributes.getQName(i);
        if ((str1.equals("id")) || (str1.equals("xml:id"))) {
          paramSvgElementBase.id = paramAttributes.getValue(i).trim();
        }
      }
      else
      {
        return;
      }
      if (str1.equals("xml:space"))
      {
        String str2 = paramAttributes.getValue(i).trim();
        if ("default".equals(str2))
        {
          paramSvgElementBase.spacePreserve = Boolean.FALSE;
          return;
        }
        if ("preserve".equals(str2))
        {
          paramSvgElementBase.spacePreserve = Boolean.TRUE;
          return;
        }
        throw new SAXException("Invalid value for \"xml:space\" attribute: " + str2);
      }
    }
  }
  
  private static void parseAttributesEllipse(SVG.Ellipse paramEllipse, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      do
      {
        do
        {
          for (;;)
          {
            i++;
            break;
            paramEllipse.cx = parseLength(str);
            continue;
            paramEllipse.cy = parseLength(str);
          }
          paramEllipse.rx = parseLength(str);
        } while (!paramEllipse.rx.isNegative());
        throw new SAXException("Invalid <ellipse> element. rx cannot be negative");
        paramEllipse.ry = parseLength(str);
      } while (!paramEllipse.ry.isNegative());
      throw new SAXException("Invalid <ellipse> element. ry cannot be negative");
    }
  }
  
  private static void parseAttributesGradient(SVG.GradientElement paramGradientElement, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      }
      for (;;)
      {
        i++;
        break;
        if ("objectBoundingBox".equals(str))
        {
          paramGradientElement.gradientUnitsAreUser = Boolean.valueOf(false);
        }
        else if ("userSpaceOnUse".equals(str))
        {
          paramGradientElement.gradientUnitsAreUser = Boolean.valueOf(true);
        }
        else
        {
          throw new SAXException("Invalid value for attribute gradientUnits");
          paramGradientElement.gradientTransform = parseTransformList(str);
          continue;
          try
          {
            paramGradientElement.spreadMethod = SVG.GradientSpread.valueOf(str);
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            throw new SAXException("Invalid spreadMethod attribute. \"" + str + "\" is not a valid value.");
          }
          if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i))) {
            paramGradientElement.href = str;
          }
        }
      }
    }
  }
  
  private static void parseAttributesImage(SVG.Image paramImage, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      for (;;)
      {
        i++;
        break;
        paramImage.x = parseLength(str);
        continue;
        paramImage.y = parseLength(str);
        continue;
        paramImage.width = parseLength(str);
        if (paramImage.width.isNegative())
        {
          throw new SAXException("Invalid <use> element. width cannot be negative");
          paramImage.height = parseLength(str);
          if (paramImage.height.isNegative())
          {
            throw new SAXException("Invalid <use> element. height cannot be negative");
            if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i)))
            {
              paramImage.href = str;
              continue;
              parsePreserveAspectRatio(paramImage, str);
            }
          }
        }
      }
    }
  }
  
  private static void parseAttributesLine(SVG.Line paramLine, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      for (;;)
      {
        i++;
        break;
        paramLine.x1 = parseLength(str);
        continue;
        paramLine.y1 = parseLength(str);
        continue;
        paramLine.x2 = parseLength(str);
        continue;
        paramLine.y2 = parseLength(str);
      }
    }
  }
  
  private static void parseAttributesLinearGradient(SVG.SvgLinearGradient paramSvgLinearGradient, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      for (;;)
      {
        i++;
        break;
        paramSvgLinearGradient.x1 = parseLength(str);
        continue;
        paramSvgLinearGradient.y1 = parseLength(str);
        continue;
        paramSvgLinearGradient.x2 = parseLength(str);
        continue;
        paramSvgLinearGradient.y2 = parseLength(str);
      }
    }
  }
  
  private static void parseAttributesMarker(SVG.Marker paramMarker, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      for (;;)
      {
        i++;
        break;
        paramMarker.refX = parseLength(str);
        continue;
        paramMarker.refY = parseLength(str);
        continue;
        paramMarker.markerWidth = parseLength(str);
        if (paramMarker.markerWidth.isNegative())
        {
          throw new SAXException("Invalid <marker> element. markerWidth cannot be negative");
          paramMarker.markerHeight = parseLength(str);
          if (paramMarker.markerHeight.isNegative())
          {
            throw new SAXException("Invalid <marker> element. markerHeight cannot be negative");
            if ("strokeWidth".equals(str))
            {
              paramMarker.markerUnitsAreUser = false;
            }
            else if ("userSpaceOnUse".equals(str))
            {
              paramMarker.markerUnitsAreUser = true;
            }
            else
            {
              throw new SAXException("Invalid value for attribute markerUnits");
              if ("auto".equals(str)) {
                paramMarker.orient = Float.valueOf((0.0F / 0.0F));
              } else {
                paramMarker.orient = Float.valueOf(parseFloat(str));
              }
            }
          }
        }
      }
    }
  }
  
  private static void parseAttributesMask(SVG.Mask paramMask, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      }
      do
      {
        do
        {
          for (;;)
          {
            i++;
            break;
            if ("objectBoundingBox".equals(str))
            {
              paramMask.maskUnitsAreUser = Boolean.valueOf(false);
            }
            else if ("userSpaceOnUse".equals(str))
            {
              paramMask.maskUnitsAreUser = Boolean.valueOf(true);
            }
            else
            {
              throw new SAXException("Invalid value for attribute maskUnits");
              if ("objectBoundingBox".equals(str))
              {
                paramMask.maskContentUnitsAreUser = Boolean.valueOf(false);
              }
              else if ("userSpaceOnUse".equals(str))
              {
                paramMask.maskContentUnitsAreUser = Boolean.valueOf(true);
              }
              else
              {
                throw new SAXException("Invalid value for attribute maskContentUnits");
                paramMask.x = parseLength(str);
                continue;
                paramMask.y = parseLength(str);
              }
            }
          }
          paramMask.width = parseLength(str);
        } while (!paramMask.width.isNegative());
        throw new SAXException("Invalid <mask> element. width cannot be negative");
        paramMask.height = parseLength(str);
      } while (!paramMask.height.isNegative());
      throw new SAXException("Invalid <mask> element. height cannot be negative");
    }
  }
  
  private static void parseAttributesPath(SVG.Path paramPath, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    String str;
    TextScanner localTextScanner;
    SVG.PathDefinition localPathDefinition;
    int j;
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    float f6;
    float f7;
    float f8;
    int k;
    float f9;
    float f10;
    float f12;
    float f13;
    label189:
    label368:
    float f49;
    label454:
    label493:
    float f50;
    label513:
    label580:
    float f51;
    float f52;
    float f53;
    float f54;
    label800:
    float f61;
    float f55;
    float f57;
    float f56;
    while (i < paramAttributes.getLength())
    {
      str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      default: 
        i++;
        break;
      case 8: 
        localTextScanner = new TextScanner(str);
        localPathDefinition = new SVG.PathDefinition();
        if (!localTextScanner.empty())
        {
          j = localTextScanner.nextChar().intValue();
          f1 = 0.0F;
          f2 = 0.0F;
          f3 = 0.0F;
          f4 = 0.0F;
          f5 = 0.0F;
          f6 = 0.0F;
          if (j != 77)
          {
            f1 = 0.0F;
            f2 = 0.0F;
            f3 = 0.0F;
            f4 = 0.0F;
            f5 = 0.0F;
            f6 = 0.0F;
            if (j != 109) {}
          }
          else
          {
            f7 = f3;
            f8 = f4;
            k = j;
            f9 = f5;
            f10 = f6;
            float f11 = f2;
            f12 = f1;
            f13 = f11;
            localTextScanner.skipWhitespace();
          }
        }
        switch (k)
        {
        default: 
        case 77: 
        case 109: 
        case 76: 
        case 108: 
        case 67: 
        case 99: 
          for (;;)
          {
            paramPath.d = localPathDefinition;
            break;
            f2 = localTextScanner.nextFloat();
            f1 = localTextScanner.checkedNextFloat(f2);
            if (Float.isNaN(f1))
            {
              Log.e("SVGParser", "Bad path coords for " + (char)k + " path segment");
            }
            else
            {
              int i1;
              if (k == 109)
              {
                if (localPathDefinition.commandsLength != 0) {
                  break label580;
                }
                i1 = 1;
                if (i1 == 0)
                {
                  f2 += f10;
                  f1 += f9;
                }
              }
              localPathDefinition.moveTo(f2, f1);
              int n;
              if (k == 109)
              {
                n = 108;
                f4 = f2;
                f5 = f1;
                f6 = f2;
                j = n;
                f3 = f1;
              }
              for (;;)
              {
                localTextScanner.skipCommaWhitespace();
                if (localTextScanner.empty()) {
                  break label368;
                }
                if (!localTextScanner.hasLetter()) {
                  break;
                }
                int m = localTextScanner.nextChar().intValue();
                f7 = f3;
                f8 = f4;
                k = m;
                f9 = f5;
                f10 = f6;
                float f21 = f2;
                f12 = f1;
                f13 = f21;
                break label189;
                i1 = 0;
                break label454;
                n = 76;
                break label493;
                f2 = localTextScanner.nextFloat();
                f1 = localTextScanner.checkedNextFloat(f2);
                if (Float.isNaN(f1))
                {
                  Log.e("SVGParser", "Bad path coords for " + (char)k + " path segment");
                  break label368;
                }
                if (k == 108)
                {
                  f2 += f10;
                  f1 += f9;
                }
                localPathDefinition.lineTo(f2, f1);
                f3 = f7;
                f4 = f8;
                f5 = f1;
                f6 = f2;
                j = k;
              }
              f49 = localTextScanner.nextFloat();
              f50 = localTextScanner.checkedNextFloat(f49);
              f51 = localTextScanner.checkedNextFloat(f50);
              f52 = localTextScanner.checkedNextFloat(f51);
              f53 = localTextScanner.checkedNextFloat(f52);
              f54 = localTextScanner.checkedNextFloat(f53);
              if (!Float.isNaN(f54)) {
                break label800;
              }
              Log.e("SVGParser", "Bad path coords for " + (char)k + " path segment");
            }
          }
          if (k != 99) {
            break label1944;
          }
          float f60 = f53 + f10;
          f61 = f54 + f9;
          float f62 = f49 + f10;
          f50 += f9;
          f51 += f10;
          f55 = f9 + f52;
          f57 = f62;
          f56 = f60;
        }
        break;
      }
    }
    for (float f58 = f61;; f58 = f54)
    {
      localPathDefinition.cubicTo(f57, f50, f51, f55, f56, f58);
      f1 = f55;
      f2 = f51;
      j = k;
      f4 = f8;
      f3 = f7;
      float f59 = f56;
      f5 = f58;
      f6 = f59;
      break label513;
      float f35 = 2.0F * f10 - f13;
      float f36 = 2.0F * f9 - f12;
      float f37 = localTextScanner.nextFloat();
      float f38 = localTextScanner.checkedNextFloat(f37);
      float f39 = localTextScanner.checkedNextFloat(f38);
      float f40 = localTextScanner.checkedNextFloat(f39);
      if (Float.isNaN(f40))
      {
        Log.e("SVGParser", "Bad path coords for " + (char)k + " path segment");
        break label368;
      }
      float f41;
      float f43;
      float f44;
      float f42;
      if (k == 115)
      {
        float f46 = f39 + f10;
        float f47 = f40 + f9;
        float f48 = f10 + f37;
        f41 = f9 + f38;
        f43 = f48;
        f44 = f47;
        f42 = f46;
      }
      for (;;)
      {
        localPathDefinition.cubicTo(f35, f36, f43, f41, f42, f44);
        f1 = f41;
        f2 = f43;
        j = k;
        f4 = f8;
        f3 = f7;
        float f45 = f42;
        f5 = f44;
        f6 = f45;
        break label513;
        localPathDefinition.addCommand((byte)8);
        f1 = f7;
        f2 = f8;
        f3 = f7;
        f4 = f8;
        f5 = f7;
        f6 = f8;
        j = k;
        break label513;
        float f33 = localTextScanner.nextFloat();
        if (Float.isNaN(f33))
        {
          Log.e("SVGParser", "Bad path coords for " + (char)k + " path segment");
          break label368;
        }
        if (k == 104) {
          f33 += f10;
        }
        localPathDefinition.lineTo(f33, f9);
        f3 = f7;
        f5 = f9;
        f6 = f33;
        j = k;
        f4 = f8;
        float f34 = f12;
        f2 = f33;
        f1 = f34;
        break label513;
        float f31 = localTextScanner.nextFloat();
        if (Float.isNaN(f31))
        {
          Log.e("SVGParser", "Bad path coords for " + (char)k + " path segment");
          break label368;
        }
        if (k == 118) {
          f31 += f9;
        }
        localPathDefinition.lineTo(f10, f31);
        f3 = f7;
        f4 = f8;
        f6 = f10;
        j = k;
        f5 = f31;
        float f32 = f31;
        f2 = f13;
        f1 = f32;
        break label513;
        f2 = localTextScanner.nextFloat();
        f1 = localTextScanner.checkedNextFloat(f2);
        float f27 = localTextScanner.checkedNextFloat(f1);
        float f28 = localTextScanner.checkedNextFloat(f27);
        if (Float.isNaN(f28))
        {
          Log.e("SVGParser", "Bad path coords for " + (char)k + " path segment");
          break label368;
        }
        float f30;
        if (k == 113)
        {
          f30 = f27 + f10;
          f28 += f9;
          f2 += f10;
          f1 += f9;
        }
        for (float f29 = f30;; f29 = f27)
        {
          localPathDefinition.quadTo(f2, f1, f29, f28);
          f5 = f28;
          f6 = f29;
          j = k;
          f4 = f8;
          f3 = f7;
          break label513;
          float f23 = 2.0F * f10 - f13;
          float f24 = 2.0F * f9 - f12;
          float f25 = localTextScanner.nextFloat();
          float f26 = localTextScanner.checkedNextFloat(f25);
          if (Float.isNaN(f26))
          {
            Log.e("SVGParser", "Bad path coords for " + (char)k + " path segment");
            break label368;
          }
          if (k == 116)
          {
            f25 += f10;
            f26 += f9;
          }
          localPathDefinition.quadTo(f23, f24, f25, f26);
          f4 = f8;
          f5 = f26;
          f6 = f25;
          f1 = f24;
          f2 = f23;
          f3 = f7;
          j = k;
          break label513;
          float f14 = localTextScanner.nextFloat();
          float f15 = localTextScanner.checkedNextFloat(f14);
          float f16 = localTextScanner.checkedNextFloat(f15);
          Boolean localBoolean1 = localTextScanner.checkedNextFlag(Float.valueOf(f16));
          Boolean localBoolean2 = localTextScanner.checkedNextFlag(localBoolean1);
          float f18;
          float f17;
          if (localBoolean2 == null)
          {
            f18 = (0.0F / 0.0F);
            f17 = f18;
          }
          for (;;)
          {
            if ((!Float.isNaN(f18)) && (f14 >= 0.0F) && (f15 >= 0.0F)) {
              break label1791;
            }
            Log.e("SVGParser", "Bad path coords for " + (char)k + " path segment");
            break;
            f17 = localTextScanner.possibleNextFloat();
            f18 = localTextScanner.checkedNextFloat(f17);
          }
          label1791:
          float f20;
          if (k == 97)
          {
            float f22 = f10 + f17;
            f20 = f18 + f9;
            f6 = f22;
          }
          for (;;)
          {
            localPathDefinition.arcTo(f14, f15, f16, localBoolean1.booleanValue(), localBoolean2.booleanValue(), f6, f20);
            f1 = f20;
            f2 = f6;
            f3 = f7;
            f4 = f8;
            f5 = f20;
            j = k;
            break label513;
            paramPath.pathLength = Float.valueOf(parseFloat(str));
            if (paramPath.pathLength.floatValue() >= 0.0F) {
              break;
            }
            throw new SAXException("Invalid <path> element. pathLength cannot be negative");
            return;
            float f19 = f18;
            f6 = f17;
            f20 = f19;
          }
        }
        f41 = f38;
        f42 = f39;
        f43 = f37;
        f44 = f40;
      }
      label1944:
      f55 = f52;
      f56 = f53;
      f57 = f49;
    }
  }
  
  private static void parseAttributesPattern(SVG.Pattern paramPattern, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      }
      for (;;)
      {
        i++;
        break;
        if ("objectBoundingBox".equals(str))
        {
          paramPattern.patternUnitsAreUser = Boolean.valueOf(false);
        }
        else if ("userSpaceOnUse".equals(str))
        {
          paramPattern.patternUnitsAreUser = Boolean.valueOf(true);
        }
        else
        {
          throw new SAXException("Invalid value for attribute patternUnits");
          if ("objectBoundingBox".equals(str))
          {
            paramPattern.patternContentUnitsAreUser = Boolean.valueOf(false);
          }
          else if ("userSpaceOnUse".equals(str))
          {
            paramPattern.patternContentUnitsAreUser = Boolean.valueOf(true);
          }
          else
          {
            throw new SAXException("Invalid value for attribute patternContentUnits");
            paramPattern.patternTransform = parseTransformList(str);
            continue;
            paramPattern.x = parseLength(str);
            continue;
            paramPattern.y = parseLength(str);
            continue;
            paramPattern.width = parseLength(str);
            if (paramPattern.width.isNegative())
            {
              throw new SAXException("Invalid <pattern> element. width cannot be negative");
              paramPattern.height = parseLength(str);
              if (paramPattern.height.isNegative())
              {
                throw new SAXException("Invalid <pattern> element. height cannot be negative");
                if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i))) {
                  paramPattern.href = str;
                }
              }
            }
          }
        }
      }
    }
  }
  
  private static void parseAttributesPolyLine(SVG.PolyLine paramPolyLine, Attributes paramAttributes, String paramString)
    throws SAXException
  {
    for (int i = 0; i < paramAttributes.getLength(); i++) {
      if (SVGAttr.fromString(paramAttributes.getLocalName(i)) == SVGAttr.points)
      {
        TextScanner localTextScanner = new TextScanner(paramAttributes.getValue(i));
        ArrayList localArrayList = new ArrayList();
        localTextScanner.skipWhitespace();
        while (!localTextScanner.empty())
        {
          float f2 = localTextScanner.nextFloat();
          if (Float.isNaN(f2)) {
            throw new SAXException("Invalid <" + paramString + "> points attribute. Non-coordinate content found in list.");
          }
          localTextScanner.skipCommaWhitespace();
          float f3 = localTextScanner.nextFloat();
          if (Float.isNaN(f3)) {
            throw new SAXException("Invalid <" + paramString + "> points attribute. There should be an even number of coordinates.");
          }
          localTextScanner.skipCommaWhitespace();
          localArrayList.add(Float.valueOf(f2));
          localArrayList.add(Float.valueOf(f3));
        }
        paramPolyLine.points = new float[localArrayList.size()];
        int j = 0;
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          float f1 = ((Float)localIterator.next()).floatValue();
          float[] arrayOfFloat = paramPolyLine.points;
          int k = j + 1;
          arrayOfFloat[j] = f1;
          j = k;
        }
      }
    }
  }
  
  private static void parseAttributesRadialGradient(SVG.SvgRadialGradient paramSvgRadialGradient, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      }
      for (;;)
      {
        i++;
        break;
        paramSvgRadialGradient.cx = parseLength(str);
        continue;
        paramSvgRadialGradient.cy = parseLength(str);
        continue;
        paramSvgRadialGradient.r = parseLength(str);
        if (paramSvgRadialGradient.r.isNegative())
        {
          throw new SAXException("Invalid <radialGradient> element. r cannot be negative");
          paramSvgRadialGradient.fx = parseLength(str);
          continue;
          paramSvgRadialGradient.fy = parseLength(str);
        }
      }
    }
  }
  
  private static void parseAttributesRect(SVG.Rect paramRect, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      do
      {
        do
        {
          do
          {
            do
            {
              for (;;)
              {
                i++;
                break;
                paramRect.x = parseLength(str);
                continue;
                paramRect.y = parseLength(str);
              }
              paramRect.width = parseLength(str);
            } while (!paramRect.width.isNegative());
            throw new SAXException("Invalid <rect> element. width cannot be negative");
            paramRect.height = parseLength(str);
          } while (!paramRect.height.isNegative());
          throw new SAXException("Invalid <rect> element. height cannot be negative");
          paramRect.rx = parseLength(str);
        } while (!paramRect.rx.isNegative());
        throw new SAXException("Invalid <rect> element. rx cannot be negative");
        paramRect.ry = parseLength(str);
      } while (!paramRect.ry.isNegative());
      throw new SAXException("Invalid <rect> element. ry cannot be negative");
    }
  }
  
  private static void parseAttributesStop(SVG.Stop paramStop, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      for (;;)
      {
        i++;
        break;
        paramStop.offset = parseGradiantOffset(str);
      }
    }
  }
  
  private static void parseAttributesStyle(SVG.SvgElementBase paramSvgElementBase, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str1 = paramAttributes.getValue(i).trim();
      if (str1.length() != 0) {
        switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
        {
        default: 
          if (paramSvgElementBase.baseStyle == null) {
            paramSvgElementBase.baseStyle = new SVG.Style();
          }
          processStyleProperty(paramSvgElementBase.baseStyle, paramAttributes.getLocalName(i), paramAttributes.getValue(i).trim());
        }
      }
      for (;;)
      {
        i++;
        break;
        TextScanner localTextScanner = new TextScanner(str1.replaceAll("/\\*.*?\\*/", ""));
        for (;;)
        {
          String str3 = localTextScanner.nextToken(':');
          localTextScanner.skipWhitespace();
          if (!localTextScanner.consume(':')) {
            break;
          }
          localTextScanner.skipWhitespace();
          String str4 = localTextScanner.nextToken(';');
          if (str4 == null) {
            break;
          }
          localTextScanner.skipWhitespace();
          if ((localTextScanner.empty()) || (localTextScanner.consume(';')))
          {
            if (paramSvgElementBase.style == null) {
              paramSvgElementBase.style = new SVG.Style();
            }
            processStyleProperty(paramSvgElementBase.style, str3, str4);
            localTextScanner.skipWhitespace();
          }
        }
        CSSParser.CSSTextScanner localCSSTextScanner = new CSSParser.CSSTextScanner(str1);
        ArrayList localArrayList = null;
        while (!localCSSTextScanner.empty())
        {
          String str2 = localCSSTextScanner.nextIdentifier();
          if (str2 == null) {
            throw new SAXException("Invalid value for \"class\" attribute: " + str1);
          }
          if (localArrayList == null) {
            localArrayList = new ArrayList();
          }
          localArrayList.add(str2);
          localCSSTextScanner.skipWhitespace();
        }
        paramSvgElementBase.classNames = localArrayList;
      }
    }
  }
  
  private static void parseAttributesTRef(SVG.TRef paramTRef, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      for (;;)
      {
        i++;
        break;
        if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i))) {
          paramTRef.href = str;
        }
      }
    }
  }
  
  private static void parseAttributesTextPath(SVG.TextPath paramTextPath, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      }
      for (;;)
      {
        i++;
        break;
        if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i)))
        {
          paramTextPath.href = str;
          continue;
          paramTextPath.startOffset = parseLength(str);
        }
      }
    }
  }
  
  private static void parseAttributesTextPosition(SVG.TextPositionedContainer paramTextPositionedContainer, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      }
      for (;;)
      {
        i++;
        break;
        paramTextPositionedContainer.x = parseLengthList(str);
        continue;
        paramTextPositionedContainer.y = parseLengthList(str);
        continue;
        paramTextPositionedContainer.dx = parseLengthList(str);
        continue;
        paramTextPositionedContainer.dy = parseLengthList(str);
      }
    }
  }
  
  private static void parseAttributesTransform(SVG.HasTransform paramHasTransform, Attributes paramAttributes)
    throws SAXException
  {
    for (int i = 0; i < paramAttributes.getLength(); i++) {
      if (SVGAttr.fromString(paramAttributes.getLocalName(i)) == SVGAttr.transform) {
        paramHasTransform.setTransform(parseTransformList(paramAttributes.getValue(i)));
      }
    }
  }
  
  private static void parseAttributesUse(SVG.Use paramUse, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      for (;;)
      {
        i++;
        break;
        paramUse.x = parseLength(str);
        continue;
        paramUse.y = parseLength(str);
        continue;
        paramUse.width = parseLength(str);
        if (paramUse.width.isNegative())
        {
          throw new SAXException("Invalid <use> element. width cannot be negative");
          paramUse.height = parseLength(str);
          if (paramUse.height.isNegative())
          {
            throw new SAXException("Invalid <use> element. height cannot be negative");
            if ("http://www.w3.org/1999/xlink".equals(paramAttributes.getURI(i))) {
              paramUse.href = str;
            }
          }
        }
      }
    }
  }
  
  private static void parseAttributesViewBox(SVG.SvgViewBoxContainer paramSvgViewBoxContainer, Attributes paramAttributes)
    throws SAXException
  {
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (SVGAttr.fromString(paramAttributes.getLocalName(i)))
      {
      }
      for (;;)
      {
        i++;
        break;
        TextScanner localTextScanner = new TextScanner(str);
        localTextScanner.skipWhitespace();
        float f1 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f2 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f3 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f4 = localTextScanner.nextFloat();
        if ((Float.isNaN(f1)) || (Float.isNaN(f2)) || (Float.isNaN(f3)) || (Float.isNaN(f4))) {
          throw new SAXException("Invalid viewBox definition - should have four numbers");
        }
        if (f3 < 0.0F) {
          throw new SAXException("Invalid viewBox. width cannot be negative");
        }
        if (f4 < 0.0F) {
          throw new SAXException("Invalid viewBox. height cannot be negative");
        }
        paramSvgViewBoxContainer.viewBox = new SVG.Box(f1, f2, f3, f4);
        continue;
        parsePreserveAspectRatio(paramSvgViewBoxContainer, str);
      }
    }
  }
  
  private static SVG.Colour parseColour(String paramString)
    throws SAXException
  {
    if (paramString.charAt(0) == '#')
    {
      int i = paramString.length();
      int j = 1;
      long l = 0L;
      IntegerParser localIntegerParser;
      if (1 >= i) {
        localIntegerParser = null;
      }
      while (localIntegerParser == null)
      {
        throw new SAXException("Bad hex colour value: " + paramString);
        label193:
        for (;;)
        {
          j++;
          if (j >= i) {
            break label195;
          }
          int i3 = paramString.charAt(j);
          if ((i3 >= 48) && (i3 <= 57)) {
            l = l * 16L + (i3 - 48);
          }
          for (;;)
          {
            if (l <= 4294967295L) {
              break label193;
            }
            localIntegerParser = null;
            break;
            if ((i3 >= 65) && (i3 <= 70))
            {
              l = 10L + (l * 16L + (i3 - 65));
            }
            else
            {
              if ((i3 < 97) || (i3 > 102)) {
                break label195;
              }
              l = 10L + (l * 16L + (i3 - 97));
            }
          }
        }
        label195:
        if (j == 1) {
          localIntegerParser = null;
        } else {
          localIntegerParser = new IntegerParser(l, j);
        }
      }
      int k = localIntegerParser.pos;
      if (k == 7) {
        return new SVG.Colour((int)localIntegerParser.value);
      }
      if (k == 4)
      {
        int m = (int)localIntegerParser.value;
        int n = m & 0xF00;
        int i1 = m & 0xF0;
        int i2 = m & 0xF;
        return new SVG.Colour(i2 | n << 16 | n << 12 | i1 << 8 | i1 << 4 | i2 << 4);
      }
      throw new SAXException("Bad hex colour value: " + paramString);
    }
    if (paramString.toLowerCase(Locale.US).startsWith("rgb("))
    {
      TextScanner localTextScanner = new TextScanner(paramString.substring(4));
      localTextScanner.skipWhitespace();
      float f1 = localTextScanner.nextFloat();
      if ((!Float.isNaN(f1)) && (localTextScanner.consume('%'))) {
        f1 = 256.0F * f1 / 100.0F;
      }
      float f2 = localTextScanner.checkedNextFloat(f1);
      if ((!Float.isNaN(f2)) && (localTextScanner.consume('%'))) {
        f2 = 256.0F * f2 / 100.0F;
      }
      float f3 = localTextScanner.checkedNextFloat(f2);
      if ((!Float.isNaN(f3)) && (localTextScanner.consume('%'))) {
        f3 = 256.0F * f3 / 100.0F;
      }
      localTextScanner.skipWhitespace();
      if ((Float.isNaN(f3)) || (!localTextScanner.consume(')'))) {
        throw new SAXException("Bad rgb() colour value: " + paramString);
      }
      return new SVG.Colour(clamp255(f1) << 16 | clamp255(f2) << 8 | clamp255(f3));
    }
    Integer localInteger = ColourKeywords.get(paramString.toLowerCase(Locale.US));
    if (localInteger == null) {
      throw new SAXException("Invalid colour keyword: " + paramString);
    }
    return new SVG.Colour(localInteger.intValue());
  }
  
  private static SVG.SvgPaint parseColourSpecifer(String paramString)
    throws SAXException
  {
    if (paramString.equals("none")) {
      return null;
    }
    if (paramString.equals("currentColor")) {
      return SVG.CurrentColor.getInstance();
    }
    return parseColour(paramString);
  }
  
  private static SVG.Style.FillRule parseFillRule(String paramString)
    throws SAXException
  {
    if ("nonzero".equals(paramString)) {
      return SVG.Style.FillRule.NonZero;
    }
    if ("evenodd".equals(paramString)) {
      return SVG.Style.FillRule.EvenOdd;
    }
    throw new SAXException("Invalid fill-rule property: " + paramString);
  }
  
  private static float parseFloat(String paramString)
    throws SAXException
  {
    int i = paramString.length();
    if (i == 0) {
      throw new SAXException("Invalid float value (empty string)");
    }
    return parseFloat$44bd8e8f(paramString, i);
  }
  
  private static float parseFloat$44bd8e8f(String paramString, int paramInt)
    throws SAXException
  {
    float f = new NumberParser().parseNumber(paramString, 0, paramInt);
    if (!Float.isNaN(f)) {
      return f;
    }
    throw new SAXException("Invalid float value: " + paramString);
  }
  
  private static void parseFont(SVG.Style paramStyle, String paramString)
    throws SAXException
  {
    Integer localInteger = null;
    SVG.Style.FontStyle localFontStyle1 = null;
    Object localObject = null;
    if ("|caption|icon|menu|message-box|small-caption|status-bar|".indexOf("|" + paramString + '|') != -1) {
      return;
    }
    TextScanner localTextScanner = new TextScanner(paramString);
    String str1;
    for (;;)
    {
      str1 = localTextScanner.nextToken('/');
      localTextScanner.skipWhitespace();
      if (str1 == null) {
        throw new SAXException("Invalid font style attribute: missing font size and family");
      }
      if ((localInteger != null) && (localFontStyle1 != null)) {
        break;
      }
      if (!str1.equals("normal")) {
        if (localInteger == null)
        {
          localInteger = FontWeightKeywords.get(str1);
          if (localInteger != null) {}
        }
        else if (localFontStyle1 == null)
        {
          localFontStyle1 = fontStyleKeyword(str1);
          if (localFontStyle1 != null) {}
        }
        else
        {
          if ((localObject != null) || (!str1.equals("small-caps"))) {
            break;
          }
          localObject = str1;
        }
      }
    }
    SVG.Length localLength = parseFontSize(str1);
    if (localTextScanner.consume('/'))
    {
      localTextScanner.skipWhitespace();
      String str2 = localTextScanner.nextToken();
      if (str2 == null) {
        throw new SAXException("Invalid font style attribute: missing line-height");
      }
      parseLength(str2);
      localTextScanner.skipWhitespace();
    }
    paramStyle.fontFamily = parseFontFamily(localTextScanner.restOfText());
    paramStyle.fontSize = localLength;
    int i;
    if (localInteger == null)
    {
      i = 400;
      paramStyle.fontWeight = Integer.valueOf(i);
      if (localFontStyle1 != null) {
        break label278;
      }
    }
    label278:
    for (SVG.Style.FontStyle localFontStyle2 = SVG.Style.FontStyle.Normal;; localFontStyle2 = localFontStyle1)
    {
      paramStyle.fontStyle = localFontStyle2;
      paramStyle.specifiedFlags = (0x1E000 | paramStyle.specifiedFlags);
      return;
      i = localInteger.intValue();
      break;
    }
  }
  
  private static List<String> parseFontFamily(String paramString)
    throws SAXException
  {
    ArrayList localArrayList = null;
    TextScanner localTextScanner = new TextScanner(paramString);
    do
    {
      String str = localTextScanner.nextQuotedString();
      if (str == null) {
        str = localTextScanner.nextToken(',');
      }
      if (str == null) {
        break;
      }
      if (localArrayList == null) {
        localArrayList = new ArrayList();
      }
      localArrayList.add(str);
      localTextScanner.skipCommaWhitespace();
    } while (!localTextScanner.empty());
    return localArrayList;
  }
  
  private static SVG.Length parseFontSize(String paramString)
    throws SAXException
  {
    SVG.Length localLength = FontSizeKeywords.get(paramString);
    if (localLength == null) {
      localLength = parseLength(paramString);
    }
    return localLength;
  }
  
  private static String parseFunctionalIRI(String paramString1, String paramString2)
    throws SAXException
  {
    if (paramString1.equals("none")) {
      return null;
    }
    if ((!paramString1.startsWith("url(")) || (!paramString1.endsWith(")"))) {
      throw new SAXException("Bad " + paramString2 + " attribute. Expected \"none\" or \"url()\" format");
    }
    return paramString1.substring(4, -1 + paramString1.length()).trim();
  }
  
  private static Float parseGradiantOffset(String paramString)
    throws SAXException
  {
    if (paramString.length() == 0) {
      throw new SAXException("Invalid offset value in <stop> (empty string)");
    }
    int i = paramString.length();
    int j = paramString.charAt(-1 + paramString.length());
    int k = 0;
    if (j == 37)
    {
      i--;
      k = 1;
    }
    float f;
    for (;;)
    {
      try
      {
        f = parseFloat$44bd8e8f(paramString, i);
        if (k == 0) {
          break label125;
        }
        f /= 100.0F;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Float localFloat;
        throw new SAXException("Invalid offset value in <stop>: " + paramString, localNumberFormatException);
      }
      localFloat = Float.valueOf(f);
      return localFloat;
      if (f > 100.0F)
      {
        f = 100.0F;
        continue;
        label125:
        if (f >= 0.0F) {
          break;
        }
        f = 0.0F;
      }
    }
  }
  
  private static SVG.Length parseLength(String paramString)
    throws SAXException
  {
    if (paramString.length() == 0) {
      throw new SAXException("Invalid length value (empty string)");
    }
    int i = paramString.length();
    Object localObject = SVG.Unit.px;
    char c = paramString.charAt(i - 1);
    if (c == '%')
    {
      i--;
      localObject = SVG.Unit.percent;
    }
    for (;;)
    {
      try
      {
        SVG.Length localLength = new SVG.Length(parseFloat$44bd8e8f(paramString, i), (SVG.Unit)localObject);
        return localLength;
      }
      catch (NumberFormatException localNumberFormatException)
      {
        String str;
        throw new SAXException("Invalid length value: " + paramString, localNumberFormatException);
      }
      if ((i <= 2) || (!Character.isLetter(c)) || (!Character.isLetter(paramString.charAt(i - 2)))) {
        continue;
      }
      i -= 2;
      str = paramString.substring(i);
      try
      {
        SVG.Unit localUnit = SVG.Unit.valueOf(str.toLowerCase(Locale.US));
        localObject = localUnit;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw new SAXException("Invalid length unit specifier: " + paramString);
      }
    }
  }
  
  private static List<SVG.Length> parseLengthList(String paramString)
    throws SAXException
  {
    if (paramString.length() == 0) {
      throw new SAXException("Invalid length list (empty string)");
    }
    ArrayList localArrayList = new ArrayList(1);
    TextScanner localTextScanner = new TextScanner(paramString);
    localTextScanner.skipWhitespace();
    while (!localTextScanner.empty())
    {
      float f = localTextScanner.nextFloat();
      if (Float.isNaN(f)) {
        throw new SAXException("Invalid length list value: " + localTextScanner.ahead());
      }
      SVG.Unit localUnit = localTextScanner.nextUnit();
      if (localUnit == null) {
        localUnit = SVG.Unit.px;
      }
      localArrayList.add(new SVG.Length(f, localUnit));
      localTextScanner.skipCommaWhitespace();
    }
    return localArrayList;
  }
  
  private static SVG.Length parseLengthOrAuto(TextScanner paramTextScanner)
  {
    if (paramTextScanner.consume("auto")) {
      return new SVG.Length(0.0F);
    }
    return paramTextScanner.nextLength();
  }
  
  private static float parseOpacity(String paramString)
    throws SAXException
  {
    float f = parseFloat(paramString);
    if (f < 0.0F) {
      f = 0.0F;
    }
    while (f <= 1.0F) {
      return f;
    }
    return 1.0F;
  }
  
  private static SVG.SvgPaint parsePaintSpecifier(String paramString1, String paramString2)
    throws SAXException
  {
    if (paramString1.startsWith("url("))
    {
      int i = paramString1.indexOf(")");
      if (i == -1) {
        throw new SAXException("Bad " + paramString2 + " attribute. Unterminated url() reference");
      }
      String str1 = paramString1.substring(4, i).trim();
      String str2 = paramString1.substring(i + 1).trim();
      int j = str2.length();
      SVG.SvgPaint localSvgPaint = null;
      if (j > 0) {
        localSvgPaint = parseColourSpecifer(str2);
      }
      return new SVG.PaintReference(str1, localSvgPaint);
    }
    return parseColourSpecifer(paramString1);
  }
  
  private static void parsePreserveAspectRatio(SVG.SvgPreserveAspectRatioContainer paramSvgPreserveAspectRatioContainer, String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    localTextScanner.skipWhitespace();
    String str1 = localTextScanner.nextToken();
    if ("defer".equals(str1))
    {
      localTextScanner.skipWhitespace();
      str1 = localTextScanner.nextToken();
    }
    PreserveAspectRatio.Alignment localAlignment = AspectRatioKeywords.get(str1);
    localTextScanner.skipWhitespace();
    boolean bool = localTextScanner.empty();
    PreserveAspectRatio.Scale localScale = null;
    String str2;
    if (!bool)
    {
      str2 = localTextScanner.nextToken();
      if (!str2.equals("meet")) {
        break label99;
      }
    }
    for (localScale = PreserveAspectRatio.Scale.Meet;; localScale = PreserveAspectRatio.Scale.Slice)
    {
      paramSvgPreserveAspectRatioContainer.preserveAspectRatio = new PreserveAspectRatio(localAlignment, localScale);
      return;
      label99:
      if (!str2.equals("slice")) {
        break;
      }
    }
    throw new SAXException("Invalid preserveAspectRatio definition: " + paramString);
  }
  
  private static Set<String> parseRequiredFormats(String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    HashSet localHashSet = new HashSet();
    while (!localTextScanner.empty())
    {
      localHashSet.add(localTextScanner.nextToken());
      localTextScanner.skipWhitespace();
    }
    return localHashSet;
  }
  
  private static SVG.Length[] parseStrokeDashArray(String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    localTextScanner.skipWhitespace();
    if (localTextScanner.empty()) {}
    float f;
    ArrayList localArrayList;
    do
    {
      SVG.Length localLength1;
      do
      {
        return null;
        localLength1 = localTextScanner.nextLength();
      } while (localLength1 == null);
      if (localLength1.isNegative()) {
        throw new SAXException("Invalid stroke-dasharray. Dash segemnts cannot be negative: " + paramString);
      }
      f = localLength1.value;
      localArrayList = new ArrayList();
      localArrayList.add(localLength1);
      while (!localTextScanner.empty())
      {
        localTextScanner.skipCommaWhitespace();
        SVG.Length localLength2 = localTextScanner.nextLength();
        if (localLength2 == null) {
          throw new SAXException("Invalid stroke-dasharray. Non-Length content found: " + paramString);
        }
        if (localLength2.isNegative()) {
          throw new SAXException("Invalid stroke-dasharray. Dash segemnts cannot be negative: " + paramString);
        }
        localArrayList.add(localLength2);
        f += localLength2.value;
      }
    } while (f == 0.0F);
    return (SVG.Length[])localArrayList.toArray(new SVG.Length[localArrayList.size()]);
  }
  
  private static Set<String> parseSystemLanguage(String paramString)
    throws SAXException
  {
    TextScanner localTextScanner = new TextScanner(paramString);
    HashSet localHashSet = new HashSet();
    while (!localTextScanner.empty())
    {
      String str = localTextScanner.nextToken();
      int i = str.indexOf('-');
      if (i != -1) {
        str = str.substring(0, i);
      }
      localHashSet.add(new Locale(str, "", "").getLanguage());
      localTextScanner.skipWhitespace();
    }
    return localHashSet;
  }
  
  private static Matrix parseTransformList(String paramString)
    throws SAXException
  {
    Matrix localMatrix1 = new Matrix();
    TextScanner localTextScanner = new TextScanner(paramString);
    localTextScanner.skipWhitespace();
    if (!localTextScanner.empty())
    {
      String str = localTextScanner.nextFunction();
      if (str == null) {
        throw new SAXException("Bad transform function encountered in transform list: " + paramString);
      }
      if (str.equals("matrix"))
      {
        localTextScanner.skipWhitespace();
        float f10 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f11 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f12 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f13 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f14 = localTextScanner.nextFloat();
        localTextScanner.skipCommaWhitespace();
        float f15 = localTextScanner.nextFloat();
        localTextScanner.skipWhitespace();
        if ((Float.isNaN(f15)) || (!localTextScanner.consume(')'))) {
          throw new SAXException("Invalid transform list: " + paramString);
        }
        Matrix localMatrix2 = new Matrix();
        localMatrix2.setValues(new float[] { f10, f12, f14, f11, f13, f15, 0.0F, 0.0F, 1.0F });
        localMatrix1.preConcat(localMatrix2);
      }
      for (;;)
      {
        if (!localTextScanner.empty())
        {
          localTextScanner.skipCommaWhitespace();
          break;
          if (str.equals("translate"))
          {
            localTextScanner.skipWhitespace();
            float f8 = localTextScanner.nextFloat();
            float f9 = localTextScanner.possibleNextFloat();
            localTextScanner.skipWhitespace();
            if ((Float.isNaN(f8)) || (!localTextScanner.consume(')'))) {
              throw new SAXException("Invalid transform list: " + paramString);
            }
            if (Float.isNaN(f9)) {
              localMatrix1.preTranslate(f8, 0.0F);
            } else {
              localMatrix1.preTranslate(f8, f9);
            }
          }
          else if (str.equals("scale"))
          {
            localTextScanner.skipWhitespace();
            float f6 = localTextScanner.nextFloat();
            float f7 = localTextScanner.possibleNextFloat();
            localTextScanner.skipWhitespace();
            if ((Float.isNaN(f6)) || (!localTextScanner.consume(')'))) {
              throw new SAXException("Invalid transform list: " + paramString);
            }
            if (Float.isNaN(f7)) {
              localMatrix1.preScale(f6, f6);
            } else {
              localMatrix1.preScale(f6, f7);
            }
          }
          else if (str.equals("rotate"))
          {
            localTextScanner.skipWhitespace();
            float f3 = localTextScanner.nextFloat();
            float f4 = localTextScanner.possibleNextFloat();
            float f5 = localTextScanner.possibleNextFloat();
            localTextScanner.skipWhitespace();
            if ((Float.isNaN(f3)) || (!localTextScanner.consume(')'))) {
              throw new SAXException("Invalid transform list: " + paramString);
            }
            if (Float.isNaN(f4)) {
              localMatrix1.preRotate(f3);
            } else if (!Float.isNaN(f5)) {
              localMatrix1.preRotate(f3, f4, f5);
            } else {
              throw new SAXException("Invalid transform list: " + paramString);
            }
          }
          else if (str.equals("skewX"))
          {
            localTextScanner.skipWhitespace();
            float f2 = localTextScanner.nextFloat();
            localTextScanner.skipWhitespace();
            if ((Float.isNaN(f2)) || (!localTextScanner.consume(')'))) {
              throw new SAXException("Invalid transform list: " + paramString);
            }
            localMatrix1.preSkew((float)Math.tan(Math.toRadians(f2)), 0.0F);
          }
          else if (str.equals("skewY"))
          {
            localTextScanner.skipWhitespace();
            float f1 = localTextScanner.nextFloat();
            localTextScanner.skipWhitespace();
            if ((Float.isNaN(f1)) || (!localTextScanner.consume(')'))) {
              throw new SAXException("Invalid transform list: " + paramString);
            }
            localMatrix1.preSkew(0.0F, (float)Math.tan(Math.toRadians(f1)));
          }
          else if (str != null)
          {
            throw new SAXException("Invalid transform list fn: " + str + ")");
          }
        }
      }
    }
    return localMatrix1;
  }
  
  protected static void processStyleProperty(SVG.Style paramStyle, String paramString1, String paramString2)
    throws SAXException
  {
    if (paramString2.length() == 0) {}
    while (paramString2.equals("inherit")) {
      return;
    }
    switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramString1).ordinal()])
    {
    default: 
      return;
    case 47: 
      paramStyle.fill = parsePaintSpecifier(paramString2, "fill");
      paramStyle.specifiedFlags = (1L | paramStyle.specifiedFlags);
      return;
    case 48: 
      paramStyle.fillRule = parseFillRule(paramString2);
      paramStyle.specifiedFlags = (0x2 | paramStyle.specifiedFlags);
      return;
    case 49: 
      paramStyle.fillOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x4 | paramStyle.specifiedFlags);
      return;
    case 50: 
      paramStyle.stroke = parsePaintSpecifier(paramString2, "stroke");
      paramStyle.specifiedFlags = (0x8 | paramStyle.specifiedFlags);
      return;
    case 51: 
      paramStyle.strokeOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x10 | paramStyle.specifiedFlags);
      return;
    case 52: 
      paramStyle.strokeWidth = parseLength(paramString2);
      paramStyle.specifiedFlags = (0x20 | paramStyle.specifiedFlags);
      return;
    case 53: 
      SVG.Style.LineCaps localLineCaps;
      if ("butt".equals(paramString2)) {
        localLineCaps = SVG.Style.LineCaps.Butt;
      }
      for (;;)
      {
        paramStyle.strokeLineCap = localLineCaps;
        paramStyle.specifiedFlags = (0x40 | paramStyle.specifiedFlags);
        return;
        if ("round".equals(paramString2))
        {
          localLineCaps = SVG.Style.LineCaps.Round;
        }
        else
        {
          if (!"square".equals(paramString2)) {
            break;
          }
          localLineCaps = SVG.Style.LineCaps.Square;
        }
      }
      throw new SAXException("Invalid stroke-linecap property: " + paramString2);
    case 54: 
      SVG.Style.LineJoin localLineJoin;
      if ("miter".equals(paramString2)) {
        localLineJoin = SVG.Style.LineJoin.Miter;
      }
      for (;;)
      {
        paramStyle.strokeLineJoin = localLineJoin;
        paramStyle.specifiedFlags = (0x80 | paramStyle.specifiedFlags);
        return;
        if ("round".equals(paramString2))
        {
          localLineJoin = SVG.Style.LineJoin.Round;
        }
        else
        {
          if (!"bevel".equals(paramString2)) {
            break;
          }
          localLineJoin = SVG.Style.LineJoin.Bevel;
        }
      }
      throw new SAXException("Invalid stroke-linejoin property: " + paramString2);
    case 55: 
      paramStyle.strokeMiterLimit = Float.valueOf(parseFloat(paramString2));
      paramStyle.specifiedFlags = (0x100 | paramStyle.specifiedFlags);
      return;
    case 56: 
      if ("none".equals(paramString2)) {}
      for (paramStyle.strokeDashArray = null;; paramStyle.strokeDashArray = parseStrokeDashArray(paramString2))
      {
        paramStyle.specifiedFlags = (0x200 | paramStyle.specifiedFlags);
        return;
      }
    case 57: 
      paramStyle.strokeDashOffset = parseLength(paramString2);
      paramStyle.specifiedFlags = (0x400 | paramStyle.specifiedFlags);
      return;
    case 58: 
      paramStyle.opacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x800 | paramStyle.specifiedFlags);
      return;
    case 59: 
      paramStyle.color = parseColour(paramString2);
      paramStyle.specifiedFlags = (0x1000 | paramStyle.specifiedFlags);
      return;
    case 60: 
      parseFont(paramStyle, paramString2);
      return;
    case 61: 
      paramStyle.fontFamily = parseFontFamily(paramString2);
      paramStyle.specifiedFlags = (0x2000 | paramStyle.specifiedFlags);
      return;
    case 62: 
      paramStyle.fontSize = parseFontSize(paramString2);
      paramStyle.specifiedFlags = (0x4000 | paramStyle.specifiedFlags);
      return;
    case 63: 
      Integer localInteger = FontWeightKeywords.get(paramString2);
      if (localInteger == null) {
        throw new SAXException("Invalid font-weight property: " + paramString2);
      }
      paramStyle.fontWeight = localInteger;
      paramStyle.specifiedFlags = (0x8000 | paramStyle.specifiedFlags);
      return;
    case 64: 
      SVG.Style.FontStyle localFontStyle = fontStyleKeyword(paramString2);
      if (localFontStyle != null)
      {
        paramStyle.fontStyle = localFontStyle;
        paramStyle.specifiedFlags = (0x10000 | paramStyle.specifiedFlags);
        return;
      }
      throw new SAXException("Invalid font-style property: " + paramString2);
    case 65: 
      SVG.Style.TextDecoration localTextDecoration;
      if ("none".equals(paramString2)) {
        localTextDecoration = SVG.Style.TextDecoration.None;
      }
      for (;;)
      {
        paramStyle.textDecoration = localTextDecoration;
        paramStyle.specifiedFlags = (0x20000 | paramStyle.specifiedFlags);
        return;
        if ("underline".equals(paramString2))
        {
          localTextDecoration = SVG.Style.TextDecoration.Underline;
        }
        else if ("overline".equals(paramString2))
        {
          localTextDecoration = SVG.Style.TextDecoration.Overline;
        }
        else if ("line-through".equals(paramString2))
        {
          localTextDecoration = SVG.Style.TextDecoration.LineThrough;
        }
        else
        {
          if (!"blink".equals(paramString2)) {
            break;
          }
          localTextDecoration = SVG.Style.TextDecoration.Blink;
        }
      }
      throw new SAXException("Invalid text-decoration property: " + paramString2);
    case 66: 
      if ("ltr".equals(paramString2)) {}
      for (SVG.Style.TextDirection localTextDirection = SVG.Style.TextDirection.LTR;; localTextDirection = SVG.Style.TextDirection.RTL)
      {
        paramStyle.direction = localTextDirection;
        paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
        return;
        if (!"rtl".equals(paramString2)) {
          break;
        }
      }
      throw new SAXException("Invalid direction property: " + paramString2);
    case 67: 
      SVG.Style.TextAnchor localTextAnchor;
      if ("start".equals(paramString2)) {
        localTextAnchor = SVG.Style.TextAnchor.Start;
      }
      for (;;)
      {
        paramStyle.textAnchor = localTextAnchor;
        paramStyle.specifiedFlags = (0x40000 | paramStyle.specifiedFlags);
        return;
        if ("middle".equals(paramString2))
        {
          localTextAnchor = SVG.Style.TextAnchor.Middle;
        }
        else
        {
          if (!"end".equals(paramString2)) {
            break;
          }
          localTextAnchor = SVG.Style.TextAnchor.End;
        }
      }
      throw new SAXException("Invalid text-anchor property: " + paramString2);
    case 68: 
      if (("visible".equals(paramString2)) || ("auto".equals(paramString2))) {}
      for (Boolean localBoolean = Boolean.TRUE;; localBoolean = Boolean.FALSE)
      {
        paramStyle.overflow = localBoolean;
        paramStyle.specifiedFlags = (0x80000 | paramStyle.specifiedFlags);
        return;
        if ((!"hidden".equals(paramString2)) && (!"scroll".equals(paramString2))) {
          break;
        }
      }
      throw new SAXException("Invalid toverflow property: " + paramString2);
    case 69: 
      paramStyle.markerStart = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.markerMid = paramStyle.markerStart;
      paramStyle.markerEnd = paramStyle.markerStart;
      paramStyle.specifiedFlags = (0xE00000 | paramStyle.specifiedFlags);
      return;
    case 70: 
      paramStyle.markerStart = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x200000 | paramStyle.specifiedFlags);
      return;
    case 71: 
      paramStyle.markerMid = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x400000 | paramStyle.specifiedFlags);
      return;
    case 72: 
      paramStyle.markerEnd = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x800000 | paramStyle.specifiedFlags);
      return;
    case 73: 
      if ((paramString2.indexOf('|') >= 0) || ("|inline|block|list-item|run-in|compact|marker|table|inline-table|table-row-group|table-header-group|table-footer-group|table-row|table-column-group|table-column|table-cell|table-caption|none|".indexOf("|" + paramString2 + '|') == -1)) {
        throw new SAXException("Invalid value for \"display\" attribute: " + paramString2);
      }
      if (!paramString2.equals("none")) {}
      for (boolean bool2 = true;; bool2 = false)
      {
        paramStyle.display = Boolean.valueOf(bool2);
        paramStyle.specifiedFlags = (0x1000000 | paramStyle.specifiedFlags);
        return;
      }
    case 74: 
      if ((paramString2.indexOf('|') >= 0) || ("|visible|hidden|collapse|".indexOf("|" + paramString2 + '|') == -1)) {
        throw new SAXException("Invalid value for \"visibility\" attribute: " + paramString2);
      }
      paramStyle.visibility = Boolean.valueOf(paramString2.equals("visible"));
      paramStyle.specifiedFlags = (0x2000000 | paramStyle.specifiedFlags);
      return;
    case 75: 
      if (paramString2.equals("currentColor")) {}
      for (paramStyle.stopColor = SVG.CurrentColor.getInstance();; paramStyle.stopColor = parseColour(paramString2))
      {
        paramStyle.specifiedFlags = (0x4000000 | paramStyle.specifiedFlags);
        return;
      }
    case 76: 
      paramStyle.stopOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x8000000 | paramStyle.specifiedFlags);
      return;
    case 77: 
      boolean bool1 = "auto".equals(paramString2);
      SVG.CSSClipRect localCSSClipRect = null;
      if (bool1) {}
      for (;;)
      {
        paramStyle.clip = localCSSClipRect;
        paramStyle.specifiedFlags = (0x100000 | paramStyle.specifiedFlags);
        return;
        if (!paramString2.toLowerCase(Locale.US).startsWith("rect(")) {
          throw new SAXException("Invalid clip attribute shape. Only rect() is supported.");
        }
        TextScanner localTextScanner = new TextScanner(paramString2.substring(5));
        localTextScanner.skipWhitespace();
        SVG.Length localLength1 = parseLengthOrAuto(localTextScanner);
        localTextScanner.skipCommaWhitespace();
        SVG.Length localLength2 = parseLengthOrAuto(localTextScanner);
        localTextScanner.skipCommaWhitespace();
        SVG.Length localLength3 = parseLengthOrAuto(localTextScanner);
        localTextScanner.skipCommaWhitespace();
        SVG.Length localLength4 = parseLengthOrAuto(localTextScanner);
        localTextScanner.skipWhitespace();
        if (!localTextScanner.consume(')')) {
          throw new SAXException("Bad rect() clip definition: " + paramString2);
        }
        localCSSClipRect = new SVG.CSSClipRect(localLength1, localLength2, localLength3, localLength4);
      }
    case 78: 
      paramStyle.clipPath = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x10000000 | paramStyle.specifiedFlags);
      return;
    case 79: 
      paramStyle.clipRule = parseFillRule(paramString2);
      paramStyle.specifiedFlags = (0x20000000 | paramStyle.specifiedFlags);
      return;
    case 80: 
      paramStyle.mask = parseFunctionalIRI(paramString2, paramString1);
      paramStyle.specifiedFlags = (0x40000000 | paramStyle.specifiedFlags);
      return;
    case 81: 
      if (paramString2.equals("currentColor")) {}
      for (paramStyle.solidColor = SVG.CurrentColor.getInstance();; paramStyle.solidColor = parseColour(paramString2))
      {
        paramStyle.specifiedFlags = (0x80000000 | paramStyle.specifiedFlags);
        return;
      }
    case 82: 
      paramStyle.solidOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
      return;
    case 83: 
      if (paramString2.equals("currentColor")) {}
      for (paramStyle.viewportFill = SVG.CurrentColor.getInstance();; paramStyle.viewportFill = parseColour(paramString2))
      {
        paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
        return;
      }
    case 84: 
      paramStyle.viewportFillOpacity = Float.valueOf(parseOpacity(paramString2));
      paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
      return;
    }
    if ("none".equals(paramString2)) {}
    for (SVG.Style.VectorEffect localVectorEffect = SVG.Style.VectorEffect.None;; localVectorEffect = SVG.Style.VectorEffect.NonScalingStroke)
    {
      paramStyle.vectorEffect = localVectorEffect;
      paramStyle.specifiedFlags = (0x0 | paramStyle.specifiedFlags);
      return;
      if (!"non-scaling-stroke".equals(paramString2)) {
        break;
      }
    }
    throw new SAXException("Invalid vector-effect property: " + paramString2);
  }
  
  private void style(Attributes paramAttributes)
    throws SAXException
  {
    if (this.currentElement == null) {
      throw new SAXException("Invalid document. Root element must be <svg>");
    }
    boolean bool = true;
    Object localObject = "all";
    int i = 0;
    if (i < paramAttributes.getLength())
    {
      String str = paramAttributes.getValue(i).trim();
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
      {
      }
      for (;;)
      {
        i++;
        break;
        bool = str.equals("text/css");
        continue;
        localObject = str;
      }
    }
    if (bool)
    {
      CSSParser.MediaType localMediaType = CSSParser.MediaType.screen;
      CSSParser.CSSTextScanner localCSSTextScanner = new CSSParser.CSSTextScanner((String)localObject);
      localCSSTextScanner.skipWhitespace();
      List localList = CSSParser.parseMediaList(localCSSTextScanner);
      if (!localCSSTextScanner.empty()) {
        throw new SAXException("Invalid @media type list");
      }
      if (CSSParser.mediaMatches(localList, localMediaType))
      {
        this.inStyleElement = true;
        return;
      }
    }
    this.ignoring = true;
    this.ignoreDepth = 1;
  }
  
  public final void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.ignoring) {}
    do
    {
      return;
      if (this.inMetadataElement)
      {
        if (this.metadataElementContents == null) {
          this.metadataElementContents = new StringBuilder(paramInt2);
        }
        this.metadataElementContents.append(paramArrayOfChar, paramInt1, paramInt2);
        return;
      }
      if (this.inStyleElement)
      {
        if (this.styleElementContents == null) {
          this.styleElementContents = new StringBuilder(paramInt2);
        }
        this.styleElementContents.append(paramArrayOfChar, paramInt1, paramInt2);
        return;
      }
    } while (!(this.currentElement instanceof SVG.TextContainer));
    SVG.SvgConditionalContainer localSvgConditionalContainer = (SVG.SvgConditionalContainer)this.currentElement;
    int i = localSvgConditionalContainer.children.size();
    if (i == 0) {}
    for (SVG.SvgObject localSvgObject = null; (localSvgObject instanceof SVG.TextSequence); localSvgObject = (SVG.SvgObject)localSvgConditionalContainer.children.get(i - 1))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      SVG.TextSequence localTextSequence = (SVG.TextSequence)localSvgObject;
      localTextSequence.text += new String(paramArrayOfChar, paramInt1, paramInt2);
      return;
    }
    ((SVG.SvgConditionalContainer)this.currentElement).addChild(new SVG.TextSequence(new String(paramArrayOfChar, paramInt1, paramInt2)));
  }
  
  public final void comment(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (this.ignoring) {}
    while (!this.inStyleElement) {
      return;
    }
    if (this.styleElementContents == null) {
      this.styleElementContents = new StringBuilder(paramInt2);
    }
    this.styleElementContents.append(paramArrayOfChar, paramInt1, paramInt2);
  }
  
  public final void endDocument()
    throws SAXException
  {}
  
  public final void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (this.ignoring)
    {
      int i = -1 + this.ignoreDepth;
      this.ignoreDepth = i;
      if (i == 0) {
        this.ignoring = false;
      }
    }
    do
    {
      do
      {
        return;
      } while ((!"http://www.w3.org/2000/svg".equals(paramString1)) && (!"".equals(paramString1)));
      switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[SVGElem.fromString(paramString2).ordinal()])
      {
      case 3: 
      case 6: 
      case 7: 
      case 8: 
      case 9: 
      case 10: 
      case 11: 
      case 12: 
      case 15: 
      default: 
        return;
      case 1: 
      case 2: 
      case 4: 
      case 5: 
      case 13: 
      case 14: 
      case 16: 
      case 17: 
      case 18: 
      case 19: 
      case 20: 
      case 21: 
      case 24: 
      case 25: 
      case 26: 
      case 27: 
      case 28: 
      case 29: 
      case 31: 
        this.currentElement = ((SVG.SvgObject)this.currentElement).parent;
        return;
      case 22: 
      case 23: 
        this.inMetadataElement = false;
        if (this.metadataTag == SVGElem.title) {
          this.svgDocument.title = this.metadataElementContents.toString();
        }
        for (;;)
        {
          this.metadataElementContents.setLength(0);
          return;
          if (this.metadataTag == SVGElem.desc) {
            this.svgDocument.desc = this.metadataElementContents.toString();
          }
        }
      }
    } while (this.styleElementContents == null);
    this.inStyleElement = false;
    String str = this.styleElementContents.toString();
    CSSParser localCSSParser = new CSSParser(CSSParser.MediaType.screen);
    SVG localSVG = this.svgDocument;
    CSSParser.CSSTextScanner localCSSTextScanner = new CSSParser.CSSTextScanner(str);
    localCSSTextScanner.skipWhitespace();
    CSSParser.Ruleset localRuleset = localCSSParser.parseRuleset(localCSSTextScanner);
    localSVG.cssRules.addAll(localRuleset);
    this.styleElementContents.setLength(0);
  }
  
  /* Error */
  protected final SVG parse(java.io.InputStream paramInputStream)
    throws SVGParseException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 1565	java/io/InputStream:markSupported	()Z
    //   4: ifne +223 -> 227
    //   7: new 1567	java/io/BufferedInputStream
    //   10: dup
    //   11: aload_1
    //   12: invokespecial 1570	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   15: astore_2
    //   16: aload_2
    //   17: iconst_3
    //   18: invokevirtual 1573	java/io/InputStream:mark	(I)V
    //   21: aload_2
    //   22: invokevirtual 1576	java/io/InputStream:read	()I
    //   25: aload_2
    //   26: invokevirtual 1576	java/io/InputStream:read	()I
    //   29: bipush 8
    //   31: ishl
    //   32: iadd
    //   33: istore 15
    //   35: aload_2
    //   36: invokevirtual 1579	java/io/InputStream:reset	()V
    //   39: iload 15
    //   41: ldc_w 1580
    //   44: if_icmpne +74 -> 118
    //   47: new 1582	java/util/zip/GZIPInputStream
    //   50: dup
    //   51: aload_2
    //   52: invokespecial 1583	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   55: astore 4
    //   57: invokestatic 1589	javax/xml/parsers/SAXParserFactory:newInstance	()Ljavax/xml/parsers/SAXParserFactory;
    //   60: astore 5
    //   62: aload 5
    //   64: invokevirtual 1593	javax/xml/parsers/SAXParserFactory:newSAXParser	()Ljavax/xml/parsers/SAXParser;
    //   67: invokevirtual 1599	javax/xml/parsers/SAXParser:getXMLReader	()Lorg/xml/sax/XMLReader;
    //   70: astore 12
    //   72: aload 12
    //   74: aload_0
    //   75: invokeinterface 1605 2 0
    //   80: aload 12
    //   82: ldc_w 1607
    //   85: aload_0
    //   86: invokeinterface 1611 3 0
    //   91: aload 12
    //   93: new 1613	org/xml/sax/InputSource
    //   96: dup
    //   97: aload 4
    //   99: invokespecial 1614	org/xml/sax/InputSource:<init>	(Ljava/io/InputStream;)V
    //   102: invokeinterface 1617 2 0
    //   107: aload 4
    //   109: invokevirtual 1620	java/io/InputStream:close	()V
    //   112: aload_0
    //   113: getfield 28	com/caverock/androidsvg/SVGParser:svgDocument	Lcom/caverock/androidsvg/SVG;
    //   116: areturn
    //   117: astore_3
    //   118: aload_2
    //   119: astore 4
    //   121: goto -64 -> 57
    //   124: astore 13
    //   126: ldc_w 472
    //   129: ldc_w 1622
    //   132: invokestatic 485	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   135: pop
    //   136: goto -24 -> 112
    //   139: astore 11
    //   141: new 1556	com/caverock/androidsvg/SVGParseException
    //   144: dup
    //   145: ldc_w 1624
    //   148: aload 11
    //   150: invokespecial 1627	com/caverock/androidsvg/SVGParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   153: athrow
    //   154: astore 7
    //   156: aload 4
    //   158: invokevirtual 1620	java/io/InputStream:close	()V
    //   161: aload 7
    //   163: athrow
    //   164: astore 10
    //   166: new 1556	com/caverock/androidsvg/SVGParseException
    //   169: dup
    //   170: ldc_w 1629
    //   173: aload 10
    //   175: invokespecial 1627	com/caverock/androidsvg/SVGParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   178: athrow
    //   179: astore 6
    //   181: new 1556	com/caverock/androidsvg/SVGParseException
    //   184: dup
    //   185: new 258	java/lang/StringBuilder
    //   188: dup
    //   189: ldc_w 1631
    //   192: invokespecial 261	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   195: aload 6
    //   197: invokevirtual 1634	org/xml/sax/SAXException:getMessage	()Ljava/lang/String;
    //   200: invokevirtual 265	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: invokevirtual 268	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   206: aload 6
    //   208: invokespecial 1627	com/caverock/androidsvg/SVGParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   211: athrow
    //   212: astore 8
    //   214: ldc_w 472
    //   217: ldc_w 1622
    //   220: invokestatic 485	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   223: pop
    //   224: goto -63 -> 161
    //   227: aload_1
    //   228: astore_2
    //   229: goto -213 -> 16
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	232	0	this	SVGParser
    //   0	232	1	paramInputStream	java.io.InputStream
    //   15	214	2	localObject1	Object
    //   117	1	3	localIOException1	java.io.IOException
    //   55	102	4	localObject2	Object
    //   60	3	5	localSAXParserFactory	javax.xml.parsers.SAXParserFactory
    //   179	28	6	localSAXException	SAXException
    //   154	8	7	localObject3	Object
    //   212	1	8	localIOException2	java.io.IOException
    //   164	10	10	localParserConfigurationException	javax.xml.parsers.ParserConfigurationException
    //   139	10	11	localIOException3	java.io.IOException
    //   70	22	12	localXMLReader	org.xml.sax.XMLReader
    //   124	1	13	localIOException4	java.io.IOException
    //   33	12	15	i	int
    // Exception table:
    //   from	to	target	type
    //   16	39	117	java/io/IOException
    //   47	57	117	java/io/IOException
    //   107	112	124	java/io/IOException
    //   62	107	139	java/io/IOException
    //   62	107	154	finally
    //   141	154	154	finally
    //   166	179	154	finally
    //   181	212	154	finally
    //   62	107	164	javax/xml/parsers/ParserConfigurationException
    //   62	107	179	org/xml/sax/SAXException
    //   156	161	212	java/io/IOException
  }
  
  public final void startDocument()
    throws SAXException
  {
    this.svgDocument = new SVG();
  }
  
  public final void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (this.ignoring) {
      this.ignoreDepth = (1 + this.ignoreDepth);
    }
    while ((!"http://www.w3.org/2000/svg".equals(paramString1)) && (!"".equals(paramString1))) {
      return;
    }
    SVGElem localSVGElem = SVGElem.fromString(paramString2);
    switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGElem[localSVGElem.ordinal()])
    {
    default: 
      this.ignoring = true;
      this.ignoreDepth = 1;
      return;
    case 1: 
      SVG.Svg localSvg = new SVG.Svg();
      localSvg.document = this.svgDocument;
      localSvg.parent = this.currentElement;
      parseAttributesCore(localSvg, paramAttributes);
      parseAttributesStyle(localSvg, paramAttributes);
      parseAttributesConditional(localSvg, paramAttributes);
      parseAttributesViewBox(localSvg, paramAttributes);
      int i = 0;
      if (i < paramAttributes.getLength())
      {
        String str = paramAttributes.getValue(i).trim();
        switch (1.$SwitchMap$com$caverock$androidsvg$SVGParser$SVGAttr[SVGAttr.fromString(paramAttributes.getLocalName(i)).ordinal()])
        {
        }
        for (;;)
        {
          i++;
          break;
          localSvg.x = parseLength(str);
          continue;
          localSvg.y = parseLength(str);
          continue;
          localSvg.width = parseLength(str);
          if (localSvg.width.isNegative())
          {
            throw new SAXException("Invalid <svg> element. width cannot be negative");
            localSvg.height = parseLength(str);
            if (localSvg.height.isNegative())
            {
              throw new SAXException("Invalid <svg> element. height cannot be negative");
              localSvg.version = str;
            }
          }
        }
      }
      if (this.currentElement == null) {
        this.svgDocument.rootElement = localSvg;
      }
      for (;;)
      {
        this.currentElement = localSvg;
        return;
        this.currentElement.addChild(localSvg);
      }
    case 2: 
    case 3: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Group localGroup = new SVG.Group();
      localGroup.document = this.svgDocument;
      localGroup.parent = this.currentElement;
      parseAttributesCore(localGroup, paramAttributes);
      parseAttributesStyle(localGroup, paramAttributes);
      parseAttributesTransform(localGroup, paramAttributes);
      parseAttributesConditional(localGroup, paramAttributes);
      this.currentElement.addChild(localGroup);
      this.currentElement = localGroup;
      return;
    case 4: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Defs localDefs = new SVG.Defs();
      localDefs.document = this.svgDocument;
      localDefs.parent = this.currentElement;
      parseAttributesCore(localDefs, paramAttributes);
      parseAttributesStyle(localDefs, paramAttributes);
      parseAttributesTransform(localDefs, paramAttributes);
      this.currentElement.addChild(localDefs);
      this.currentElement = localDefs;
      return;
    case 5: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Use localUse = new SVG.Use();
      localUse.document = this.svgDocument;
      localUse.parent = this.currentElement;
      parseAttributesCore(localUse, paramAttributes);
      parseAttributesStyle(localUse, paramAttributes);
      parseAttributesTransform(localUse, paramAttributes);
      parseAttributesConditional(localUse, paramAttributes);
      parseAttributesUse(localUse, paramAttributes);
      this.currentElement.addChild(localUse);
      this.currentElement = localUse;
      return;
    case 6: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Path localPath = new SVG.Path();
      localPath.document = this.svgDocument;
      localPath.parent = this.currentElement;
      parseAttributesCore(localPath, paramAttributes);
      parseAttributesStyle(localPath, paramAttributes);
      parseAttributesTransform(localPath, paramAttributes);
      parseAttributesConditional(localPath, paramAttributes);
      parseAttributesPath(localPath, paramAttributes);
      this.currentElement.addChild(localPath);
      return;
    case 7: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Rect localRect = new SVG.Rect();
      localRect.document = this.svgDocument;
      localRect.parent = this.currentElement;
      parseAttributesCore(localRect, paramAttributes);
      parseAttributesStyle(localRect, paramAttributes);
      parseAttributesTransform(localRect, paramAttributes);
      parseAttributesConditional(localRect, paramAttributes);
      parseAttributesRect(localRect, paramAttributes);
      this.currentElement.addChild(localRect);
      return;
    case 8: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Circle localCircle = new SVG.Circle();
      localCircle.document = this.svgDocument;
      localCircle.parent = this.currentElement;
      parseAttributesCore(localCircle, paramAttributes);
      parseAttributesStyle(localCircle, paramAttributes);
      parseAttributesTransform(localCircle, paramAttributes);
      parseAttributesConditional(localCircle, paramAttributes);
      parseAttributesCircle(localCircle, paramAttributes);
      this.currentElement.addChild(localCircle);
      return;
    case 9: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Ellipse localEllipse = new SVG.Ellipse();
      localEllipse.document = this.svgDocument;
      localEllipse.parent = this.currentElement;
      parseAttributesCore(localEllipse, paramAttributes);
      parseAttributesStyle(localEllipse, paramAttributes);
      parseAttributesTransform(localEllipse, paramAttributes);
      parseAttributesConditional(localEllipse, paramAttributes);
      parseAttributesEllipse(localEllipse, paramAttributes);
      this.currentElement.addChild(localEllipse);
      return;
    case 10: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Line localLine = new SVG.Line();
      localLine.document = this.svgDocument;
      localLine.parent = this.currentElement;
      parseAttributesCore(localLine, paramAttributes);
      parseAttributesStyle(localLine, paramAttributes);
      parseAttributesTransform(localLine, paramAttributes);
      parseAttributesConditional(localLine, paramAttributes);
      parseAttributesLine(localLine, paramAttributes);
      this.currentElement.addChild(localLine);
      return;
    case 11: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.PolyLine localPolyLine = new SVG.PolyLine();
      localPolyLine.document = this.svgDocument;
      localPolyLine.parent = this.currentElement;
      parseAttributesCore(localPolyLine, paramAttributes);
      parseAttributesStyle(localPolyLine, paramAttributes);
      parseAttributesTransform(localPolyLine, paramAttributes);
      parseAttributesConditional(localPolyLine, paramAttributes);
      parseAttributesPolyLine(localPolyLine, paramAttributes, "polyline");
      this.currentElement.addChild(localPolyLine);
      return;
    case 12: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Polygon localPolygon = new SVG.Polygon();
      localPolygon.document = this.svgDocument;
      localPolygon.parent = this.currentElement;
      parseAttributesCore(localPolygon, paramAttributes);
      parseAttributesStyle(localPolygon, paramAttributes);
      parseAttributesTransform(localPolygon, paramAttributes);
      parseAttributesConditional(localPolygon, paramAttributes);
      parseAttributesPolyLine(localPolygon, paramAttributes, "polygon");
      this.currentElement.addChild(localPolygon);
      return;
    case 13: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Text localText = new SVG.Text();
      localText.document = this.svgDocument;
      localText.parent = this.currentElement;
      parseAttributesCore(localText, paramAttributes);
      parseAttributesStyle(localText, paramAttributes);
      parseAttributesTransform(localText, paramAttributes);
      parseAttributesConditional(localText, paramAttributes);
      parseAttributesTextPosition(localText, paramAttributes);
      this.currentElement.addChild(localText);
      this.currentElement = localText;
      return;
    case 14: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      if (!(this.currentElement instanceof SVG.TextContainer)) {
        throw new SAXException("Invalid document. <tspan> elements are only valid inside <text> or other <tspan> elements.");
      }
      SVG.TSpan localTSpan = new SVG.TSpan();
      localTSpan.document = this.svgDocument;
      localTSpan.parent = this.currentElement;
      parseAttributesCore(localTSpan, paramAttributes);
      parseAttributesStyle(localTSpan, paramAttributes);
      parseAttributesConditional(localTSpan, paramAttributes);
      parseAttributesTextPosition(localTSpan, paramAttributes);
      this.currentElement.addChild(localTSpan);
      this.currentElement = localTSpan;
      if ((localTSpan.parent instanceof SVG.TextRoot))
      {
        localTSpan.textRoot = ((SVG.TextRoot)localTSpan.parent);
        return;
      }
      localTSpan.textRoot = ((SVG.TextChild)localTSpan.parent).getTextRoot();
      return;
    case 15: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      if (!(this.currentElement instanceof SVG.TextContainer)) {
        throw new SAXException("Invalid document. <tref> elements are only valid inside <text> or <tspan> elements.");
      }
      SVG.TRef localTRef = new SVG.TRef();
      localTRef.document = this.svgDocument;
      localTRef.parent = this.currentElement;
      parseAttributesCore(localTRef, paramAttributes);
      parseAttributesStyle(localTRef, paramAttributes);
      parseAttributesConditional(localTRef, paramAttributes);
      parseAttributesTRef(localTRef, paramAttributes);
      this.currentElement.addChild(localTRef);
      if ((localTRef.parent instanceof SVG.TextRoot))
      {
        localTRef.textRoot = ((SVG.TextRoot)localTRef.parent);
        return;
      }
      localTRef.textRoot = ((SVG.TextChild)localTRef.parent).getTextRoot();
      return;
    case 16: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Switch localSwitch = new SVG.Switch();
      localSwitch.document = this.svgDocument;
      localSwitch.parent = this.currentElement;
      parseAttributesCore(localSwitch, paramAttributes);
      parseAttributesStyle(localSwitch, paramAttributes);
      parseAttributesTransform(localSwitch, paramAttributes);
      parseAttributesConditional(localSwitch, paramAttributes);
      this.currentElement.addChild(localSwitch);
      this.currentElement = localSwitch;
      return;
    case 17: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Symbol localSymbol = new SVG.Symbol();
      localSymbol.document = this.svgDocument;
      localSymbol.parent = this.currentElement;
      parseAttributesCore(localSymbol, paramAttributes);
      parseAttributesStyle(localSymbol, paramAttributes);
      parseAttributesConditional(localSymbol, paramAttributes);
      parseAttributesViewBox(localSymbol, paramAttributes);
      this.currentElement.addChild(localSymbol);
      this.currentElement = localSymbol;
      return;
    case 18: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Marker localMarker = new SVG.Marker();
      localMarker.document = this.svgDocument;
      localMarker.parent = this.currentElement;
      parseAttributesCore(localMarker, paramAttributes);
      parseAttributesStyle(localMarker, paramAttributes);
      parseAttributesConditional(localMarker, paramAttributes);
      parseAttributesViewBox(localMarker, paramAttributes);
      parseAttributesMarker(localMarker, paramAttributes);
      this.currentElement.addChild(localMarker);
      this.currentElement = localMarker;
      return;
    case 19: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.SvgLinearGradient localSvgLinearGradient = new SVG.SvgLinearGradient();
      localSvgLinearGradient.document = this.svgDocument;
      localSvgLinearGradient.parent = this.currentElement;
      parseAttributesCore(localSvgLinearGradient, paramAttributes);
      parseAttributesStyle(localSvgLinearGradient, paramAttributes);
      parseAttributesGradient(localSvgLinearGradient, paramAttributes);
      parseAttributesLinearGradient(localSvgLinearGradient, paramAttributes);
      this.currentElement.addChild(localSvgLinearGradient);
      this.currentElement = localSvgLinearGradient;
      return;
    case 20: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.SvgRadialGradient localSvgRadialGradient = new SVG.SvgRadialGradient();
      localSvgRadialGradient.document = this.svgDocument;
      localSvgRadialGradient.parent = this.currentElement;
      parseAttributesCore(localSvgRadialGradient, paramAttributes);
      parseAttributesStyle(localSvgRadialGradient, paramAttributes);
      parseAttributesGradient(localSvgRadialGradient, paramAttributes);
      parseAttributesRadialGradient(localSvgRadialGradient, paramAttributes);
      this.currentElement.addChild(localSvgRadialGradient);
      this.currentElement = localSvgRadialGradient;
      return;
    case 21: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      if (!(this.currentElement instanceof SVG.GradientElement)) {
        throw new SAXException("Invalid document. <stop> elements are only valid inside <linearGradiant> or <radialGradient> elements.");
      }
      SVG.Stop localStop = new SVG.Stop();
      localStop.document = this.svgDocument;
      localStop.parent = this.currentElement;
      parseAttributesCore(localStop, paramAttributes);
      parseAttributesStyle(localStop, paramAttributes);
      parseAttributesStop(localStop, paramAttributes);
      this.currentElement.addChild(localStop);
      this.currentElement = localStop;
      return;
    case 22: 
    case 23: 
      this.inMetadataElement = true;
      this.metadataTag = localSVGElem;
      return;
    case 24: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.ClipPath localClipPath = new SVG.ClipPath();
      localClipPath.document = this.svgDocument;
      localClipPath.parent = this.currentElement;
      parseAttributesCore(localClipPath, paramAttributes);
      parseAttributesStyle(localClipPath, paramAttributes);
      parseAttributesTransform(localClipPath, paramAttributes);
      parseAttributesConditional(localClipPath, paramAttributes);
      parseAttributesClipPath(localClipPath, paramAttributes);
      this.currentElement.addChild(localClipPath);
      this.currentElement = localClipPath;
      return;
    case 25: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.TextPath localTextPath = new SVG.TextPath();
      localTextPath.document = this.svgDocument;
      localTextPath.parent = this.currentElement;
      parseAttributesCore(localTextPath, paramAttributes);
      parseAttributesStyle(localTextPath, paramAttributes);
      parseAttributesConditional(localTextPath, paramAttributes);
      parseAttributesTextPath(localTextPath, paramAttributes);
      this.currentElement.addChild(localTextPath);
      this.currentElement = localTextPath;
      if ((localTextPath.parent instanceof SVG.TextRoot))
      {
        localTextPath.textRoot = ((SVG.TextRoot)localTextPath.parent);
        return;
      }
      localTextPath.textRoot = ((SVG.TextChild)localTextPath.parent).getTextRoot();
      return;
    case 26: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Pattern localPattern = new SVG.Pattern();
      localPattern.document = this.svgDocument;
      localPattern.parent = this.currentElement;
      parseAttributesCore(localPattern, paramAttributes);
      parseAttributesStyle(localPattern, paramAttributes);
      parseAttributesConditional(localPattern, paramAttributes);
      parseAttributesViewBox(localPattern, paramAttributes);
      parseAttributesPattern(localPattern, paramAttributes);
      this.currentElement.addChild(localPattern);
      this.currentElement = localPattern;
      return;
    case 27: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Image localImage = new SVG.Image();
      localImage.document = this.svgDocument;
      localImage.parent = this.currentElement;
      parseAttributesCore(localImage, paramAttributes);
      parseAttributesStyle(localImage, paramAttributes);
      parseAttributesTransform(localImage, paramAttributes);
      parseAttributesConditional(localImage, paramAttributes);
      parseAttributesImage(localImage, paramAttributes);
      this.currentElement.addChild(localImage);
      this.currentElement = localImage;
      return;
    case 28: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.View localView = new SVG.View();
      localView.document = this.svgDocument;
      localView.parent = this.currentElement;
      parseAttributesCore(localView, paramAttributes);
      parseAttributesConditional(localView, paramAttributes);
      parseAttributesViewBox(localView, paramAttributes);
      this.currentElement.addChild(localView);
      this.currentElement = localView;
      return;
    case 29: 
      if (this.currentElement == null) {
        throw new SAXException("Invalid document. Root element must be <svg>");
      }
      SVG.Mask localMask = new SVG.Mask();
      localMask.document = this.svgDocument;
      localMask.parent = this.currentElement;
      parseAttributesCore(localMask, paramAttributes);
      parseAttributesStyle(localMask, paramAttributes);
      parseAttributesConditional(localMask, paramAttributes);
      parseAttributesMask(localMask, paramAttributes);
      this.currentElement.addChild(localMask);
      this.currentElement = localMask;
      return;
    case 30: 
      style(paramAttributes);
      return;
    }
    if (this.currentElement == null) {
      throw new SAXException("Invalid document. Root element must be <svg>");
    }
    SVG.SolidColor localSolidColor = new SVG.SolidColor();
    localSolidColor.document = this.svgDocument;
    localSolidColor.parent = this.currentElement;
    parseAttributesCore(localSolidColor, paramAttributes);
    parseAttributesStyle(localSolidColor, paramAttributes);
    this.currentElement.addChild(localSolidColor);
    this.currentElement = localSolidColor;
  }
  
  private static final class AspectRatioKeywords
  {
    private static final Map<String, PreserveAspectRatio.Alignment> aspectRatioKeywords;
    
    static
    {
      HashMap localHashMap = new HashMap(10);
      aspectRatioKeywords = localHashMap;
      localHashMap.put("none", PreserveAspectRatio.Alignment.None);
      aspectRatioKeywords.put("xMinYMin", PreserveAspectRatio.Alignment.XMinYMin);
      aspectRatioKeywords.put("xMidYMin", PreserveAspectRatio.Alignment.XMidYMin);
      aspectRatioKeywords.put("xMaxYMin", PreserveAspectRatio.Alignment.XMaxYMin);
      aspectRatioKeywords.put("xMinYMid", PreserveAspectRatio.Alignment.XMinYMid);
      aspectRatioKeywords.put("xMidYMid", PreserveAspectRatio.Alignment.XMidYMid);
      aspectRatioKeywords.put("xMaxYMid", PreserveAspectRatio.Alignment.XMaxYMid);
      aspectRatioKeywords.put("xMinYMax", PreserveAspectRatio.Alignment.XMinYMax);
      aspectRatioKeywords.put("xMidYMax", PreserveAspectRatio.Alignment.XMidYMax);
      aspectRatioKeywords.put("xMaxYMax", PreserveAspectRatio.Alignment.XMaxYMax);
    }
    
    public static PreserveAspectRatio.Alignment get(String paramString)
    {
      return (PreserveAspectRatio.Alignment)aspectRatioKeywords.get(paramString);
    }
  }
  
  private static final class ColourKeywords
  {
    private static final Map<String, Integer> colourKeywords;
    
    static
    {
      HashMap localHashMap = new HashMap(47);
      colourKeywords = localHashMap;
      localHashMap.put("aliceblue", Integer.valueOf(15792383));
      colourKeywords.put("antiquewhite", Integer.valueOf(16444375));
      colourKeywords.put("aqua", Integer.valueOf(65535));
      colourKeywords.put("aquamarine", Integer.valueOf(8388564));
      colourKeywords.put("azure", Integer.valueOf(15794175));
      colourKeywords.put("beige", Integer.valueOf(16119260));
      colourKeywords.put("bisque", Integer.valueOf(16770244));
      colourKeywords.put("black", Integer.valueOf(0));
      colourKeywords.put("blanchedalmond", Integer.valueOf(16772045));
      colourKeywords.put("blue", Integer.valueOf(255));
      colourKeywords.put("blueviolet", Integer.valueOf(9055202));
      colourKeywords.put("brown", Integer.valueOf(10824234));
      colourKeywords.put("burlywood", Integer.valueOf(14596231));
      colourKeywords.put("cadetblue", Integer.valueOf(6266528));
      colourKeywords.put("chartreuse", Integer.valueOf(8388352));
      colourKeywords.put("chocolate", Integer.valueOf(13789470));
      colourKeywords.put("coral", Integer.valueOf(16744272));
      colourKeywords.put("cornflowerblue", Integer.valueOf(6591981));
      colourKeywords.put("cornsilk", Integer.valueOf(16775388));
      colourKeywords.put("crimson", Integer.valueOf(14423100));
      colourKeywords.put("cyan", Integer.valueOf(65535));
      colourKeywords.put("darkblue", Integer.valueOf(139));
      colourKeywords.put("darkcyan", Integer.valueOf(35723));
      colourKeywords.put("darkgoldenrod", Integer.valueOf(12092939));
      colourKeywords.put("darkgray", Integer.valueOf(11119017));
      colourKeywords.put("darkgreen", Integer.valueOf(25600));
      colourKeywords.put("darkgrey", Integer.valueOf(11119017));
      colourKeywords.put("darkkhaki", Integer.valueOf(12433259));
      colourKeywords.put("darkmagenta", Integer.valueOf(9109643));
      colourKeywords.put("darkolivegreen", Integer.valueOf(5597999));
      colourKeywords.put("darkorange", Integer.valueOf(16747520));
      colourKeywords.put("darkorchid", Integer.valueOf(10040012));
      colourKeywords.put("darkred", Integer.valueOf(9109504));
      colourKeywords.put("darksalmon", Integer.valueOf(15308410));
      colourKeywords.put("darkseagreen", Integer.valueOf(9419919));
      colourKeywords.put("darkslateblue", Integer.valueOf(4734347));
      colourKeywords.put("darkslategray", Integer.valueOf(3100495));
      colourKeywords.put("darkslategrey", Integer.valueOf(3100495));
      colourKeywords.put("darkturquoise", Integer.valueOf(52945));
      colourKeywords.put("darkviolet", Integer.valueOf(9699539));
      colourKeywords.put("deeppink", Integer.valueOf(16716947));
      colourKeywords.put("deepskyblue", Integer.valueOf(49151));
      colourKeywords.put("dimgray", Integer.valueOf(6908265));
      colourKeywords.put("dimgrey", Integer.valueOf(6908265));
      colourKeywords.put("dodgerblue", Integer.valueOf(2003199));
      colourKeywords.put("firebrick", Integer.valueOf(11674146));
      colourKeywords.put("floralwhite", Integer.valueOf(16775920));
      colourKeywords.put("forestgreen", Integer.valueOf(2263842));
      colourKeywords.put("fuchsia", Integer.valueOf(16711935));
      colourKeywords.put("gainsboro", Integer.valueOf(14474460));
      colourKeywords.put("ghostwhite", Integer.valueOf(16316671));
      colourKeywords.put("gold", Integer.valueOf(16766720));
      colourKeywords.put("goldenrod", Integer.valueOf(14329120));
      colourKeywords.put("gray", Integer.valueOf(8421504));
      colourKeywords.put("green", Integer.valueOf(32768));
      colourKeywords.put("greenyellow", Integer.valueOf(11403055));
      colourKeywords.put("grey", Integer.valueOf(8421504));
      colourKeywords.put("honeydew", Integer.valueOf(15794160));
      colourKeywords.put("hotpink", Integer.valueOf(16738740));
      colourKeywords.put("indianred", Integer.valueOf(13458524));
      colourKeywords.put("indigo", Integer.valueOf(4915330));
      colourKeywords.put("ivory", Integer.valueOf(16777200));
      colourKeywords.put("khaki", Integer.valueOf(15787660));
      colourKeywords.put("lavender", Integer.valueOf(15132410));
      colourKeywords.put("lavenderblush", Integer.valueOf(16773365));
      colourKeywords.put("lawngreen", Integer.valueOf(8190976));
      colourKeywords.put("lemonchiffon", Integer.valueOf(16775885));
      colourKeywords.put("lightblue", Integer.valueOf(11393254));
      colourKeywords.put("lightcoral", Integer.valueOf(15761536));
      colourKeywords.put("lightcyan", Integer.valueOf(14745599));
      colourKeywords.put("lightgoldenrodyellow", Integer.valueOf(16448210));
      colourKeywords.put("lightgray", Integer.valueOf(13882323));
      colourKeywords.put("lightgreen", Integer.valueOf(9498256));
      colourKeywords.put("lightgrey", Integer.valueOf(13882323));
      colourKeywords.put("lightpink", Integer.valueOf(16758465));
      colourKeywords.put("lightsalmon", Integer.valueOf(16752762));
      colourKeywords.put("lightseagreen", Integer.valueOf(2142890));
      colourKeywords.put("lightskyblue", Integer.valueOf(8900346));
      colourKeywords.put("lightslategray", Integer.valueOf(7833753));
      colourKeywords.put("lightslategrey", Integer.valueOf(7833753));
      colourKeywords.put("lightsteelblue", Integer.valueOf(11584734));
      colourKeywords.put("lightyellow", Integer.valueOf(16777184));
      colourKeywords.put("lime", Integer.valueOf(65280));
      colourKeywords.put("limegreen", Integer.valueOf(3329330));
      colourKeywords.put("linen", Integer.valueOf(16445670));
      colourKeywords.put("magenta", Integer.valueOf(16711935));
      colourKeywords.put("maroon", Integer.valueOf(8388608));
      colourKeywords.put("mediumaquamarine", Integer.valueOf(6737322));
      colourKeywords.put("mediumblue", Integer.valueOf(205));
      colourKeywords.put("mediumorchid", Integer.valueOf(12211667));
      colourKeywords.put("mediumpurple", Integer.valueOf(9662683));
      colourKeywords.put("mediumseagreen", Integer.valueOf(3978097));
      colourKeywords.put("mediumslateblue", Integer.valueOf(8087790));
      colourKeywords.put("mediumspringgreen", Integer.valueOf(64154));
      colourKeywords.put("mediumturquoise", Integer.valueOf(4772300));
      colourKeywords.put("mediumvioletred", Integer.valueOf(13047173));
      colourKeywords.put("midnightblue", Integer.valueOf(1644912));
      colourKeywords.put("mintcream", Integer.valueOf(16121850));
      colourKeywords.put("mistyrose", Integer.valueOf(16770273));
      colourKeywords.put("moccasin", Integer.valueOf(16770229));
      colourKeywords.put("navajowhite", Integer.valueOf(16768685));
      colourKeywords.put("navy", Integer.valueOf(128));
      colourKeywords.put("oldlace", Integer.valueOf(16643558));
      colourKeywords.put("olive", Integer.valueOf(8421376));
      colourKeywords.put("olivedrab", Integer.valueOf(7048739));
      colourKeywords.put("orange", Integer.valueOf(16753920));
      colourKeywords.put("orangered", Integer.valueOf(16729344));
      colourKeywords.put("orchid", Integer.valueOf(14315734));
      colourKeywords.put("palegoldenrod", Integer.valueOf(15657130));
      colourKeywords.put("palegreen", Integer.valueOf(10025880));
      colourKeywords.put("paleturquoise", Integer.valueOf(11529966));
      colourKeywords.put("palevioletred", Integer.valueOf(14381203));
      colourKeywords.put("papayawhip", Integer.valueOf(16773077));
      colourKeywords.put("peachpuff", Integer.valueOf(16767673));
      colourKeywords.put("peru", Integer.valueOf(13468991));
      colourKeywords.put("pink", Integer.valueOf(16761035));
      colourKeywords.put("plum", Integer.valueOf(14524637));
      colourKeywords.put("powderblue", Integer.valueOf(11591910));
      colourKeywords.put("purple", Integer.valueOf(8388736));
      colourKeywords.put("red", Integer.valueOf(16711680));
      colourKeywords.put("rosybrown", Integer.valueOf(12357519));
      colourKeywords.put("royalblue", Integer.valueOf(4286945));
      colourKeywords.put("saddlebrown", Integer.valueOf(9127187));
      colourKeywords.put("salmon", Integer.valueOf(16416882));
      colourKeywords.put("sandybrown", Integer.valueOf(16032864));
      colourKeywords.put("seagreen", Integer.valueOf(3050327));
      colourKeywords.put("seashell", Integer.valueOf(16774638));
      colourKeywords.put("sienna", Integer.valueOf(10506797));
      colourKeywords.put("silver", Integer.valueOf(12632256));
      colourKeywords.put("skyblue", Integer.valueOf(8900331));
      colourKeywords.put("slateblue", Integer.valueOf(6970061));
      colourKeywords.put("slategray", Integer.valueOf(7372944));
      colourKeywords.put("slategrey", Integer.valueOf(7372944));
      colourKeywords.put("snow", Integer.valueOf(16775930));
      colourKeywords.put("springgreen", Integer.valueOf(65407));
      colourKeywords.put("steelblue", Integer.valueOf(4620980));
      colourKeywords.put("tan", Integer.valueOf(13808780));
      colourKeywords.put("teal", Integer.valueOf(32896));
      colourKeywords.put("thistle", Integer.valueOf(14204888));
      colourKeywords.put("tomato", Integer.valueOf(16737095));
      colourKeywords.put("turquoise", Integer.valueOf(4251856));
      colourKeywords.put("violet", Integer.valueOf(15631086));
      colourKeywords.put("wheat", Integer.valueOf(16113331));
      colourKeywords.put("white", Integer.valueOf(16777215));
      colourKeywords.put("whitesmoke", Integer.valueOf(16119285));
      colourKeywords.put("yellow", Integer.valueOf(16776960));
      colourKeywords.put("yellowgreen", Integer.valueOf(10145074));
    }
    
    public static Integer get(String paramString)
    {
      return (Integer)colourKeywords.get(paramString);
    }
  }
  
  private static final class FontSizeKeywords
  {
    private static final Map<String, SVG.Length> fontSizeKeywords;
    
    static
    {
      HashMap localHashMap = new HashMap(9);
      fontSizeKeywords = localHashMap;
      localHashMap.put("xx-small", new SVG.Length(0.694F, SVG.Unit.pt));
      fontSizeKeywords.put("x-small", new SVG.Length(0.833F, SVG.Unit.pt));
      fontSizeKeywords.put("small", new SVG.Length(10.0F, SVG.Unit.pt));
      fontSizeKeywords.put("medium", new SVG.Length(12.0F, SVG.Unit.pt));
      fontSizeKeywords.put("large", new SVG.Length(14.4F, SVG.Unit.pt));
      fontSizeKeywords.put("x-large", new SVG.Length(17.299999F, SVG.Unit.pt));
      fontSizeKeywords.put("xx-large", new SVG.Length(20.700001F, SVG.Unit.pt));
      fontSizeKeywords.put("smaller", new SVG.Length(83.330002F, SVG.Unit.percent));
      fontSizeKeywords.put("larger", new SVG.Length(120.0F, SVG.Unit.percent));
    }
    
    public static SVG.Length get(String paramString)
    {
      return (SVG.Length)fontSizeKeywords.get(paramString);
    }
  }
  
  private static final class FontWeightKeywords
  {
    private static final Map<String, Integer> fontWeightKeywords;
    
    static
    {
      HashMap localHashMap = new HashMap(13);
      fontWeightKeywords = localHashMap;
      localHashMap.put("normal", Integer.valueOf(400));
      fontWeightKeywords.put("bold", Integer.valueOf(700));
      fontWeightKeywords.put("bolder", Integer.valueOf(1));
      fontWeightKeywords.put("lighter", Integer.valueOf(-1));
      fontWeightKeywords.put("100", Integer.valueOf(100));
      fontWeightKeywords.put("200", Integer.valueOf(200));
      fontWeightKeywords.put("300", Integer.valueOf(300));
      fontWeightKeywords.put("400", Integer.valueOf(400));
      fontWeightKeywords.put("500", Integer.valueOf(500));
      fontWeightKeywords.put("600", Integer.valueOf(600));
      fontWeightKeywords.put("700", Integer.valueOf(700));
      fontWeightKeywords.put("800", Integer.valueOf(800));
      fontWeightKeywords.put("900", Integer.valueOf(900));
    }
    
    public static Integer get(String paramString)
    {
      return (Integer)fontWeightKeywords.get(paramString);
    }
  }
  
  private static enum SVGAttr
  {
    private static final Map<String, SVGAttr> cache = new HashMap();
    
    static
    {
      clipPathUnits = new SVGAttr("clipPathUnits", 3);
      clip_rule = new SVGAttr("clip_rule", 4);
      color = new SVGAttr("color", 5);
      cx = new SVGAttr("cx", 6);
      cy = new SVGAttr("cy", 7);
      direction = new SVGAttr("direction", 8);
      dx = new SVGAttr("dx", 9);
      dy = new SVGAttr("dy", 10);
      fx = new SVGAttr("fx", 11);
      fy = new SVGAttr("fy", 12);
      d = new SVGAttr("d", 13);
      display = new SVGAttr("display", 14);
      fill = new SVGAttr("fill", 15);
      fill_rule = new SVGAttr("fill_rule", 16);
      fill_opacity = new SVGAttr("fill_opacity", 17);
      font = new SVGAttr("font", 18);
      font_family = new SVGAttr("font_family", 19);
      font_size = new SVGAttr("font_size", 20);
      font_weight = new SVGAttr("font_weight", 21);
      font_style = new SVGAttr("font_style", 22);
      gradientTransform = new SVGAttr("gradientTransform", 23);
      gradientUnits = new SVGAttr("gradientUnits", 24);
      height = new SVGAttr("height", 25);
      href = new SVGAttr("href", 26);
      id = new SVGAttr("id", 27);
      marker = new SVGAttr("marker", 28);
      marker_start = new SVGAttr("marker_start", 29);
      marker_mid = new SVGAttr("marker_mid", 30);
      marker_end = new SVGAttr("marker_end", 31);
      markerHeight = new SVGAttr("markerHeight", 32);
      markerUnits = new SVGAttr("markerUnits", 33);
      markerWidth = new SVGAttr("markerWidth", 34);
      mask = new SVGAttr("mask", 35);
      maskContentUnits = new SVGAttr("maskContentUnits", 36);
      maskUnits = new SVGAttr("maskUnits", 37);
      media = new SVGAttr("media", 38);
      offset = new SVGAttr("offset", 39);
      opacity = new SVGAttr("opacity", 40);
      orient = new SVGAttr("orient", 41);
      overflow = new SVGAttr("overflow", 42);
      pathLength = new SVGAttr("pathLength", 43);
      patternContentUnits = new SVGAttr("patternContentUnits", 44);
      patternTransform = new SVGAttr("patternTransform", 45);
      patternUnits = new SVGAttr("patternUnits", 46);
      points = new SVGAttr("points", 47);
      preserveAspectRatio = new SVGAttr("preserveAspectRatio", 48);
      r = new SVGAttr("r", 49);
      refX = new SVGAttr("refX", 50);
      refY = new SVGAttr("refY", 51);
      requiredFeatures = new SVGAttr("requiredFeatures", 52);
      requiredExtensions = new SVGAttr("requiredExtensions", 53);
      requiredFormats = new SVGAttr("requiredFormats", 54);
      requiredFonts = new SVGAttr("requiredFonts", 55);
      rx = new SVGAttr("rx", 56);
      ry = new SVGAttr("ry", 57);
      solid_color = new SVGAttr("solid_color", 58);
      solid_opacity = new SVGAttr("solid_opacity", 59);
      spreadMethod = new SVGAttr("spreadMethod", 60);
      startOffset = new SVGAttr("startOffset", 61);
      stop_color = new SVGAttr("stop_color", 62);
      stop_opacity = new SVGAttr("stop_opacity", 63);
      stroke = new SVGAttr("stroke", 64);
      stroke_dasharray = new SVGAttr("stroke_dasharray", 65);
      stroke_dashoffset = new SVGAttr("stroke_dashoffset", 66);
      stroke_linecap = new SVGAttr("stroke_linecap", 67);
      stroke_linejoin = new SVGAttr("stroke_linejoin", 68);
      stroke_miterlimit = new SVGAttr("stroke_miterlimit", 69);
      stroke_opacity = new SVGAttr("stroke_opacity", 70);
      stroke_width = new SVGAttr("stroke_width", 71);
      style = new SVGAttr("style", 72);
      systemLanguage = new SVGAttr("systemLanguage", 73);
      text_anchor = new SVGAttr("text_anchor", 74);
      text_decoration = new SVGAttr("text_decoration", 75);
      transform = new SVGAttr("transform", 76);
      type = new SVGAttr("type", 77);
      vector_effect = new SVGAttr("vector_effect", 78);
      version = new SVGAttr("version", 79);
      viewBox = new SVGAttr("viewBox", 80);
      width = new SVGAttr("width", 81);
      x = new SVGAttr("x", 82);
      y = new SVGAttr("y", 83);
      x1 = new SVGAttr("x1", 84);
      y1 = new SVGAttr("y1", 85);
      x2 = new SVGAttr("x2", 86);
      y2 = new SVGAttr("y2", 87);
      viewport_fill = new SVGAttr("viewport_fill", 88);
      viewport_fill_opacity = new SVGAttr("viewport_fill_opacity", 89);
      visibility = new SVGAttr("visibility", 90);
      UNSUPPORTED = new SVGAttr("UNSUPPORTED", 91);
      SVGAttr[] arrayOfSVGAttr = new SVGAttr[92];
      arrayOfSVGAttr[0] = CLASS;
      arrayOfSVGAttr[1] = clip;
      arrayOfSVGAttr[2] = clip_path;
      arrayOfSVGAttr[3] = clipPathUnits;
      arrayOfSVGAttr[4] = clip_rule;
      arrayOfSVGAttr[5] = color;
      arrayOfSVGAttr[6] = cx;
      arrayOfSVGAttr[7] = cy;
      arrayOfSVGAttr[8] = direction;
      arrayOfSVGAttr[9] = dx;
      arrayOfSVGAttr[10] = dy;
      arrayOfSVGAttr[11] = fx;
      arrayOfSVGAttr[12] = fy;
      arrayOfSVGAttr[13] = d;
      arrayOfSVGAttr[14] = display;
      arrayOfSVGAttr[15] = fill;
      arrayOfSVGAttr[16] = fill_rule;
      arrayOfSVGAttr[17] = fill_opacity;
      arrayOfSVGAttr[18] = font;
      arrayOfSVGAttr[19] = font_family;
      arrayOfSVGAttr[20] = font_size;
      arrayOfSVGAttr[21] = font_weight;
      arrayOfSVGAttr[22] = font_style;
      arrayOfSVGAttr[23] = gradientTransform;
      arrayOfSVGAttr[24] = gradientUnits;
      arrayOfSVGAttr[25] = height;
      arrayOfSVGAttr[26] = href;
      arrayOfSVGAttr[27] = id;
      arrayOfSVGAttr[28] = marker;
      arrayOfSVGAttr[29] = marker_start;
      arrayOfSVGAttr[30] = marker_mid;
      arrayOfSVGAttr[31] = marker_end;
      arrayOfSVGAttr[32] = markerHeight;
      arrayOfSVGAttr[33] = markerUnits;
      arrayOfSVGAttr[34] = markerWidth;
      arrayOfSVGAttr[35] = mask;
      arrayOfSVGAttr[36] = maskContentUnits;
      arrayOfSVGAttr[37] = maskUnits;
      arrayOfSVGAttr[38] = media;
      arrayOfSVGAttr[39] = offset;
      arrayOfSVGAttr[40] = opacity;
      arrayOfSVGAttr[41] = orient;
      arrayOfSVGAttr[42] = overflow;
      arrayOfSVGAttr[43] = pathLength;
      arrayOfSVGAttr[44] = patternContentUnits;
      arrayOfSVGAttr[45] = patternTransform;
      arrayOfSVGAttr[46] = patternUnits;
      arrayOfSVGAttr[47] = points;
      arrayOfSVGAttr[48] = preserveAspectRatio;
      arrayOfSVGAttr[49] = r;
      arrayOfSVGAttr[50] = refX;
      arrayOfSVGAttr[51] = refY;
      arrayOfSVGAttr[52] = requiredFeatures;
      arrayOfSVGAttr[53] = requiredExtensions;
      arrayOfSVGAttr[54] = requiredFormats;
      arrayOfSVGAttr[55] = requiredFonts;
      arrayOfSVGAttr[56] = rx;
      arrayOfSVGAttr[57] = ry;
      arrayOfSVGAttr[58] = solid_color;
      arrayOfSVGAttr[59] = solid_opacity;
      arrayOfSVGAttr[60] = spreadMethod;
      arrayOfSVGAttr[61] = startOffset;
      arrayOfSVGAttr[62] = stop_color;
      arrayOfSVGAttr[63] = stop_opacity;
      arrayOfSVGAttr[64] = stroke;
      arrayOfSVGAttr[65] = stroke_dasharray;
      arrayOfSVGAttr[66] = stroke_dashoffset;
      arrayOfSVGAttr[67] = stroke_linecap;
      arrayOfSVGAttr[68] = stroke_linejoin;
      arrayOfSVGAttr[69] = stroke_miterlimit;
      arrayOfSVGAttr[70] = stroke_opacity;
      arrayOfSVGAttr[71] = stroke_width;
      arrayOfSVGAttr[72] = style;
      arrayOfSVGAttr[73] = systemLanguage;
      arrayOfSVGAttr[74] = text_anchor;
      arrayOfSVGAttr[75] = text_decoration;
      arrayOfSVGAttr[76] = transform;
      arrayOfSVGAttr[77] = type;
      arrayOfSVGAttr[78] = vector_effect;
      arrayOfSVGAttr[79] = version;
      arrayOfSVGAttr[80] = viewBox;
      arrayOfSVGAttr[81] = width;
      arrayOfSVGAttr[82] = x;
      arrayOfSVGAttr[83] = y;
      arrayOfSVGAttr[84] = x1;
      arrayOfSVGAttr[85] = y1;
      arrayOfSVGAttr[86] = x2;
      arrayOfSVGAttr[87] = y2;
      arrayOfSVGAttr[88] = viewport_fill;
      arrayOfSVGAttr[89] = viewport_fill_opacity;
      arrayOfSVGAttr[90] = visibility;
      arrayOfSVGAttr[91] = UNSUPPORTED;
      $VALUES = arrayOfSVGAttr;
    }
    
    private SVGAttr() {}
    
    public static SVGAttr fromString(String paramString)
    {
      SVGAttr localSVGAttr1 = (SVGAttr)cache.get(paramString);
      if (localSVGAttr1 != null) {
        return localSVGAttr1;
      }
      if (paramString.equals("class"))
      {
        cache.put(paramString, CLASS);
        return CLASS;
      }
      if (paramString.indexOf('_') != -1)
      {
        cache.put(paramString, UNSUPPORTED);
        return UNSUPPORTED;
      }
      try
      {
        SVGAttr localSVGAttr2 = valueOf(paramString.replace('-', '_'));
        if (localSVGAttr2 != CLASS)
        {
          cache.put(paramString, localSVGAttr2);
          return localSVGAttr2;
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        cache.put(paramString, UNSUPPORTED);
      }
      return UNSUPPORTED;
    }
  }
  
  private static enum SVGElem
  {
    private static final Map<String, SVGElem> cache = new HashMap();
    
    static
    {
      a = new SVGElem("a", 1);
      circle = new SVGElem("circle", 2);
      clipPath = new SVGElem("clipPath", 3);
      defs = new SVGElem("defs", 4);
      desc = new SVGElem("desc", 5);
      ellipse = new SVGElem("ellipse", 6);
      g = new SVGElem("g", 7);
      image = new SVGElem("image", 8);
      line = new SVGElem("line", 9);
      linearGradient = new SVGElem("linearGradient", 10);
      marker = new SVGElem("marker", 11);
      mask = new SVGElem("mask", 12);
      path = new SVGElem("path", 13);
      pattern = new SVGElem("pattern", 14);
      polygon = new SVGElem("polygon", 15);
      polyline = new SVGElem("polyline", 16);
      radialGradient = new SVGElem("radialGradient", 17);
      rect = new SVGElem("rect", 18);
      solidColor = new SVGElem("solidColor", 19);
      stop = new SVGElem("stop", 20);
      style = new SVGElem("style", 21);
      SWITCH = new SVGElem("SWITCH", 22);
      symbol = new SVGElem("symbol", 23);
      text = new SVGElem("text", 24);
      textPath = new SVGElem("textPath", 25);
      title = new SVGElem("title", 26);
      tref = new SVGElem("tref", 27);
      tspan = new SVGElem("tspan", 28);
      use = new SVGElem("use", 29);
      view = new SVGElem("view", 30);
      UNSUPPORTED = new SVGElem("UNSUPPORTED", 31);
      SVGElem[] arrayOfSVGElem = new SVGElem[32];
      arrayOfSVGElem[0] = svg;
      arrayOfSVGElem[1] = a;
      arrayOfSVGElem[2] = circle;
      arrayOfSVGElem[3] = clipPath;
      arrayOfSVGElem[4] = defs;
      arrayOfSVGElem[5] = desc;
      arrayOfSVGElem[6] = ellipse;
      arrayOfSVGElem[7] = g;
      arrayOfSVGElem[8] = image;
      arrayOfSVGElem[9] = line;
      arrayOfSVGElem[10] = linearGradient;
      arrayOfSVGElem[11] = marker;
      arrayOfSVGElem[12] = mask;
      arrayOfSVGElem[13] = path;
      arrayOfSVGElem[14] = pattern;
      arrayOfSVGElem[15] = polygon;
      arrayOfSVGElem[16] = polyline;
      arrayOfSVGElem[17] = radialGradient;
      arrayOfSVGElem[18] = rect;
      arrayOfSVGElem[19] = solidColor;
      arrayOfSVGElem[20] = stop;
      arrayOfSVGElem[21] = style;
      arrayOfSVGElem[22] = SWITCH;
      arrayOfSVGElem[23] = symbol;
      arrayOfSVGElem[24] = text;
      arrayOfSVGElem[25] = textPath;
      arrayOfSVGElem[26] = title;
      arrayOfSVGElem[27] = tref;
      arrayOfSVGElem[28] = tspan;
      arrayOfSVGElem[29] = use;
      arrayOfSVGElem[30] = view;
      arrayOfSVGElem[31] = UNSUPPORTED;
      $VALUES = arrayOfSVGElem;
    }
    
    private SVGElem() {}
    
    public static SVGElem fromString(String paramString)
    {
      SVGElem localSVGElem1 = (SVGElem)cache.get(paramString);
      if (localSVGElem1 != null) {
        return localSVGElem1;
      }
      if (paramString.equals("switch"))
      {
        cache.put(paramString, SWITCH);
        return SWITCH;
      }
      try
      {
        SVGElem localSVGElem2 = valueOf(paramString);
        if (localSVGElem2 != SWITCH)
        {
          cache.put(paramString, localSVGElem2);
          return localSVGElem2;
        }
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        cache.put(paramString, UNSUPPORTED);
      }
      return UNSUPPORTED;
    }
  }
  
  protected static class TextScanner
  {
    protected String input;
    protected int inputLength = 0;
    private NumberParser numberParser = new NumberParser();
    protected int position = 0;
    
    public TextScanner(String paramString)
    {
      this.input = paramString.trim();
      this.inputLength = this.input.length();
    }
    
    protected static boolean isWhitespace(int paramInt)
    {
      return (paramInt == 32) || (paramInt == 10) || (paramInt == 13) || (paramInt == 9);
    }
    
    protected final int advanceChar()
    {
      if (this.position == this.inputLength) {}
      do
      {
        return -1;
        this.position = (1 + this.position);
      } while (this.position >= this.inputLength);
      return this.input.charAt(this.position);
    }
    
    public final String ahead()
    {
      int i = this.position;
      while ((!empty()) && (!isWhitespace(this.input.charAt(this.position)))) {
        this.position = (1 + this.position);
      }
      String str = this.input.substring(i, this.position);
      this.position = i;
      return str;
    }
    
    public final Boolean checkedNextFlag(Object paramObject)
    {
      if (paramObject == null) {}
      int i;
      do
      {
        do
        {
          return null;
          skipCommaWhitespace();
        } while (this.position == this.inputLength);
        i = this.input.charAt(this.position);
      } while ((i != 48) && (i != 49));
      this.position = (1 + this.position);
      if (i == 49) {}
      for (boolean bool = true;; bool = false) {
        return Boolean.valueOf(bool);
      }
    }
    
    public final float checkedNextFloat(float paramFloat)
    {
      if (Float.isNaN(paramFloat)) {
        return (0.0F / 0.0F);
      }
      skipCommaWhitespace();
      return nextFloat();
    }
    
    public final boolean consume(char paramChar)
    {
      if ((this.position < this.inputLength) && (this.input.charAt(this.position) == paramChar)) {}
      for (boolean bool = true;; bool = false)
      {
        if (bool) {
          this.position = (1 + this.position);
        }
        return bool;
      }
    }
    
    public final boolean consume(String paramString)
    {
      int i = paramString.length();
      if ((this.position <= this.inputLength - i) && (this.input.substring(this.position, i + this.position).equals(paramString))) {}
      for (boolean bool = true;; bool = false)
      {
        if (bool) {
          this.position = (i + this.position);
        }
        return bool;
      }
    }
    
    public final boolean empty()
    {
      return this.position == this.inputLength;
    }
    
    public final boolean hasLetter()
    {
      if (this.position == this.inputLength) {}
      int i;
      do
      {
        return false;
        i = this.input.charAt(this.position);
      } while (((i < 97) || (i > 122)) && ((i < 65) || (i > 90)));
      return true;
    }
    
    public final Integer nextChar()
    {
      if (this.position == this.inputLength) {
        return null;
      }
      String str = this.input;
      int i = this.position;
      this.position = (i + 1);
      return Integer.valueOf(str.charAt(i));
    }
    
    public final float nextFloat()
    {
      float f = this.numberParser.parseNumber(this.input, this.position, this.inputLength);
      if (!Float.isNaN(f)) {
        this.position = this.numberParser.pos;
      }
      return f;
    }
    
    public final String nextFunction()
    {
      if (empty()) {
        return null;
      }
      int i = this.position;
      for (int j = this.input.charAt(this.position); ((j >= 97) && (j <= 122)) || ((j >= 65) && (j <= 90)); j = advanceChar()) {}
      int k = this.position;
      while (isWhitespace(j)) {
        j = advanceChar();
      }
      if (j == 40)
      {
        this.position = (1 + this.position);
        return this.input.substring(i, k);
      }
      this.position = i;
      return null;
    }
    
    public final SVG.Length nextLength()
    {
      float f = nextFloat();
      if (Float.isNaN(f)) {
        return null;
      }
      SVG.Unit localUnit = nextUnit();
      if (localUnit == null) {
        return new SVG.Length(f, SVG.Unit.px);
      }
      return new SVG.Length(f, localUnit);
    }
    
    public final String nextQuotedString()
    {
      if (empty()) {}
      int i;
      int j;
      do
      {
        return null;
        i = this.position;
        j = this.input.charAt(this.position);
      } while ((j != 39) && (j != 34));
      int k;
      do
      {
        k = advanceChar();
      } while ((k != -1) && (k != j));
      if (k == -1)
      {
        this.position = i;
        return null;
      }
      this.position = (1 + this.position);
      return this.input.substring(i + 1, -1 + this.position);
    }
    
    public final String nextToken()
    {
      return nextToken(' ');
    }
    
    public final String nextToken(char paramChar)
    {
      if (empty()) {}
      char c1;
      do
      {
        return null;
        c1 = this.input.charAt(this.position);
      } while ((isWhitespace(c1)) || (c1 == paramChar));
      int i = this.position;
      for (char c2 = advanceChar(); (c2 != 'ð¿¿') && (c2 != paramChar) && (!isWhitespace(c2)); c2 = advanceChar()) {}
      return this.input.substring(i, this.position);
    }
    
    public final SVG.Unit nextUnit()
    {
      if (empty()) {
        return null;
      }
      if (this.input.charAt(this.position) == '%')
      {
        this.position = (1 + this.position);
        return SVG.Unit.percent;
      }
      if (this.position > -2 + this.inputLength) {
        return null;
      }
      try
      {
        SVG.Unit localUnit = SVG.Unit.valueOf(this.input.substring(this.position, 2 + this.position).toLowerCase(Locale.US));
        this.position = (2 + this.position);
        return localUnit;
      }
      catch (IllegalArgumentException localIllegalArgumentException) {}
      return null;
    }
    
    public final float possibleNextFloat()
    {
      skipCommaWhitespace();
      float f = this.numberParser.parseNumber(this.input, this.position, this.inputLength);
      if (!Float.isNaN(f)) {
        this.position = this.numberParser.pos;
      }
      return f;
    }
    
    public final String restOfText()
    {
      if (empty()) {
        return null;
      }
      int i = this.position;
      this.position = this.inputLength;
      return this.input.substring(i);
    }
    
    public final boolean skipCommaWhitespace()
    {
      skipWhitespace();
      if (this.position == this.inputLength) {}
      while (this.input.charAt(this.position) != ',') {
        return false;
      }
      this.position = (1 + this.position);
      skipWhitespace();
      return true;
    }
    
    public final void skipWhitespace()
    {
      while ((this.position < this.inputLength) && (isWhitespace(this.input.charAt(this.position)))) {
        this.position = (1 + this.position);
      }
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.SVGParser
 * JD-Core Version:    0.7.0.1
 */