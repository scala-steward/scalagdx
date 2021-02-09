# ScalaGDX: Math
[![license](https://img.shields.io/badge/license-MIT-green)](https://opensource.org/licenses/MIT)

Scala prefers immutability, while LibGDX only provides mutable data containers.  
This module provides immutable variants of vectors, as well as utility methods to lower verbosity.  
Due to the modularity of the framework, you can choose to use only this module with LibGDX for convenience, if desired.

## Vector2

This framework provides a new Vector2 trait to support both the mutable and immutable variants.  
The ``immutable`` variant extends Vector2 directly.  
The ``mutable`` variant however, is an alias for ``com.badlogic.gdx.math.Vector2``.  
This will be discussed in detail below.

### MutableVector2

As mentioned above, MutableVector2 is an alias for the LibGDX Vector2.  
Anywhere that requires a ``com.badlogic.gdx.math.Vector2``, you can simply pass in a
``MutableVector2``.

- Constructing instances can be done through an apply method, rather than constructor:
  - Using apply method: ``MutableVector2()``
  - Using constructor: ``new MutableVector2()``
- Destructuring instances can be done through an unapply method:
  - ``val MutableVector2(x, y) = MutableVector2(1f, 2f)``
- Augmented assignments are provided through implicits:
  - Requires ``import scalagdx.math.syntax.vector2._``
  - ``a += b`` is equivalent to ``a.add(b)``
  - ``a -= b`` is equivalent to ``a.sub(b)``
  - ``a *= b`` is equivalent to ``a.scl(b)``
  - ``a /= b`` is equivalent to ``a.div(b)``
  - Note: These methods are statements, not expressions.
- Arithmetic operators are provided through implicits:
  - Requires ``import scalagdx.math.syntax.vector2._``
  - ``a + b`` is equivalent to ``a.cpy().add(b)``
  - ``a - b`` is equivalent to ``a.cpy().sub(b)``
  - ``a * b`` is equivalent to ``a.cpy().scl(b)``
  - ``a / b`` is equivalent to ``a.cpy().div(b)``
- Comparison operators are provided through implicits:
  - Uses the vector's ``len2`` for comparisons
  - Requires ``import scalagdx.math.syntax.vector2._``
  - ``a < b``, ``a <= b``, ``a > b``, ``a >= b``
- ``Vector2[MutableVector2]`` is ordered, allowing you to sort vectors in an array:
  - Requires ``import scalagdx.math.syntax.vector2._``
  - ``val array: Array[Vector2[_]] = Array(MutableVector2(100f, 200f), MutableVector2(1f, 2f))``
  - ``scala.util.Sorting.quickSort(array)``
- Can be converted to the immutable variant:
  - Requires ``import scalagdx.math.syntax.vector2._``
  - ``vector.toImmutable``
  
### ImmutableVector2

ImmutableVector2 is a rewrite of MutableVector2, providing new instances anytime it should mutate.  
While everything from MutableVector2 is available for ImmutableVector2, there are a couple differences.

- Augmented assignments are not available
- Arithmetic operators already provide new instances:
  - ``a + b`` is equivalent to ``a.add(b)``
  - ``a - b`` is equivalent to ``a.sub(b)``
  - ``a * b`` is equivalent to ``a.scl(b)``
  - ``a / b`` is equivalent to ``a.div(b)``
- Can be converted to the mutable variant: ``vector.toMutable``
  
### Gotcha #1: Vector2 Equality

Comparing between ``MutableVector2`` and ``ImmutableVector2`` is possible, but you need to be wary of typing issues.

When comparing using ``==``, this WILL fail:
```scala
MutableVector2() == ImmutableVector2()
ImmutableVector2() == MutableVector2()
```
Vector2 provides a type-safe ``===`` equality method for comparing between both variants:
```scala
import scalagdx.math.syntax.vector2._
MutableVector2() === ImmutableVector2()
ImmutableVector2() === MutableVector2()
```
However, despite the required import available in scope, this will still fail:
```scala
import scalagdx.math.syntax.vector2._
MutableVector2() == ImmutableVector2()
ImmutableVector2() == MutableVector2()
```

Be careful when testing, as ScalaTiC provides its own ``===`` method which will not force an implicit conversion.
To prevent any equality errors, always use ``===`` and ``import scalagdx.math.syntax.vector2._``.

### Gotcha #2: Vector2 Destructuring

Using ``Vector2#unapply`` can give an unexpected ``MatchError``. For example:
```scala
val Vector2(x1, y1) = ImmutableVector2(1f, 2f) // Runs fine
val Vector2(x2, y2) = MutableVector2(1f, 2f) // MatchError at runtime
```
The problem here is ``Vector2#unapply`` requires ``Vector2[_]`` instances.  
``ImmutableVector2`` is an instance of ``Vector2[ImmutableVector2]``, so it destructures fine.
``MutableVector2`` is not an instance however, unless we use an implicit conversion.

```scala
import scalagdx.math.syntax.vector2._
val Vector2(x1, y1) = MutableVector2(1f, 2f) // Still throws a MatchError..
val Vector2(x2, y2): Vector2[MutableVector2] = MutableVector2(1f, 2f) // Success, it destructured!
```

As you can see, the former still throws a MatchError as no implicit conversion is made.  
The latter forces a conversion because of the explicit typing of the variable.  
This looks too verbose, so prefer using the actual type's unapply methods:
```scala
val ImmutableVector2(x1, y1) = ImmutableVector2(1f, 2f)
val MutableVector2(x2, y2) = MutableVector2(1f, 2f)
```

## Vector3

Similarly to ``Vector2``, ``Vector3`` is a new trait to support both the mutable and immutable variants.  
The ``immutable`` variant extends Vector3 directly.  
The ``mutable`` variant however, is an alias for ``com.badlogic.gdx.math.Vector3``.  

### MutableVector3

As mentioned above, MutableVector3 is an alias for the LibGDX Vector2.  
Anywhere that requires a ``com.badlogic.gdx.math.Vector3``, you can simply pass in a
``MutableVector3``.

- Constructing instances can be done through an apply method, rather than constructor:
  - Using apply method: ``MutableVector3()``
  - Using constructor: ``new MutableVector3()``
- Destructuring instances can be done through an unapply method:
  - ``val MutableVector2(x, y, z) = MutableVector2(1f, 2f, 3f)``
- Augmented assignments are provided through implicits:
  - Requires ``import scalagdx.math.syntax.vector3._``
  - ``a += b`` is equivalent to ``a.add(b)``
  - ``a -= b`` is equivalent to ``a.sub(b)``
  - ``a *= b`` is equivalent to ``a.scl(b)``
  - ``a /= b`` is equivalent to ``a.div(b)``
  - Note: These methods are statements, not expressions.
- Arithmetic operators are provided through implicits:
  - Requires ``import scalagdx.math.syntax.vector3._``
  - ``a + b`` is equivalent to ``a.cpy().add(b)``
  - ``a - b`` is equivalent to ``a.cpy().sub(b)``
  - ``a * b`` is equivalent to ``a.cpy().scl(b)``
  - ``a / b`` is equivalent to ``a.cpy().div(b)``
- Comparison operators are provided through implicits:
  - Uses the vector's ``len2`` for comparisons
  - Requires ``import scalagdx.math.syntax.vector3._``
  - ``a < b``, ``a <= b``, ``a > b``, ``a >= b``
- ``Vector3[MutableVector3]`` is ordered, allowing you to sort vectors in an array:
  - Requires ``import scalagdx.math.syntax.vector3._``
  - ``val array: Array[Vector3[_]] = Array(MutableVector3(100f, 200f, 300f), MutableVector3(1f, 2f, 3f))``
  - ``scala.util.Sorting.quickSort(array)``
- Can be converted to the immutable variant:
  - Requires ``import scalagdx.math.syntax.vector3._``
  - ``vector.toImmutable``
  
### ImmutableVector3

ImmutableVector3 is a rewrite of MutableVector3, providing new instances anytime it should mutate.  
While everything from MutableVector3 is available for ImmutableVector3, there are a couple differences.

- Augmented assignments are not available
- Arithmetic operators already provide new instances:
  - ``a + b`` is equivalent to ``a.add(b)``
  - ``a - b`` is equivalent to ``a.sub(b)``
  - ``a * b`` is equivalent to ``a.scl(b)``
  - ``a / b`` is equivalent to ``a.div(b)``
- Can be converted to the mutable variant: ``vector.toMutable``
  
### Gotcha #1: Vector3 Equality

Comparing between ``MutableVector3`` and ``ImmutableVector3`` is possible, but you need to be wary of typing issues.

When comparing using ``==``, this WILL fail:
```scala
MutableVector3() == ImmutableVector3()
ImmutableVector3() == MutableVector3()
```
Vector2 provides a type-safe ``===`` equality method for comparing between both variants:
```scala
import scalagdx.math.syntax.vector2._
MutableVector3() === ImmutableVector3()
ImmutableVector3() === MutableVector3()
```
However, despite the required import available in scope, this will still fail:
```scala
import scalagdx.math.syntax.vector3._
MutableVector3() == ImmutableVector3()
ImmutableVector3() == MutableVector3()
```

Be careful when testing, as ScalaTiC provides its own ``===`` method which will not force an implicit conversion.
To prevent any equality errors, always use ``===`` and ``import scalagdx.math.syntax.vector3._``.

### Gotcha #2: Vector3 Destructuring

Using ``Vector2#unapply`` can give an unexpected ``MatchError``. For example:
```scala
val Vector3(x1, y1, z2) = ImmutableVector3(1f, 2f, 3f) // Runs fine
val Vector3(x2, y2, z2) = MutableVector3(1f, 2f, 3f) // MatchError at runtime
```
The problem here is ``Vector3#unapply`` requires ``Vector3[_]`` instances.  
``ImmutableVector3`` is an instance of ``Vector3[ImmutableVector3]``, so it destructures fine.
``MutableVector3`` is not an instance however, unless we use an implicit conversion.

```scala
import scalagdx.math.syntax.vector3._
val Vector3(x1, y1, z1) = MutableVector3(1f, 2f, 3f) // Still throws a MatchError..
val Vector3(x2, y2, z2): Vector3[MutableVector3] = MutableVector3(1f, 2f, 3f) // Success, it destructured!
```

As you can see, the former still throws a MatchError as no implicit conversion is made.  
The latter forces a conversion because of the explicit typing of the variable.  
This looks too verbose, so prefer using the actual type's unapply methods:
```scala
val ImmutableVector3(x1, y1, z1) = ImmutableVector3(1f, 2f, 3f)
val MutableVector3(x2, y2, z2) = MutableVector3(1f, 2f, 3f)
```

## Importing all required implicits
When working with MutableVector2, this import is recommended:
```scala
import scalagdx.math.syntax.vector2._
```
When working with MutableVector3, this import is recommended:
```scala
import scalagdx.math.syntax.vector3._
```
This import is actually a shortcut to import all of the above:
```scala
import scalagdx.math.syntax.all._
```
And in fact, this is also a shortcut to the import right above:
```scala
import scalagdx.math.implicits._
```
Therefore, the simplest way to import implicits required for the math module is ``import scalagdx.math.implicits._``  
Make sure there are no duplicate imports as that can cause issues with the compiler.

## What is refined?

You may notice some parameters in the vector classes require a refined type. Refined is a library used for compile-time validation.  
For example, ``Vector2#fromString`` requires a string in the form ``"(x,y)"``:
```scala
import eu.timepit.refined.auto._
Vector2.fromString("(123,456)") // x = 123, y = 456
Vector2.fromString("(  123  ,  456   )") // x = 123, y = 456
Vector2.fromString("(123,456)  ") // Fails to compile due to space after )
Vector2.fromString("ABCDEFG") // Fails to compile
```
But what if I want to refine a value at runtime and I know its value is valid? Use ``Refined#unsafeApply``:
```scala
val string = "(123,456)"
Vector2.fromString(Refined.unsafeApply(string)) // x = 123, y = 456
```
Click [here](https://github.com/fthomas/refined) for more information on how to use refined.
