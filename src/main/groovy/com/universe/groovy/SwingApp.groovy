package com.universe.groovy;

import javax.swing.*
import java.awt.*

class SwingApp extends JFrame {
  SwingApp() {
    // 设置标题
    title = "Swing Application"

    // 设置窗口大小
    size = new Dimension(400, 300)

    // 设置窗口是否可见
    visible = true

    // 关闭窗口的操作
    defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    // 添加组件到面板
    addComponents()
  }

  void addComponents() {
    // 创建面板
    JPanel panel = new JPanel()

    // 创建标签
    JLabel label = new JLabel("Hello World!")

    // 将标签添加到面板
    panel.add(label)

    // 将面板添加到窗口
    add(panel)
  }

  static void main(String[] args) {
    new SwingApp()
  }
}
