This test application is an example of how one can use MVP mixed with a use-case
approach to write a simple application on Android

Please be sure to keep in mind that right now edge cases such has on configuration
change aren't directly support supported.  This was intented to keep development
time shorter.

##How to login

Use any username that is as least 5 character long and then use the password **trov22**.
Once logged in, any of the tweets you create will use your username as the sender.

## Some Details

### Package structure
The project is separated in 3 main packages

1. com.example.android.twitterclient.ui
2. com.example.android.twitterclient.domain
3. com.example.android.twitterclient.data

The **ui** is the presentation package then you have the **domain** which contains the business logic of
of our application and lastly you have the **data** package which contains classes that deals with
fetching data or persisting data.

It's important to note the direction the dependencies.  The **ui** package depends on the **domain** package
and the **data** package also depends on the **domain** package.  This creates an environment where
the business logic code (the application) is independent from everything else.  This is accomplished
using interfaces in the domain package and having the other packages implement the necessary interfaces.

ui -> domain <- data

This allows for simple testing of the business logic independent from Android as best as possible.  
Using this approach we could also create each of those packages as separate gradle modules.
It can be really useful if you have different teams that work on difference layers of the application.

### Testing
For the sake of time, only a single test class as been added per package.  It gives the idea
of how of layers can be tested independently.  To clarify not all use cases or conditional block
have been tested.  Only a few particular have been put to the test.  It goes without saying that
for a production application you'd want to cover more code with tests but it's also important
to tests the right things.

Unit tests can be run using this command `./gradlew test` from the command line at the root of
the project.  Or you can simply add a JUnit configuration in AndroidStudio

The **ui** should also be tested using larger tests such as integration tests.  I would use the
**espresso 2.0** libary supported by google to accomplish such tasks.  It allows to easily test
using a fluent dsl the integration of your application.

For example you could test that given valid creditials that clicking on the login button results 
in launching the TweetsActivity.  You can also verify that if a particular state occurs that
a specific view is set enabled or disabled.  It allows you to easily test such cases.

One such test was added to test that when a user clicks on the add tweet button that the
ComposeActivity is lauched successfully.

Integration test can be run using this command `./gradlew connectedAndroidTest` from the command
line or by adding an Android Studio Android Test configuration.


Another great way to test is to have a debug panel in the app for internal builds which is used to
switch on and off specific features and and or tweak responses where necessary.  This hasn't been 
implemented here.

### Twitter Api
The twitter api was implemented using Retrofit and mocked using retrofit-mock.  Retrofit-mock
allows to control the behavior of the response.  This means that we can actually add delays to
the rest call and deliver mocked data to get same feel you would get using the real twitter api.

There's 2 preferences that can be changed to affect the delay and the error probability of your
api request.  I haven't added the necessary debugging code to support chaning those values at runtime
but if you wish to experiment with them you can modify the MockNetModule.java file.

Look for `provideMockRetrofit` method and change the value for `mock_request_delay` which is in
seconds and or `mock_failure_percent` which is a percentage.

Only 3 API was implemented for the twitter api.  It's important to note that it's highly probable
that the real Twitter api would require a very different interface but that wasn't taken into account
for this test app.

Also the login api method is not secure what's so ever.  In a production application login would
be implemented very diffently, most likely using oauth or basic authentication.

### The Looks
It looks awful I know, it's not very sexy, I spent most of my time focusing on the functional aspect.
My focus was to write code that would be easily testable.  I hope you won't hate me for it ;)