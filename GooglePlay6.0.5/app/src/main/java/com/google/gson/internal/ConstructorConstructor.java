package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public final class ConstructorConstructor
{
  private final Map<Type, InstanceCreator<?>> instanceCreators;
  
  public ConstructorConstructor()
  {
    this(Collections.emptyMap());
  }
  
  public ConstructorConstructor(Map<Type, InstanceCreator<?>> paramMap)
  {
    this.instanceCreators = paramMap;
  }
  
  private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> paramClass)
  {
    try
    {
      final Constructor localConstructor = paramClass.getDeclaredConstructor(new Class[0]);
      if (!localConstructor.isAccessible()) {
        localConstructor.setAccessible(true);
      }
      ObjectConstructor local2 = new ObjectConstructor()
      {
        public final T construct()
        {
          try
          {
            Object localObject = localConstructor.newInstance(null);
            return localObject;
          }
          catch (InstantiationException localInstantiationException)
          {
            throw new RuntimeException("Failed to invoke " + localConstructor + " with no args", localInstantiationException);
          }
          catch (InvocationTargetException localInvocationTargetException)
          {
            throw new RuntimeException("Failed to invoke " + localConstructor + " with no args", localInvocationTargetException.getTargetException());
          }
          catch (IllegalAccessException localIllegalAccessException)
          {
            throw new AssertionError(localIllegalAccessException);
          }
        }
      };
      return local2;
    }
    catch (NoSuchMethodException localNoSuchMethodException) {}
    return null;
  }
  
  public final <T> ObjectConstructor<T> getConstructor(TypeToken<T> paramTypeToken)
  {
    final Type localType = paramTypeToken.type;
    final Class localClass = paramTypeToken.rawType;
    final InstanceCreator localInstanceCreator = (InstanceCreator)this.instanceCreators.get(localType);
    Object localObject1;
    if (localInstanceCreator != null) {
      localObject1 = new ObjectConstructor()
      {
        public final T construct()
        {
          return localInstanceCreator.createInstance$6d6ddcce();
        }
      };
    }
    do
    {
      return localObject1;
      localObject1 = newDefaultConstructor(localClass);
    } while (localObject1 != null);
    Object localObject2;
    if (Collection.class.isAssignableFrom(localClass)) {
      if (SortedSet.class.isAssignableFrom(localClass)) {
        localObject2 = new ObjectConstructor()
        {
          public final T construct()
          {
            return new TreeSet();
          }
        };
      }
    }
    while (localObject2 != null)
    {
      return localObject2;
      if (Set.class.isAssignableFrom(localClass))
      {
        localObject2 = new ObjectConstructor()
        {
          public final T construct()
          {
            return new LinkedHashSet();
          }
        };
      }
      else if (Queue.class.isAssignableFrom(localClass))
      {
        localObject2 = new ObjectConstructor()
        {
          public final T construct()
          {
            return new LinkedList();
          }
        };
      }
      else
      {
        localObject2 = new ObjectConstructor()
        {
          public final T construct()
          {
            return new ArrayList();
          }
        };
        continue;
        if (Map.class.isAssignableFrom(localClass)) {
          localObject2 = new ObjectConstructor()
          {
            public final T construct()
            {
              return new LinkedHashMap();
            }
          };
        } else {
          localObject2 = null;
        }
      }
    }
    new ObjectConstructor()
    {
      private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();
      
      public final T construct()
      {
        try
        {
          Object localObject = this.unsafeAllocator.newInstance(localClass);
          return localObject;
        }
        catch (Exception localException)
        {
          throw new RuntimeException("Unable to invoke no-args constructor for " + localType + ". Register an InstanceCreator with Gson for this type may fix this problem.", localException);
        }
      }
    };
  }
  
  public final String toString()
  {
    return this.instanceCreators.toString();
  }
}


/* Location:           F:\apktool\apktool\Google_Play_Store6.0.5\classes-dex2jar.jar
 * Qualified Name:     com.google.gson.internal.ConstructorConstructor
 * JD-Core Version:    0.7.0.1
 */