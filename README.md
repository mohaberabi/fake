# Fake

**Fake** is a Gradle plugin powered by **KSP** that automatically generates **type-safe fake builders** for Kotlin
`data class` models —
designed for **tests only to generate fakes for models while testing**.

It works with **Kotlin**, **Android**, and **Kotlin Multiplatform (KMP)** projects.

---

## ✨ What it does

Given model:

```kotlin
data class User(
    val id: String,
    val age: Int,
    val tags: List<String>
)
```

Fake generates:

```kotlin
val user = fakeUser {
    id = "123"
    age = 30
}
```

---

## 📦 Installation

```kotlin
plugins {
    id("io.github.mohaberabi.fakeklass.plugin") version "<version>"
}
dependencies {
    add("kspCommonMainMetadata", "io.github.mohaberabi:fake:<version>")
}
```

Make sure `mavenCentral()` is configured.

---

## ⚙️ Configuration

Tell FakeKlass which packages contain your models:
Generated fakes are available and shipped  **only in for the source set you want **.

```kotlin
fakeKlass {
    allowedPackages(
        "com.example.models",
        "com.example.domain"
    )
}
sourceSets {
    commonTest {
        kotlin.srcDir(layout.buildDirectory.dir("generated/ksp/metadata/commonMain"))
    }
}

```

---

## 🧪 Using fakes in tests

```kotlin
@Test
fun createUser() {
    val user = fakeUser {
        age = 42
    }

    assertEquals(42, user.age)
}

```

