# JvmOverloads annotation

We can define default values in **Kotlin**. However, in **Java** there is no such thing default value.

With `@JvmOverloads` makes possible to generate traditional methods from the one with default values.


```java
class CustomView : View {
    constructor(context: Context):
        super(context)
    constructor(context: Context, attrs: AttributeSet?):
        super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):
        super(context, attrs, defStyleAttr)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int):
        super(context, attrs, defStyleAttr, defStyleRes)
}
```


The above code code snippet with `@JvmOverloads` annotation.
```java
class CustomView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0,
        defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes)
```

They seem to be the same. The difference is the following:
![difference](./pictures/jvmOverloads.png)

**Every time the all params constructor of the parent class will be called.** This can cause an issue if anything else happens in the other constructors of the parent. 
