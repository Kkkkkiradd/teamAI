package com.example.model.helper

import org.springframework.stereotype.Component

/**
 * @Author: chenyizong
 * @Date: 2021/5/16
 */
@Component
class Stringcyz {
  def main()
  {
    // Creating a set
    val s1 = Set("你好", "啊啊啊", "我是谁", "你真的傻逼", "一个人哈哈哈哈哈")
    val s2 = Set("nh","aaa","wss","nzdsb","ygrhhhhh")

    // Applying max method
    val result = s2.max

    // Display output
    println(result)
  }
}
