package com.caverock.androidsvg;

import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.xml.sax.SAXException;

public final class CSSParser
{
  private boolean inMediaRule = false;
  private MediaType rendererMediaType = null;
  
  public CSSParser(MediaType paramMediaType)
  {
    this.rendererMediaType = paramMediaType;
  }
  
  private static int getChildPosition(List<SVG.SvgContainer> paramList, int paramInt, SVG.SvgElementBase paramSvgElementBase)
  {
    if (paramInt < 0)
    {
      i = -1;
      return i;
    }
    if (paramList.get(paramInt) != paramSvgElementBase.parent) {
      return -1;
    }
    int i = 0;
    Iterator localIterator = paramSvgElementBase.parent.getChildren().iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        break label72;
      }
      if ((SVG.SvgObject)localIterator.next() == paramSvgElementBase) {
        break;
      }
      i++;
    }
    label72:
    return -1;
  }
  
  static boolean mediaMatches(List<MediaType> paramList, MediaType paramMediaType)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      MediaType localMediaType = (MediaType)localIterator.next();
      if ((localMediaType == MediaType.all) || (localMediaType == paramMediaType)) {
        return true;
      }
    }
    return false;
  }
  
  private static SVG.Style parseDeclarations(CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    SVG.Style localStyle = new SVG.Style();
    do
    {
      String str1 = paramCSSTextScanner.nextIdentifier();
      paramCSSTextScanner.skipWhitespace();
      if (!paramCSSTextScanner.consume(':')) {
        break;
      }
      paramCSSTextScanner.skipWhitespace();
      String str2 = paramCSSTextScanner.nextPropertyValue();
      if (str2 == null) {
        break;
      }
      paramCSSTextScanner.skipWhitespace();
      if (paramCSSTextScanner.consume('!'))
      {
        paramCSSTextScanner.skipWhitespace();
        if (!paramCSSTextScanner.consume("important")) {
          throw new SAXException("Malformed rule set in <style> element: found unexpected '!'");
        }
        paramCSSTextScanner.skipWhitespace();
      }
      paramCSSTextScanner.consume(';');
      SVGParser.processStyleProperty(localStyle, str1, str2);
      paramCSSTextScanner.skipWhitespace();
      if (paramCSSTextScanner.consume('}')) {
        return localStyle;
      }
    } while (!paramCSSTextScanner.empty());
    throw new SAXException("Malformed rule set in <style> element");
  }
  
  static List<MediaType> parseMediaList(CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    ArrayList localArrayList = new ArrayList();
    for (;;)
    {
      String str;
      if (!paramCSSTextScanner.empty()) {
        str = paramCSSTextScanner.nextToken(',');
      }
      try
      {
        localArrayList.add(MediaType.valueOf(str));
        if (paramCSSTextScanner.skipCommaWhitespace()) {
          continue;
        }
        return localArrayList;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw new SAXException("Invalid @media type list");
      }
    }
  }
  
  private static boolean parseRule(Ruleset paramRuleset, CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    ArrayList localArrayList;
    if (paramCSSTextScanner.empty()) {
      localArrayList = null;
    }
    while ((localArrayList != null) && (!localArrayList.isEmpty())) {
      if (!paramCSSTextScanner.consume('{'))
      {
        throw new SAXException("Malformed rule block in <style> element: missing '{'");
        localArrayList = new ArrayList(1);
        Selector localSelector = new Selector();
        while ((!paramCSSTextScanner.empty()) && (paramCSSTextScanner.nextSimpleSelector(localSelector))) {
          if (paramCSSTextScanner.skipCommaWhitespace())
          {
            localArrayList.add(localSelector);
            localSelector = new Selector();
          }
        }
        if (!localSelector.isEmpty()) {
          localArrayList.add(localSelector);
        }
      }
      else
      {
        paramCSSTextScanner.skipWhitespace();
        SVG.Style localStyle = parseDeclarations(paramCSSTextScanner);
        paramCSSTextScanner.skipWhitespace();
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext()) {
          paramRuleset.add(new Rule((Selector)localIterator.next(), localStyle));
        }
        return true;
      }
    }
    return false;
  }
  
  static boolean ruleMatch(Selector paramSelector, int paramInt1, List<SVG.SvgContainer> paramList, int paramInt2, SVG.SvgElementBase paramSvgElementBase)
  {
    for (;;)
    {
      SimpleSelector localSimpleSelector = paramSelector.get(paramInt1);
      if (!selectorMatch(localSimpleSelector, paramList, paramInt2, paramSvgElementBase)) {
        break label38;
      }
      label38:
      int i;
      do
      {
        do
        {
          return false;
          if (localSimpleSelector.combinator != Combinator.DESCENDANT) {
            break;
          }
          if (paramInt1 == 0)
          {
            return true;
            paramInt2--;
          }
        } while (paramInt2 < 0);
        if (!ruleMatchOnAncestors(paramSelector, paramInt1 - 1, paramList, paramInt2)) {
          break;
        }
        return true;
        if (localSimpleSelector.combinator == Combinator.CHILD) {
          return ruleMatchOnAncestors(paramSelector, paramInt1 - 1, paramList, paramInt2);
        }
        i = getChildPosition(paramList, paramInt2, paramSvgElementBase);
      } while (i <= 0);
      SVG.SvgElementBase localSvgElementBase = (SVG.SvgElementBase)paramSvgElementBase.parent.getChildren().get(i - 1);
      paramInt1--;
      paramSvgElementBase = localSvgElementBase;
    }
  }
  
  private static boolean ruleMatchOnAncestors(Selector paramSelector, int paramInt1, List<SVG.SvgContainer> paramList, int paramInt2)
  {
    SimpleSelector localSimpleSelector = paramSelector.get(paramInt1);
    SVG.SvgElementBase localSvgElementBase1 = (SVG.SvgElementBase)paramList.get(paramInt2);
    if (!selectorMatch(localSimpleSelector, paramList, paramInt2, localSvgElementBase1)) {
      label30:
      break label50;
    }
    label50:
    int i;
    do
    {
      do
      {
        return false;
        if (localSimpleSelector.combinator != Combinator.DESCENDANT) {
          break;
        }
        if (paramInt1 == 0) {
          return true;
        }
      } while (paramInt2 <= 0);
      int j = paramInt1 - 1;
      paramInt2--;
      if (!ruleMatchOnAncestors(paramSelector, j, paramList, paramInt2)) {
        break label30;
      }
      return true;
      if (localSimpleSelector.combinator == Combinator.CHILD)
      {
        paramInt1--;
        paramInt2--;
        break;
      }
      i = getChildPosition(paramList, paramInt2, localSvgElementBase1);
    } while (i <= 0);
    SVG.SvgElementBase localSvgElementBase2 = (SVG.SvgElementBase)localSvgElementBase1.parent.getChildren().get(i - 1);
    return ruleMatch(paramSelector, paramInt1 - 1, paramList, paramInt2, localSvgElementBase2);
  }
  
  static boolean selectorMatch(SimpleSelector paramSimpleSelector, List<SVG.SvgContainer> paramList, int paramInt, SVG.SvgElementBase paramSvgElementBase)
  {
    if (paramSimpleSelector.tag != null) {
      if (paramSimpleSelector.tag.equalsIgnoreCase("G"))
      {
        if (!(paramSvgElementBase instanceof SVG.Group)) {
          return false;
        }
      }
      else if (!paramSimpleSelector.tag.equals(paramSvgElementBase.getClass().getSimpleName().toLowerCase(Locale.US))) {
        return false;
      }
    }
    if (paramSimpleSelector.attribs != null)
    {
      Iterator localIterator2 = paramSimpleSelector.attribs.iterator();
      while (localIterator2.hasNext())
      {
        Attrib localAttrib = (Attrib)localIterator2.next();
        if (localAttrib.name == "id")
        {
          if (!localAttrib.value.equals(paramSvgElementBase.id)) {
            return false;
          }
        }
        else if (localAttrib.name == "class")
        {
          if (paramSvgElementBase.classNames == null) {
            return false;
          }
          if (!paramSvgElementBase.classNames.contains(localAttrib.value)) {
            return false;
          }
        }
        else
        {
          return false;
        }
      }
    }
    if (paramSimpleSelector.pseudos != null)
    {
      Iterator localIterator1 = paramSimpleSelector.pseudos.iterator();
      while (localIterator1.hasNext()) {
        if (((String)localIterator1.next()).equals("first-child"))
        {
          if (getChildPosition(paramList, paramInt, paramSvgElementBase) != 0) {
            return false;
          }
        }
        else {
          return false;
        }
      }
    }
    return true;
  }
  
  private static void skipAtRule(CSSTextScanner paramCSSTextScanner)
  {
    int i = 0;
    do
    {
      int j;
      do
      {
        for (;;)
        {
          if (!paramCSSTextScanner.empty())
          {
            j = paramCSSTextScanner.nextChar().intValue();
            if ((j != 59) || (i != 0)) {}
          }
          else
          {
            return;
          }
          if (j != 123) {
            break;
          }
          i++;
        }
      } while ((j != 125) || (i <= 0));
      i--;
    } while (i != 0);
  }
  
  final Ruleset parseRuleset(CSSTextScanner paramCSSTextScanner)
    throws SAXException
  {
    Ruleset localRuleset = new Ruleset();
    do
    {
      for (;;)
      {
        if (paramCSSTextScanner.empty()) {
          return localRuleset;
        }
        if ((!paramCSSTextScanner.consume("<!--")) && (!paramCSSTextScanner.consume("-->")))
        {
          if (!paramCSSTextScanner.consume('@')) {
            break;
          }
          String str = paramCSSTextScanner.nextIdentifier();
          paramCSSTextScanner.skipWhitespace();
          if (str == null) {
            throw new SAXException("Invalid '@' rule in <style> element");
          }
          if ((!this.inMediaRule) && (str.equals("media")))
          {
            List localList = parseMediaList(paramCSSTextScanner);
            if (!paramCSSTextScanner.consume('{')) {
              throw new SAXException("Invalid @media rule: missing rule set");
            }
            paramCSSTextScanner.skipWhitespace();
            if (mediaMatches(localList, this.rendererMediaType))
            {
              this.inMediaRule = true;
              localRuleset.addAll(parseRuleset(paramCSSTextScanner));
              this.inMediaRule = false;
            }
          }
          while (!paramCSSTextScanner.consume('}'))
          {
            throw new SAXException("Invalid @media rule: expected '}' at end of rule set");
            parseRuleset(paramCSSTextScanner);
            continue;
            Log.w("AndroidSVG CSSParser", String.format("Ignoring @%s rule", new Object[] { str }));
            skipAtRule(paramCSSTextScanner);
          }
          paramCSSTextScanner.skipWhitespace();
        }
      }
    } while (parseRule(localRuleset, paramCSSTextScanner));
    return localRuleset;
  }
  
  public static final class Attrib
  {
    public String name = null;
    public CSSParser.AttribOp operation;
    public String value = null;
    
    public Attrib(String paramString1, CSSParser.AttribOp paramAttribOp, String paramString2)
    {
      this.name = paramString1;
      this.operation = paramAttribOp;
      this.value = paramString2;
    }
  }
  
  private static enum AttribOp
  {
    static
    {
      EQUALS = new AttribOp("EQUALS", 1);
      INCLUDES = new AttribOp("INCLUDES", 2);
      DASHMATCH = new AttribOp("DASHMATCH", 3);
      AttribOp[] arrayOfAttribOp = new AttribOp[4];
      arrayOfAttribOp[0] = EXISTS;
      arrayOfAttribOp[1] = EQUALS;
      arrayOfAttribOp[2] = INCLUDES;
      arrayOfAttribOp[3] = DASHMATCH;
      $VALUES = arrayOfAttribOp;
    }
    
    private AttribOp() {}
  }
  
  private static final class CSSTextScanner
    extends SVGParser.TextScanner
  {
    public CSSTextScanner(String paramString)
    {
      super();
    }
    
    public final String nextIdentifier()
    {
      if (empty()) {}
      int j;
      for (int m = this.position; m == this.position; m = j)
      {
        return null;
        int i = this.position;
        j = this.position;
        int k = this.input.charAt(this.position);
        if (k == 45) {
          k = advanceChar();
        }
        if (((k >= 65) && (k <= 90)) || ((k >= 97) && (k <= 122)) || (k == 95))
        {
          for (int n = advanceChar(); ((n >= 65) && (n <= 90)) || ((n >= 97) && (n <= 122)) || ((n >= 48) && (n <= 57)) || (n == 45) || (n == 95); n = advanceChar()) {}
          j = this.position;
        }
        this.position = i;
      }
      String str = this.input.substring(this.position, m);
      this.position = m;
      return str;
    }
    
    public final String nextPropertyValue()
    {
      if (empty()) {
        return null;
      }
      int i = this.position;
      int j = this.position;
      int k = this.input.charAt(this.position);
      if ((k != -1) && (k != 59) && (k != 125) && (k != 33))
      {
        if ((k == 10) || (k == 13)) {}
        for (int m = 1;; m = 0)
        {
          if (m != 0) {
            break label102;
          }
          if (!isWhitespace(k)) {
            j = 1 + this.position;
          }
          k = advanceChar();
          break;
        }
      }
      label102:
      if (this.position > i) {
        return this.input.substring(i, j);
      }
      this.position = i;
      return null;
    }
    
    public final boolean nextSimpleSelector(CSSParser.Selector paramSelector)
      throws SAXException
    {
      if (empty()) {
        return false;
      }
      int i = this.position;
      boolean bool1 = paramSelector.isEmpty();
      CSSParser.Combinator localCombinator = null;
      CSSParser.SimpleSelector localSimpleSelector;
      if (!bool1)
      {
        if (consume('>'))
        {
          localCombinator = CSSParser.Combinator.CHILD;
          skipWhitespace();
        }
      }
      else
      {
        if (!consume('*')) {
          break label147;
        }
        localSimpleSelector = new CSSParser.SimpleSelector(localCombinator, null);
      }
      label147:
      label412:
      label432:
      label436:
      int j;
      for (;;)
      {
        if (!empty())
        {
          if (consume('.'))
          {
            if (localSimpleSelector == null) {
              localSimpleSelector = new CSSParser.SimpleSelector(localCombinator, null);
            }
            String str5 = nextIdentifier();
            if (str5 == null)
            {
              throw new SAXException("Invalid \".class\" selector in <style> element");
              boolean bool3 = consume('+');
              localCombinator = null;
              if (!bool3) {
                break;
              }
              localCombinator = CSSParser.Combinator.FOLLOWS;
              skipWhitespace();
              break;
              String str6 = nextIdentifier();
              localSimpleSelector = null;
              if (str6 == null) {
                continue;
              }
              localSimpleSelector = new CSSParser.SimpleSelector(localCombinator, str6);
              paramSelector.specificity = (1 + paramSelector.specificity);
              continue;
            }
            localSimpleSelector.addAttrib("class", CSSParser.AttribOp.EQUALS, str5);
            paramSelector.addedAttributeOrPseudo();
            continue;
          }
          if (consume('#'))
          {
            if (localSimpleSelector == null) {
              localSimpleSelector = new CSSParser.SimpleSelector(localCombinator, null);
            }
            String str4 = nextIdentifier();
            if (str4 == null) {
              throw new SAXException("Invalid \"#id\" selector in <style> element");
            }
            localSimpleSelector.addAttrib("id", CSSParser.AttribOp.EQUALS, str4);
            paramSelector.specificity = (10000 + paramSelector.specificity);
          }
          if (localSimpleSelector != null) {
            if (consume('['))
            {
              skipWhitespace();
              String str2 = nextIdentifier();
              if (str2 == null) {
                throw new SAXException("Invalid attribute selector in <style> element");
              }
              skipWhitespace();
              CSSParser.AttribOp localAttribOp;
              String str3;
              if (consume('='))
              {
                localAttribOp = CSSParser.AttribOp.EQUALS;
                str3 = null;
                if (localAttribOp == null) {
                  break label436;
                }
                skipWhitespace();
                if (!empty()) {
                  break label412;
                }
                str3 = null;
              }
              for (;;)
              {
                if (str3 != null) {
                  break label432;
                }
                throw new SAXException("Invalid attribute selector in <style> element");
                if (consume("~="))
                {
                  localAttribOp = CSSParser.AttribOp.INCLUDES;
                  break;
                }
                boolean bool2 = consume("|=");
                localAttribOp = null;
                if (!bool2) {
                  break;
                }
                localAttribOp = CSSParser.AttribOp.DASHMATCH;
                break;
                str3 = nextQuotedString();
                if (str3 == null) {
                  str3 = nextIdentifier();
                }
              }
              skipWhitespace();
              if (!consume(']')) {
                throw new SAXException("Invalid attribute selector in <style> element");
              }
              if (localAttribOp == null) {
                localAttribOp = CSSParser.AttribOp.EXISTS;
              }
              localSimpleSelector.addAttrib(str2, localAttribOp, str3);
              paramSelector.addedAttributeOrPseudo();
            }
            else if (consume(':'))
            {
              j = this.position;
              if (nextIdentifier() != null)
              {
                if (!consume('(')) {
                  break label583;
                }
                skipWhitespace();
                if (nextIdentifier() == null) {
                  break label583;
                }
                skipWhitespace();
                if (consume(')')) {
                  break label583;
                }
                this.position = (j - 1);
              }
            }
          }
        }
      }
      while (localSimpleSelector != null)
      {
        if (paramSelector.selector == null) {
          paramSelector.selector = new ArrayList();
        }
        paramSelector.selector.add(localSimpleSelector);
        return true;
        label583:
        String str1 = this.input.substring(j, this.position);
        if (localSimpleSelector.pseudos == null) {
          localSimpleSelector.pseudos = new ArrayList();
        }
        localSimpleSelector.pseudos.add(str1);
        paramSelector.addedAttributeOrPseudo();
      }
      this.position = i;
      return false;
    }
  }
  
  private static enum Combinator
  {
    static
    {
      CHILD = new Combinator("CHILD", 1);
      FOLLOWS = new Combinator("FOLLOWS", 2);
      Combinator[] arrayOfCombinator = new Combinator[3];
      arrayOfCombinator[0] = DESCENDANT;
      arrayOfCombinator[1] = CHILD;
      arrayOfCombinator[2] = FOLLOWS;
      $VALUES = arrayOfCombinator;
    }
    
    private Combinator() {}
  }
  
  public static enum MediaType
  {
    static
    {
      MediaType[] arrayOfMediaType = new MediaType[10];
      arrayOfMediaType[0] = all;
      arrayOfMediaType[1] = aural;
      arrayOfMediaType[2] = braille;
      arrayOfMediaType[3] = embossed;
      arrayOfMediaType[4] = handheld;
      arrayOfMediaType[5] = print;
      arrayOfMediaType[6] = projection;
      arrayOfMediaType[7] = screen;
      arrayOfMediaType[8] = tty;
      arrayOfMediaType[9] = tv;
      $VALUES = arrayOfMediaType;
    }
    
    private MediaType() {}
  }
  
  public static final class Rule
  {
    public CSSParser.Selector selector = null;
    public SVG.Style style = null;
    
    public Rule(CSSParser.Selector paramSelector, SVG.Style paramStyle)
    {
      this.selector = paramSelector;
      this.style = paramStyle;
    }
    
    public final String toString()
    {
      return this.selector + " {}";
    }
  }
  
  public static final class Ruleset
  {
    List<CSSParser.Rule> rules = null;
    
    public final void add(CSSParser.Rule paramRule)
    {
      if (this.rules == null) {
        this.rules = new ArrayList();
      }
      for (int i = 0; i < this.rules.size(); i++) {
        if (((CSSParser.Rule)this.rules.get(i)).selector.specificity > paramRule.selector.specificity)
        {
          this.rules.add(i, paramRule);
          return;
        }
      }
      this.rules.add(paramRule);
    }
    
    public final void addAll(Ruleset paramRuleset)
    {
      if (paramRuleset.rules == null) {}
      for (;;)
      {
        return;
        if (this.rules == null) {
          this.rules = new ArrayList(paramRuleset.rules.size());
        }
        Iterator localIterator = paramRuleset.rules.iterator();
        while (localIterator.hasNext())
        {
          CSSParser.Rule localRule = (CSSParser.Rule)localIterator.next();
          this.rules.add(localRule);
        }
      }
    }
    
    public final String toString()
    {
      if (this.rules == null) {
        return "";
      }
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = this.rules.iterator();
      while (localIterator.hasNext()) {
        localStringBuilder.append(((CSSParser.Rule)localIterator.next()).toString()).append('\n');
      }
      return localStringBuilder.toString();
    }
  }
  
  public static final class Selector
  {
    public List<CSSParser.SimpleSelector> selector = null;
    public int specificity = 0;
    
    public final void addedAttributeOrPseudo()
    {
      this.specificity = (100 + this.specificity);
    }
    
    public final CSSParser.SimpleSelector get(int paramInt)
    {
      return (CSSParser.SimpleSelector)this.selector.get(paramInt);
    }
    
    public final boolean isEmpty()
    {
      if (this.selector == null) {
        return true;
      }
      return this.selector.isEmpty();
    }
    
    public final int size()
    {
      if (this.selector == null) {
        return 0;
      }
      return this.selector.size();
    }
    
    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      Iterator localIterator = this.selector.iterator();
      while (localIterator.hasNext()) {
        localStringBuilder.append((CSSParser.SimpleSelector)localIterator.next()).append(' ');
      }
      return '(' + this.specificity + ')';
    }
  }
  
  private static final class SimpleSelector
  {
    public List<CSSParser.Attrib> attribs = null;
    public CSSParser.Combinator combinator = null;
    public List<String> pseudos = null;
    public String tag = null;
    
    public SimpleSelector(CSSParser.Combinator paramCombinator, String paramString)
    {
      if (paramCombinator != null) {}
      for (;;)
      {
        this.combinator = paramCombinator;
        this.tag = paramString;
        return;
        paramCombinator = CSSParser.Combinator.DESCENDANT;
      }
    }
    
    public final void addAttrib(String paramString1, CSSParser.AttribOp paramAttribOp, String paramString2)
    {
      if (this.attribs == null) {
        this.attribs = new ArrayList();
      }
      this.attribs.add(new CSSParser.Attrib(paramString1, paramAttribOp, paramString2));
    }
    
    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      String str1;
      label35:
      label59:
      CSSParser.Attrib localAttrib;
      if (this.combinator == CSSParser.Combinator.CHILD)
      {
        localStringBuilder.append("> ");
        if (this.tag != null) {
          break label166;
        }
        str1 = "*";
        localStringBuilder.append(str1);
        if (this.attribs == null) {
          break label228;
        }
        Iterator localIterator2 = this.attribs.iterator();
        if (!localIterator2.hasNext()) {
          break label228;
        }
        localAttrib = (CSSParser.Attrib)localIterator2.next();
        localStringBuilder.append('[').append(localAttrib.name);
        switch (CSSParser.1.$SwitchMap$com$caverock$androidsvg$CSSParser$AttribOp[localAttrib.operation.ordinal()])
        {
        }
      }
      for (;;)
      {
        localStringBuilder.append(']');
        break label59;
        if (this.combinator != CSSParser.Combinator.FOLLOWS) {
          break;
        }
        localStringBuilder.append("+ ");
        break;
        label166:
        str1 = this.tag;
        break label35;
        localStringBuilder.append('=').append(localAttrib.value);
        continue;
        localStringBuilder.append("~=").append(localAttrib.value);
        continue;
        localStringBuilder.append("|=").append(localAttrib.value);
      }
      label228:
      if (this.pseudos != null)
      {
        Iterator localIterator1 = this.pseudos.iterator();
        while (localIterator1.hasNext())
        {
          String str2 = (String)localIterator1.next();
          localStringBuilder.append(':').append(str2);
        }
      }
      return localStringBuilder.toString();
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.CSSParser
 * JD-Core Version:    0.7.0.1
 */