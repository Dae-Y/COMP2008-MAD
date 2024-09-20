package com.example.tute05kotlinrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tute05kotlinrecyclerview.databinding.ItemViewBinding

/* The adapter (ItemAdapter) is responsible for creating and binding viewHolder objects
 * to display a list of Item objects in a RecyclerView.
 * LayoutInflater is used to inflate the item layout for each ViewHolder.
 * ItemViewBinding allows for easy access to the views in the item layout without
 * needing findViewById.
 * onCreateViewHolder inflates the item layout, onBindViewHolder binds the data to the views,
 * and getItemCount returns the number of items in the list.
 */
class ItemAdapter(
    private val items: List<Item>,
    private val itemClickListener: (Item) -> Unit // Accept click listener
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    // Task 2: Modify the ItemViewHolder to Bind the Image
    class ItemViewHolder(private val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item, clickListener: (Item) -> Unit) {
            binding.tvTitle.text = item.title
            binding.tvDescription.text = item.description

            // Set the image resource
            binding.imageView.setImageResource(item.imageResId)

            // Set the click listener
            binding.root.setOnClickListener {
                clickListener(item)
            }
        }
    }
}
