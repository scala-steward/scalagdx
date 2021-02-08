# ScalaGDX: Math
[![license](https://img.shields.io/badge/license-MIT-green)](https://opensource.org/licenses/MIT)

Scala prefers immutability, while LibGDX only provides mutable data containers.  
This module aims to provide scala friendly syntax for existing classes.

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
  
### Gotcha: Vector2 Equality

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
  
### Gotcha: Vector3 Equality

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