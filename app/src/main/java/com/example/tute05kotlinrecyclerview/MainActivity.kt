package com.example.tute05kotlinrecyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tute05kotlinrecyclerview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemAdapter
    private val items = mutableListOf<Item>() // Use MutableList for dynamic changes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize with 100 items
        for (i in 1..100) {
            items.add(Item("Title $i", "Description $i", R.drawable.pikachu001))
        }

        // Set up RecyclerView
        adapter = ItemAdapter(items) { item ->
            Toast.makeText(this, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
            // Show a Toast message with the item's title
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Set click listener for 'Add item' button
        binding.btnAddItem.setOnClickListener {
            addItem()
        }
    }

    // Function to add a new item to the list and update the RecyclerView
    private fun addItem() {
        val newItemIndex = items.size + 1
        val newItem = Item("Title $newItemIndex", "Description $newItemIndex", R.drawable.pikachu001)
        items.add(newItem) // Add the new item to the list

        // Notify the adapter and scroll to the newly added item
        adapter.notifyItemInserted(items.size - 1)
        // notifies the RecyclerView.Adapter that a new item has been inserted.

        binding.recyclerView.scrollToPosition(items.size - 1)
        // After adding a new item, the RecyclerView scrolls to the position
        // of the newly added item to make it visible.



        /* Task 2, added image
        val items = List(100) { index ->
            //  Need to pass the image resource IDs when creating the items in MainActivity.
            Item("Title ${index + 1}", "Description ${index + 1}", R.drawable.pikachu001)
        }

        // Set up RecyclerView with click listener
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemAdapter(items) { item ->
            // Show a Toast message with the item's title
            Toast.makeText(this, "Clicked: ${item.title}", Toast.LENGTH_SHORT).show()
        }
        */

    }
}
