# My American Client Application

In this repository you will find my solution to the american client app.

### Implemented functionalities:
  - Login screen with Karumi logo and two edit text to introduce the username and password.
  - Private zone screen with a welcome message and a button to logout.
  - Use case to login using the username and password introduced.
  - Use case to store session data.
  - Use case to get stored session data.
  - Use case to check if the stored data is still valid.
  - Use case to remove session data when the user decide to logout in private zone.
  - Instrumentation tests and unit tests.
  - Error control.
  - Continuos integration with GitHub Actions.
  - Check code style with Ktlint.
  - Use Shot to test the app through screenshots.

### Libraries

In the development of this app we have used the following libraries:

* [Dagger2](https://dagger.dev/) - DI.
* [Retrofit2](https://square.github.io/retrofit/) - Used to mock responses with MockInterceptor.
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Routing of the app.
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Jobs execution.
* [Mockk](https://mockk.io/) - Testing.
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - View loading.
* [Arrow](https://arrow-kt.io/) - Functional Either.
* [Ktlint](https://arrow-kt.io/) - Check code style.
* [Shot](https://github.com/Karumi/Shot) - Test the app through screenshots.

### How the app works

* The application consists of a single Activity. This activity has as main screen, the login screen with Karumi logo and two texts fields to introduce username and password. If the user introduce the right credentials (any not empty credentials) the app validates it with the login, stored session data and the user can access to private zone. If the user closes the app, if he had done login process before, he can access to private area without to intrduce his credetials again thanks to validate session data use case.

* However, if the user introduce wrong credentials the app returns a wrong validation, displays a snackbar error and the user can not access to private zone. **"error" in username is considered as "the wrong credential"**



 

