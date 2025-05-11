package lavoro.teamup.core.view

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import lavoro.teamup.data.model.brand.Brand
import lavoro.teamup.data.model.city.City
import lavoro.teamup.data.model.client.Client
import lavoro.teamup.data.model.product.Product

internal fun Spinner.selectQuery(query: String?) {
    if (query.isNullOrBlank()) return

    for (i in 0 until this.count) {
        if (this.getItemAtPosition(i) == query) {
            this.setSelection(i)
            break
        }
    }
}

internal fun Spinner.bindCityList(
    dataList: List<City>,
    selectTitle: String? = null,
    selectAction: ((Int?) -> Unit)? = null
) {
    dataList.let {

        if (it.isEmpty()) {
            this.visible(false)
            return
        }

        val hasTitle = selectTitle != null

        val list = mutableListOf<String>()

        if (hasTitle) list.add(selectTitle ?: "Select")

        for (i in it.indices) list.add(it[i].name)

        val aa: ArrayAdapter<String> =
            ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        adapter = aa
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {

                if (hasTitle) selectAction?.invoke(if (position == 0) null else position - 1)
                else selectAction?.invoke(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
}

internal fun Spinner.bindBrandList(
    dataList: List<Brand>,
    selectTitle: String? = null,
    selectAction: ((Int?) -> Unit)? = null
) {

    dataList.let {

        if (it.isEmpty()) {
            this.visible(false)
            return
        }

        val hasTitle = selectTitle != null

        val list = mutableListOf<String>()

        if (hasTitle) list.add(selectTitle ?: "Select")

        for (i in it.indices) list.add(it[i].name)

        val aa: ArrayAdapter<String> =
            ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        adapter = aa
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {

                if (hasTitle) selectAction?.invoke(if (position == 0) null else position - 1)
                else selectAction?.invoke(position)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
}

internal fun Spinner.bindClientList(
    dataList: List<Client>,
    selectTitle: String? = null,
    selectAction: ((Int?) -> Unit)? = null
) {

    dataList.let {
        if (it.isEmpty()) {
            this.visible(false)
            return
        }

        val hasTitle = selectTitle != null

        val list = mutableListOf<String>()

        if (hasTitle) list.add(selectTitle ?: "Select")

        for (i in it.indices) list.add(it[i].name)

        val aa: ArrayAdapter<String> =
            ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        adapter = aa
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {

                if (hasTitle) selectAction?.invoke(if (position == 0) null else position - 1)
                else selectAction?.invoke(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
}

internal fun Spinner.bindProductList(
    dataList: List<Product>,
    selectTitle: String? = null,
    selectAction: ((Int?) -> Unit)? = null
) {

    dataList.let {
        if (it.isEmpty()) {
            this.visible(false)
            return
        }

        val hasTitle = selectTitle != null

        val list = mutableListOf<String>()

        if (hasTitle) list.add(selectTitle ?: "Select")

        for (i in it.indices) list.add(it[i].name)

        val aa: ArrayAdapter<String> =
            ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        adapter = aa
        onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: View?, position: Int, id: Long
            ) {

                if (hasTitle) selectAction?.invoke(if (position == 0) null else position - 1)
                else selectAction?.invoke(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

    }
}