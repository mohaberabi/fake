package org.mohaberabi.testideplugin.com.mohaberabi.models

data class AnotherClass(
    val lastName: String,
    val myClass: MyClass? = null,
)

data class MyClass(
    val name: String,
    val anotherClass: AnotherClass,
    val boolean: Boolean,
    val int: Int,
    val long: Long,
    val double: Double,
    val float: Float,
    val list1: List<String>,
    val list2: List<Long>,
    val list3: List<Int>,
    val list4: List<Double>,
    val list5: List<Float>,
    val set: Set<String>,
    val map: Map<String, String>,
    val nullalble: String? = null,
    val map2: Map<AnotherClass, AnotherClass>
)
