package com.rappi.components.widgets.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.AttrRes
import com.jonathanbernal.libbase.databinding.ViewSpinnerOutlineWidgetBinding
import com.jonathanbernal.libbase.list.items.GenericItemView
import com.rappi.components.widgets.actions.WidgetActionSpinnerChange
import com.jonathanbernal.libbase.widgets.model.ElementSpinner
import com.jonathanbernal.libbase.widgets.model.SpinnerModelWidget
import com.jonathanbernal.libbase.widgets.model.views.WEIGHT_RES
import java.util.Locale

class SpinnerOutlineWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr),
    View.OnFocusChangeListener,
    GenericItemView<SpinnerModelWidget> {

    private lateinit var item: SpinnerModelWidget

    private val TAG = "SpinnerOutlineWidget"

    private lateinit var binding: ViewSpinnerOutlineWidgetBinding

    private var itemSelectedListener: (Any) -> Unit = {}

    override fun bind(item: SpinnerModelWidget) {
        this.item = item
        addInflate()
    }

    private fun addInflate() {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ViewSpinnerOutlineWidgetBinding.inflate(inflater)
        addView(binding.root)
        initView()
    }

    private fun initView() {
        item.extras?.get(WEIGHT_RES)?.let {
            addWeight(it.toString().toFloat())
        }
        addMargin()
        addPadding()
        binding.spinner.tag = TAG
        binding.spinner.onFocusChangeListener = this
        item.elements?.let {
            setDataAutocomplete(it)
        }
        item.colorHint?.let { color ->
            binding.textViewHolder.setTextColor(color)
        }
        item.hint?.let { text ->
            binding.textViewHolder.text = text
        }
        setItemSelectedListener()
    }

    private fun onSelectItem(id: String) {
        item.elements?.indexOfFirst { it.id == id }?.let { index ->
            if (index > 0) {
                binding.spinner.setSelection(index)
            }
        }
        item.elements?.firstOrNull { it.id == id }?.let { selectItem ->
            itemSelectedListener(
                WidgetActionSpinnerChange(
                    selectItem.id,
                    selectItem.name
                )
            )
        }
    }

    private fun addMargin() {
        layoutParams = LinearLayout.LayoutParams(
            item.width,
            item.height
        ).apply {
            setMargins(
                resources.getDimensionPixelSize(item.margin.spacingLeft),
                resources.getDimensionPixelSize(item.margin.spacingTop),
                resources.getDimensionPixelSize(item.margin.spacingRight),
                resources.getDimensionPixelSize(item.margin.spacingBottom),
            )
        }
    }

    private fun addPadding() {
        val top: Int = resources.getDimensionPixelSize(item.padding.spacingTop)
        val bottom: Int = resources.getDimensionPixelSize(item.padding.spacingBottom)
        val left: Int = resources.getDimensionPixelSize(item.padding.spacingLeft)
        val right: Int = resources.getDimensionPixelSize(item.padding.spacingRight)
        setPadding(left, top, right, bottom)
    }

    private fun addWeight(weight: Float) {
        layoutParams = LinearLayout.LayoutParams(
            item.width,
            item.height,
            weight
        )
    }

    private fun setOptions(adapter: ArrayAdapter<String>) {
        binding.spinner.adapter = adapter
    }

    private fun setItemSelectedListener() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val value = item.elements?.get(position)?.id.orEmpty()
                itemSelectedListener(
                    WidgetActionSpinnerChange(
                        item.id,
                        value
                    )
                )
            }

            override fun onNothingSelected(parent: AdapterView<*>?) = Unit
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) = Unit

    private fun setDataAutocomplete(options: List<ElementSpinner>) {
        val items = ArrayList<String>()
        options.mapTo(items) { it.name.capitalize(Locale.ROOT) }
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, items)
        setOptions(arrayAdapter)
        item.selectElement?.let {
            onSelectItem(it)
        }
    }

    fun setItemSelectedListener(listener: (Any) -> Unit = {}) {
        this.itemSelectedListener = listener
    }

    override fun getData(): SpinnerModelWidget {
        return item
    }

    override fun getView(): View = rootView
}
