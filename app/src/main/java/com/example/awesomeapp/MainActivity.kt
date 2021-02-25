package com.example.awesomeapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.awesomeapp.adapter.ItemAdapter
import com.example.awesomeapp.adapter.ItemAdapter.Companion.SPAN_COUNT_ONE
import com.example.awesomeapp.adapter.ItemAdapter.Companion.SPAN_COUNT_TWO
import com.example.awesomeapp.model.Item
import com.google.android.material.appbar.MaterialToolbar
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MainActivity : AppCompatActivity() {
    private var recyclerView: RecyclerView? = null
    private var itemAdapter: ItemAdapter? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var items: MutableList<Item>? = null
    var mToolbar: MaterialToolbar? = null
    var mTvError: TextView? = null
    private var pageNumber = 1
    private var isScrolling = false
    private var totalItems = 0
    private var scrollOutItems = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mToolbar = findViewById(R.id.toolbar)
        mTvError = findViewById(R.id.tv_error)
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        setupToolbar()
        setAdapter()
        fetchItem()
    }

    protected fun setupToolbar() {
        setSupportActionBar(mToolbar)
        val mActionBar = this.supportActionBar!!
        mToolbar!!.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        mActionBar.title = "Awesome App"
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
        items = ArrayList()
        gridLayoutManager = GridLayoutManager(this, SPAN_COUNT_ONE)
        itemAdapter = ItemAdapter(items as ArrayList<Item>, this, gridLayoutManager!!)
        recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView
        recyclerView!!.adapter = itemAdapter
        recyclerView!!.layoutManager = gridLayoutManager
        itemAdapter!!.itemOnClick(object : ItemAdapter.ItemOnClickListener {
            override fun onClick(pos: Int, item: Item?) {
                val intent = Intent(this@MainActivity, DetailImageActivity::class.java)
                val bundle = Bundle()
                bundle.putString("img", item?.original)
                bundle.putString("title", item?.photographer)
                bundle.putString("url", item?.photographerUrl)
                intent.putExtras(bundle)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
            }
        })
        recyclerView!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItems = gridLayoutManager!!.itemCount
                scrollOutItems = gridLayoutManager!!.findLastVisibleItemPosition()
                if (scrollOutItems > totalItems - 2 && dy > 0) {
                    isScrolling = false
                    fetchItem()
                }
            }
        })
    }

    private fun fetchItem() {
        val request: StringRequest = object : StringRequest(
            Method.GET,
            "https://api.pexels.com/v1/curated/?page=$pageNumber&per_page=10",
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("photos")
                    val length = jsonArray.length()
                    for (i in 0 until length) {
                        val `object` = jsonArray.getJSONObject(i)
                        val id = `object`.getInt("id")
                        val width = `object`.getInt("width")
                        val height = `object`.getInt("height")
                        val url = `object`.getString("url")
                        val photographer = `object`.getString("photographer")
                        val photographerUrl = `object`.getString("photographer_url")
                        val photographerId = `object`.getInt("photographer_id")
                        val avgColor = `object`.getString("avg_color")
                        val objectImages = `object`.getJSONObject("src")
                        val original = objectImages.getString("original")
                        val large2x = objectImages.getString("large2x")
                        val large = objectImages.getString("large")
                        val medium = objectImages.getString("medium")
                        val small = objectImages.getString("small")
                        val portrait = objectImages.getString("portrait")
                        val landscape = objectImages.getString("landscape")
                        val tiny = objectImages.getString("tiny")
                        val item = Item(
                            id,
                            width,
                            height,
                            url,
                            photographer,
                            photographerUrl,
                            photographerId,
                            avgColor,
                            original,
                            large2x,
                            large,
                            medium,
                            small,
                            portrait,
                            landscape,
                            tiny
                        )
                        items!!.add(item)
                    }
                    itemAdapter!!.notifyDataSetChanged()
                    pageNumber++
                    mTvError!!.visibility = View.GONE
                } catch (e: JSONException) {
                    mTvError!!.visibility = View.VISIBLE
                }
            }, Response.ErrorListener { }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Authorization"] = "Apikey"
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(request)
    }
}