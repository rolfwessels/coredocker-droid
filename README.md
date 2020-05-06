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
* :space_invader: GraphQl
* :coffee: Espresso


## Todo
- [X] Add a way to add users
- [X] Add a way to edit users
- [X] Add a way to remove users

- [ ] Use storage for users and observe that
- [ ] Indicator when its loading from web
 
- [ ] Add firebase analytics back into the mix
- [ ] Setup circle ci or github actions
- [ ] Logging SUCKS! need something better
- [ ] Look at deployment
