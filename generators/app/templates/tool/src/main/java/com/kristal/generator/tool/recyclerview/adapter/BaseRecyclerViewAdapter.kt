package <%= appPackage %>.tool.recyclerview.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import <%= appPackage %>.tool.recyclerview.scrollListener.SimpleLoadMore

/**
 * Created by Kristal on 6/14/2017.
 */

abstract class BaseRecyclerViewAdapter(
        private val recyclerView: RecyclerView
) : RecyclerView.Adapter<RecyclerViewHolder>() {
    private val itemLayouts = ArrayList<BaseItemLayout<*>>()

    var data: List<RecyclerViewData>? = null
        set(value) {
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
                override fun getOldListSize(): Int = field?.size ?: 0
                override fun getNewListSize(): Int = value?.size ?: 0

                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
                        = field != null &&
                        value != null &&
                        field!![oldItemPosition].isSameId(value[newItemPosition])

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
                        = field != null &&
                        value != null &&
                        field!![oldItemPosition].isSameData(value[newItemPosition])
            })

            field = value
            result.dispatchUpdatesTo(this)
        }

    override fun getItemViewType(position: Int): Int {
        val name = data!![position]::class.java.name
        return (0 until itemLayouts.size)
                .firstOrNull { itemLayouts[it].id == name }
                ?: -1
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerViewHolder {
        val itemLayout = itemLayouts[viewType]
        val view = LayoutInflater.from(parent!!.context).inflate(itemLayout.layoutId, parent, false)
        val holder = RecyclerViewHolder(view)
        itemLayout.onCreate(holder)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder?, position: Int) {
        val viewType = getItemViewType(position)
        val itemLayout = itemLayouts[viewType]
        itemLayout.bind?.invoke(holder!!, data!![position])
    }

    override fun getItemCount(): Int = data?.size ?: 0

    fun <DATA : RecyclerViewData> register(itemLayout: BaseItemLayout<DATA>) {
        itemLayouts.add(itemLayout)
    }

    fun verticalGridLayoutManager(count: Int) {
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, count)
    }

    fun horizontalGridLayoutManager(count: Int) {
        recyclerView.layoutManager = GridLayoutManager(recyclerView.context, count, GridLayoutManager.HORIZONTAL, false)
    }

    fun verticalLinearLayoutManager() {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
    }

    fun horizontalLinearLayoutManager() {
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context, GridLayoutManager.HORIZONTAL, false)
    }

    fun addDecoration(decoration: RecyclerView.ItemDecoration) {
        recyclerView.addItemDecoration(decoration)
    }

    fun loadMore(loadMore: (() -> Unit)) {
        recyclerView.addOnScrollListener(SimpleLoadMore(loadMore))
    }

    fun linearSnap() {
        LinearSnapHelper().attachToRecyclerView(recyclerView)
    }

    fun apply() {
        recyclerView.adapter = this
    }
}