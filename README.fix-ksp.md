fix(build): use ksp() Kotlin DSL for KSP dependencies

Replaced quoted "ksp" dependency declarations with the Kotlin-DSL form ksp(...)
so the Kotlin Gradle DSL recognizes the KSP configuration.
