package flyingshooter.shared.domain.util

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
) {

    class Success<T>(data: T): Resource<T>(data){
        operator fun invoke() = data!!
    }
    class Error<T>(val exception: Throwable): Resource<T>(null)
    class Loading<T>: Resource<T>(null)
}