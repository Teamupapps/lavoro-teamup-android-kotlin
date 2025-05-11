package lavoro.teamup.core.view

import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import lavoro.teamup.data.model.client.Client
import lavoro.teamup.data.model.product.Product


class AutoTextTitle(val title: String) {
    override fun toString(): String {
        return title
    }
}

internal fun AutoCompleteTextView.bindClientCity(
    list: List<Client>,
    action: ((Int) -> Unit)? = null
) {
    list.let {

        if (it.isEmpty()) return

        val clientList = mutableListOf<AutoTextTitle>()

        for (i in it.indices) {
            clientList.add(AutoTextTitle("${it[i].name}\\${it[i].cityEntry.name}"))
        }

        val adapter: ArrayAdapter<AutoTextTitle> = ArrayAdapter<AutoTextTitle>(
            context, android.R.layout.simple_dropdown_item_1line, clientList
        )

        this@bindClientCity.setAdapter(adapter)

        onItemClickListener =
            OnItemClickListener { arg0, arg1, arg2, arg3 ->
                action?.invoke(arg2)
            }
    }
}

internal fun AutoCompleteTextView.bindStockProduct(
    list: List<Product>,
    action: ((Int) -> Unit)? = null
) {
    list.let {

        if (it.isEmpty()) return

        val productList = mutableListOf<AutoTextTitle>()

        for (i in it.indices) {
            productList.add(AutoTextTitle("${it[i].name}\\${it[i].brandEntry.name}"))
        }

        val adapter: ArrayAdapter<AutoTextTitle> = ArrayAdapter<AutoTextTitle>(
            context, android.R.layout.simple_dropdown_item_1line, productList
        )

        this@bindStockProduct.setAdapter(adapter)

        onItemClickListener =
            OnItemClickListener { arg0, arg1, arg2, arg3 ->
                action?.invoke(arg2)
            }
    }
}




