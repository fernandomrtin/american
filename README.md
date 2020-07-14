# My American Client Application

In this repository you will find my solution to the american client app.

### Implemented functionalities:
  - Login screen with Karumi logo and two edit text to introduce the username and password.
  - Private zone screen with a welcome message and a button to logout.
  - Use case to login using the username and password introduced.
  - Use case to store session data.
  - Use case to check store session data to know if it is still valid. This allow us to keep session active even if the user leaves the app.
  - Use case to remove session data when the user decide to logout in private zone.
  - Instrumentation tests and unit tests.
  - Error control.
  - Continuos integration with GitHub Actions.
  - Check code style with Ktlint

### Libraries

In the development of this app we have used the following libraries:

* [Dagger2](https://dagger.dev/) - DI.
* [Navigation Component](https://developer.android.com/guide/navigation/navigation-getting-started) - Routing of the app.
* [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - Jobs execution.
* [Mockk](https://mockk.io/) - Testing.
* [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - View loading.
* [Arrow](https://arrow-kt.io/) - Functional Either.
* [Ktlint](https://arrow-kt.io/) - Check code style.

### How the app works

* The application consists of a single Activity. This activity has as main screen, the login screen with Karumi logo and two texts fields to introduce username and password. If the user introduce the right credentials (username = fernando, password = fernando) the app validates it, stored session data and the user can access to private zone. However, if the user introduce wrong credentials the app returns a wrong validation, displays a snackbar error and the user can not access to private zone.



 

