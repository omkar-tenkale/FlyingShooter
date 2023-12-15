package flyingshooter.android.presentation

import androidx.compose.foundation.clickable
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import flyingshooter.android.domain.Resource
import flyingshooter.android.domain.entities.GameInfo

@Composable
fun <T> ResourceState(
    value: MutableState<Resource<T>>,
    onRetry: () -> Unit = {},
    errorContent: @Composable ((Throwable, () -> Unit) -> Unit)? = null,
    content: @Composable (T) -> Unit
) {
    val value = value.value
    when (value) {
        is Resource.Loading -> Text(text = "Loading")
        is Resource.Success -> content(value())
        is Resource.Error -> errorContent?.let { it(value.exception, onRetry) } ?: Text(
            text = "Error",
            modifier = Modifier.clickable { onRetry() })
    }
}

@Composable
fun <T> LoadingResource() = remember {
    mutableStateOf<Resource<T>>(Resource.Loading())
}

suspend fun <T> updateResource(
    block: suspend () -> T,
    onUpdate: (Resource<T>) -> Unit
) {
    onUpdate(Resource.Loading())
    try {
        val result = block()
        onUpdate(Resource.Success(result))
    } catch (e: Exception) {
        onUpdate(Resource.Error(e))
    }
}


suspend fun <T> updateResource(
    state: MutableState<Resource<T>>,
    block: suspend () -> T,
) {
    state.value = Resource.Loading()
    state.value = try {
        val result = block()
        Resource.Success(result)
    } catch (e: Exception) {
        e.printStackTrace()
        Resource.Error(e)
    }
}


fun <T>  MutableState<Resource<List<T>>>.remove(item: T){
    value = (invoke() - item).asResource()
}

fun <T> T.asResource(): Resource<T> = Resource.Success(this)

operator fun <T> MutableState<Resource<T>>.invoke() = value.data
operator fun <T> MutableState<Resource<List<T>>>.invoke() = value.data ?: emptyList()
operator fun MutableState<Resource<Boolean>>.invoke() = value.data ?: false
