package com.google.gson.internal;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class $Gson$Types
{
  static final Type[] EMPTY_TYPE_ARRAY = new Type[0];
  
  private static GenericArrayType arrayOf(Type paramType)
  {
    return new GenericArrayTypeImpl(paramType);
  }
  
  public static Type canonicalize(Type paramType)
  {
    if ((paramType instanceof Class))
    {
      Object localObject = (Class)paramType;
      if (((Class)localObject).isArray()) {
        localObject = new GenericArrayTypeImpl(canonicalize(((Class)localObject).getComponentType()));
      }
      return localObject;
    }
    if ((paramType instanceof ParameterizedType))
    {
      ParameterizedType localParameterizedType = (ParameterizedType)paramType;
      return new ParameterizedTypeImpl(localParameterizedType.getOwnerType(), localParameterizedType.getRawType(), localParameterizedType.getActualTypeArguments());
    }
    if ((paramType instanceof GenericArrayType)) {
      return new GenericArrayTypeImpl(((GenericArrayType)paramType).getGenericComponentType());
    }
    if ((paramType instanceof WildcardType))
    {
      WildcardType localWildcardType = (WildcardType)paramType;
      return new WildcardTypeImpl(localWildcardType.getUpperBounds(), localWildcardType.getLowerBounds());
    }
    return paramType;
  }
  
  public static boolean equals(Type paramType1, Type paramType2)
  {
    if (paramType1 == paramType2) {}
    label261:
    TypeVariable localTypeVariable1;
    TypeVariable localTypeVariable2;
    do
    {
      WildcardType localWildcardType1;
      WildcardType localWildcardType2;
      do
      {
        for (;;)
        {
          return true;
          if ((paramType1 instanceof Class)) {
            return paramType1.equals(paramType2);
          }
          if (!(paramType1 instanceof ParameterizedType)) {
            break;
          }
          if (!(paramType2 instanceof ParameterizedType)) {
            return false;
          }
          ParameterizedType localParameterizedType1 = (ParameterizedType)paramType1;
          ParameterizedType localParameterizedType2 = (ParameterizedType)paramType2;
          Type localType1 = localParameterizedType1.getOwnerType();
          Type localType2 = localParameterizedType2.getOwnerType();
          if ((localType1 == localType2) || ((localType1 != null) && (localType1.equals(localType2)))) {}
          for (int i = 1; (i == 0) || (!localParameterizedType1.getRawType().equals(localParameterizedType2.getRawType())) || (!Arrays.equals(localParameterizedType1.getActualTypeArguments(), localParameterizedType2.getActualTypeArguments())); i = 0) {
            return false;
          }
        }
        if ((paramType1 instanceof GenericArrayType))
        {
          if (!(paramType2 instanceof GenericArrayType)) {
            return false;
          }
          GenericArrayType localGenericArrayType1 = (GenericArrayType)paramType1;
          GenericArrayType localGenericArrayType2 = (GenericArrayType)paramType2;
          paramType1 = localGenericArrayType1.getGenericComponentType();
          paramType2 = localGenericArrayType2.getGenericComponentType();
          break;
        }
        if (!(paramType1 instanceof WildcardType)) {
          break label261;
        }
        if (!(paramType2 instanceof WildcardType)) {
          return false;
        }
        localWildcardType1 = (WildcardType)paramType1;
        localWildcardType2 = (WildcardType)paramType2;
      } while ((Arrays.equals(localWildcardType1.getUpperBounds(), localWildcardType2.getUpperBounds())) && (Arrays.equals(localWildcardType1.getLowerBounds(), localWildcardType2.getLowerBounds())));
      return false;
      if (!(paramType1 instanceof TypeVariable)) {
        break label322;
      }
      if (!(paramType2 instanceof TypeVariable)) {
        return false;
      }
      localTypeVariable1 = (TypeVariable)paramType1;
      localTypeVariable2 = (TypeVariable)paramType2;
    } while ((localTypeVariable1.getGenericDeclaration() == localTypeVariable2.getGenericDeclaration()) && (localTypeVariable1.getName().equals(localTypeVariable2.getName())));
    return false;
    label322:
    return false;
  }
  
  public static Type getArrayComponentType(Type paramType)
  {
    if ((paramType instanceof GenericArrayType)) {
      return ((GenericArrayType)paramType).getGenericComponentType();
    }
    return ((Class)paramType).getComponentType();
  }
  
  public static Type getCollectionElementType(Type paramType, Class<?> paramClass)
  {
    Type localType = getSupertype(paramType, paramClass, Collection.class);
    if ((localType instanceof WildcardType)) {
      localType = ((WildcardType)localType).getUpperBounds()[0];
    }
    if ((localType instanceof ParameterizedType)) {
      return ((ParameterizedType)localType).getActualTypeArguments()[0];
    }
    return Object.class;
  }
  
  private static Type getGenericSupertype(Type paramType, Class<?> paramClass1, Class<?> paramClass2)
  {
    if (paramClass2 == paramClass1) {
      return paramType;
    }
    if (paramClass2.isInterface())
    {
      Class[] arrayOfClass = paramClass1.getInterfaces();
      int i = 0;
      int j = arrayOfClass.length;
      for (;;)
      {
        if (i >= j) {
          break label87;
        }
        if (arrayOfClass[i] == paramClass2) {
          return paramClass1.getGenericInterfaces()[i];
        }
        if (paramClass2.isAssignableFrom(arrayOfClass[i]))
        {
          paramType = paramClass1.getGenericInterfaces()[i];
          paramClass1 = arrayOfClass[i];
          break;
        }
        i++;
      }
    }
    label87:
    if (!paramClass1.isInterface()) {
      for (;;)
      {
        if (paramClass1 == Object.class) {
          return paramClass2;
        }
        Class localClass = paramClass1.getSuperclass();
        if (localClass == paramClass2) {
          return paramClass1.getGenericSuperclass();
        }
        if (paramClass2.isAssignableFrom(localClass))
        {
          paramType = paramClass1.getGenericSuperclass();
          paramClass1 = localClass;
          break;
        }
        paramClass1 = localClass;
      }
    }
    return paramClass2;
  }
  
  public static Type[] getMapKeyAndValueTypes(Type paramType, Class<?> paramClass)
  {
    if (paramType == Properties.class) {
      return new Type[] { String.class, String.class };
    }
    Type localType = getSupertype(paramType, paramClass, Map.class);
    if ((localType instanceof ParameterizedType)) {
      return ((ParameterizedType)localType).getActualTypeArguments();
    }
    return new Type[] { Object.class, Object.class };
  }
  
  public static Class<?> getRawType(Type paramType)
  {
    for (;;)
    {
      if ((paramType instanceof Class)) {
        return (Class)paramType;
      }
      if ((paramType instanceof ParameterizedType))
      {
        Type localType = ((ParameterizedType)paramType).getRawType();
        .Gson.Preconditions.checkArgument(localType instanceof Class);
        return (Class)localType;
      }
      if ((paramType instanceof GenericArrayType)) {
        return Array.newInstance(getRawType(((GenericArrayType)paramType).getGenericComponentType()), 0).getClass();
      }
      if ((paramType instanceof TypeVariable)) {
        return Object.class;
      }
      if (!(paramType instanceof WildcardType)) {
        break;
      }
      paramType = ((WildcardType)paramType).getUpperBounds()[0];
    }
    if (paramType == null) {}
    for (String str = "null";; str = paramType.getClass().getName()) {
      throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + paramType + "> is of type " + str);
    }
  }
  
  private static Type getSupertype(Type paramType, Class<?> paramClass1, Class<?> paramClass2)
  {
    .Gson.Preconditions.checkArgument(paramClass2.isAssignableFrom(paramClass1));
    return resolve(paramType, paramClass1, getGenericSupertype(paramType, paramClass1, paramClass2));
  }
  
  public static Type resolve(Type paramType1, Class<?> paramClass, Type paramType2)
  {
    TypeVariable localTypeVariable;
    Class localClass3;
    int m;
    label69:
    label108:
    Object localObject;
    for (;;)
    {
      if ((paramType2 instanceof TypeVariable))
      {
        localTypeVariable = (TypeVariable)paramType2;
        GenericDeclaration localGenericDeclaration = localTypeVariable.getGenericDeclaration();
        if ((localGenericDeclaration instanceof Class))
        {
          localClass3 = (Class)localGenericDeclaration;
          if (localClass3 == null) {
            break label138;
          }
          Type localType9 = getGenericSupertype(paramType1, paramClass, localClass3);
          if (!(localType9 instanceof ParameterizedType)) {
            break label138;
          }
          TypeVariable[] arrayOfTypeVariable = localClass3.getTypeParameters();
          m = 0;
          int n = arrayOfTypeVariable.length;
          if (m >= n) {
            break label130;
          }
          if (!localTypeVariable.equals(arrayOfTypeVariable[m])) {
            break label124;
          }
          paramType2 = ((ParameterizedType)localType9).getActualTypeArguments()[m];
          if (paramType2 != localTypeVariable) {
            continue;
          }
          localObject = paramType2;
        }
      }
    }
    label124:
    Type[] arrayOfType2;
    label130:
    label138:
    label241:
    Type localType1;
    label385:
    label466:
    do
    {
      do
      {
        Type[] arrayOfType1;
        Type localType2;
        do
        {
          Type localType4;
          int i;
          Type[] arrayOfType3;
          do
          {
            Type localType6;
            Type localType7;
            do
            {
              return localObject;
              localClass3 = null;
              break;
              m++;
              break label69;
              throw new NoSuchElementException();
              paramType2 = localTypeVariable;
              break label108;
              if (((paramType2 instanceof Class)) && (((Class)paramType2).isArray()))
              {
                Class localClass1 = (Class)paramType2;
                Class localClass2 = localClass1.getComponentType();
                Type localType8 = resolve(paramType1, paramClass, localClass2);
                if (localClass2 == localType8) {
                  return localClass1;
                }
                return arrayOf(localType8);
              }
              if (!(paramType2 instanceof GenericArrayType)) {
                break label241;
              }
              localObject = (GenericArrayType)paramType2;
              localType6 = ((GenericArrayType)localObject).getGenericComponentType();
              localType7 = resolve(paramType1, paramClass, localType6);
            } while (localType6 == localType7);
            return arrayOf(localType7);
            if (!(paramType2 instanceof ParameterizedType)) {
              break label385;
            }
            localObject = (ParameterizedType)paramType2;
            Type localType3 = ((ParameterizedType)localObject).getOwnerType();
            localType4 = resolve(paramType1, paramClass, localType3);
            if (localType4 != localType3) {}
            for (i = 1;; i = 0)
            {
              arrayOfType3 = ((ParameterizedType)localObject).getActualTypeArguments();
              int j = 0;
              int k = arrayOfType3.length;
              while (j < k)
              {
                Type localType5 = resolve(paramType1, paramClass, arrayOfType3[j]);
                if (localType5 != arrayOfType3[j])
                {
                  if (i == 0)
                  {
                    arrayOfType3 = (Type[])arrayOfType3.clone();
                    i = 1;
                  }
                  arrayOfType3[j] = localType5;
                }
                j++;
              }
            }
          } while (i == 0);
          return new ParameterizedTypeImpl(localType4, ((ParameterizedType)localObject).getRawType(), arrayOfType3);
          if (!(paramType2 instanceof WildcardType)) {
            return paramType2;
          }
          localObject = (WildcardType)paramType2;
          arrayOfType1 = ((WildcardType)localObject).getLowerBounds();
          arrayOfType2 = ((WildcardType)localObject).getUpperBounds();
          if (arrayOfType1.length != 1) {
            break label466;
          }
          localType2 = resolve(paramType1, paramClass, arrayOfType1[0]);
        } while (localType2 == arrayOfType1[0]);
        return new WildcardTypeImpl(new Type[] { Object.class }, new Type[] { localType2 });
      } while (arrayOfType2.length != 1);
      localType1 = resolve(paramType1, paramClass, arrayOfType2[0]);
    } while (localType1 == arrayOfType2[0]);
    return new WildcardTypeImpl(new Type[] { localType1 }, EMPTY_TYPE_ARRAY);
    return paramType2;
  }
  
  public static String typeToString(Type paramType)
  {
    if ((paramType instanceof Class)) {
      return ((Class)paramType).getName();
    }
    return paramType.toString();
  }
  
  private static final class GenericArrayTypeImpl
    implements Serializable, GenericArrayType
  {
    private final Type componentType;
    
    public GenericArrayTypeImpl(Type paramType)
    {
      this.componentType = .Gson.Types.canonicalize(paramType);
    }
    
    public final boolean equals(Object paramObject)
    {
      return ((paramObject instanceof GenericArrayType)) && (.Gson.Types.equals(this, (GenericArrayType)paramObject));
    }
    
    public final Type getGenericComponentType()
    {
      return this.componentType;
    }
    
    public final int hashCode()
    {
      return this.componentType.hashCode();
    }
    
    public final String toString()
    {
      return .Gson.Types.typeToString(this.componentType) + "[]";
    }
  }
  
  private static final class ParameterizedTypeImpl
    implements Serializable, ParameterizedType
  {
    private final Type ownerType;
    private final Type rawType;
    private final Type[] typeArguments;
    
    public ParameterizedTypeImpl(Type paramType1, Type paramType2, Type... paramVarArgs)
    {
      boolean bool1;
      if ((paramType2 instanceof Class))
      {
        Class localClass1 = (Class)paramType2;
        if ((paramType1 != null) || (localClass1.getEnclosingClass() == null))
        {
          bool1 = true;
          .Gson.Preconditions.checkArgument(bool1);
          boolean bool2;
          if (paramType1 != null)
          {
            Class localClass2 = localClass1.getEnclosingClass();
            bool2 = false;
            if (localClass2 == null) {}
          }
          else
          {
            bool2 = true;
          }
          .Gson.Preconditions.checkArgument(bool2);
        }
      }
      else
      {
        if (paramType1 != null) {
          break label159;
        }
      }
      label159:
      for (Type localType = null;; localType = .Gson.Types.canonicalize(paramType1))
      {
        this.ownerType = localType;
        this.rawType = .Gson.Types.canonicalize(paramType2);
        this.typeArguments = ((Type[])paramVarArgs.clone());
        for (int i = 0; i < this.typeArguments.length; i++)
        {
          .Gson.Preconditions.checkNotNull(this.typeArguments[i]);
          .Gson.Types.access$000(this.typeArguments[i]);
          this.typeArguments[i] = .Gson.Types.canonicalize(this.typeArguments[i]);
        }
        bool1 = false;
        break;
      }
    }
    
    public final boolean equals(Object paramObject)
    {
      return ((paramObject instanceof ParameterizedType)) && (.Gson.Types.equals(this, (ParameterizedType)paramObject));
    }
    
    public final Type[] getActualTypeArguments()
    {
      return (Type[])this.typeArguments.clone();
    }
    
    public final Type getOwnerType()
    {
      return this.ownerType;
    }
    
    public final Type getRawType()
    {
      return this.rawType;
    }
    
    public final int hashCode()
    {
      return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ .Gson.Types.access$100(this.ownerType);
    }
    
    public final String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(30 * (1 + this.typeArguments.length));
      localStringBuilder.append(.Gson.Types.typeToString(this.rawType));
      if (this.typeArguments.length == 0) {
        return localStringBuilder.toString();
      }
      localStringBuilder.append("<").append(.Gson.Types.typeToString(this.typeArguments[0]));
      for (int i = 1; i < this.typeArguments.length; i++) {
        localStringBuilder.append(", ").append(.Gson.Types.typeToString(this.typeArguments[i]));
      }
      return ">";
    }
  }
  
  private static final class WildcardTypeImpl
    implements Serializable, WildcardType
  {
    private final Type lowerBound;
    private final Type upperBound;
    
    public WildcardTypeImpl(Type[] paramArrayOfType1, Type[] paramArrayOfType2)
    {
      if (paramArrayOfType2.length <= i)
      {
        int k = i;
        .Gson.Preconditions.checkArgument(k);
        if (paramArrayOfType1.length != i) {
          break label88;
        }
        int n = i;
        label29:
        .Gson.Preconditions.checkArgument(n);
        if (paramArrayOfType2.length != i) {
          break label99;
        }
        .Gson.Preconditions.checkNotNull(paramArrayOfType2[0]);
        .Gson.Types.access$000(paramArrayOfType2[0]);
        if (paramArrayOfType1[0] != Object.class) {
          break label94;
        }
      }
      for (;;)
      {
        .Gson.Preconditions.checkArgument(i);
        this.lowerBound = .Gson.Types.canonicalize(paramArrayOfType2[0]);
        this.upperBound = Object.class;
        return;
        int m = 0;
        break;
        label88:
        int i1 = 0;
        break label29;
        label94:
        int j = 0;
      }
      label99:
      .Gson.Preconditions.checkNotNull(paramArrayOfType1[0]);
      .Gson.Types.access$000(paramArrayOfType1[0]);
      this.lowerBound = null;
      this.upperBound = .Gson.Types.canonicalize(paramArrayOfType1[0]);
    }
    
    public final boolean equals(Object paramObject)
    {
      return ((paramObject instanceof WildcardType)) && (.Gson.Types.equals(this, (WildcardType)paramObject));
    }
    
    public final Type[] getLowerBounds()
    {
      if (this.lowerBound != null)
      {
        Type[] arrayOfType = new Type[1];
        arrayOfType[0] = this.lowerBound;
        return arrayOfType;
      }
      return .Gson.Types.EMPTY_TYPE_ARRAY;
    }
    
    public final Type[] getUpperBounds()
    {
      Type[] arrayOfType = new Type[1];
      arrayOfType[0] = this.upperBound;
      return arrayOfType;
    }
    
    public final int hashCode()
    {
      if (this.lowerBound != null) {}
      for (int i = 31 + this.lowerBound.hashCode();; i = 1) {
        return i ^ 31 + this.upperBound.hashCode();
      }
    }
    
    public final String toString()
    {
      if (this.lowerBound != null) {
        return "? super " + .Gson.Types.typeToString(this.lowerBound);
      }
      if (this.upperBound == Object.class) {
        return "?";
      }
      return "? extends " + .Gson.Types.typeToString(this.upperBound);
    }
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal..Gson.Types
 * JD-Core Version:    0.7.0.1
 */