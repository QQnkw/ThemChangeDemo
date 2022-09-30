package com.them.change

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.them.change.lib.base.BaseActivity
import com.them.change.lib.loader.SkinManager
import com.them.change.lib.util.AttrHelper
import java.io.File

class MainActivity : BaseActivity() {
    private val list: List<Pair<Int, Int>> = listOf(
        Pair(R.drawable.ic_1, R.string.text_1),
        Pair(R.drawable.ic_2, R.string.text_2),
        Pair(R.drawable.ic_3, R.string.text_3),
        Pair(R.drawable.ic_4, R.string.text_4),
        Pair(R.drawable.ic_5, R.string.text_5),
        Pair(R.drawable.ic_6, R.string.text_6),
        Pair(R.drawable.ic_7, R.string.text_7),
        Pair(R.drawable.ic_8, R.string.text_8),
        Pair(R.drawable.ic_9, R.string.text_9)
    )
    private var adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return object : RecyclerView.ViewHolder(view) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val pair = list[position]
            holder.itemView.findViewById<ImageView>(R.id.iv).setImageDrawable(AttrHelper.getExternalDrawable(this@MainActivity,pair.first))
            holder.itemView.findViewById<TextView>(R.id.tv).text =
                AttrHelper.getExternalString(this@MainActivity,pair.second)
        }

        override fun getItemCount(): Int {
            return list.size
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn1).setOnClickListener {
            if (SkinManager.getInstance().isExternalSkin) {
                SkinManager.getInstance().resetDefaultSkin()
            } else {
                val path = Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "ThemBlack.skin"
                SkinManager.getInstance().loadSkin(path,null)
            }
        }
        findViewById<Button>(R.id.btn2).setOnClickListener {
            if (SkinManager.getInstance().isExternalFont) {
                SkinManager.getInstance().resetDefaultFont()
            } else {
                val path = Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "msz_regular.ttf"
                SkinManager.getInstance().loadFont(path, null)
            }

        }

        findViewById<Button>(R.id.btn3).setOnClickListener {
            if (SkinManager.getInstance().isExternalLanguage) {
                SkinManager.getInstance().resetDefaultLanguage()
            } else {
                val path = Environment.getExternalStorageDirectory()
                    .toString() + File.separator + "ThemLanguage.skin"
                SkinManager.getInstance().loadLanguage(path,null)
            }
        }

        val recyclerView = findViewById<RecyclerView>(R.id.list_view)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    override fun onChangeLanguage() {
        super.onChangeLanguage()
        adapter.notifyItemRangeChanged(0, list.size)
    }

    override fun onChangeSkin() {
        super.onChangeSkin()
        adapter.notifyItemRangeChanged(0, list.size)
    }
}