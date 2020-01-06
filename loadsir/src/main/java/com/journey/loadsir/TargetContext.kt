package com.journey.loadsir

import android.content.Context
import android.view.View
import android.view.ViewGroup

/// 这个类中记录了Target对象所在父容器中的位置。
class TargetContext(
    val context: Context,//context 上下文
    val parentView: ViewGroup,//target对应的父容器
    internal val oldContent: View, //target转化后所对应的View , internal 在同一个模块(module)内可以访问
    internal val childIndex: Int//target转化后所对应View在父容器中的索引值
)
