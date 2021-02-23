package com.example.awesomeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.awesomeapp.adapter.ItemAdapter
import com.example.awesomeapp.adapter.ItemAdapter.Companion.SPAN_COUNT_ONE
import com.example.awesomeapp.adapter.ItemAdapter.Companion.SPAN_COUNT_TWO
import com.example.awesomeapp.model.Item
import com.google.android.material.appbar.MaterialToolbar
import java.util.*

open class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var itemAdapter: ItemAdapter? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var items: MutableList<*>? = null
    var mToolbar: MaterialToolbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mToolbar = findViewById(R.id.toolbar)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setupToolbar()
        initItemsData()
        setAdapter()
    }

    private fun setupToolbar() {
        setSupportActionBar(mToolbar)
        val mActionBar = this.supportActionBar!!
        mToolbar!!.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        mActionBar.title = "Nature"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_list -> {
                gridLayoutManager!!.spanCount = SPAN_COUNT_ONE
                itemAdapter!!.notifyItemRangeChanged(0, itemAdapter!!.itemCount)
                true
            }
            R.id.action_grid -> {
                gridLayoutManager!!.spanCount = SPAN_COUNT_TWO
                itemAdapter!!.notifyItemRangeChanged(0, itemAdapter!!.itemCount)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setAdapter() {
        gridLayoutManager = GridLayoutManager(this, SPAN_COUNT_ONE)
        itemAdapter = ItemAdapter(items!!, gridLayoutManager!!)
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView!!.adapter = itemAdapter
        recyclerView!!.layoutManager = gridLayoutManager
        itemAdapter!!.itemClick(object : ItemAdapter.ItemListener {
            override fun onClick(position: Int, item: Item) {
                val intent = Intent(this@MainActivity, DetailImageActivity::class.java)
                val bundle = Bundle()
                bundle.putString("title", item.title)
                bundle.putString("desc", item.description)
                bundle.putInt("img", item.imgResId)
                intent.putExtras(bundle)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
            }
        })
    }

    private fun initItemsData() {
        items = ArrayList<Any>()
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.bird,
                "Bird",
                "This picture was taken in Punta del Este, Uruguay"
            )
        )
        (items as ArrayList<Any>).add(Item(R.drawable.bird1, "Bird 1", "Preety little owl"))
        (items as ArrayList<Any>).add(Item(R.drawable.bird2, "Bird 2", "Owl serious mode on"))
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.butterfly,
                "Butterfly",
                "Very Beautifull Butterfly"
            )
        )
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.cactus,
                "Cactus",
                "A cactus is a member of the plant family Cactaceae"
            )
        )
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.fish,
                "Fish",
                "This picture was taken in Indonesia"
            )
        )
        (items as ArrayList<Any>).add(Item(R.drawable.flower, "Flower", "The Red Flower"))
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.flower1,
                "Flower 1",
                "Beautifull Flower in London, United Kingdom"
            )
        )
        (items as ArrayList<Any>).add(Item(R.drawable.flower2, "Flower 2", "Calm flower"))
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.flower3,
                "Flower 3",
                "Beautifull pinky flower"
            )
        )
        (items as ArrayList<Any>).add(Item(R.drawable.lake, "Lake", "Lake create the reflection"))
        (items as ArrayList<Any>).add(Item(R.drawable.leaf, "Leaf", "Green in the sky"))
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.leaf1,
                "Leaf 1",
                "In the morning with breezy air"
            )
        )
        (items as ArrayList<Any>).add(Item(R.drawable.leaf2, "Leaf 2", "Leaf with darkness"))
        (items as ArrayList<Any>).add(Item(R.drawable.leaf3, "Leaf 3", "Mexican leaf"))
        (items as ArrayList<Any>).add(Item(R.drawable.moon, "Moon", "Reaching the moon"))
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.mountain,
                "Mountain",
                "Great mount with lake"
            )
        )
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.mountain1,
                "Mountain 1",
                "the blue dew faded at the horizon"
            )
        )
        (items as ArrayList<Any>).add(Item(R.drawable.mountain2, "Mountain 2", "cheerful camper"))
        (items as ArrayList<Any>).add(
            Item(
                R.drawable.mountain3,
                "Mountain 3",
                "United States Great Mount"
            )
        )
        (items as ArrayList<Any>).add(Item(R.drawable.mushroom, "Moshroom", "Li'l Mushroom"))
        (items as ArrayList<Any>).add(Item(R.drawable.ocean, "Ocean", "Oceanic animal"))
        (items as ArrayList<Any>).add(Item(R.drawable.rainbow, "Rainbow", "Happy rainbow"))
        (items as ArrayList<Any>).add(Item(R.drawable.tree, "Tree", "Holaa Beach ..."))
        (items as ArrayList<Any>).add(Item(R.drawable.waterfall, "Waterfall", "Amazing waterfall"))
        (items as ArrayList<Any>).add(Item(R.drawable.wave, "Wave", "Wave the ocean"))
        (items as ArrayList<Any>).add(Item(R.drawable.wave1, "Wave 1", "Hi beautifull beach"))
        (items as ArrayList<Any>).add(Item(R.drawable.wave2, "Wave 2", "Lets Surf!"))
    }
}