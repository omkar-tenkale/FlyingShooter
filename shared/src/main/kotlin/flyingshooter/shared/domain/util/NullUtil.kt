package flyingshooter.shared.domain.util

fun <T> T?.required(): T = this ?: error("Required not null")