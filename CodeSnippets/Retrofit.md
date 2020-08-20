# How to use Retrofit

#### Adding dependencies
- dependency for Retrofit
- dependency for GSON-CONVERTER

#### Serialization/Deserialization
- java object to json is **Serialization**
- json to java object is **Deserialization**

#### Model class for the remote response
- field names in the POJO class should match with the resposne OR
- USE following annotation `@SerializedName("here_goes_the_json_field_name")`

#### Creating interface to communicate with endpoints

```java
public interface MyRestApi {
  @GET("posts")
	fun getPosts(): Call<List<Post>>
}
```

- **GET** defines an endpoint

###### If we want to create a parameterized endpoint

```java
@GET("posts/{id}")
fun getPosts(@Path("id") postId: Int)
```

###### If we want to add parameters to our query
```java
GET("posts")
fun getPosts(@Query("userId") userId: Int)
```
  - if we want to ignore parameters, we should call the function with **null** values. Primitive types cannot be nulls, so use for example **Integer** instead of **Int**
  - if we want to use **more userId** than type should be **Integer[]**


###### If we want to define query parameters in runtime

```java
fun getPosts(@QueryMap parameters: Map<String, String>)
```
- first half of the map is the **name of the query param** and second half is **the value**


#### Create the retrofit instance
Baseurl should end with `/`

```java
retrofit = Retrofit.Builder()
  .baseUrl()
  .addConverterFactory(GsonConverterFactory.create())
  .build()

myapi = retrofit.create(MyRestApi::class.java)
```
