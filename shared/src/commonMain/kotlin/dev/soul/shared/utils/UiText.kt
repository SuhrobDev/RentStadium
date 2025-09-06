package dev.soul.shared.utils

sealed class UiText {
    data class DynamicString(val text: String) : UiText()
//    data class StringResourceWrapper(val stringResource: StringResource) : UiText()
//      data class StringResource(val resId: Int): UiText()

    fun asString(): String {
        return when (this) {
            is DynamicString -> text
//            is StringResource -> resId
//            is StringResourceWrapper -> stringResource.desc().localized()
        }
    }
}