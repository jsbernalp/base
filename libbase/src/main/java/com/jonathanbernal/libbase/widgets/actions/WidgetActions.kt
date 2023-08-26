package com.jonathanbernal.libbase.widgets.actions

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.DialogFragment

open class WidgetAction(
    val id: String,
)

class WidgetActionDialog(
    val data: DialogFragment,
) : WidgetAction(WidgetActionDialog::class.java.name)

class WidgetActionFunction(
    val data: (() -> Unit),
) : WidgetAction(WidgetActionFunction::class.java.name)

class WidgetActionIntent(
    val data: IntentModel,
) : WidgetAction(WidgetActionIntent::class.java.name)

data class IntentModel(
    val action: String,
    val bundle: Bundle? = null,
    val uri: Uri? = null,
    val code: Int = 0,
    val flags: Int? = null,
    val packageName: String? = null,
    val className: String? = null,
)

class WidgetActionInputTextChange(
    val key: String,
    val value: Any,
) : WidgetAction(WidgetActionInputTextChange::class.java.name)

class WidgetActionSpinnerChange(
    val key: String,
    val value: Any,
) : WidgetAction(WidgetActionSpinnerChange::class.java.name)

class WidgetActionCloseView : WidgetAction(WidgetActionCloseView::class.java.name)

data class WidgetActionService(
    val method: String,
    val url: String,
    val data: Map<String, String>?,
) : WidgetAction(WidgetActionService::class.java.name)
