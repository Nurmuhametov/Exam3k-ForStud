package ru.smak.graphics.windows

import ru.smak.graphics.components.GraphicsPanel
import ru.smak.graphics.convertation.CartesianScreenPlane
import ru.smak.graphics.painting.*
import java.awt.Dimension
import java.lang.Math.pow
import javax.swing.GroupLayout
import javax.swing.JFrame
import javax.swing.WindowConstants
import kotlin.math.sqrt

class MainWindow : JFrame("Экзамен: КТ, 3 курс") {

    /**
     * Блок оконных компонентов
     */
    private val mainPanel = GraphicsPanel()


    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        minimumSize = Dimension(480, 500)
        val gl = GroupLayout(contentPane)
        layout = gl
        gl.setVerticalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel, 450, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
        )
        gl.setHorizontalGroup(
            gl.createSequentialGroup()
                .addGap(4)
                .addComponent(mainPanel, 450, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                .addGap(4)
        )
        pack()
        val plane = CartesianScreenPlane(
            mainPanel.width,
            mainPanel.height,
            -4.5, 4.5,
            -4.5, 4.5
        )
        val cartesianP = CartesianPainter(plane)
        mainPanel.addPainter(cartesianP)
        val gridP = GridPainter(plane)
        mainPanel.addPainter(gridP, 0)
        /**
         * Добавление указателя в позицию мыши
         */
        val pointer = PanelPointer(plane)
        mainPanel.mousePressedListeners.add { e -> e?.let { pointer.set(e.point, mainPanel.graphics) } }
        mainPanel.mouseDraggedListeners.add { e -> e?.let { pointer.move(e.point, mainPanel.graphics) } }
        mainPanel.mouseReleasedListeners.add { e -> e?.let { pointer.remove(mainPanel.graphics) } }

        val func = {x:Double -> sqrt(16-x*x) }
        val pfunc: (Double)->Pair<Double, Double> = {t: Double -> Pair(pow(2.0,t-1),0.25*(t*t*t+1))}
        val fp = FunctionPainter(plane, func)
        val pfp = ParamFunctionPainter(plane,pfunc)
        mainPanel.addPainter(fp)
        mainPanel.addPainter(pfp)
    }

    fun makeVisible() {
        isVisible = true
        repaint()
    }

}