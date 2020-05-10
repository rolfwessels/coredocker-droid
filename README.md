# CoreDocker - Android templat


## GraphQl

Download the latest schema
```cmd
gradlew downloadApolloSchema -Pcom.apollographql.apollo.endpoint=https://api.dev.coredocker.wessels.online/graphql -Pcom.apollographql.apollo.schema=src/main/graphql/com/coredocker/schema.json
gradlew downloadApolloSchema -Pcom.apollographql.apollo.endpoint=http://localhost:5000/graphql -Pcom.apollographql.apollo.schema=src/main/graphql/com/coredocker/schema.json
gradlew generateApolloSources

```

## What to do when you want to update gradel

Update gradle
```cmd
gradlew --version
gradlew help --warning-mode=all
gradlew wrapper --gradle-version 5.6
#gradlew wrapper --gradle-version 6.3 to update the project to 6.3.
gradlew clean
gradlew sync
```


### What does it include? ###

It includes many libraries that make development easier and provides a nice folder/package structure, such as

* :books: MVVM
* :twisted_rightwards_arrows: Coroutines
* :rocket: Jetpack suite
* :globe_with_meridians: Navigation library
* :space_invader: Robolectric
* :green_book: [GraphQl](https://www.apollographql.com/docs/android)
* :scroll: [Timber](https://github.com/JakeWharton/timber)
* :art: [glide](https://github.com/bumptech/glide)
* :coffee: Espresso

## Logging
Logging is done using https://github.com/JakeWharton/timber.

For nicer output in logcat update the `settings` > `android logcat`
```
Debug: A8CA9E
Info: 9BDE81
Warn: E8BF81
Error: FF6B68
``` 

## Todo
- [X] Add a way to add users
- [X] Add a way to edit users
- [X] Add a way to remove users
- [X] Logging SUCKS! need something better
- [X] Setup circle ci or github actions
- [X] Use storage for users and observe that

- [ ] If authentication fails then we should show login screen.
- [ ] Add some graphs on a dashboard
- [ ] Add integration to camera to capture QR code or something
- [ ] Indicator when its loading from web
- [ ] Add integration to get geo location
- [ ] Add firebase analytics back into the mix
- [ ] Add centralized logging. Could be from firebase.
- [ ] Look at deployment

## Developers

Always run the following before adding pull request.
```
gradlew lint ktformat ktcheck bundleDebug test
```