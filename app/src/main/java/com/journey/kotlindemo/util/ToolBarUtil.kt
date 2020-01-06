package com.journey.kotlindemo.util

import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.journey.kotlindemo.R

/**
 * toolbar 工具
 */
class ToolBarUtil {

    companion object{
        val NORMAL_TYPE = 0
        val SEARCH_TYPE = 1

        fun getNormalTitle(toolbar: Toolbar): TextView{
            return toolbar.findViewById(R.id.tool_tv_title)
        }
    }

    class Builder(val toolbar: Toolbar) {
        private var params: Params = Params()

        fun setType(type: Int): Builder {
            params.type = type
            return this
        }

        fun setBackListener(backListener: (view: View) -> Unit): Builder {
            params.backListener = backListener
            return this
        }

        fun setBackRes(backRes: Int): Builder {
            params.backRes = backRes
            return this
        }

        fun setCloseListener(closeListener: (view: View) -> Unit): Builder {
            params.closeListener = closeListener
            return this
        }

        fun setCloseRes(closeRes: Int): Builder {
            params.closeRes = closeRes
            return this
        }

        fun setNormalTitleRes(normalTitleRes: Int): Builder {
            params.normalTitleRes = normalTitleRes
            return this
        }

        fun setNormalTitle(normalTitle: String): Builder {
            params.normalTitle = normalTitle
            return this
        }

        fun setNormalTitleColor(normalTitleColor: Int): Builder {
            params.normalTitleColor = normalTitleColor
            return this
        }

        fun setNormalTitleSize(normalTitleSize: Int): Builder {
            params.normalTitleSize = normalTitleSize
            return this
        }

        // search
        fun setSearchBgListener(searchBgListener: (view: View) -> Unit): Builder {
            params.searchBgListener = searchBgListener
            return this
        }

        fun setSearchBgRes(searchBgRes: Int): Builder {
            params.searchBgRes = searchBgRes
            return this
        }

        fun setSearchLogoListener(searchLogoListener: (view: View) -> Unit): Builder {
            params.searchLogoListener = searchLogoListener
            return this
        }

        fun setSearchLogoRes(searchLogoRes: Int): Builder {
            params.searchLogoRes = searchLogoRes
            return this
        }

        fun setSearchRes(searchRes: Int): Builder {
            params.searchRes = searchRes
            return this
        }

        fun setSearchListener(searchListener: (view: View) -> Unit): Builder {
            params.searchListener = searchListener
            return this
        }

        fun setSearchTitleRes(searchTitleRes: Int): Builder {
            params.searchTitleRes = searchTitleRes
            return this
        }

        fun setSearchTitle(searchTitle: String): Builder {
            params.searchTitle = searchTitle
            return this
        }

        fun setSearchTitleColor(searchTitleColor: Int): Builder {
            params.searchTitleColor = searchTitleColor
            return this
        }

        fun setSearchTitleSize(searchTitleSize: Int): Builder {
            params.searchTitleSize = searchTitleSize
            return this
        }

        fun setSearchTitleListener(searchTitleListener: (view: View) -> Unit): Builder {
            params.searchTitleListener = searchTitleListener
            return this
        }

        fun builder(): Toolbar {
            val toolIvBack = toolbar.findViewById<ImageView>(R.id.tool_iv_back)
            val toolTvTitle = toolbar.findViewById<TextView>(R.id.tool_tv_title)
            val toolIvClose = toolbar.findViewById<ImageView>(R.id.tool_iv_close)
            val toolIvMore = toolbar.findViewById<ImageView>(R.id.tool_iv_more)

            val toolVSearch = toolbar.findViewById<View>(R.id.tool_v_search)
            val toolIvLogo = toolbar.findViewById<ImageView>(R.id.tool_iv_logo)
            val toolIvSearch = toolbar.findViewById<ImageView>(R.id.tool_iv_search)
            val toolTvSearchTitle = toolbar.findViewById<TextView>(R.id.tool_tv_search_title)

            if (params.type == SEARCH_TYPE) {
                toolIvBack.visibility = View.GONE
                toolTvTitle.visibility = View.GONE
                toolIvClose.visibility = View.GONE
                toolIvMore.visibility = View.GONE

                toolVSearch.visibility = View.VISIBLE
                toolIvLogo.visibility = View.VISIBLE
                toolIvSearch.visibility = View.VISIBLE
                toolTvSearchTitle.visibility = View.VISIBLE

                setSearchLayout(toolbar, params)
            } else {
                toolVSearch.visibility = View.GONE
                toolIvLogo.visibility = View.GONE
                toolIvSearch.visibility = View.GONE
                toolTvSearchTitle.visibility = View.GONE
                setNormalLayout(toolbar, params)
            }
            return toolbar
        }


        private fun setNormalLayout(view: View, params: Params) {
            val toolIvBack = view.findViewById<ImageView>(R.id.tool_iv_back)
            val toolTvTitle = view.findViewById<TextView>(R.id.tool_tv_title)
            val toolIvClose = view.findViewById<ImageView>(R.id.tool_iv_close)
            val toolIvMore = view.findViewById<ImageView>(R.id.tool_iv_more)

            params.backRes?.let {
                toolIvBack.visibility = View.VISIBLE
                toolIvBack.setImageResource(it)
            }

            params.backListener?.let { listener ->
                toolIvBack.visibility = View.VISIBLE
                toolIvBack.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.closeRes?.let {
                toolIvClose.visibility = View.VISIBLE
                toolIvClose.setImageResource(it)
            }

            params.closeListener?.let { listener ->
                toolIvClose.visibility = View.VISIBLE
                toolIvClose.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.normalTitleRes?.let {
                toolTvTitle.visibility = View.VISIBLE
                toolTvTitle.setText(it)
            }

            params.normalTitle?.let {
                toolTvTitle.visibility = View.VISIBLE
                toolTvTitle.text = it
            }

            params.normalTitleColor?.let {
                toolTvTitle.visibility = View.VISIBLE
                toolTvTitle.setTextColor(it)
            }

            params.normalTitleSize?.let {
                toolTvTitle.visibility = View.VISIBLE
                toolTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.toFloat())
            }
        }

        private fun setSearchLayout(view: View, params: Params) {
            val toolVSearch = view.findViewById<View>(R.id.tool_v_search)
            val toolIvLogo = view.findViewById<ImageView>(R.id.tool_iv_logo)
            val toolIvSearch = view.findViewById<ImageView>(R.id.tool_iv_search)
            val toolTvSearchTitle = view.findViewById<TextView>(R.id.tool_tv_search_title)

            params.searchBgRes?.let {
                toolVSearch.visibility = View.VISIBLE
                toolVSearch.setBackgroundResource(it)
            }

            params.searchBgListener?.let { listener ->
                toolVSearch.visibility = View.VISIBLE
                toolVSearch.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.searchLogoRes?.let {
                toolIvLogo.visibility = View.VISIBLE
                toolIvLogo.setImageResource(it)
            }

            params.searchLogoListener?.let { listener ->
                toolIvLogo.visibility = View.VISIBLE
                toolIvLogo.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.searchRes?.let {
                toolIvSearch.visibility = View.VISIBLE
                toolIvSearch.setImageResource(it)
            }

            params.searchListener?.let { listener ->
                toolIvSearch.visibility = View.VISIBLE
                toolIvSearch.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.searchTitleRes?.let {
                toolTvSearchTitle.visibility = View.VISIBLE
                toolTvSearchTitle.setText(it)
            }

            params.searchTitle?.let {
                toolTvSearchTitle.visibility = View.VISIBLE
                toolTvSearchTitle.text = it
            }

            params.searchTitleListener?.let { listener ->
                toolTvSearchTitle.visibility = View.VISIBLE
                toolTvSearchTitle.setOnClickListener {
                    listener.invoke(it)
                }
            }

            params.searchTitleColor?.let {
                toolTvSearchTitle.visibility = View.VISIBLE
                toolTvSearchTitle.setTextColor(it)
            }

            params.searchTitleSize?.let {
                toolTvSearchTitle.visibility = View.VISIBLE
                toolTvSearchTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, it.toFloat())
            }
        }
    }


    private class Params {
        var type = NORMAL_TYPE
        var backRes: Int? = null
        var backListener: ((View) -> Unit?)? = null
        var closeListener: ((View) -> Unit?)? = null
        var closeRes: Int? = null
        var normalTitleRes: Int? = null
        var normalTitle: String? = null
        var normalTitleColor: Int? = null
        var normalTitleSize: Int? = null

        var searchBgRes: Int? = null
        var searchBgListener: ((View) -> Unit?)? = null
        var searchLogoRes: Int? = null
        var searchLogoListener: ((View) -> Unit?)? = null
        var searchRes: Int? = null
        var searchListener: ((View) -> Unit?)? = null
        var searchTitleRes: Int? = null
        var searchTitle: String? = null
        var searchTitleColor: Int? = null
        var searchTitleSize: Int? = null
        var searchTitleListener: ((View) -> Unit?)? = null
    }
}